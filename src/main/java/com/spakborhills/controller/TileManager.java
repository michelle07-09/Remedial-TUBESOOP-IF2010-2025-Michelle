package com.spakborhills.controller;

import java.io.*;
import java.awt.*;
import java.net.URL;
import java.util.*;

import javax.imageio.ImageIO;

import com.spakborhills.model.game.GameMap;
import com.spakborhills.model.game.Tile;
import com.spakborhills.view.gui.GamePanel;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    private GameMap currentActiveMap;
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> tileCols = new ArrayList<>();
    private String loadedMap;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        // READ TILE DATA
        InputStream is = getClass().getResourceAsStream("/assets/WorldMaps/TileData");
        BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));

        // GETTING TILE NAME AND COLLISION
        String line;

        try {
            while ((line = br.readLine()) != null) {
                fileNames.add(line);
                tileCols.add(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        tile = new Tile[fileNames.size()];
        getTileImage();
    }

    public void getTileImage() {
        for (int i = 0; i < fileNames.size(); i++){
            String tileName;
            boolean tileCol;

            tileName = fileNames.get(i);

            if (tileCols.get(i).equals("true")) {
                tileCol = true;
            } else {
                tileCol = false;
            }

            setup(i, tileName, tileCol);
        }
    }

    public void setup(int index, String imageName, boolean collision) {
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/MapTiles/" + imageName)));
            tile[index].collision = collision;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setActiveMap(GameMap map) {
        this.currentActiveMap = map;
        System.out.println("TileManager active map set to: " + map.getMapName());
    }

    public void draw(Graphics2D g2) {
        if (currentActiveMap == null || currentActiveMap.getTileData() == null) {
//            System.err.println("Error: No active map or tile data in TileManager to draw.");
            return;
        }

        int[][] mapTileNum = currentActiveMap.getTileData(); // Ambil data dari map aktif

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.getTileSize();
            int worldY = worldRow * gp.getTileSize();
            int screenX = worldX - gp.getPlayerView().worldX + gp.getPlayerView().getScreenX();
            int screenY = worldY - gp.getPlayerView().worldY + gp.getPlayerView().getScreenY();

            // STOPING AT EDGE
            if (gp.getPlayerView().getScreenX() > gp.getPlayerView().worldX) {
                screenX = worldX;
            } if (gp.getPlayerView().getScreenY() > gp.getPlayerView().worldY) {
                screenY = worldY;
            }

            int rightOffSet = gp.screenWidth - gp.getPlayerView().getScreenX();
            if (rightOffSet > gp.worldWidth - gp.getPlayerView().worldX) {
                screenX = gp.screenWidth - (gp.worldWidth - worldX);
            }
            int bottomOffSet = gp.screenHeight - gp.getPlayerView().getScreenY();
            if (bottomOffSet > gp.worldHeight - gp.getPlayerView().worldY) {
                screenY = gp.screenHeight - (gp.worldHeight - worldY);
            }

            // DRAW THE TILE
            if (worldX + gp.getTileSize() > gp.getPlayerView().worldX - gp.getPlayerView().getScreenX() &&
                    worldX - gp.getTileSize() < gp.getPlayerView().worldX + gp.getPlayerView().getScreenX() &&
                    worldY + gp.getTileSize() > gp.getPlayerView().worldY - gp.getPlayerView().getScreenY() &&
                    worldY - gp.getTileSize() < gp.getPlayerView().worldY + gp.getPlayerView().getScreenY()) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
            } else if (gp.getPlayerView().getScreenX() > gp.getPlayerView().worldX ||
                    gp.getPlayerView().getScreenY() > gp.getPlayerView().worldY ||
                    rightOffSet > gp.worldWidth - gp.getPlayerView().worldX ||
                    bottomOffSet > gp.worldHeight - gp.getPlayerView().worldY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

    // Metode baru untuk mendapatkan jenis tile di posisi pemain
    public String getTileTypeAtPlayerPosition(int worldX, int worldY) {
        if (currentActiveMap == null || currentActiveMap.getTileData() == null) {
            return "Unknown";
        }
        int tileCol = (worldX + gp.getTileSize() / 2) / gp.getTileSize();
        int tileRow = (worldY + gp.getTileSize() / 2) / gp.getTileSize();

        if (tileCol >= 0 && tileCol < gp.maxWorldCol && tileRow >= 0 && tileRow < gp.maxWorldRow) {
            int tileNum = currentActiveMap.getTileData()[tileCol][tileRow]; // Ambil dari active map
            return fileNames.get(tileNum);
        }
        return "Unknown";
    }

    // Metode untuk mendapatkan jenis tile yang ada di hadapan Player
    public String getTileTypeInFrontOfPlayer(int playerWorldX, int playerWorldY, String playerDirection) {
        if (currentActiveMap == null || currentActiveMap.getTileData() == null) {
            return "Unknown";
        }
        playerWorldX += gp.getTileSize() / 2;
        playerWorldY += gp.getTileSize() / 2;

        int targetCol = playerWorldX / gp.getTileSize();
        int targetRow = playerWorldY / gp.getTileSize();

        switch (playerDirection) {
            case "up":
                targetRow-=2;
                break;
            case "down":
                targetRow+=2;
                break;
            case "left":
                targetCol-=2;
                break;
            case "right":
                targetCol+=2;
                break;
        }

        if (targetCol >= 0 && targetCol < gp.maxWorldCol && targetRow >= 0 && targetRow < gp.maxWorldRow) {
            int tileNum = currentActiveMap.getTileData()[targetCol][targetRow]; // Ambil dari active map
            return fileNames.get(tileNum);
        }

        return "Unknown";
    }

    public void changeTile(int col, int row, String newTileFileName) {
        if (currentActiveMap == null || currentActiveMap.getTileData() == null) {
            System.err.println("Error: Cannot change tile, no active map or tile data.");
            return;
        }
        if (col >= 0 && col < gp.maxWorldCol && row >= 0 && row < gp.maxWorldRow) {
            int newTileIndex = fileNames.indexOf(newTileFileName);
            if (newTileIndex != -1) {
                currentActiveMap.getTileData()[col][row] = newTileIndex; // Ubah pada active map
                System.out.println("Tile at [" + col + "," + row + "] changed to " + newTileFileName + " in " + currentActiveMap.getMapName());
            } else {
                System.err.println("Error: Tile file name '" + newTileFileName + "' not found in TileData.");
            }
        } else {
            System.err.println("Error: Attempted to change tile out of bounds at [" + col + "," + row + "]");
        }
    }

    public GameMap getActiveMap() {
        return currentActiveMap;
    }
}
