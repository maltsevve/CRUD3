package com.maltsevve.crud3.controller;

import com.maltsevve.crud3.model.User;
import com.maltsevve.crud3.service.UserService;

import java.util.List;

public class UserController {
    UserService userService = new UserService();

    public User save(User user) {
        return userService.save(user);
    }

    public User update(User user) {
        return userService.update(user);
    }

    public User getById(Long aLong) {
        return userService.getById(aLong);
    }

    public List<User> getAll() {
        return userService.getAll();
    }

    public void deleteById(Long aLong) {
        userService.deleteById(aLong);
    }
}
