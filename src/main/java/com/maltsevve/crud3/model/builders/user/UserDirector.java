package com.maltsevve.crud3.model.builders.user;

import com.maltsevve.crud3.model.User;

public class UserDirector {
    UserBuilder userBuilder;

    public void setUserBuilder(UserBuilder userBuilder) {
        this.userBuilder = userBuilder;
    }

    public User buildUser() {
        userBuilder.buildUser();
        userBuilder.buildFirstName();
        userBuilder.buildLastName();
        userBuilder.buildPosts();
        userBuilder.buildRegion();
        return userBuilder.getUser();
    }
}
