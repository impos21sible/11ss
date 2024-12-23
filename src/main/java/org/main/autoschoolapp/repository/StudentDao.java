package org.main.autoschoolapp.repository;

import org.main.autoschoolapp.model.Student;


public class StudentDao extends BaseDao<Student> {
    public StudentDao() {
        super(Student.class);
    }
}
