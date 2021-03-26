package com.maltsevve.crud3.view;

import com.maltsevve.crud3.controller.RegionController;
import com.maltsevve.crud3.model.Region;
import com.maltsevve.crud3.model.builders.region.ActualRegionBuilder;
import com.maltsevve.crud3.model.builders.region.RegionDirector;

import java.util.List;

import static com.maltsevve.crud3.view.Console.start;

public class RegionView {
    private final RegionController regionController = new RegionController();
    private final RegionDirector regionDirector = new RegionDirector();

    private void regionMenu() {
        System.out.println("'REGIONS'\n" +
                "Select menu item:\n" +
                "1 - Save\n" +
                "2 - Update\n" +
                "3 - Get by ID\n" +
                "4 - Get all\n" +
                "5 - Delete\n" +
                "6 - Return");
    }

    public void logic() {
        regionMenu();
        String input = ClientInput.getScanner().nextLine();

        if (input.matches("\\d+")) {
            switch (Integer.parseInt(input)) {
                case 1 -> { // SAVE
                    System.out.println("Input region name: ");
                    input = ClientInput.getScanner().nextLine();

                    regionDirector.setRegionBuilder(new ActualRegionBuilder(input));
                    regionController.save(regionDirector.buildRegion());
                    System.out.println();

                    logic();
                }

                case 2 -> { // UPDATE
                    System.out.println("Input id and a new name of region: 'id=name'");
                    String[] strs = ClientInput.getScanner().nextLine().split("=");

                    if (strs.length == 2 && strs[0].matches("\\d+") &&
                            strs[1].matches("^([A-zА-яё]+)([\\s-]?[A-zА-яё])*$")) {
                        // Регулярное выражение позволяет ввести:
                        // только буквы, не более одного пробела или одного дефиса подряд.

                        Long id = Long.parseLong(strs[0]);
                        Region region = regionController.getById(id);

                        if (region != null) {

                            if (!region.getName().equals(strs[1])) {
                                regionDirector.setRegionBuilder(new ActualRegionBuilder(strs[1]));
                                region = regionDirector.buildRegion();
                                region.setId(id);

                                regionController.update(region);
                            } else {
                                System.out.println("The entered name is identical to the existing one.\n");
                            }
                        } else {
                            System.out.println("No region with specified ID in the data base.\n");
                        }
                    } else {
                        System.out.println("Invalid input format.\n");
                    }

                    logic();
                }

                case 3 -> { // GET BY ID
                    System.out.println("Input region id: ");
                    input = ClientInput.getScanner().nextLine();

                    System.out.println();

                    if (input.matches("\\d+")) {
                        Region region = regionController.getById(Long.parseLong(input));

                        if (region != null) {
                            System.out.println(region.getId() + " " + region.getName() + "\n");
                        } else {
                            System.out.println("No such region in the data base.\n");
                        }
                    } else {
                        System.out.println("Invalid ID.\n");
                    }

                    logic();
                }

                case 4 -> { // GET ALL
                    System.out.println();
                    List<Region> regionList = regionController.getAll();

                    if (!regionList.isEmpty()) {
                        regionList.forEach((r) -> System.out.println(r.getId() + " " + r.getName()));
                        System.out.println();
                    } else {
                        System.out.println("Table is empty.\n");
                    }

                    logic();
                }

                case 5 -> { // DELETE BY ID
                    System.out.println("Input region id: ");
                    input = ClientInput.getScanner().nextLine();

                    if (input.matches("\\d+")) {
                        Long id = Long.parseLong(input);

                        if (id > 0 && regionController.getById(id) != null) {
                            regionController.deleteById(id);
                            System.out.println();
                        } else {
                            System.out.println("No such region in the data base.\n");
                        }
                    } else {
                        System.out.println("Invalid ID.");
                    }

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
}
