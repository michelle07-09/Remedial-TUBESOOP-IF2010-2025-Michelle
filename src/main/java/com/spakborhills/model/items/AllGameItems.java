package com.spakborhills.model.items;

import java.util.*;
import com.spakborhills.model.items.crops.CropsRegistry;
import com.spakborhills.model.items.fish.FishRegistry;
import com.spakborhills.model.items.foods.FoodRegistry;
import com.spakborhills.model.items.misc.MiscRegistry;
import com.spakborhills.model.items.seeds.SeedRegistry;

public class AllGameItems {

    public static Set<Item> getAllGameItems(){
        Set<Item> allItems = new HashSet<>();

        if (FishRegistry.getAvailableFishNames() != null){
            for (String name : FishRegistry.getAvailableFishNames()){
                Item fish = FishRegistry.getFishPrototype(name);
                if (fish != null){
                    allItems.add(fish);
                }
            }
        }

        if (CropsRegistry.getAvailableCropsNames() != null){
            for (String name : CropsRegistry.getAvailableCropsNames()){
                Item crop = CropsRegistry.getCropsPrototype(name);
                if (crop != null){
                    allItems.add(crop);
                }
            }
        }

        if (MiscRegistry.getAvailableMiscNames() != null){
            for (String name : MiscRegistry.getAvailableMiscNames()){
                Item misc = MiscRegistry.getMiscPrototype(name);
                if (misc != null){
                    allItems.add(misc);
                }
            }
        }

        if (FoodRegistry.getAvailableFoodNames() != null){
            for (String name : FoodRegistry.getAvailableFoodNames()){
                Item food = FoodRegistry.getFoodPrototype(name);
                if (food != null){
                    allItems.add(food);
                }
            }
        }

        if (SeedRegistry.getAvailableSeedNames() != null){
            for (String name : SeedRegistry.getAvailableSeedNames()){
                Item seed = SeedRegistry.getSeedPrototype(name);
                if (seed != null){
                    allItems.add(seed);
                }
            }
        }
        return allItems;
    }
}
