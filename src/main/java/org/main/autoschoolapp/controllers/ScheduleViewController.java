package org.main.autoschoolapp.controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;import java.time.format.DateTimeFormatter;

import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.main.autoschoolapp.AutoschoolApp;
import org.main.autoschoolapp.model.Lesson;
import org.main.autoschoolapp.model.Status;
import org.main.autoschoolapp.repository.BaseDao;
import org.main.autoschoolapp.service.LessonService;
import org.main.autoschoolapp.util.Manager;

import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Optional;

public class ScheduleViewController  {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    @FXML
    private TableView<Lesson> lessonTable;
    @FXML
    private TableColumn<Lesson, Long> lessonIdColumn;
    @FXML
    private TableColumn<Lesson, String> startDateColumn;
    @FXML
    private TableColumn<Lesson, String> endDateColumn;
    @FXML
    private TableColumn<Lesson, String> classRoomColumn;
    @FXML
    private TableColumn<Lesson, String> statusColumn;

    private final LessonService lessonService = new LessonService();
    private final ObservableList<Lesson> lessonList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        lessonIdColumn.setCellValueFactory(new PropertyValueFactory<>("lessonId"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        classRoomColumn.setCellValueFactory(new PropertyValueFactory<>("classRoom"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        lessonList.addAll(lessonService.findAll());
        lessonTable.setItems(lessonList);
    }

    @FXML
    public void handleAdd() {
        Stage newWindow = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(AutoschoolApp.class.getResource("editLesson.fxml"));

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

    }

    @FXML
    public void handleDelete() {
        Lesson selectedLesson = lessonTable.getSelectionModel().getSelectedItem();
        if (selectedLesson != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Вы уверены, что хотите удалить урок?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                lessonService.delete(selectedLesson);
                lessonList.remove(selectedLesson);
            }
        } else {
            showAlert("Ошибка", "Выберите урок для удаления");
        }
    }

    @FXML
    public void handlePrint() {
        ObservableList<Lesson> lessons = lessonTable.getItems(); // This is fine as it's already a List<Lesson>
        try {
            // Pass the ObservableList directly to the method
            PrintScheduleToPDF(new ArrayList<>(lessons)); // No need for 'com.itextpdf.text.List', just use ArrayList
        } catch (DocumentException | IOException e) {
            showAlert("Ошибка", "Не удалось создать PDF файл: " + e.getMessage());
        }
    }


    public static void PrintScheduleToPDF(java.util.List<Lesson> lessons) throws DocumentException, IOException {
        String FONT = "src/main/resources/fonts/arial.ttf";

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.PDF)", "*.pdf"));

        // Show save file dialog
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(file));
            Font titleFont = FontFactory.getFont(FONT, "cp1251", BaseFont.EMBEDDED, 18);
            Font headerFont = FontFactory.getFont(FONT, "cp1251", BaseFont.EMBEDDED, 12, Font.BOLD);
            Font cellFont = FontFactory.getFont(FONT, "cp1251", BaseFont.EMBEDDED, 10);

            document.open();

            // Adding a header to the document with better design
            Paragraph title = new Paragraph("Расписание уроков", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Create a table with stylish design
            PdfPTable table = new PdfPTable(new float[]{5, 25, 25, 15});
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setSpacingAfter(10);

            // Header row with background color and text alignment
            PdfPCell headerCell = new PdfPCell(new Phrase("№", headerFont));
            headerCell.setBackgroundColor(BaseColor.DARK_GRAY);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setPadding(5);
            headerCell.setBorderWidth(2);
            table.addCell(headerCell);

            headerCell.setPhrase(new Phrase("Дата начала", headerFont));
            table.addCell(headerCell);

            headerCell.setPhrase(new Phrase("Дата окончания", headerFont));
            table.addCell(headerCell);

            headerCell.setPhrase(new Phrase("Кабинет", headerFont));
            table.addCell(headerCell);

            // Add lessons to the table
            int k = 1;
            for (Lesson lesson : lessons) {
                PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(k), cellFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                table.addCell(cell);

                cell.setPhrase(new Phrase(lesson.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), cellFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell.setPhrase(new Phrase(lesson.getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), cellFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                // Assuming you want the Status from the Lesson object
                String classroom = lesson.getClassRoom() != null ? lesson.getClassRoom().getTitle().toString() : "N/A";



                cell.setPhrase(new Phrase(classroom, cellFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                k++;
            }

            // Add the table to the document with a border and padding for the content
            document.add(table);

            // Adding a footer with some info or date
            Paragraph footer = new Paragraph("Составлено автоматически", FontFactory.getFont(FONT, "cp1251", BaseFont.EMBEDDED, 8));
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(30);
            document.add(footer);

            document.close();
        }
    }














    // Метод для отображения сообщений
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
