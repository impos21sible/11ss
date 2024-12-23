package org.main.autoschoolapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.main.autoschoolapp.model.Vehicle;
import org.main.autoschoolapp.service.VehicleService;
import org.main.autoschoolapp.util.Manager;
import org.main.autoschoolapp.util.Manager.*;

import java.net.URL;
import java.util.ResourceBundle;

import static org.main.autoschoolapp.util.Manager.MessageBox;

public class VehicleEditViewController implements Initializable {
    private VehicleService vehicleService = new VehicleService();

    @FXML
    private Button BtnCancel;

    @FXML
    private Button BtnSave;

    @FXML
    private TextField TextFieldModel;

    @FXML
    void BtnCancelAction(ActionEvent event) {
        Stage stage = (Stage) BtnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    void BtnSaveAction(ActionEvent event) {
        String error = checkFields().toString();
        if (!error.isEmpty()) {
            MessageBox("Ошибка", "Заполните поля", error, Alert.AlertType.ERROR);
            return;
        }
        Manager.currentVehicle.setModel(TextFieldModel.getText());
        if (Manager.currentVehicle.getVehicleId() == null) {
            Manager.currentVehicle.setModel(TextFieldModel.getText());
            vehicleService.save(Manager.currentVehicle);
            MessageBox("Информация", "", "Данные сохранены успешно", Alert.AlertType.INFORMATION);
        } else {
            vehicleService.update(Manager.currentVehicle);
            MessageBox("Информация", "", "Данные обновлены успешно", Alert.AlertType.INFORMATION);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Manager.currentVehicle != null) {
            TextFieldModel.setText(Manager.currentVehicle.getModel());
        } else {
            Manager.currentVehicle = new Vehicle();
        }
    }
    StringBuilder checkFields() {
        StringBuilder error = new StringBuilder();
        if (TextFieldModel.getText().isEmpty()) {
            error.append("Укажите модель\n");
        }
        return error;
    }
}
