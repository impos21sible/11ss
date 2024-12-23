package org.main.autoschoolapp.model;

import jakarta.persistence.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.main.autoschoolapp.AutoschoolApp;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@Entity
@Table(name = "students", schema = "autoschool")

public class Student {

    @Id
    @Column(name = "student_id", nullable = false, length = 100)
    private String studentId;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "notes")
    private String notes;
    @Column(name = "max_payment_amount", nullable = false)
    private Double maxPaymentAmount;
    @Column(name = "age")
    private Integer age;
    @Column(name = "paid_amount")
    private Integer paidAmount;
    @Column(name = "attendance_rate", nullable = false)
    private Integer attendanceRate;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "student")
    private List<LessonAttendance> lessonAttendances = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;
    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor;
    @ManyToOne
    @JoinColumn(name = "license_type_id", nullable = false)
    private LicenseType licenseType;
    @ManyToOne
    @JoinColumn(name = "group_category_id", nullable = false)
    private GroupCategory category;
    @Column(name = "photo")
    private byte[] photo;
    public Student() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student product)) return false;
        return Objects.equals(studentId, product.studentId)
                && Objects.equals(name, product.name)
                && Objects.equals(notes, product.notes)
                && Objects.equals(age, product.age)
                && Objects.equals(maxPaymentAmount, product.maxPaymentAmount) && Objects.equals(paidAmount, product.paidAmount) && Objects.equals(attendanceRate, product.attendanceRate) && Objects.equals(vehicle, product.vehicle) && Objects.equals(instructor, product.instructor) && Objects.equals(licenseType, product.licenseType) && Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId,
                name, notes, age,
                maxPaymentAmount, paidAmount, attendanceRate, vehicle, instructor, licenseType, category);
    }

    public StringProperty getPropertyTitle() {
        return new SimpleStringProperty(this.name);
    }

    public List<LessonAttendance> getLessonAttendance() {
        return lessonAttendances;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getMaxPaymentAmount() {
        return maxPaymentAmount;
    }

    public void setMaxPaymentAmount(Double maxPaymentAmount) {
        this.maxPaymentAmount = maxPaymentAmount;
    }

    public Integer getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Integer paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Integer getAttendanceRate() {
        return attendanceRate;
    }

    public void setAttendanceRate(Integer attendanceRate) {
        this.attendanceRate = attendanceRate;
        if (attendanceRate < 0)
            this.attendanceRate = 0;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public LicenseType getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(LicenseType licenseType) {
        this.licenseType = licenseType;
    }

    public GroupCategory getGroupCategory() {
        return category;
    }

    public void setGroupCategory(GroupCategory category) {
        this.category = category;
    }

    public boolean isHasPhoto() {
        return photo != null;
    }

    public Image getPhoto() throws IOException {
        if (photo == null)
            return new Image(AutoschoolApp.class.getResourceAsStream("picture.png"));
        BufferedImage capture = ImageIO.read(new ByteArrayInputStream(photo));
        return SwingFXUtils.toFXImage(capture, null);
    }

    public void setPhoto(Image img) throws IOException {
        BufferedImage buf = SwingFXUtils.fromFXImage(img, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(buf, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        this.photo = bytes;
    }

    public ImageView getImage() throws IOException {
        ImageView image = new ImageView();
        image.setImage(getPhoto());
        image.setFitHeight(60);
        image.setPreserveRatio(true);
        return image;
    }

    public Double getPriceWithDiscount() {
        return age * (1 - paidAmount / 100.0);
    }
}