package org.main.autoschoolapp.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.main.autoschoolapp.model.Instructor;
import org.main.autoschoolapp.model.Vehicle;
import org.main.autoschoolapp.service.VehicleService;
import org.main.autoschoolapp.util.Manager;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static org.main.autoschoolapp.util.Manager.*;


public class VehicleTableViewController implements Initializable {
    private int itemsCount;
    private VehicleService vehicleService = new VehicleService();

    @FXML
    private TableColumn<Vehicle, String> TableColumnVehicleId;
    @FXML
    private TableColumn<Vehicle, String> TableColumnModel;

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
    private TableView<Vehicle> TableViewVehicles;

    @FXML
    private TextField TextFieldSearch;

    @FXML
    void MenuItemAddAction(ActionEvent event) {
        currentVehicle = null;
        ShowEditWindow("/org/main/autoschoolapp/vehicle-edit-table-view.fxml");
        filterData();
    }

    @FXML
    void MenuItemBackAction(ActionEvent event) {
        Manager.LoadSecondStageScene("/org/main/autoschoolapp/main-view.fxml", "Cтуденты");
    }

    @FXML
    void MenuItemDeleteAction(ActionEvent event) {
        Vehicle unittype = TableViewVehicles.getSelectionModel().getSelectedItem();
        if (!unittype.getStudents().isEmpty()) {
            ShowErrorMessageBox("Ошибка целостности данного автомобиля есть зависимости.");
            return;
        }

        Optional<ButtonType> result = ShowConfirmPopup();
        if (result.get() == ButtonType.OK) {
            vehicleService.delete(unittype);
            filterData();
        }
    }

    @FXML
    void MenuItemUpdateAction(ActionEvent event) {
        Vehicle vehicle = TableViewVehicles.getSelectionModel().getSelectedItem();
        currentVehicle = vehicle;
       ShowEditWindow("/org/main/autoschoolapp/vehicle-edit-table-view.fxml");
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

    public void initController() {
        LabelUser.setText(Manager.currentUser.getFirstName());
        List<Vehicle> unittypeList = vehicleService.findAll();
        unittypeList.add(0, new Vehicle(0L, "Все"));
        ObservableList<Vehicle> unittypes = FXCollections.observableArrayList(unittypeList);
        TableViewVehicles.setItems(unittypes);
        setCellValueFactories();

        filterData();
    }

    void filterData() {
        List<Vehicle> vehicle = vehicleService.findAll();
        itemsCount = vehicle.size();

        String searchText = TextFieldSearch.getText();
        if (!searchText.isEmpty()) {
            vehicle = vehicle.stream().filter(unittypes -> unittypes.getModel().toLowerCase().contains(searchText.toLowerCase())).collect(Collectors.toList());
        }
        TableViewVehicles.getItems().clear();
        for (Vehicle vehicles : vehicle) {
            TableViewVehicles.getItems().add(vehicles);
        }
        int filteredItemsCount = vehicle.size();
        LabelInfo.setText("Всего записей " + filteredItemsCount + " из " + itemsCount);
        TableViewVehicles.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
    }

    private void setCellValueFactories() {
        TableColumnVehicleId.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getVehicleId())));
        TableColumnModel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModel()));
    }
}
