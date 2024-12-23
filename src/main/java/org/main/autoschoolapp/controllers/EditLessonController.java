package org.main.autoschoolapp.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.main.autoschoolapp.model.Lesson;
import org.main.autoschoolapp.model.ClassRoom;
import org.main.autoschoolapp.model.Status;
import org.main.autoschoolapp.service.ClassRoomService;
import org.main.autoschoolapp.service.LessonService;
import org.main.autoschoolapp.service.StatusService;

public class EditLessonController {

    @FXML
    private TextField verificationCodeField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ComboBox<ClassRoom> classRoomComboBox;

    @FXML
    private ComboBox<Status> statusComboBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private Lesson lesson;
    private LessonService lessonService;
    private Stage dialogStage;
    private final ClassRoomService classRoomService = new ClassRoomService();
    private final StatusService statusService = new StatusService();

    public EditLessonController() {
        lessonService = new LessonService();
    }



    public void initialize() {
        if (lesson == null) {
            lesson = new Lesson();  // Инициализируем объект, если он ещё не был передан
        }
        // Заполнение ComboBox для аудиторий
        ObservableList<ClassRoom> classRooms = FXCollections.observableArrayList(classRoomService.findAll());
        classRoomComboBox.setItems(classRooms);

        // Заполнение ComboBox для статусов
        ObservableList<Status> statuses = FXCollections.observableArrayList(statusService.findAll());
        statusComboBox.setItems(statuses);

        // Убедитесь, что ComboBox использует правильный отображаемый текст для объектов ClassRoom
        classRoomComboBox.setCellFactory(param -> new ListCell<ClassRoom>() {
            @Override
            protected void updateItem(ClassRoom item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getTitle()); // Метод getTitle() возвращает название аудитории
                }
            }
        });
        classRoomComboBox.setButtonCell(classRoomComboBox.getCellFactory().call(null));

        // Привязка обработчиков событий для кнопок
        saveButton.setOnAction(event -> handleSave());
        cancelButton.setOnAction(event -> handleCancel());
    }



    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;

        if (lesson != null) {
            verificationCodeField.setText(String.valueOf(lesson.getVerificationCode()));
            startDatePicker.setValue(lesson.getStartDate());
            endDatePicker.setValue(lesson.getEndDate());
            classRoomComboBox.setValue(lesson.getClassRoom());
            statusComboBox.setValue(lesson.getStatus());
        }
    }

    private void handleSave() {
        if (lesson != null) {
            // Устанавливаем значение verificationCode из TextField
            if (isInputValid()) {
                lesson.setVerificationCode(Integer.parseInt(verificationCodeField.getText()));
                lesson.setStartDate(startDatePicker.getValue());
                lesson.setEndDate(endDatePicker.getValue());
                lesson.setClassRoom(classRoomComboBox.getValue());
                lesson.setStatus(statusComboBox.getValue());

                // Сохраняем объект lesson через сервис
                lessonService.save(lesson);


            } else {
                showErrorDialog("Ошибка ввода", "Пожалуйста, проверьте введенные данные.");
            }
        } else {
            // Если lesson == null, обработайте этот случай
            System.out.println("Ошибка: объект lesson не инициализирован.");
        }
    }


    private void handleCancel() {

    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (verificationCodeField.getText() == null || verificationCodeField.getText().trim().isEmpty()) {
            errorMessage += "Код подтверждения не может быть пустым!\n";
        } else {
            try {
                Integer.parseInt(verificationCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Код подтверждения должен быть числом!\n";
            }
        }

        if (startDatePicker.getValue() == null) {
            errorMessage += "Дата начала занятия должна быть указана!\n";
        }

        if (endDatePicker.getValue() == null) {
            errorMessage += "Дата окончания занятия должна быть указана!\n";
        }

        if (classRoomComboBox.getValue() == null) {
            errorMessage += "Аудитория должна быть выбрана!\n";
        }

        if (statusComboBox.getValue() == null) {
            errorMessage += "Статус должен быть выбран!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showErrorDialog("Неверный ввод данных", errorMessage);
            return false;
        }
    }

    private void showErrorDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
