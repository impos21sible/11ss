package org.main.autoschoolapp.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import org.main.autoschoolapp.AutoschoolApp;
import org.main.autoschoolapp.model.Student;

import java.io.IOException;

public class StudentCell extends ListCell<Student> {
    private final Parent root ;
    private ListCellController controller ;
    public StudentCell() {
        try {
            FXMLLoader loader = new FXMLLoader(AutoschoolApp.class.getResource("studentcell-view.fxml"));
            root = loader.load();
            controller = loader.getController() ;
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    protected void updateItem(Student student, boolean empty) {
        super.updateItem(student, empty);

        if (empty || student==null) {
            setGraphic(null);
        } else {
            try {
                controller.setStudent(student);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            setGraphic(root);
        }
    }
}
