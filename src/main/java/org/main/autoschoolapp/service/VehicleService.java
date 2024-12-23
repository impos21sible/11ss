package org.main.autoschoolapp.service;
import org.main.autoschoolapp.model.Vehicle;
import org.main.autoschoolapp.repository.VehicleDao;

import java.util.List;

public class VehicleService {
    private VehicleDao vehicleDao = new VehicleDao();

    public VehicleService() {
    }

    public List<Vehicle> findAll() {
        return vehicleDao.findAll();
    }

    public Vehicle findOne(final long id) {
        return vehicleDao.findOne(id);
    }

    public void save(final Vehicle entity) {
        if (entity == null)
            return;
        vehicleDao.save(entity);
    }

    public void update(final Vehicle entity) {
        if (entity == null)
            return;
        vehicleDao.update(entity);
    }

    public void delete(final Vehicle entity) {
        if (entity == null)
            return;
        vehicleDao.delete(entity);
    }

    public void deleteById(final Long id) {
        if (id == null)
            return;
        vehicleDao.deleteById(id);
    }
}
