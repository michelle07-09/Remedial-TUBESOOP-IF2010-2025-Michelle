package com.spakborhills.controller;

public class Gold {
    private int amount;

    public Gold() {
        this.amount = 0;
    }

    public Gold(int initialAmount) {
        this.amount = Math.max(0, initialAmount);
    }

    public int getAmount() {
        return amount;
    }

    public void add(int value) {
        if (value > 0) {
            amount += value;
        }
    }

    public void subtract(int value) {
        if (value > amount) {
            return;
        }
        amount -= value;
    }

    public boolean canAfford(int value) {
        return amount >= value;
    }

    @Override
    public String toString() {
        return amount + "g";
    }
}

