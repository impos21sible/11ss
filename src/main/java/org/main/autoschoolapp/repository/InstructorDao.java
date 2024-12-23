package org.main.autoschoolapp.repository;

import org.main.autoschoolapp.model.Instructor;


public class InstructorDao extends BaseDao<Instructor> {
    public InstructorDao() {
        super(Instructor.class);
    }
}