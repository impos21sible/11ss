package org.main.autoschoolapp.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.main.autoschoolapp.model.GroupCategory;
import org.main.autoschoolapp.model.Student;
import org.main.autoschoolapp.service.GroupCategoryService;
import org.main.autoschoolapp.service.StudentService;
import org.main.autoschoolapp.util.Manager;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainWindowController implements Initializable {


    @FXML
    private TextField TextFieldSearch;

    @FXML
    private ComboBox<GroupCategory> CmboBoxStudentType;

    @FXML
    private ComboBox<String> ComboboxSort;

    @FXML
    private ListView<Student> ListViewStudent;

    @FXML
    private ToolBar ToolBarMain;

    @FXML
    private Button BtnStudents;

    @FXML
    private Button BtnBack;

    @FXML
    private Label LabelUser;

    @FXML
    private Label LabelInfo;

    @FXML
    private AnchorPane AnchorPaneMain;

    @FXML
    private TitledPane TitledPaneHeader;
    @FXML
    ComboBox<String> ComboBoxDiscount;


    private final GroupCategoryService groupCategoryService = new GroupCategoryService();
    private final StudentService studentService = new StudentService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LabelUser.setText(Manager.currentUser.getFirstName());
        List<GroupCategory> categoryList = groupCategoryService.findAll();
        categoryList.add(0, new GroupCategory(0L, "Все"));
        ObservableList<GroupCategory> categories = FXCollections.observableArrayList(categoryList);
        CmboBoxStudentType.setItems(categories);
        ObservableList<String> discounts = FXCollections.observableArrayList("По рейтингу", "0-35", "35-65", "от 65");
        ComboBoxDiscount.setItems(discounts);
        ObservableList<String> orders = FXCollections.observableArrayList("по возрастанию цены", "по убыванию цены");
        ComboboxSort.setItems(orders);
        filterData();
    }


    public void TextFieldSearchAction(ActionEvent actionEvent) {
        filterData();
    }

    public void CmboBoxStudentTypeAction(ActionEvent actionEvent) {filterData();
    }

    // Method for sort combo box action
    public void ComboboxSortAction(ActionEvent actionEvent) {
        filterData();
    }

    @FXML
    void filterData() {
        // Получаем всех студентов
        List<Student> students = studentService.findAll();
        int ageStudent = students.size();

        // Фильтрация по типу студента
        if (!CmboBoxStudentType.getSelectionModel().isEmpty()) {
            GroupCategory category = CmboBoxStudentType.getValue();
            if (category.getGroupCategoryId() != 0) {
                students = students.stream()
                        .filter(student -> student.getGroupCategory().getGroupCategoryId().equals(category.getGroupCategoryId()))
                        .collect(Collectors.toList());
            }
        }

        // Фильтрация по рейтингу посещаемости
        if (!ComboBoxDiscount.getSelectionModel().isEmpty()) {
            String attendanceRate = ComboBoxDiscount.getValue();
            switch (attendanceRate) {
                case "0-35":
                    students = students.stream()
                            .filter(student -> student.getAttendanceRate() >= 0 && student.getAttendanceRate() <= 35)
                            .collect(Collectors.toList());
                    break;
                case "35-65":
                    students = students.stream()
                            .filter(student -> student.getAttendanceRate() > 35 && student.getAttendanceRate() <= 65)
                            .collect(Collectors.toList());
                    break;
                case "от 65":
                    students = students.stream()
                            .filter(student -> student.getAttendanceRate() > 65)
                            .collect(Collectors.toList());
                    break;
                case "По рейтингу":
                    // Если выбрано "По рейтингу", оставляем всех студентов без изменений
                    break;
            }
        }

        // Сортировка
        if (!ComboboxSort.getSelectionModel().isEmpty()) {
            String order = ComboboxSort.getValue();
            Comparator<Student> comparator = Comparator.comparing(Student::getPaidAmount);
            if (order.equals("по убыванию цены")) {
                comparator = comparator.reversed();
            }
            students = students.stream()
                    .sorted(comparator)
                    .collect(Collectors.toList());
        }

        // Поиск
        String searchText = TextFieldSearch.getText();
        if (!searchText.isEmpty()) {
            students = students.stream()
                    .filter(student -> student.getName().toLowerCase().contains(searchText.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Обновление списка
        ListViewStudent.getItems().clear();
        ListViewStudent.getItems().addAll(students);
        ListViewStudent.setCellFactory(lv -> new StudentCell());

        // Обновление информации
        LabelInfo.setText("Всего записей " + students.size() + " из " + ageStudent);
    }




    public void BtnStudentsAction() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/main/autoschoolapp/students-table-view.fxml"));
        Scene scene = null;

        try {
            scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add("base-styles.css");

            Manager.secondStage.setScene(scene);
            // Если необходимо, можно вызвать метод для отображения главного окна: Manager.mainStage.show();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки окна студентов: " + e.getMessage(), e);
        }
    }



    public void BtnBackAction(ActionEvent actionEvent) {
    }

    public void loadStudents(GroupCategory groupCategory) {
        ListViewStudent.getItems().clear();
        List<Student> students = studentService.findAll();
        if (groupCategory != null) {
            students = students.stream()
                    .filter(student -> student.getGroupCategory().getGroupCategoryId().equals(groupCategory.getGroupCategoryId()))
                    .collect(Collectors.toList());
        }
        for (Student student : students) {
            ListViewStudent.getItems().add(student);  // добавляем объект Student, а не его строковое представление
        }
        // Указываем, что для отображения данных в ListView будет использоваться StudentCell
        ListViewStudent.setCellFactory(lv -> new StudentCell());
    }

    public void BtnGoVehicleAction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/main/autoschoolapp/vehicle-table-view.fxml"));
        Scene scene = null;

        try {
            scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add("base-styles.css");

            Manager.secondStage.setScene(scene);
            // Если необходимо, можно вызвать метод для отображения главного окна: Manager.mainStage.show();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки окна студентов: " + e.getMessage(), e);
        }
    }

    public void BtnGroupCategoryAction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/main/autoschoolapp/group-category-table-view.fxml"));
        Scene scene = null;

        try {
            scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add("base-styles.css");

            Manager.secondStage.setScene(scene);
            // Если необходимо, можно вызвать метод для отображения главного окна: Manager.mainStage.show();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки окна категорий: " + e.getMessage(), e);
        }
    }

    public void BtnJournalAction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/main/autoschoolapp/schedule-view.fxml"));

        Scene scene = null;

        try {
            scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add("base-styles.css");

            Manager.secondStage.setScene(scene);
            // Если необходимо, можно вызвать метод для отображения главного окна: Manager.mainStage.show();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки окна категорий: " + e.getMessage(), e);
        }
    }
}
