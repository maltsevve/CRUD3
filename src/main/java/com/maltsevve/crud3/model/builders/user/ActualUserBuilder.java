package com.maltsevve.crud3.model.builders.user;

import com.maltsevve.crud3.model.Post;
import com.maltsevve.crud3.model.Region;
import com.maltsevve.crud3.model.Role;
import com.maltsevve.crud3.model.User;

import java.util.List;

public class ActualUserBuilder extends UserBuilder{
    String firstName;
    String lastName;
    List<Post> posts;
    Region region;
    Role role;

    public ActualUserBuilder(String firstName, String lastName, List<Post> posts, Region region, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
        this.region = region;
        this.role = role;
    }

    @Override
    public void buildFirstName() {
        user.setFirstName(firstName);
    }

    @Override
    public void buildLastName() {
        user.setLastName(lastName);
    }

    @Override
    public void buildPosts() {
        user.setPosts(posts);
    }

    @Override
    public void buildRole() {
        user.setRole(role);
    }

    @Override
    public void buildRegion() {
        user.setRegion(region);
    }
}
