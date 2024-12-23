package org.main.autoschoolapp.repository;

import org.main.autoschoolapp.model.Status;

public class StatusDao extends BaseDao<Status> {
    public StatusDao(){
        super(Status.class);
    }
}
