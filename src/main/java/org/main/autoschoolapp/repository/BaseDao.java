package org.main.autoschoolapp.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.main.autoschoolapp.util.HibernateSessionFactoryUtil;

import java.util.List;

public class BaseDao<T> {
    private Class<T> clazz;

    public BaseDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    protected Session getCurrentSession() {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession();
    }

    public void save(final T entity) {
        Session session = getCurrentSession();
        Transaction tx1 = session.beginTransaction();
        session.save(entity);
        tx1.commit();
        session.close();
    }

    public void update(final T entity) {
        Session session = getCurrentSession();
        Transaction tx1 = session.beginTransaction();
        // Используем merge вместо update для предотвращения ошибок сессий
        session.merge(entity);
        tx1.commit();
        session.close();
    }

    public void delete(final T entity) {
        Session session = getCurrentSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(entity);
        tx1.commit();
        session.close();
    }

    public void deleteById(final long entityId) {
        final T entity = findOne(entityId);
        delete(entity);
    }

    public T findOne(final long id) {
        Session session = getCurrentSession();
        T entity = session.get(clazz, id);
        session.close();
        return entity;
    }

    public List<T> findAll() {
        Session session = getCurrentSession();
        List<T> items = session.createQuery("from " + clazz.getName(), clazz).list();
        session.close();
        return items;
    }
}
