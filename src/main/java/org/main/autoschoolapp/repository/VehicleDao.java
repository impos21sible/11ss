package org.main.autoschoolapp.repository;


import org.main.autoschoolapp.model.Vehicle;

public class VehicleDao extends BaseDao<Vehicle> {
    public VehicleDao() {
        super(Vehicle.class);
    }
}
