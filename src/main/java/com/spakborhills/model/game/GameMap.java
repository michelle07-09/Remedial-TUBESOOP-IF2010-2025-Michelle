package com.spakborhills.model.game;

import com.spakborhills.view.gui.GamePanel; // Diperlukan untuk gp.maxWorldCol, gp.maxWorldRow, gp.getTileSize()
import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.util.Objects;

public class GameMap {
    private String mapName; // e.g., "farm", "world", "house"
    private String mapFilePath; // e.g., "/assets/FarmMaps/farm_map.txt"
    private int[][] tileData; // Data tile untuk peta ini

    // Referensi ke GamePanel untuk mendapatkan ukuran dunia
    private GamePanel gp;

    public GameMap(GamePanel gp, String mapName, String mapFilePath) {
        this.gp = gp;
        this.mapName = mapName;
        this.mapFilePath = mapFilePath;
        this.tileData = new int[gp.maxWorldCol][gp.maxWorldRow];
        loadMapData(); // Muat data peta saat GameMap dibuat
    }

    private void loadMapData() {
        try {
            InputStream is = getClass().getResourceAsStream(mapFilePath);
            if (is == null) {
                throw new FileNotFoundException("Map file not found: " + mapFilePath);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                if (line == null) break; // Berhenti jika sudah akhir file

                String[] numbers = line.split(" ");

                while (col < gp.maxWorldCol) {
                    if (col < numbers.length) {
                        int num = Integer.parseInt(numbers[col]);
                        tileData[col][row] = num;
                    } else {
                        // Jika baris lebih pendek dari maxWorldCol, isi dengan tile default (misal 0)
                        tileData[col][row] = 0;
                    }
                    col++;
                }
                col = 0;
                row++;
            }
            br.close();
            System.out.println("Successfully loaded map data for: " + mapName + " from " + mapFilePath);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to load map data for " + mapName + " from: " + mapFilePath);
            // Inisialisasi tileData dengan default (misal tile 0, yang diasumsikan tidak kolisi)
            for (int i = 0; i < gp.maxWorldCol; i++) {
                for (int j = 0; j < gp.maxWorldRow; j++) {
                    tileData[i][j] = 0; // Set ke tile 0 (misal rumput)
                }
            }
        }
    }

    public String getMapName() {
        return mapName;
    }

    public int[][] getTileData() {
        return tileData;
    }

    // Metode untuk mendapatkan tileData pada koordinat tertentu
    public int getTile(int col, int row) {
        if (col >= 0 && col < gp.maxWorldCol && row >= 0 && row < gp.maxWorldRow) {
            return tileData[col][row];
        }
        return -1; // Atau lempar exception, tergantung penanganan error
    }

    // Metode untuk mengubah tileData pada koordinat tertentu
    public void setTile(int col, int row, int tileIndex) {
        if (col >= 0 && col < gp.maxWorldCol && row >= 0 && row < gp.maxWorldRow) {
            tileData[col][row] = tileIndex;
        }
    }
}
