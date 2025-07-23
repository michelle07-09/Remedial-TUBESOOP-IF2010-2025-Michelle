package com.spakborhills.model.items.crops;

import com.spakborhills.model.items.Item;
import com.spakborhills.model.items.behavior.Edible;

public class Crops extends Item implements Cloneable, Edible {
    int cropAmt;
    
    public Crops(String name, int buyPrice, int sellPrice) {
        super(name, sellPrice, buyPrice);
    }

    public Crops(String name, Integer buyPrice, Integer sellPrice, int cropAmt) {
        super(name, sellPrice, buyPrice);
        this.cropAmt = cropAmt;
    }

    @Override
    public Crops clone(){
        try {
            Crops cloned = (Crops) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    int getCropAmt() {
        return cropAmt;
    }

    @Override
    public String toString() {
        return "Crops{" +
                "name='" + getName() + '\'' +
                ", buyPrice=" + getBuyPrice() +
                ", sellPrice=" + getSellPrice();
    }
}
