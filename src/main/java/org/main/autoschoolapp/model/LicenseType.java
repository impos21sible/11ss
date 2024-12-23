package org.main.autoschoolapp.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "license_types", schema = "autoschool")
public class LicenseType {


    @Id
    @Column(name = "license_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long licenseTypeId;

    @Column(name = "title", nullable = false, length = 200)
    private String title;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "licenseType")
    private Set<Student> students = new HashSet<Student>();

    public LicenseType() {

    }

    public LicenseType(Long licenseTypeId, String title) {
        this.licenseTypeId = licenseTypeId;
        this.title = title;

    }

    public Long getLicenseTypeId() {
        return licenseTypeId;
    }

    public void setLicenseTypeId(Long licenseTypeId) {
        this.licenseTypeId = licenseTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LicenseType licenseType)) return false;
        return Objects.equals(licenseTypeId, licenseType.licenseTypeId) && Objects.equals(title, licenseType.title);
    }

    @Override
    public int hashCode() {
        final int hashCode = 17 * licenseTypeId.hashCode() + 31 * title.hashCode();
        return hashCode;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
