package org.main.autoschoolapp.service;
import org.main.autoschoolapp.model.Instructor;
import org.main.autoschoolapp.repository.InstructorDao;

import java.util.List;
public class InstructorService {
    private InstructorDao instructorDao = new InstructorDao();

    public InstructorService() {
    }

    public List<Instructor> findAll() {
        return instructorDao.findAll();
    }

    public Instructor findOne(final long id) {
        return instructorDao.findOne(id);
    }

    public void save(final Instructor entity) {
        if (entity == null)
            return;
        instructorDao.save(entity);
    }

    public void update(final Instructor entity) {
        if (entity == null)
            return;
        instructorDao.update(entity);
    }

    public void delete(final Instructor entity) {
        if (entity == null)
            return;
        instructorDao.delete(entity);
    }

    public void deleteById(final Long id) {
        if (id == null)
            return;
        instructorDao.deleteById(id);
    }
}
