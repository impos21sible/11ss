package org.main.autoschoolapp.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@IdClass(LessonAttendanceId.class)
@Table(name = "lesson_attendance", schema = "autoschool")
public class LessonAttendance {

    @Id
    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // Removed the direct 'age' column mapping, it will be accessed via the 'Student' entity

    public LessonAttendance(Lesson lesson, Student student) {
        this.lesson = lesson;
        this.student = student;
    }

    public LessonAttendance(Student student) {
        this.student = student;
    }

    public LessonAttendance() {
        // Default constructor
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getAge() {
        return student != null ? student.getAge() : null;  // Access 'age' from 'Student' entity
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LessonAttendance that)) return false;
        return Objects.equals(lesson, that.lesson) && Objects.equals(student, that.student);
    }

    @Override
    public int hashCode() {
        return 17 * lesson.getLessonId().hashCode() +
                31 * student.getStudentId().hashCode();
    }
}
