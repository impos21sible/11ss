package org.main.autoschoolapp.repository;

import org.main.autoschoolapp.model.Lesson;

public class LessonDao extends BaseDao<Lesson>{
    public LessonDao(){
        super(Lesson.class);
    }
}
