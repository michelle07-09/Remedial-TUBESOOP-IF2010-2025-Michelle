package com.spakborhills.model.items.foods;

import java.io.IOException;
import java.util.*;

public class FoodRegistry {
    private static final Map<String, Food> foodPrototypes = new HashMap<>();
    static {

        try {
            FoodLoader.loadFood("src/main/java/com/spakborhills/model/items/foods/foodslist.txt");

            List<Food> loadedFood = FoodLoader.getFoodList();
            for (Food food : loadedFood) {
                if (food != null && food.getName() != null) {
                    foodPrototypes.put(food.getName(), food);
                }
            }

            // PROTOTYPE ALL FOODS

            // FISH N' CHIPS
            Food fishnchipsFile = foodPrototypes.get("Fish n' Chips");
            Food fishnchips = new Food("Fish n' Chips", fishnchipsFile.getBuyPrice(), fishnchipsFile.getSellPrice(), 50);
            registerFood(fishnchips);

            // BAGUETTE
            Food baguetteFile = foodPrototypes.get("Baguette");
            Food baguette = new Food("Baguette", baguetteFile.getBuyPrice(), baguetteFile.getSellPrice(), 25);
            registerFood(baguette);

            // SASHIMI
            Food sashimiFile = foodPrototypes.get("Sashimi");
            Food sashimi = new Food("Sashimi", sashimiFile.getBuyPrice(), sashimiFile.getSellPrice(), 70);
            registerFood(sashimi);

            // FUGU
            Food fuguFile = foodPrototypes.get("Fugu");
            Food fugu = new Food("Fugu", fuguFile.getBuyPrice(), fuguFile.getSellPrice(), 50);
            registerFood(fugu);

            // WINE
            Food wineFile = foodPrototypes.get("Wine");
            Food wine = new Food("Wine", wineFile.getBuyPrice(), wineFile.getSellPrice(), 20);
            registerFood(wine);

            // PUMPKIN PIE
            Food pumpkinpieFile = foodPrototypes.get("Pumpkin Pie");
            Food pumpkinpie = new Food("Pumpkin Pie", pumpkinpieFile.getBuyPrice(), pumpkinpieFile.getSellPrice(), 35);
            registerFood(pumpkinpie);

            // VEGGIE SOUP
            Food veggiesoupFile = foodPrototypes.get("Veggie Soup");
            Food veggiesoup = new Food("Veggie Soup", veggiesoupFile.getBuyPrice(), veggiesoupFile.getSellPrice(), 40);
            registerFood(veggiesoup);

            // FISH STEW
            Food fishstewFile = foodPrototypes.get("Fish Stew");
            Food fishstew = new Food("Fish Stew", fishstewFile.getBuyPrice(), fishstewFile.getSellPrice(), 70);
            registerFood(fishstew);

            // SPAKBOR SALAD
            Food spakborsaladFile = foodPrototypes.get("Spakbor Salad");
            Food spakborsalad = new Food("Spakbor Salad", spakborsaladFile.getBuyPrice(), spakborsaladFile.getSellPrice(), 70);
            registerFood(spakborsalad);

            // FISH SANDWICH
            Food fishsandwichFile = foodPrototypes.get("Fish Sandwich");
            Food fishsandwich = new Food("Fish Sandwich", fishsandwichFile.getBuyPrice(), fishsandwichFile.getSellPrice(), 50);
            registerFood(fishsandwich);

            // THE LEGENDS OF SPAKBOR
            Food thelegendsofspakborFile = foodPrototypes.get("The Legends of Spakbor");
            Food thelegendsofspakbor = new Food("The Legends of Spakbor", thelegendsofspakborFile.getBuyPrice(), thelegendsofspakborFile.getSellPrice(), 100);
            registerFood(thelegendsofspakbor);

            // COOKED PIG'S HEAD
            Food cookedpigsheadFile = foodPrototypes.get("Cooked Pig's Head");
            Food cookedpigshead = new Food("Cooked Pig's Head", cookedpigsheadFile.getBuyPrice(), cookedpigsheadFile.getSellPrice(), 70);
            registerFood(cookedpigshead);

        } catch (IOException e) {
            System.out.println("Error loading food list");
            e.printStackTrace();
        }
    }

    public static void registerFood(Food prototype) {
        if (prototype != null && prototype.getName() != null) {
            foodPrototypes.put(prototype.getName(), prototype);
        }
    }

    public static Food getFoodPrototype(String name) {
        Food prototype = foodPrototypes.get(name);
        if (prototype != null) {
            return prototype.clone();
        }
        return null;
    }

    public static Set<String> getAvailableFoodNames() {
        return new HashSet<>(foodPrototypes.keySet());
    }
}
