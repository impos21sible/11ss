package org.main.autoschoolapp.service;




import org.main.autoschoolapp.model.Status;
import org.main.autoschoolapp.repository.StatusDao;

import java.util.List;

public class StatusService {
    private StatusDao statusDao = new StatusDao();

    public StatusService() {
    }

    public List<Status> findAll() {
        return statusDao.findAll();
    }

    public Status findOne(final long id) {
        return statusDao.findOne(id);
    }

    public void save(final Status entity) {
        if (entity == null)
            return;
        statusDao.save(entity);
    }

    public void update(final Status entity) {
        if (entity == null)
            return;
        statusDao.update(entity);
    }

    public void delete(final Status entity) {
        if (entity == null)
            return;
        statusDao.delete(entity);
    }

    public void deleteById(final Long id) {
        if (id == null)
            return;
        statusDao.deleteById(id);
    }
}
