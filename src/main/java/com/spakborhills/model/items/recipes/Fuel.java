package com.spakborhills.model.items.recipes;

import com.spakborhills.model.items.Item;

public class Fuel extends Item {
    private final int fuelPower;

    public Fuel(String name) {
        super(name); // harus di atas
        switch (name.toLowerCase()) {
            case "coal":
                this.fuelPower = 20;
                break;
            case "wood":
                this.fuelPower = 10;
                break;
            default:
                this.fuelPower = 5;
                break;
        }
    }

    public int getFuelPower() {
        return fuelPower;
    }
}
