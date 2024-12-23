package org.main.autoschoolapp.service;

import org.main.autoschoolapp.model.ClassRoom;
import org.main.autoschoolapp.repository.ClassRoomDao;

import java.util.List;

public class ClassRoomService {
    private ClassRoomDao classRoomDao = new ClassRoomDao();

    public ClassRoomService() {
    }

    public List<ClassRoom> findAll() {
        return classRoomDao.findAll();
    }

    public ClassRoom findOne(final long id) {
        return classRoomDao.findOne(id);
    }

    public void save(final ClassRoom entity) {
        if (entity == null)
            return;
        classRoomDao.save(entity);
    }

    public void update(final ClassRoom entity) {
        if (entity == null)
            return;
        classRoomDao.update(entity);
    }

    public void delete(final ClassRoom entity) {
        if (entity == null)
            return;
        classRoomDao.delete(entity);
    }

    public void deleteById(final Long id) {
        if (id == null)
            return;
        classRoomDao.deleteById(id);
    }
}
