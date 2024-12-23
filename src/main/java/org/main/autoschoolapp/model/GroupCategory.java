package org.main.autoschoolapp.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "group_categories", schema = "autoschool")
public class GroupCategory {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @Column(name = "title", nullable = false, length = 200)
    private String title;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    private Set<Student> students = new HashSet<Student>();

    public GroupCategory() {

    }

    public GroupCategory(Long categoryId, String title) {
        this.categoryId = categoryId;
        this.title = title;

    }

    public Set<Student> getStudents() {
        return students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupCategory groupCategory)) return false;
        return Objects.equals(categoryId, groupCategory.categoryId) && Objects.equals(title, groupCategory.title);
    }

    @Override
    public int hashCode() {
        final int hashCode = 17 * categoryId.hashCode() + 31 * title.hashCode();
        return hashCode;
    }

    public Long getGroupCategoryId() {
        return categoryId;
    }

    public void setGroupCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
}