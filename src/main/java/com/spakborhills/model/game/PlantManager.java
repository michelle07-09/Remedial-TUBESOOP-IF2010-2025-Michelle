package com.spakborhills.model.game;

import com.spakborhills.view.gui.GamePanel;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class PlantManager implements Observer {
    Map<Point, PlantInfo> plants = new HashMap<>();
    GamePanel gp;

    public PlantManager(GamePanel gp) {
        this.gp = gp;
    }

    public void setPlant(Point location, PlantInfo plant) {
            plants.put(location, plant);
        }

    public void removePlant(Point location) {
            plants.remove(location);
        }

    public Map<Point, PlantInfo> getPlants() {
        return plants;
    }

    public void clearAllPlants(){
        if(plants.isEmpty()) return;
        else {

            for (Point location : plants.keySet()) {
                gp.tileM.changeTile(location.x, location.y, "000.png");
                System.out.println("Tile resetted");
            }

            plants.clear();
            System.out.println("All plants died due to changed season");
        }
    }

    public void updatePlant(Object weather) {
        for (Point location : plants.keySet()) {
            PlantInfo plant = plants.get(location);
            int col = location.x;
            int row = location.y;
            if (!plant.isReadyToHarvest()) {
                if (plant.isWatered()) {
                    plant.reduceGrowthTime();
                }
                if (plant.getGrowthTime() == 0){
                    plant.setReadyToHarvest(true);
                }

                String currentTile = gp.tileM.getTileTypeAtPlayerPosition(col * gp.getTileSize(), row * gp.getTileSize());

                if (weather.equals(Weather.RAINY)){
                    plant.setWatered(true);
                    // Ubah tile jadi basah
                    if ("004.png".equals(currentTile)) {
                        gp.tileM.changeTile(col, row, "147.png");
                    } else if ("148.png".equals(currentTile)) {
                        gp.tileM.changeTile(col, row, "146.png");
                    }
                } else {
                    plant.setWatered(false);
                    // Ubah tile jadi kering
                    if ("147.png".equals(currentTile)) {
                        gp.tileM.changeTile(col, row, "004.png");
                    } else if ("146.png".equals(currentTile)) {
                        gp.tileM.changeTile(col, row, "148.png");
                    }
                }
            }
            else{ //ready to harvest tetep update gambar tilenya
                if (weather.equals(Weather.RAINY)){
                    gp.tileM.changeTile(col, row, "146.png");
                }
                else{
                    gp.tileM.changeTile(col, row, "148.png");
                }
            }
            System.out.println("Plant updated");
        }
    }

    @Override
    public void update(String eventType, Object data) {
        if ("SEASON CHANGED".equals(eventType)) {
            clearAllPlants();
        }
        else if ("DAYS CHANGED".equals(eventType)) {
            updatePlant(data);
        }
    }
}
