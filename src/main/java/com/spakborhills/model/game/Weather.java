package com.spakborhills.model.game;

public enum Weather {
    RAINY("Rainy"), SUNNY("Sunny");

    private String name;

    Weather(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
