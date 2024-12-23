package org.main.autoschoolapp.service;


import org.main.autoschoolapp.model.Lesson;

import org.main.autoschoolapp.repository.LessonDao;

import java.util.List;

public class LessonService {
    private LessonDao lessonDao = new LessonDao();

    public LessonService() {
    }

    public List<Lesson> findAll() {
        return lessonDao.findAll();
    }

    public Lesson findOne(final long id) {
        return lessonDao.findOne(id);
    }

    public void save(final Lesson entity) {
        if (entity == null)
            return;
        lessonDao.save(entity);
    }

    public void update(final Lesson entity) {
        if (entity == null)
            return;
        lessonDao.update(entity);
    }

    public void delete(final Lesson entity) {
        if (entity == null)
            return;
        lessonDao.delete(entity);
    }

    public void deleteById(final Long id) {
        if (id == null)
            return;
        lessonDao.deleteById(id);
    }
}
