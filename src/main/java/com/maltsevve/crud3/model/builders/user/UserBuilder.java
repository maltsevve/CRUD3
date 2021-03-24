package com.maltsevve.crud3.model.builders.user;

import com.maltsevve.crud3.model.User;

public abstract class UserBuilder {
    User user;

    public void buildUser() {
        user = new User();
    }

    public void buildFirstName() {

    }

    public void buildLastName() {

    }

    public void buildPosts() {

    }

    public void buildRegion() {

    }

    public User getUser() {
        return user;
    }
}
