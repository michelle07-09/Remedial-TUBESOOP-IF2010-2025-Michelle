package com.spakborhills.model.items.crops;

import java.util.*;
import java.io.*;

public class CropsRegistry {
    private static final Map<String, Crops> cropsPrototypes = new HashMap<>();
    static {

        try {
            CropsLoader.loadCrops("src/main/java/com/spakborhills/model/items/crops/cropslist.txt");

            List<Crops> loadedCrops = CropsLoader.getCropsList();
            for (Crops corp : loadedCrops) {
                if (corp != null && corp.getName() != null) {
                    cropsPrototypes.put(corp.getName(), corp);
                }
            }

            // PROTOTYPE ALL CORPS

            // PARSNIP
            Crops cparsnipFile = cropsPrototypes.get("Parsnip");
            Crops cparsnip = new Crops("Parsnip", cparsnipFile.getBuyPrice(), cparsnipFile.getSellPrice(), 1);
            registerCrops(cparsnip);

            // CAULIFLOWER
            Crops ccauliflowerFile = cropsPrototypes.get("Cauliflower");
            Crops ccauliflower = new Crops("Cauliflower", ccauliflowerFile.getBuyPrice(), ccauliflowerFile.getSellPrice(), 1);
            registerCrops(ccauliflower);

            // Potato
            Crops cpotatoFile = cropsPrototypes.get("Potato");
            Crops cpotato = new Crops("Potato", cpotatoFile.getBuyPrice(), cpotatoFile.getSellPrice(), 1);
            registerCrops(cpotato);

            // WHEAT
            Crops cwheatFile = cropsPrototypes.get("Wheat");
            Crops cwheat = new Crops("Wheat", cwheatFile.getBuyPrice(), cwheatFile.getSellPrice(), 3);
            registerCrops(cwheat);

            // BLUEBERRY
            Crops cblueberryFile = cropsPrototypes.get("Blueberry");
            Crops cblueberry = new Crops("Blueberry", cblueberryFile.getBuyPrice(), cblueberryFile.getSellPrice(), 3);
            registerCrops(cblueberry);

            // TOMATO
            Crops ctomatoFile = cropsPrototypes.get("Tomato");
            Crops ctomato = new Crops("Tomato", ctomatoFile.getBuyPrice(), ctomatoFile.getSellPrice(), 1);
            registerCrops(ctomato);

            // HOT PEPPER
            Crops chotpepperFile = cropsPrototypes.get("Hot Pepper");
            Crops chotpepper = new Crops("Hot Pepper", chotpepperFile.getBuyPrice(), chotpepperFile.getSellPrice(), 1);
            registerCrops(chotpepper);

            // MELON
            Crops cmelonFile = cropsPrototypes.get("Melon");
            Crops cmelon = new Crops("Melon", cmelonFile.getBuyPrice(), cmelonFile.getSellPrice(), 1);
            registerCrops(cmelon);

            // CRANBERRY
            Crops ccranberryFile = cropsPrototypes.get("Cranberry");
            Crops ccranberry = new Crops("Cranberry", ccranberryFile.getBuyPrice(), ccranberryFile.getSellPrice(), 10);
            registerCrops(ccranberry);

            // PUMPKIN
            Crops cpumpkinFile = cropsPrototypes.get("Pumpkin");
            Crops cpumpkin = new Crops("Pumpkin", cpumpkinFile.getBuyPrice(), cpumpkinFile.getSellPrice(), 1);
            registerCrops(cpumpkin);

            // GRAPE
            Crops cgrapeFile = cropsPrototypes.get("Grape");
            Crops cgrape = new Crops("Grape", cgrapeFile.getBuyPrice(), cgrapeFile.getSellPrice(), 20);
        }
        catch (IOException e){
            System.out.println("Error loading crops list");
            e.printStackTrace();
        }
    }

    public static void registerCrops(Crops prototype) {
        if (prototype != null && prototype.getName() != null) {
            cropsPrototypes.put(prototype.getName(), prototype);
        }
    }

    public static Crops getCropsPrototype(String name) {
        Crops prototype = cropsPrototypes.get(name);
        if (prototype != null) {
            return prototype.clone();
        }
        return null;
    }

    public static Set<String> getAvailableCropsNames() {
        return new HashSet<>(cropsPrototypes.keySet());
    }
}
