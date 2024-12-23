package org.main.autoschoolapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.main.autoschoolapp.model.Student;

import java.io.IOException;

public class ListCellController {

    // Ссылка на ImageView для фотографии студента
    @FXML
    private ImageView ImageViewPhoto;

    // Ссылка на Label для отображения оценки посещаемости
    @FXML
    private Label LabelAttendanceRate;

    // Ссылка на Label для отображения имени студента
    @FXML
    private Label LabelName;

    // Ссылка на Label для отображения имени инструктора
    @FXML
    private Label LabelInstructor;

    // Ссылка на Label для отображения цены
    @FXML
    private Label LabelMaxPaymentAmount;

    // Метод для инициализации значений, которые будут отображаться на экране
    public void initialize() {

    }

    public void setStudent(Student student) throws IOException {
        ImageViewPhoto.setImage(student.getPhoto());
        LabelAttendanceRate.setText("Рейтинг - "+ student.getAttendanceRate().toString());
        LabelName.setText("Имя студента: " + student.getName());
        LabelInstructor.setText("Инструктор: " + student.getInstructor().getName());
        LabelMaxPaymentAmount.setText("Цена оплаты: " + String.valueOf(student.getMaxPaymentAmount()));



    }
}
