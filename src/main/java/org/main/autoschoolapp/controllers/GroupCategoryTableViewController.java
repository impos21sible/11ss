package org.main.autoschoolapp.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import org.main.autoschoolapp.AutoschoolApp;
import org.main.autoschoolapp.model.GroupCategory;
import org.main.autoschoolapp.service.GroupCategoryService;
import org.main.autoschoolapp.util.Manager;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static org.main.autoschoolapp.util.Manager.*;

public class GroupCategoryTableViewController implements Initializable {

    @FXML
    private Label LabelInfo;

    @FXML
    private Label LabelUser;

    @FXML
    private MenuItem MenuItemAdd;

    @FXML
    private MenuItem MenuItemBack;

    @FXML
    private MenuItem MenuItemDelete;

    @FXML
    private MenuItem MenuItemUpdate;

    @FXML
    private TableColumn<GroupCategory, String> TableColumnId;

    @FXML
    private TableColumn<GroupCategory, String> TableColumnTitle;

    @FXML
    private TableView<GroupCategory> TableViewCategories;

    @FXML
    private TextField TextFieldSearch;
    private int itemsCount;
    private GroupCategoryService groupCategoryService = new GroupCategoryService();

    void filterData() {
        List<GroupCategory> categories = groupCategoryService.findAll();
        itemsCount = categories.size();

        String searchText = TextFieldSearch.getText();
        if (!searchText.isEmpty()) {
            categories = categories.stream().filter(product -> product.getTitle().toLowerCase().contains(searchText.toLowerCase())).collect(Collectors.toList());
        }
        TableViewCategories.getItems().clear();
        for (GroupCategory category : categories) {
            TableViewCategories.getItems().add(category);
        }

        int filteredItemsCount = categories.size();
        LabelInfo.setText("Всего записей " + filteredItemsCount + " из " + itemsCount);
    }


    @FXML
    void MenuItemAddAction(ActionEvent event) {
        Manager.currentStudent = null;
        //ShowEditProductWindow();
        filterData();
    }

    public void initController() {
        LabelUser.setText("Вы вошли как " + currentUser.getSecondName()+ " "+ currentUser.getFirstName());
        setCellValueFactories();
        filterData();
    }

    private void setCellValueFactories() {

        TableColumnId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGroupCategoryId().toString()));
        TableColumnTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
    }


    @FXML
    void MenuItemBackAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(AutoschoolApp.class.getResource("main-view.fxml"));

        Scene scene = null;
        try {
            // Задаем фиксированные размеры окна
            scene = new Scene(fxmlLoader.load(), 1280, 660);
            scene.getStylesheets().add("base-styles.css");

            // Убираем автоматическое разворачивание окна
            Manager.secondStage.setMaximized(false);
            Manager.secondStage.setResizable(false);

            // Устанавливаем сцену
            Manager.secondStage.setScene(scene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void MenuItemDeleteAction(ActionEvent event) {
        GroupCategory category = TableViewCategories.getSelectionModel().getSelectedItem();
        if (!category.getStudents().isEmpty()) {
            ShowErrorMessageBox("Ошибка целостности данных, у данного курса есть студенты");
            return;
        }

        Optional<ButtonType> result = ShowConfirmPopup();
        if (result.get() == ButtonType.OK) {
            groupCategoryService.delete(category);
            filterData();
        }
    }

    @FXML
    void MenuItemUpdateAction(ActionEvent event) {
        GroupCategory category = TableViewCategories.getSelectionModel().getSelectedItem();
        // Manager.currentProduct = category;
        // ShowEditCategoryWindow();
        filterData();
    }

    @FXML
    void TextFieldSearchAction(ActionEvent event) {
        filterData();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initController();

    }
}
