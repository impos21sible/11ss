package org.main.autoschoolapp.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.main.autoschoolapp.AutoschoolApp;
import org.main.autoschoolapp.model.GroupCategory;
import org.main.autoschoolapp.model.Student;
import org.main.autoschoolapp.service.GroupCategoryService;
import org.main.autoschoolapp.service.StudentService;
import org.main.autoschoolapp.util.Manager;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static org.main.autoschoolapp.util.Manager.ShowConfirmPopup;
import static org.main.autoschoolapp.util.Manager.ShowErrorMessageBox;


public class StudentTableViewController implements Initializable {
    private final GroupCategoryService categoryService = new GroupCategoryService();
    private final StudentService studentService = new StudentService();
    @FXML
    private ComboBox<String> ComboBoxPaidAmount;

    @FXML
    private ComboBox<GroupCategory> ComboBoxStudentType;

    @FXML
    private ComboBox<String> ComboBoxSort;
    @FXML
    private Button BtnBack;

    @FXML
    private TableColumn<Student, ImageView> TableColumnPhoto;

    @FXML
    private TableColumn<Student, Integer> TableColumnAttendanceRate;

    @FXML
    private TableColumn<Student, Integer> TableColumnPaidAmount;

    @FXML
    private TableColumn<Student, String> TableColumnAge;

    @FXML
    private TableColumn<Student, String> TableColumnStudentId;
    @FXML
    private Button BtnAdd;

    @FXML
    private Button BtnDelete;

    @FXML
    private Button BtnUpdate;

    @FXML
    private TableColumn<Student, String> TableColumnName;
    @FXML
    private Label LabelInfo;
    @FXML
    private Label LabelUser;
    @FXML
    private TextField TextFieldSearch;


    @FXML
    private TableView<Student> TableViewStudents;

    void filterData() {
        List<Student> students = studentService.findAll();
        int itemsCount = students.size();

        // Фильтрация по типу группы
        if (!ComboBoxStudentType.getSelectionModel().isEmpty()) {
            GroupCategory category = ComboBoxStudentType.getValue();
            if (category.getGroupCategoryId() != 0) {
                students = students.stream()
                        .filter(student -> student.getGroupCategory().getGroupCategoryId().equals(category.getGroupCategoryId()))
                        .collect(Collectors.toList());
            }
        }

        // Фильтрация по сумме оплаты
        if (!ComboBoxPaidAmount.getSelectionModel().isEmpty()) {
            String discounts = ComboBoxPaidAmount.getValue();
            if (discounts.equals("0 - 5 000")) {
                students = students.stream().filter(student -> student.getPaidAmount() < 5000).collect(Collectors.toList());
            } else if (discounts.equals("5 000 - 15 000")) {
                students = students.stream().filter(student -> student.getPaidAmount() >= 5000 && student.getPaidAmount() < 15000).collect(Collectors.toList());
            } else if (discounts.equals("15 000 и более")) {
                students = students.stream().filter(student -> student.getPaidAmount() >= 15000).collect(Collectors.toList());
            }

        }

        // Сортировка по цене
        if (!ComboBoxSort.getSelectionModel().isEmpty()) {
            String order = ComboBoxSort.getValue();
            if (order.equals("по возрастанию цены")) {
                students = students.stream().sorted(Comparator.comparing(Student::getPaidAmount)).collect(Collectors.toList());
            } else if (order.equals("по убыванию цены")) {
                students = students.stream().sorted(Comparator.comparing(Student::getPaidAmount).reversed()).collect(Collectors.toList());
            }
        }

        // Поиск по имени студента
        String searchText = TextFieldSearch.getText();
        if (!searchText.isEmpty()) {
            students = students.stream().filter(student -> student.getName().toLowerCase().contains(searchText.toLowerCase())).collect(Collectors.toList());
        }

        // Обновление таблицы
        TableViewStudents.getItems().clear();
        TableViewStudents.getItems().addAll(students);

        // Обновление информации о количестве записей
        int filteredItemsCount = students.size();
        LabelInfo.setText("Всего записей " + filteredItemsCount + " из " + itemsCount);
    }

    public void BtnAddAction(ActionEvent actionEvent) {
        Student student = TableViewStudents.getSelectionModel().getSelectedItem();
        Manager.currentStudent = null;
        ShowEditProductWindow();
        filterData();
    }

    void ShowEditProductWindow() {
        Stage newWindow = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(AutoschoolApp.class.getResource("student-edit-view.fxml"));

        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add("base-styles.css");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        newWindow.setTitle("Изменить данные");
        newWindow.initOwner(Manager.secondStage);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.setScene(scene);
        Manager.currentStage = newWindow;
        newWindow.showAndWait();
        Manager.currentStage = null;
        filterData();
    }

    public void BtnUpdateAction(ActionEvent actionEvent) {
        Student student = TableViewStudents.getSelectionModel().getSelectedItem();
        Manager.currentStudent = student;
        ShowEditProductWindow();
        filterData();
    }



    @FXML
    void BtnDeleteAction(ActionEvent event) {
        Student student = TableViewStudents.getSelectionModel().getSelectedItem();
        if (!student.getLessonAttendance().isEmpty())
        {
            ShowErrorMessageBox("Ошибка целостности данных, у данного клиента есть оценки");
            return;
        }


        Optional<ButtonType> result = ShowConfirmPopup();
        if (result.get() == ButtonType.OK) {
            studentService.delete(student);
            filterData();
        }
    }






    public void BtnBackAction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(AutoschoolApp.class.getResource("main-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add("base-styles.css");
            Manager.secondStage.setScene(scene);
            //Manager.mainStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void TextFieldSearchAction(ActionEvent actionEvent) {
        filterData();
    }

    public void TextFieldTextChanged(InputMethodEvent inputMethodEvent) {
    }

    public void ComboBoxStudentTypeAction(ActionEvent actionEvent) {
        filterData();
    }

    public void ComboBoxPaidAmount(ActionEvent actionEvent) {
        filterData();
    }

    public void ComboBoxSortAction(ActionEvent actionEvent) {
        filterData();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initController();

    }

    public void initController() {
        LabelUser.setText(Manager.currentUser.getFirstName());
        List<GroupCategory> categoryList = categoryService.findAll();
        categoryList.add(0, new GroupCategory(0L, "Все"));
        ObservableList<GroupCategory> categories = FXCollections.observableArrayList(categoryList);
        ComboBoxStudentType.setItems(categories);
        ObservableList<String> discounts = FXCollections.observableArrayList("Все студенты", "0 - 5 000", "5 000 - 15 000", "15 000 и более");

        ComboBoxPaidAmount.setItems(discounts);
        ObservableList<String> orders = FXCollections.observableArrayList("по возрастанию цены", "по убыванию цены");
        ComboBoxSort.setItems(orders);
        setCellValueFactories();
        filterData();
    }
    private void setCellValueFactories() {

        TableColumnPhoto.setCellValueFactory(cellData -> {
            try {
                return new SimpleObjectProperty<ImageView>(cellData.getValue().getImage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        TableColumnStudentId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudentId()));
        TableColumnName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        TableColumnAge.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAge()).asObject().asString());
        TableColumnAttendanceRate.setCellValueFactory(cellData ->
                new SimpleIntegerProperty((int) cellData.getValue().getAttendanceRate()).asObject());
        TableColumnPaidAmount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPaidAmount()).asObject());
    }

}
