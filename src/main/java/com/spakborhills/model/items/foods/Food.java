package com.spakborhills.model.items.foods;

import com.spakborhills.model.items.Item;
import com.spakborhills.model.items.behavior.Edible;

public class Food extends Item implements Cloneable, Edible {
    int energyGain;

    public Food(String name, int buyPrice, int sellPrice) {
        super(name, sellPrice, buyPrice);
    }
    public Food(String name, int buyPrice, int sellPrice, int energyGain) {
        super(name, sellPrice, buyPrice);
        this.energyGain = energyGain;
    }

    @Override
    public Food clone(){
        try {
            Food cloned = (Food) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public int getEnergy() { return energyGain; }


    @Override
    public String toString() {
        return "Food{" +
                "name='" + getName() + '\'' +
                ", buyPrice=" + getBuyPrice() +
                ", sellPrice=" + getSellPrice() +
                '}';
    }
}
