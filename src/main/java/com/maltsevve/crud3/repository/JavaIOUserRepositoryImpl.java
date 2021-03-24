package com.maltsevve.crud3.repository;

import com.maltsevve.crud3.SQL.DataBaseConnector;
import com.maltsevve.crud3.model.User;
import com.maltsevve.crud3.model.builders.user.UserDirector;

import java.sql.Connection;
import java.util.List;

public class JavaIOUserRepositoryImpl implements UserRepository{
    private final static Connection CONNECTION = DataBaseConnector.getDataBaseConnector().getConnection();
    private final static UserDirector USER_DIRECTOR = new UserDirector();

    JavaIOUserRepositoryImpl() {

    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User getById(Long aLong) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
