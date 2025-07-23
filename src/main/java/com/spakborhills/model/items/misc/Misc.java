package com.spakborhills.model.items.misc;

import com.spakborhills.model.items.Item;

public class Misc extends Item implements Cloneable{
    public Misc(String name, int buyPrice, int sellPrice) {
        super(name, sellPrice, buyPrice);
    }

    @Override
    public Misc clone(){
        try {
            Misc cloned = (Misc) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Misc{" +
                "name='" + getName() + '\'' +
                ", buyPrice=" + getBuyPrice() +
                ", sellPrice=" + getSellPrice() +
                '}';
    }
}
