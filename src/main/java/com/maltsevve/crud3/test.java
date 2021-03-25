package com.maltsevve.crud3;

import com.maltsevve.crud3.model.Post;
import com.maltsevve.crud3.model.Region;
import com.maltsevve.crud3.model.Role;
import com.maltsevve.crud3.model.User;
import com.maltsevve.crud3.model.builders.user.ActualUserBuilder;
import com.maltsevve.crud3.model.builders.user.UserDirector;
import com.maltsevve.crud3.repository.JavaIOUserRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        JavaIOUserRepositoryImpl ju = new JavaIOUserRepositoryImpl();
//        List<Post> postList = new ArrayList<>();
//        Region region = new Region();
//        region.setId(1L);
//        region.setName("Rostov");
//
//        UserDirector userDirector = new UserDirector();
//        userDirector.setUserBuilder(new ActualUserBuilder("Sidor", "Ivanov", postList, region, Role.USER));
//
//        User user = userDirector.buildUser();
//        user.setId(1L);
        ju.getAll().forEach(System.out::println);

    }
}
