package com.spakborhills.model.items.seeds;

import com.spakborhills.model.game.Season;
import com.spakborhills.model.items.Item;

public class Seed extends Item implements Cloneable{
    private Season season;
    private int daysToHarvest;

    public Seed(String name, int buyPrice) {
        super(name, buyPrice / 2, buyPrice);
    }

    public Seed(String name, int buyPrice, Season season, int daysToHarvest) {
        super(name, buyPrice / 2, buyPrice);
        this.season = season;
        this.daysToHarvest = daysToHarvest;
    }

    @Override
    public Seed clone(){
        try {
            Seed cloned = (Seed) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public int getDaysToHarvest() {
        return daysToHarvest;
    }

    public Season getSeason() {
        return season;
    }

    @Override
    public String toString() {
        return "Seed{" +
                "name='" + getName() + '\'' +
                ", buyPrice=" + getBuyPrice() +
                ", sellPrice=" + getSellPrice();
    }
}
