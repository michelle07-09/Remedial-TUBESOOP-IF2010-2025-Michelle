package com.spakborhills.model.game;

import java.awt.*;

public class PlantInfo {
    private final Point soilLocation;
    private final String cropName;
    private int growthTime; //in days
    private boolean watered;
    private boolean readyToHarvest;

    public PlantInfo(int soilCol, int soilRow, String cropName, int growthTime, boolean watered, boolean readyToHarvest) {
        soilLocation = new Point(soilCol, soilRow);
        this.cropName = cropName;
        this.growthTime = growthTime;
        this.watered = watered;
        this.readyToHarvest = readyToHarvest;
    }

    public Point getSoilLocation() {
        return soilLocation;
    }

    public String getCropName() {
        return cropName;
    }

    public int getGrowthTime() {
        return growthTime;
    }

    public void reduceGrowthTime() {
        growthTime -= 1;
    }

    public boolean isWatered() {
        return watered;
    }

    public void setWatered(boolean watered) {
        this.watered = watered;
    }

    public boolean isReadyToHarvest() {
        return readyToHarvest;
    }

    public void setReadyToHarvest(boolean readyToHarvest) {
        this.readyToHarvest = readyToHarvest;
    }
}
