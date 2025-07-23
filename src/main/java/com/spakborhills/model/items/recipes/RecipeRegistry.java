//package com.spakborhills.model.items.recipes;
//
//import com.spakborhills.model.items.foods.Food;
//import com.spakborhills.model.items.foods.FoodRegistry;
//import com.spakborhills.model.items.crops.Crops;
//import com.spakborhills.model.items.crops.CropsRegistry;
//import com.spakborhills.model.items.fish.Fish;
//import com.spakborhills.model.items.fish.FishRegistry;
//import com.spakborhills.model.items.Item;
//
//import java.io.*;
//import java.util.*;
//
//public class RecipeRegistry {
//    private static final Map<String, Recipe> recipePrototypes = new HashMap<>();
//
//    static{
//        // BAGUETTE
//        Map<Item, Integer> baguetteIngredients = new HashMap<>();
//        baguetteIngredients.put(CropsRegistry.getCropsPrototype("Wheat"), 3);
//        Recipe baguette = new Recipe("recipe_2", FoodRegistry.getFoodPrototype("Baguette"), baguetteIngredients, null, false);
//        registerFood(baguette);
//
//        // WINE
//        Map<Item, Integer> wineIngredients = new HashMap<>();
//        wineIngredients.put(CropsRegistry.getCropsPrototype("Grape"), 2);
//        Recipe wine = new Recipe("recipe_5", FoodRegistry.getFoodPrototype("Wine"), wineIngredients, null, false);
//        registerFood(wine);
//
//        // PUMPKIN PIE
//        Map<Item, Integer> pumpkinpieIngredients = new HashMap<>();
//        pumpkinpieIngredients.put(CropsRegistry.getCropsPrototype("Pumpkin"), 1);
//        pumpkinpieIngredients.put(CropsRegistry.getCropsPrototype("Wheat"), 1);
//        pumpkinpieIngredients.put(FoodRegistry.getFoodPrototype("Egg"), 1);
//        Recipe pumpkinpie = new Recipe("recipe_9", FoodRegistry.getFoodPrototype("Pumpkin Pie"), pumpkinpieIngredients, null, false);
//        registerFood(pumpkinpie);
//
//        // SPAKBOR SALAD
//        Map<Item, Integer> spakborsaladIngredients = new HashMap<>();
//        spakborsaladIngredients.put(CropsRegistry.getCropsPrototype("Melon"), 1);
//        spakborsaladIngredients.put(CropsRegistry.getCropsPrototype("Cranberry"), 1);
//        spakborsaladIngredients.put(CropsRegistry.getCropsPrototype("Blueberry"), 1);
//        spakborsaladIngredients.put(CropsRegistry.getCropsPrototype("Tomato"), 1);
//        Recipe spakborsalad = new Recipe("recipe_7", FoodRegistry.getFoodPrototype("Spakbor Salad"), spakborsaladIngredients, null, false);
//        registerFood(spakborsalad);
//    }
//
//    public static void registerFood(Recipe prototype) {
//        if (prototype != null && prototype.getRecipeName() != null) {
//            recipePrototypes.put(prototype.getRecipeName(), prototype);
//        }
//    }
//
//    public static Recipe getRecipePrototype(String name) {
//        Recipe prototype = recipePrototypes.get(name);
//        if (prototype != null) {
//            return prototype.clone();
//        }
//        return null;
//    }
//
//    public static Set<String> getAvailableRecipeNames() {
//        return new HashSet<>(recipePrototypes.keySet());
//    }
//
//}
