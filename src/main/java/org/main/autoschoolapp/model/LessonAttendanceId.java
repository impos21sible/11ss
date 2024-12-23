package org.main.autoschoolapp.model;
import java.io.Serializable;
import java.util.Objects;

public class LessonAttendanceId implements Serializable {
    private Lesson lesson;

    private Student student;

    public LessonAttendanceId() {
    }

    public LessonAttendanceId(Lesson lesson, Student student) {
        this.lesson = lesson;
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LessonAttendanceId that)) return false;
        return Objects.equals(lesson, that.lesson) && Objects.equals(student, that.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lesson, student);
    }

    // equals() and hashCode()
}