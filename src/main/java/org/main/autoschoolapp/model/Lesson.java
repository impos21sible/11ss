package org.main.autoschoolapp.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

import static java.lang.Math.abs;

@Entity
@Table(name = "lessons", schema = "autoschool")

public class Lesson {

    @Id
    @Column(name = "lesson_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Используйте нужную стратегию для генерации ID
    private Long lessonId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson order)) return false;
        return Objects.equals(lessonId, order.lessonId) && Objects.equals(status, order.status) && Objects.equals(classRoom, order.classRoom) && Objects.equals(startDate, order.startDate) && Objects.equals(endDate, order.endDate) && Objects.equals(user, order.user) && Objects.equals(verificationCode, order.verificationCode) && Objects.equals(lessonAttendances, order.lessonAttendances);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonId, status,
                classRoom, startDate, endDate,
                user, verificationCode, lessonAttendances);
    }

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "classroom_id", nullable = false)
    private ClassRoom classRoom;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "username", nullable = true)
    private User user;
    @Column(name = "verification_code", nullable = false)
    private Integer verificationCode;

    @OneToMany(mappedBy = "lesson", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<LessonAttendance> lessonAttendances = new ArrayList<>();


    public Lesson() {
    }






    public List<LessonAttendance> getLessonAttendances() {
        return lessonAttendances;
    }

    public void setLessonAttendances(List<LessonAttendance> lessonAttendances) {
        this.lessonAttendances = lessonAttendances;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public LocalDate getStartDate() {
        return startDate;
    }




    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(Integer verificationCode) {
        this.verificationCode = verificationCode;
    }
}