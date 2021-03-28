package com.maltsevve.crud3.view;

public class Console {
    static UserView userView = new UserView();
    static PostView postView = new PostView();
    static RegionView regionView = new RegionView();

    public Console() {

    }

    public static void start() {
        showMainMenu();
        String input = ClientInput.getScanner().nextLine();

        if (input.matches("\\d+")) {
            switch (Integer.parseInt(input)) {
                case 1 -> userView.logic();
                case 2 -> postView.logic();
                case 3 -> regionView.logic();
                case 4 -> ClientInput.getScanner().close();
                default -> {
                    System.out.println("Non-existent menu item. Try again.\n");
                    showMainMenu();
                }
            }
        } else {
            System.out.println("Use digits from 1 to 4.\n");
            showMainMenu();
        }
    }

    public static void showMainMenu() {
        System.out.println("""
                'MENU'
                Select menu item:
                1 - Users
                2 - Posts
                3 - Regions
                4 - Exit""");
    }
}
