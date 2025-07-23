package com.spakborhills.model.game;

import com.spakborhills.controller.PlayerStats;
import com.spakborhills.model.entity.Player;
import com.spakborhills.model.items.foods.Food;
import com.spakborhills.model.items.seeds.*;
import com.spakborhills.model.items.crops.*;
import com.spakborhills.model.items.foods.FoodRegistry;
//import com.spakborhills.model.items.recipes.*;
import com.spakborhills.model.items.seeds.*;
import com.spakborhills.model.items.Item;

import java.util.*;

import javax.swing.JOptionPane;

public class Store {
    List<Item> seedsList;
    List<Item> cropsList;
    List<Item> foodsList;
    Player player;
    PlayerStats playerStats = PlayerStats.getInstance();

    public Store(){
        seedsList = new ArrayList<>();
        cropsList = new ArrayList<>();
        foodsList = new ArrayList<>();

        initializeStore();
    }

    public void initializeStore(){
        Set<String> availSeeds = SeedRegistry.getAvailableSeedNames();
        for (String seedName : availSeeds){
            Item seed = SeedRegistry.getSeedPrototype(seedName);
            seedsList.add(seed);
        }

        Set<String> availCrops = CropsRegistry.getAvailableCropsNames();
        for (String cropName : availCrops){
            Item crops = CropsRegistry.getCropsPrototype(cropName);
            cropsList.add(crops);
        }

        Set<String> availFoods = CropsRegistry.getAvailableCropsNames();
        for (String foodName : availFoods){
            Item foods = FoodRegistry.getFoodPrototype(foodName);
            foodsList.add(foods);
        }
    }

    public void buyItem (){
        displayStore();

        String itemToBuy = JOptionPane.showInputDialog(null, "Masukkan nama item yang ingin dibeli atau back untuk kembali:", "Beli Item", JOptionPane.QUESTION_MESSAGE);

        if (itemToBuy == null || itemToBuy.equalsIgnoreCase("back")){
            return;
        }

        String quantityStr = JOptionPane.showInputDialog(null, "Masukkan jumlah yang ingin dibeli:", "Masukkan jumlah", JOptionPane.QUESTION_MESSAGE);

        if (quantityStr == null || quantityStr.equalsIgnoreCase("back")){
            return;
        }

        int quantity = Integer.parseInt(quantityStr);
        if (quantity <= 0){
            System.out.println("Quantity invalid");
            return;
        }

        Item found = findItem(itemToBuy);

        if (found == null){
            System.out.println("Item not found");
        }

        int total = found.getBuyPrice() * quantity;
        if (player.getGold() < total){
            System.out.println("Not enough gold");
        }
        player.setGold(player.getGold() - total);
        player.getInventory().add(found, quantity);
        for (int i = 0; i < quantity; i++) {
            PlayerStats.getInstance().addToOutcome(found);
        }

        if (found.getClass().equals(Food.class)){
            playerStats.notifyObservers("FOOD_PURCHASED", found.getName());
        }
        System.out.println("Successfully bought " + quantity + " " + found.getName() + " for " + total + " gold!");
    }

    // nyari item
    private Item findItem(String itemName){
        for (Item seed : seedsList) {
        if (seed.getName().equalsIgnoreCase(itemName)) {
            return seed;
            }
        }
    
        for (Item crop : cropsList) {
            if (crop.getName().equalsIgnoreCase(itemName)) {
                return crop;
            }
        }
        
        // Check foods
        for (Item food : foodsList) {
            if (food.getName().equalsIgnoreCase(itemName)) {
                return food;
            }
    }
    
    return null;
    }
    public void displayStore(){
        System.out.println("╔════════════════════ STORE ════════════════════╗");

        // display seeds
        System.out.println("║                     SEEDS                      ║");
        System.out.println("╠════════════════════════════════════════════════╣");
        for (Item seed : seedsList){
            System.out.printf("║ %-20s | Price: %-15d ║%n", seed.getName(), seed.getBuyPrice());
        }

        // display crops
        System.out.println("╠════════════════════════════════════════════════╣");
        System.out.println("║                     CROPS                      ║");
        System.out.println("╠════════════════════════════════════════════════╣");
        for (Item crop : cropsList) {
            System.out.printf("║ %-20s | Price: %-15d ║%n", crop.getName(), crop.getBuyPrice());
        }

        // display foods
        System.out.println("╠════════════════════════════════════════════════╣");
        System.out.println("║                     FOODS                      ║");
        System.out.println("╠════════════════════════════════════════════════╣");
        for (Item food : foodsList) {
            System.out.printf("║ %-20s | Price: %-15d ║%n", food.getName(), food.getBuyPrice());
        }
    }
}
