//Denys Gordiichuk LAB1 var 4

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Vegetable> salad = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        int calories = 0;
        int minCalories = 0;
        int maxCalories = 0;
        int choice = 0;
        int choice2 = 0;
        int choice3 = 0;

        while (choice != 4) {
            System.out.println("1. Add vegetable to salad");
            System.out.println("2. Sort salad");
            System.out.println("3. Find vegetables in salad that match a given calorie range");
            System.out.println("4. Exit");
            choice = in.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("1. Add tomato");
                    System.out.println("2. Add cucumber");
                    System.out.println("3. Add carrot");
                    System.out.println("4. Add onion");
                    System.out.println("5. Add pepper");
                
                    choice2 = in.nextInt();
                    switch (choice2) {
                        case 1:
                            salad.add(new Tomato());
                            break;
                        case 2:
                            salad.add(new Cucumber());
                            break;
                        case 3:
                            salad.add(new Carrot());
                            break;
                        case 4:
                            salad.add(new Onion());
                            break;
                        case 5:
                            salad.add(new Pepper());
                            break;
                    }
                    break;
                case 2:
                    System.out.println("1. Sort by calories");
                    System.out.println("2. Sort by weight");
                    choice3 = in.nextInt();
                    switch (choice3) {
                        case 1:
                            Collections.sort(salad, new Comparator<Vegetable>() {
                                @Override
                                public int compare(Vegetable o1, Vegetable o2) {
                                    return o1.getCalories() - o2.getCalories();
                                }
                            });
                            break;
                        case 2:
                            Collections.sort(salad, new Comparator<Vegetable>() {
                                @Override
                                public int compare(Vegetable o1, Vegetable o2) {
                                    return o1.getWeight() - o2.getWeight();
                                }
                            });
                            break;
                    }
                    break;
                case 3:
                    System.out.println("Enter min calories");
                    minCalories = in.nextInt();
                    System.out.println("Enter max calories");
                    maxCalories = in.nextInt();
                    for (Vegetable vegetable : salad) {
                        if (vegetable.getCalories() >= minCalories && vegetable.getCalories() <= maxCalories) {
                            System.out.println(vegetable.getName());
                        }
                    }
                    break;
            }
        }
        for (Vegetable vegetable : salad) {
            calories += vegetable.getCalories();
        }
        System.out.println("Calories: " + calories);
    }
}

class Vegetable {
    private String name;
    private int calories;
    private int weight;

    public Vegetable(String name, int calories, int weight) {
        this.name = name;
        this.calories = calories;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public int getWeight() {
        return weight;
    }
}

class Tomato extends Vegetable {
    public Tomato() {
        super("Tomato", 18, 100);
    }
}

class Cucumber extends Vegetable {
    public Cucumber() {
        super("Cucumber", 15, 150);
    }
}

class Carrot extends Vegetable {
    public Carrot() {
        super("Carrot", 41, 200);
    }
}

class Onion extends Vegetable {
    public Onion() {
        super("Onion", 40, 50);
    }
}

class Pepper extends Vegetable {
    public Pepper() {
        super("Pepper", 31, 70);
    }
}


