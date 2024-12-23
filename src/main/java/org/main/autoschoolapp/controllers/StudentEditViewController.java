package org.main.autoschoolapp.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.main.autoschoolapp.model.*;
import org.main.autoschoolapp.service.*;
import org.main.autoschoolapp.util.Manager;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.main.autoschoolapp.util.Manager.MessageBox;


public class StudentEditViewController implements Initializable {

    public TextField TextFieldDiscountAmountMax;
    public TextField TextFieldMaxPaymentAmount;

    boolean imageLoaded = false;

    @FXML
    private Button BtnCancel;
    @FXML
    private Button BtnLoadImage;
    @FXML
    private Button BtnSave;

    private final GroupCategoryService groupCategoryService = new GroupCategoryService();
    private final InstructorService instructorService = new InstructorService();
    private final LicenseTypeService licenseTypeService = new LicenseTypeService();
    private final VehicleService vehicleService = new VehicleService();


    private StudentService studentService = new StudentService();

    @FXML
    private ComboBox<GroupCategory> ComboBoxGroupCategory;
    @FXML
    private ComboBox<Instructor> ComboBoxInstructor;
    @FXML
    private ComboBox<LicenseType> ComboBoxLicenseType;
    @FXML
    private ComboBox<Vehicle> ComboBoxVehicle;

    @FXML
    private ImageView ImageViewPhoto;
    @FXML
    private TextArea TextAreaNote;

    @FXML
    private TextField TextFieldIDStudent;
    @FXML
    private TextField TextFieldAge;
    @FXML
    private TextField TextFieldAttendanceRate;
    @FXML
    private TextField TextFieldPaidAmount;

    @FXML
    private TextField TextFieldName;

    public void BtnLoadImageAction(ActionEvent actionEvent) throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );
        Stage stage = (Stage) BtnLoadImage.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            String imageUrl = file.toURI().toURL().toExternalForm();
            ImageViewPhoto.setImage(new Image(imageUrl));
            imageLoaded = true;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ComboBoxGroupCategory.setItems(FXCollections.observableArrayList(groupCategoryService.findAll()));
        ComboBoxLicenseType.setItems(FXCollections.observableArrayList(licenseTypeService.findAll()));
        ComboBoxInstructor.setItems(FXCollections.observableArrayList(instructorService.findAll()));
        ComboBoxVehicle.setItems(FXCollections.observableArrayList(vehicleService.findAll()));


        if (Manager.currentStudent != null) {
            TextFieldIDStudent.setEditable(false);
            TextFieldIDStudent.setText(Manager.currentStudent.getStudentId());
            TextFieldName.setText(Manager.currentStudent.getName());
            TextAreaNote.setText(Manager.currentStudent.getNotes());
            TextFieldMaxPaymentAmount.setText(String.format("%.2f", (double) Manager.currentStudent.getAge()));

            // Здесь выводим посещаемость в TextField
            TextFieldAge.setText(Manager.currentStudent.getAge().toString());
            TextFieldAttendanceRate.setText(Manager.currentStudent.getAttendanceRate().toString());

            TextFieldPaidAmount.setText(Manager.currentStudent.getPaidAmount().toString());
            TextFieldMaxPaymentAmount.setText(Manager.currentStudent.getMaxPaymentAmount().toString());
            TextFieldDiscountAmountMax.setText(Manager.currentStudent.getPaidAmount().toString());
            try {
                ImageViewPhoto.setImage(Manager.currentStudent.getPhoto());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ComboBoxGroupCategory.setValue(Manager.currentStudent.getGroupCategory());
            ComboBoxLicenseType.setValue(Manager.currentStudent.getLicenseType());
            ComboBoxInstructor.setValue(Manager.currentStudent.getInstructor());
            ComboBoxVehicle.setValue(Manager.currentStudent.getVehicle());
        } else {
            Manager.currentStudent = new Student();
        }
    }


    @FXML
    void BtnSaveAction(ActionEvent event) throws IOException {
        // Проверка обязательных полей
        StringBuilder validationResult = checkFields();
        if (validationResult.length() > 0) {
            MessageBox("Ошибка", "Пожалуйста, исправьте следующие ошибки:\n" + validationResult.toString(), "Ошибка", Alert.AlertType.ERROR);
            return;
        }

        // Устанавливаем значения для студента
        Manager.currentStudent.setName(TextFieldName.getText());
        Manager.currentStudent.setGroupCategory(ComboBoxGroupCategory.getValue());
        Manager.currentStudent.setLicenseType(ComboBoxLicenseType.getValue());
        Manager.currentStudent.setVehicle(ComboBoxVehicle.getValue());
        Manager.currentStudent.setInstructor(ComboBoxInstructor.getValue());
        Manager.currentStudent.setNotes(TextAreaNote.getText());

        // Преобразуем и устанавливаем возраст
        try {
            int age = Integer.parseInt(TextFieldAge.getText());
            Manager.currentStudent.setAge(age);
        } catch (NumberFormatException e) {
            MessageBox("Ошибка", "Неверно указан возраст", "Ошибка", Alert.AlertType.ERROR);
            return;
        }

        // Преобразуем и устанавливаем числовые значения для PaidAmount и MaxPaymentAmount
        try {
            int paidAmount = Integer.parseInt(TextFieldDiscountAmountMax.getText());
            Manager.currentStudent.setPaidAmount(paidAmount);
        } catch (NumberFormatException e) {
            MessageBox("Ошибка", "Неверно указана сумма оплаты", "Ошибка", Alert.AlertType.ERROR);
            return;
        }

        try {
            double maxPaymentAmount = Double.parseDouble(TextFieldMaxPaymentAmount.getText());
            Manager.currentStudent.setMaxPaymentAmount(maxPaymentAmount);  // Corrected method
        } catch (NumberFormatException e) {
            MessageBox("Ошибка", "Неверно указана максимальная сумма оплаты", "Ошибка", Alert.AlertType.ERROR);
            return;
        }


        try {
            int attendanceRate = Integer.parseInt(TextFieldAttendanceRate.getText());
            Manager.currentStudent.setAttendanceRate(attendanceRate);
        } catch (NumberFormatException e) {
            MessageBox("Ошибка", "Неверно указана посещаемость", "Ошибка", Alert.AlertType.ERROR);
            return;
        }

        // Устанавливаем изображение, если оно было загружено
        if (imageLoaded) {
            Manager.currentStudent.setPhoto(ImageViewPhoto.getImage());
        }

        // Сохраняем или обновляем данные студента
        if (Manager.currentStudent.getStudentId() == null || Manager.currentStudent.getStudentId().isEmpty()) {
            Manager.currentStudent.setStudentId(TextFieldIDStudent.getText());
            studentService.save(Manager.currentStudent);
            MessageBox("Информация", "", "Данные сохранены успешно", Alert.AlertType.INFORMATION);
        } else {
            studentService.update(Manager.currentStudent);
            MessageBox("Информация", "", "Данные обновлены успешно", Alert.AlertType.INFORMATION);
        }
    }






    StringBuilder checkFields() {
        StringBuilder error = new StringBuilder();
        if (TextFieldIDStudent.getText().isEmpty()) {
            error.append("Укажите ID cтудента\n");
        }
        if (TextFieldName.getText().isEmpty()) {
            error.append("Укажите имя студента\n");
        }
        if (TextFieldAge.getText().isEmpty()) {
            error.append("Укажите возраст\n");
        }
        if (ComboBoxGroupCategory.getValue() == null) {
            error.append("Выберите категорию\n");
        }
        if (ComboBoxInstructor.getValue() == null) {
            error.append("Выберите инструктора\n");
        }
        if (ComboBoxLicenseType.getValue() == null) {
            error.append("Выберите лицензию\n");
        }
        if (ComboBoxVehicle.getValue() == null) {
            error.append("Выберите автомобиль\n");
        }



        return error;
    }


    public void BtnCancelAction(ActionEvent actionEvent) {
        Stage stage = (Stage) BtnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public void TextFieldName(ActionEvent actionEvent) {
        // Implement text field name action
    }

    public void TextAreaDescription(MouseEvent mouseEvent) {
        // Implement text area description action
    }

    public void TextFieldMaxPayment(ActionEvent actionEvent) {
        // Implement max payment field action
    }

    public void ComboBoxCategory(ActionEvent actionEvent) {
        // Implement category combo box action
    }

    public void ComboBoxAttendanceRate(ActionEvent actionEvent) {
        // Implement attendance rate combo box action
    }

    public void ComboBoxInstructor(ActionEvent actionEvent) {
        // Implement instructor combo box action
    }

    public void ComboBoxVehicle(ActionEvent actionEvent) {
        // Implement vehicle combo box action
    }



    public void TextFieldAge(ActionEvent actionEvent) {
        // Implement age field action
    }

    public void TextFieldDiscountAmountMax(ActionEvent actionEvent) {
        // Implement discount amount max field action
    }

    public void TextFieldPaidAmount(ActionEvent actionEvent) {
        // Implement paid amount field action
    }


    public void ComboBoxLicenseType(ActionEvent actionEvent) {
    }



    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    public void TextFieldIDStudent(ActionEvent actionEvent) {
    }

    public void TextFieldAttendanceRate(ActionEvent actionEvent) {
    }
}
