package org.main.autoschoolapp.model;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "vehicles", schema = "autoschool")
public class Vehicle {


    @Id
    @Column(name = "vehicle_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;

    @Column(name = "model", nullable = false, length = 200)
    private String model;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "vehicle")
    private Set<Student> students = new HashSet<Student>();
    public Vehicle() {

    }

    public Vehicle(Long vehicleId, String title) {
        this.vehicleId = vehicleId;
        this.model = title;

    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long categoryId) {
        this.vehicleId = vehicleId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle vehicle)) return false;
        return Objects.equals(vehicleId, vehicle.vehicleId) && Objects.equals(model, vehicle.model);
    }

    @Override
    public int hashCode() {
        final int hashCode = 17 * vehicleId.hashCode() + 31 * model.hashCode();
        return hashCode;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}