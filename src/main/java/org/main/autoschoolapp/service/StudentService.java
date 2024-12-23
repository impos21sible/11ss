package org.main.autoschoolapp.service;
import org.main.autoschoolapp.model.Student;
import org.main.autoschoolapp.repository.StudentDao;

import java.util.List;

public class StudentService {
    private StudentDao studentDao = new StudentDao();

    public StudentService() {
    }

    public List<Student> findAll() {
        return studentDao.findAll();
    }

    public Student findOne(final long id) {
        return studentDao.findOne(id);
    }

    public void save(final Student entity)
    {
        if (entity == null)
            return;
        studentDao.save(entity);
    }

    public void update(final Student entity)
    {
        if (entity == null)
            return;
        studentDao.update(entity);
    }

    public void delete(final Student entity)
    {
        if (entity == null)
            return;
        studentDao.delete(entity);
    }

    public void deleteById(final Long id)
    {
        if (id == null)
            return;
        studentDao.deleteById(id);
    }
}
