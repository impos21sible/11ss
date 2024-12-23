package org.main.autoschoolapp.model;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "classroom", schema = "autoschool")
public class ClassRoom {

    @Id
    @Column(name = "classroom_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classRoomId;
    @Column(name = "address", nullable = false)
    private String address;

    public ClassRoom() {
    }



    public ClassRoom(Long classRoomId, String address) {
        this.classRoomId = classRoomId;
        this.address = address;
    }
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "classRoom")
    private Set<Lesson> lessons = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassRoom that)) return false;
        return Objects.equals(classRoomId, that.classRoomId) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        final int hashCode = 17 * classRoomId.hashCode() + 31 * address.hashCode();
        return hashCode;
    }



    @Override
    public String toString() {
        return address;
    }

    public Long getClassRoomId() {
        return classRoomId;
    }

    public void setClassRoomId(Long classRoomId) {
        this.classRoomId = classRoomId;
    }

    public String getTitle() {
        return address;
    }

    public void setTitle(String address) {
        this.address = address;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }
}