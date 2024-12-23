package org.main.autoschoolapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.main.autoschoolapp.service.GroupCategoryService;
import org.main.autoschoolapp.service.VehicleService;
import org.main.autoschoolapp.util.Manager;

import java.io.IOException;

import static org.main.autoschoolapp.util.Manager.MessageBox;

public class GroupCategoryEditViewController {
    private GroupCategoryService groupCategoryService = new GroupCategoryService();
    @FXML
    private Button BtnCancel;

    @FXML
    private Button BtnSave;

    @FXML
    private TextField TextFieldTitle;

    @FXML
    void BtnCancelAction(ActionEvent event) {
        Stage stage = (Stage) BtnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    void BtnSaveAction(ActionEvent event) throws IOException {
        String error = checkFields().toString();
        if (!error.isEmpty()) {
            MessageBox("Ошибка", "Заполните поля", error, Alert.AlertType.ERROR);
            return;
        }
        Manager.currentGroupCategory.setTitle(TextFieldTitle.getText());
        if (Manager.currentGroupCategory.getGroupCategoryId() == null) {
            Manager.currentGroupCategory.setTitle(TextFieldTitle.getText());
            groupCategoryService.save(Manager.currentGroupCategory);
            MessageBox("Информация", "", "Данные сохранены успешно", Alert.AlertType.INFORMATION);
        } else {
            groupCategoryService.update(Manager.currentGroupCategory);
            MessageBox("Информация", "", "Данные обновлены успешно", Alert.AlertType.INFORMATION);
        }
    }

    StringBuilder checkFields() {
        StringBuilder error = new StringBuilder();
        if (TextFieldTitle.getText().isEmpty()) {
            error.append("Укажите название категории\n");
        }
        return error;
    }

}
