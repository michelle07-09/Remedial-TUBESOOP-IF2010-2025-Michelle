package com.spakborhills.model.items.recipes;

import java.util.*;

public class FuelRegistry {
    private static final Map<String, Fuel> fuelMap = new HashMap<>();

    static {
        registerFuel(new Fuel("Coal"));
        registerFuel(new Fuel("Wood"));
    }

    public static void registerFuel(Fuel fuel) {
        fuelMap.put(fuel.getName(), fuel);
    }

    public static Set<String> getAvailableFuelNames() {
        return fuelMap.keySet();
    }

    public static Fuel getFuelPrototype(String name) {
        return fuelMap.get(name);
    }
}
