package com.maltsevve.crud3.view;

import com.maltsevve.crud3.controller.RegionController;
import com.maltsevve.crud3.controller.UserController;
import com.maltsevve.crud3.model.Region;
import com.maltsevve.crud3.model.Role;
import com.maltsevve.crud3.model.User;
import com.maltsevve.crud3.model.builders.region.ActualRegionBuilder;
import com.maltsevve.crud3.model.builders.region.RegionDirector;
import com.maltsevve.crud3.model.builders.user.ActualUserBuilder;
import com.maltsevve.crud3.model.builders.user.UserDirector;

import java.util.List;

import static com.maltsevve.crud3.view.Console.start;

public class UserView {
    private final UserController userController = new UserController();
    private final UserDirector userDirector = new UserDirector();
    private final RegionController regionController = new RegionController();
    private final RegionDirector regionDirector = new RegionDirector();

    private void userMenu() {
        System.out.println("""
                'USERS'
                Select menu item:
                1 - Save
                2 - Update
                3 - Get by ID
                4 - Get all
                5 - Delete
                6 - Return""");
    }

    public void logic() {
        userMenu();

        String input = ClientInput.getScanner().nextLine();

        if (input.matches("\\d+")) {
            switch (Integer.parseInt(input)) {
                case 1 -> { // SAVE
                    User user = createUser();

                    if (user != null) {
                        userController.save(user);
                    }

                    System.out.println();

                    logic();
                }

                case 2 -> { // UPDATE
                    System.out.println("Input user id: ");
                    input = ClientInput.getScanner().nextLine();

                    if (input.matches("\\d+")) {
                        Long id = Long.parseLong(input);
                        User user = userController.getById(id);

                        if (user != null) {
                            User user1 = createUser();

                            if (user1 != null) {
                                user1.setId(id);

                                if (!user.equals(user1)) {
                                    user1.setPosts(user.getPosts());
                                    userController.update(user1);
                                    System.out.println();
                                } else {
                                    System.out.println("The entered user is identical to the existing one.\n");
                                }
                            }
                        } else {
                            System.out.println("No users with specified ID in the data base.\n");
                        }
                    } else {
                        System.out.println("Invalid input.\n");
                    }

                    logic();
                }

                case 3 -> { // GET BY ID
                    System.out.println("Input user id: ");
                    input = ClientInput.getScanner().nextLine();

                    System.out.println();

                    if (input.matches("\\d+")) {
                        User user = userController.getById(Long.parseLong(input));

                        if (user != null) {
                            System.out.println(user.getId() + " " + user.getFirstName() + " " + user.getLastName() +
                                    " Region: " + user.getRegion() + " Role: " + user.getRole());
                            user.getPosts().forEach(System.out::println);
                        } else {
                            System.out.println("No such user in the data base.");
                        }
                    } else {
                        System.out.println("Invalid ID.");
                    }

                    System.out.println();

                    logic();
                }

                case 4 -> { // GET ALL
                    System.out.println();
                    List<User> userList = userController.getAll();

                    if (!userList.isEmpty()) {
                        for (User user : userList) {
                            System.out.println(user.getId() + " " + user.getFirstName() + " " + user.getLastName() +
                                    " Region: " + user.getRegion() + " Role: " + user.getRole());
                            user.getPosts().forEach(System.out::println);
                            System.out.println();
                        }
                    }

                    logic();
                }

                case 5 -> { // DELETE BY ID
                    System.out.println("Input user id: ");
                    input = ClientInput.getScanner().nextLine();

                    if (input.matches("\\d+")) {
                        Long id = Long.parseLong(input);
                        User user;

                        if (id > 0 && (user = userController.getById(id)) != null) {
                            userController.deleteById(id);
                            System.out.println("User with id " + user.getId() + " and name " + user.getFirstName() + " "
                                    + user.getLastName() + " deleted from table.");
                        } else {
                            System.out.println("No such user in the data base.");
                        }
                    } else {
                        System.out.println("Invalid ID.");
                    }

                    System.out.println();

                    logic();
                }

                case 6 -> { // Return to main menu
                    System.out.println();
                    start();
                }

                default -> { // Invalid input
                    System.out.println("Non-existent menu item. Try again.\n");
                    logic();
                }
            }
        } else {
            System.out.println("Use digits from 1 to 6.\n");
            logic();
        }
    }

    private User createUser() {
        User user = null;

        String firstName;
        String lastName;
        Region region;
        Role role = Role.USER;

        System.out.println("Input user first name: ");
        String input = ClientInput.getScanner().nextLine();

        if (input.matches("^([A-zА-яё]+)([\\s-]?[A-zА-яё])*$")) {
            firstName = input;

            System.out.println("Input user last name: ");
            input = ClientInput.getScanner().nextLine();

            if (input.matches("^([A-zА-яё]+)([\\s-]?[A-zА-яё])*$")) {
                lastName = input;

                System.out.println("Input user region: ");
                input = ClientInput.getScanner().nextLine();

                if (input.matches("^([A-zА-яё]+)([\\s-]?[A-zА-яё])*$")) {
                    regionDirector.setRegionBuilder(new ActualRegionBuilder(input));
                    region = regionController.save(regionDirector.buildRegion());
                    System.out.println();

                    System.out.print("""
                            Select user role: 
                            1 - Administrator
                            2 - Moderator
                            3 - User 
                            """);
                    input = ClientInput.getScanner().nextLine();

                    if (input.matches("[1-3]")) {
                        switch (Integer.parseInt(input)) {
                            case 1 -> role = Role.ADMIN;
                            case 2 -> role = Role.MODERATOR;
                            case 3 -> {
                            }
                        }

                        userDirector.setUserBuilder(new ActualUserBuilder(firstName, lastName,
                                null, region, role));

                        user = userDirector.buildUser();
                    } else {
                        System.out.println("Invalid input.");
                    }
                } else {
                    System.out.println("Region name can only contain the letters A-z/A-z" +
                            " and the characters: 'space' and '-'");
                }
            } else {
                System.out.println("Last name can only contain the letters A-z/A-z" +
                        " and the characters: 'space' and '-'");
            }
        } else {
            System.out.println("First name can only contain the letters A-z/A-z" +
                    " and the characters: 'space' and '-'");
        }

        return user;
    }
}
