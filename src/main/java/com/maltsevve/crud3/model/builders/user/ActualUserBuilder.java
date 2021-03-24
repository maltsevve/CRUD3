package com.maltsevve.crud3.model.builders.user;

import com.maltsevve.crud3.model.Post;
import com.maltsevve.crud3.model.Region;
import com.maltsevve.crud3.model.User;

import java.util.List;

public class ActualUserBuilder extends UserBuilder{
    String firstName;
    String lastName;
    List<Post> posts;
    Region region;

    public ActualUserBuilder(String firstName, String lastName, List<Post> posts, Region region) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
        this.region = region;
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
    public void buildRegion() {
        user.setRegion(region);
    }
}
