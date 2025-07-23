package com.spakborhills.model.items;

import java.util.Objects;

public class Item {
    private String name;
    private int sellPrice;
    private int buyPrice;

    public Item() {
        this.name = "";
        this.sellPrice = 0;
        this.buyPrice = 0;
    }

    public Item(String name) {
        this.name = name;
        this.sellPrice = 0;
        this.buyPrice = 0;
    }

    public Item(String name, int sellPrice, int buyPrice) {
        this.name = name;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getSellPrice() {
        return sellPrice;
    }
    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getBuyPrice() {
        return buyPrice;
    }
    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(this.name, item.name);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.name);
    }
}
