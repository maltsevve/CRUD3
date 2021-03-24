package com.maltsevve.crud3.repository;

import com.maltsevve.crud3.model.User;

import java.util.List;

public interface UserRepository extends GenericRepository<User, Long>{
    @Override
    User save(User user);

    @Override
    User update(User user);

    @Override
    User getById(Long aLong);

    @Override
    List<User> getAll();

    @Override
    void deleteById(Long aLong);
}
