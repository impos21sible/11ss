package org.main.autoschoolapp.util;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.main.autoschoolapp.AutoschoolApp;
import org.main.autoschoolapp.model.GroupCategory;
import org.main.autoschoolapp.model.Student;
import org.main.autoschoolapp.model.User;
import org.main.autoschoolapp.model.Vehicle;

import java.io.IOException;
import java.util.Optional;

public class Manager {
    public static User currentUser = null;
    public static Stage mainStage;
    public static Stage secondStage;
    public static Stage currentStage;
    public static Student currentStudent;

    public static Vehicle currentVehicle;
    public static GroupCategory currentGroupCategory;
    public static Window primaryStage;


    public static void ShowEditWindow(String fxmlFileName) {
        Stage newWindow = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(AutoschoolApp.class.getResource(fxmlFileName));

        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add("base-styles.css");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        newWindow.setTitle("Изменить данные");
        newWindow.initOwner(Manager.secondStage);
        newWindow.setResizable(false);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.setScene(scene);
        Manager.currentStage = newWindow;
        newWindow.showAndWait();
        Manager.currentStage = null;
    }


    public static void LoadSecondStageScene(String fxmlFileName, String title) {
        FXMLLoader fxmlLoader = new FXMLLoader(AutoschoolApp.class.getResource(fxmlFileName));
        Scene scene = null;
        try {
            // Устанавливаем фиксированные размеры для сцены
            scene = new Scene(fxmlLoader.load(), 1280, 660);  // Ширина 1280px, высота 660px
            scene.getStylesheets().add("base-styles.css");

            // Устанавливаем сцену для второго окна
            Manager.secondStage.setScene(scene);

            // Устанавливаем окно с заданным размером
            Manager.secondStage.setWidth(1280);  // Ширина 1280px
            Manager.secondStage.setHeight(660); // Высота 660px

            // Убираем максимизацию, если она была
            Manager.secondStage.setMaximized(false);

            Manager.secondStage.setTitle(title);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    public static void ShowPopup() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Закрыть приложение");
        alert.setHeaderText("Вы хотите выйти из приложения?");
        alert.setContentText("Все несохраненные данные, будут утеряны");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    public static void ShowErrorMessageBox(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public static void MessageBox(String title, String header, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();

    }

    public static Optional<ButtonType> ShowConfirmPopup() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Удаление");
        alert.setHeaderText("Вы действительно хотите удалить запись?");
        alert.setContentText("Также будут удалены все зависимые от этой записи данные");
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }
}