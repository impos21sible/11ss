package org.main.autoschoolapp.repository;


import org.main.autoschoolapp.model.User;

public class UserDao extends BaseDao<User> {
    public UserDao() {
        super(User.class);
    }
}
