package org.main.autoschoolapp.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "instructors", schema = "autoschool")
public class Instructor {


    @Id
    @Column(name = "instructor_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long instructorId;

    @Column(name = "name", nullable = false, length = 200)
    private String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "instructor")
    private Set<Student> students = new HashSet<Student>();

    public Instructor() {

    }

    public Instructor(Long instructorId, String title) {
        this.instructorId = instructorId;
        this.name = title;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Instructor that)) return false;
        return Objects.equals(instructorId, that.instructorId) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        final int hashCode = 17 * instructorId.hashCode() + 31 * name.hashCode();
        return hashCode;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}