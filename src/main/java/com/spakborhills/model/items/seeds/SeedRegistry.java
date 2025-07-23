package com.spakborhills.model.items.seeds;

import com.spakborhills.model.game.Season;

import java.util.*;
import java.io.*;

public class SeedRegistry {
    private static final Map<String, Seed> seedPrototypes = new HashMap<>();

    static {

        try{
            SeedLoader.loadSeeds("src/main/java/com/spakborhills/model/items/seeds/seedslist.txt");

            List<Seed> loadedSeeds = SeedLoader.getSeedList();
            for (Seed seed : loadedSeeds) {
                if (seed != null && seed.getName() != null) {
                    seedPrototypes.put(seed.getName(), seed);
                }
            }

            // PROTOTYPE ALL SEEDS

            // PARSNIP
            Seed parsnipFile = seedPrototypes.get("Parsnip Seeds");
            Seed parsnip = new Seed("Parsnip Seeds", parsnipFile.getBuyPrice(), Season.SPRING, 1);
            registerSeed(parsnip);

            // CAULIFLOWER
            Seed cauliflowerFile = seedPrototypes.get("Cauliflower Seeds");
            Seed cauliflower = new Seed("Cauliflower Seeds", cauliflowerFile.getBuyPrice(), Season.SPRING, 5);
            registerSeed(cauliflower);

            // POTATO
            Seed potatoFile = seedPrototypes.get("Potato Seeds");
            Seed potato = new Seed("Potato Seeds", potatoFile.getBuyPrice(), Season.SPRING, 3);
            registerSeed(potato);

            // WHEAT
            Seed wheatFile = seedPrototypes.get("Wheat Seeds");
            Seed wheatSpring = new Seed("Wheat Seeds Spring", wheatFile.getBuyPrice(), Season.SPRING, 1);
            registerSeed(wheatSpring);

            Seed wheatFall = new Seed("Wheat Seeds Fall", wheatFile.getBuyPrice(), Season.FALL, 1);
            registerSeed(wheatFall);

            // BLUEBERRY
            Seed blueberryFile = seedPrototypes.get("Blueberry Seeds");
            Seed blueberry = new Seed("Blueberry Seeds", blueberryFile.getBuyPrice(), Season.SUMMER, 7);
            registerSeed(blueberry);

            // TOMATO
            Seed tomatoFile = seedPrototypes.get("Tomato Seeds");
            Seed tomato = new Seed("Tomato Seeds", tomatoFile.getBuyPrice(), Season.SUMMER, 3);
            registerSeed(tomato);

            // HOT PEPPER
            Seed hotpepperFile = seedPrototypes.get("Hot Pepper Seeds");
            Seed hotpepper = new Seed("Hot Pepper Seeds", hotpepperFile.getBuyPrice(), Season.SUMMER, 1);
            registerSeed(hotpepper);

            // MELON
            Seed melonFile = seedPrototypes.get("Melon Seeds");
            Seed melon = new Seed("Melon Seeds", melonFile.getBuyPrice(), Season.SUMMER, 4);
            registerSeed(melon);

            // CRANBERRY
            Seed cranberryFile = seedPrototypes.get("Cranberry Seeds");
            Seed cranberry = new Seed("Cranberry Seeds", cranberryFile.getBuyPrice(), Season.FALL, 2);
            registerSeed(cranberry);

            // PUMPKIN
            Seed pumpkinFile = seedPrototypes.get("Pumpkin Seeds");
            Seed pumpkin = new Seed("Pumpkin Seeds", pumpkinFile.getBuyPrice(), Season.FALL, 7);
            registerSeed(pumpkin);

            // GRAPE
            Seed grapeFile = seedPrototypes.get("Grape Seeds");
            Seed grape = new Seed("Grape Seeds", grapeFile.getBuyPrice(), Season.FALL, 3);
            registerSeed(grape);

        } catch (IOException e) {
            System.out.println("Error loading seeds list");
            e.printStackTrace();
        }
    }
    public static void registerSeed(Seed prototype) {
        if (prototype != null && prototype.getName() != null) {
            seedPrototypes.put(prototype.getName(), prototype);
        }
    }

    public static Seed getSeedPrototype(String name) {
        Seed prototype = seedPrototypes.get(name);
        if (prototype != null) {
            return prototype.clone();
        }
        return null;
    }

    public static Set<String> getAvailableSeedNames() {
        return new HashSet<>(seedPrototypes.keySet());
    }
}
