package com.spakborhills.model.entity.npc;

import java.util.*;

import com.spakborhills.model.items.crops.Crops;
import com.spakborhills.model.items.fish.FishRegistry;
import com.spakborhills.model.items.seeds.SeedRegistry;
import com.spakborhills.model.items.crops.CropsRegistry;
import com.spakborhills.model.items.foods.FoodRegistry;
import com.spakborhills.model.items.misc.MiscRegistry;

import com.spakborhills.model.items.Item;

public class NPCRegistry {
    private static final Map<String, NPC> npcPrototypes = new HashMap<>();

    static {
        // Mayor tadi
        Item legend = FishRegistry.getFishPrototype("Legend");
        Item angler = FishRegistry.getFishPrototype("Angler");
        Item crimsonfish = FishRegistry.getFishPrototype("Crimsonfish");
        Item glacierfish = FishRegistry.getFishPrototype("Glacierfish");


        NPC mayorTadi = new NPC("Mayor Tadi")
                .addLovedItem(legend)
                .addLikedItem(angler, crimsonfish, glacierfish);
        List<Item> hatedItemsMayor = mayorTadi.setHatedItemList();
        mayorTadi.addHatedItem(hatedItemsMayor.toArray(new Item[0]));
        registerNPC(mayorTadi);

        // Caroline
        Item firewood = MiscRegistry.getMiscPrototype("Firewood");
        Item coal = MiscRegistry.getMiscPrototype("Coal");
        Item potato = CropsRegistry.getCropsPrototype("Potato");
        Item wheat = CropsRegistry.getCropsPrototype("Wheat");
        Item hotpepper = CropsRegistry.getCropsPrototype("Hot Pepper");

        NPC caroline = new NPC("Caroline")
                .addLovedItem(firewood, coal)
                .addLikedItem(potato, wheat)
                .addHatedItem(hotpepper);
        registerNPC(caroline);

        // Perry
        Item cranberry = CropsRegistry.getCropsPrototype("Cranberry");
        Item blueberry = CropsRegistry.getCropsPrototype("Blueberry");
        Item wine = FoodRegistry.getFoodPrototype("Wine");

        Set<String> availFish = FishRegistry.getAvailableFishNames();
        List<Item> fishList = new ArrayList<>();
        for (String fishName : availFish) {
            Item fish = FishRegistry.getFishPrototype(fishName);
            fishList.add(fish);
        }
        NPC perry = new NPC("Perry")
                .addLovedItem(cranberry, blueberry)
                .addLikedItem(wine)
                .addHatedItem(fishList.toArray(new Item[0]));
        registerNPC(perry);

        // Dasco
        Item pufferfish = FishRegistry.getFishPrototype("Pufferfish");
        Item salmon = FishRegistry.getFishPrototype("Salmon");

        Item fugu = FoodRegistry.getFoodPrototype("Fugu");
        Item thelegendsofspakbor = FoodRegistry.getFoodPrototype("The Legends of Spakbor");
        Item cookedpigshead = FoodRegistry.getFoodPrototype("Cooked Pig's Head");
        Item spakborsalad = FoodRegistry.getFoodPrototype("Spakbor Salad");

        Item fishsandwich = FoodRegistry.getFoodPrototype("Fish Sandwich");
        Item fishstew = FoodRegistry.getFoodPrototype("Fish Stew");
        Item baguette = FoodRegistry.getFoodPrototype("Baguette");
        Item fishnchips = FoodRegistry.getFoodPrototype("Fish n' Chips");

        Item grape = CropsRegistry.getCropsPrototype("Grape");
        Item cauliflower = CropsRegistry.getCropsPrototype("Cauliflower");

        NPC dasco = new NPC("Dasco")
                .addLovedItem(thelegendsofspakbor, cookedpigshead, wine, fugu, spakborsalad)
                .addLikedItem(fishsandwich, fishstew, baguette, fishnchips)
                .addHatedItem(legend, grape, cauliflower, wheat, pufferfish, salmon);
        registerNPC(dasco);

        // Emily
        Set<String> availSeeds = SeedRegistry.getAvailableSeedNames();
        List<Item> seedsList = new ArrayList<>();
        for (String seedName : availSeeds) {
            Item seed = SeedRegistry.getSeedPrototype(seedName);
            seedsList.add(seed);
        }

        Item catfish = FishRegistry.getFishPrototype("Catfish");
        Item sardine = FishRegistry.getFishPrototype("Sardine");

        NPC emily = new NPC("Emily")
                .addLovedItem(seedsList.toArray(new Item[0]))
                .addLikedItem(catfish, salmon, sardine)
                .addHatedItem(coal, firewood);
        registerNPC(emily);

        // Abigail
        Item melon = CropsRegistry.getCropsPrototype("Melon");
        Item pumpkin = CropsRegistry.getCropsPrototype("Pumpkin");

        Item pumpkinpie = FoodRegistry.getFoodPrototype("Pumpkin Pie");

        Item parsnip = CropsRegistry.getCropsPrototype("Parsnip");
        NPC abigail = new NPC("Abigail")
                .addLovedItem(blueberry, melon, pumpkin, grape, cranberry)
                .addLikedItem(baguette, pumpkinpie, wine)
                .addHatedItem(hotpepper, cauliflower, parsnip, wheat);
        registerNPC(abigail);


    }

    public static void registerNPC(NPC prototype) {
        npcPrototypes.put(prototype.getName(), prototype);
    }

    public static NPC getNPCPrototype(String name) {
        NPC prototype = npcPrototypes.get(name);
        if (prototype != null) {
            return prototype.clone();
        }
        return null;
    }

    public static boolean hasPrototype(String name) {
        return npcPrototypes.containsKey(name);
    }

    public static Set<String> getAvailableNPCs() {
        return new HashSet<>(npcPrototypes.keySet());
    }

}
