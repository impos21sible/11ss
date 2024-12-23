package org.main.autoschoolapp.service;
import org.main.autoschoolapp.model.LicenseType;
import org.main.autoschoolapp.repository.LicenseTypeDao;

import java.util.List;

public class LicenseTypeService {
    private LicenseTypeDao licenseTypeDao = new LicenseTypeDao();

    public LicenseTypeService() {
    }

    public List<LicenseType> findAll() {
        return licenseTypeDao.findAll();
    }

    public LicenseType findOne(final long id) {
        return licenseTypeDao.findOne(id);
    }

    public void save(final LicenseType entity) {
        if (entity == null)
            return;
        licenseTypeDao.save(entity);
    }

    public void update(final LicenseType entity) {
        if (entity == null)
            return;
        licenseTypeDao.update(entity);
    }

    public void delete(final LicenseType entity) {
        if (entity == null)
            return;
        licenseTypeDao.delete(entity);
    }

    public void deleteById(final Long id) {
        if (id == null)
            return;
        licenseTypeDao.deleteById(id);
    }
}
