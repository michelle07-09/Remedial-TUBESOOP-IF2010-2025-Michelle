package com.spakborhills.model.items.foods;

import com.spakborhills.model.items.foods.Food;

import java.util.*;
import java.io.*;

public class FoodLoader {
    private static final Map<String, Food> corpsByName = new LinkedHashMap<>();

    public FoodLoader() {}

    public static synchronized void loadFood (String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\s+");

            if (parts.length >= 3) {
                int sellPrice = Integer.parseInt(parts[parts.length-1]);
                int buyPrice = Integer.parseInt(parts[parts.length-2]);
                String name = String.join(" ", Arrays.copyOfRange(parts, 0, parts.length-2));

                Food corps = new Food(name, buyPrice, sellPrice);
                corpsByName.put(name, corps);

            } else {
                System.out.println("Error: crops format incorrect");
            }
        }
        System.out.println("Loaded " + corpsByName.size() + " foods");
    }
    public static synchronized List<Food> getFoodList(){
        return new ArrayList<Food>(corpsByName.values());
    }

    public static synchronized Food getFoodName(String name){
        for (Food c : corpsByName.values()){
            if (c.getName().equalsIgnoreCase(name)){
                return c;
            }
        }
        return null;
    }
}
