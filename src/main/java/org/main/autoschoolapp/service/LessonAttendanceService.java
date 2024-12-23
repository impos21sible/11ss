package org.main.autoschoolapp.service;

import org.main.autoschoolapp.model.LessonAttendance;
import org.main.autoschoolapp.repository.LessonAttendanceDao;

import java.util.List;

public class LessonAttendanceService {
    private LessonAttendanceDao lessonAttendanceDao = new LessonAttendanceDao();

    public LessonAttendanceService() {
    }

    public List<LessonAttendance> findAll() {
        return lessonAttendanceDao.findAll();
    }

    public LessonAttendance findOne(final long id) {
        return lessonAttendanceDao.findOne(id);
    }

    public void save(final LessonAttendance entity) {
        if (entity == null)
            return;
        lessonAttendanceDao.save(entity);
    }

    public void update(final LessonAttendance entity) {
        if (entity == null)
            return;
        lessonAttendanceDao.update(entity);
    }

    public void delete(final LessonAttendance entity) {
        if (entity == null)
            return;
        lessonAttendanceDao.delete(entity);
    }

    public void deleteById(final Long id) {
        if (id == null)
            return;
        lessonAttendanceDao.deleteById(id);
    }
}
