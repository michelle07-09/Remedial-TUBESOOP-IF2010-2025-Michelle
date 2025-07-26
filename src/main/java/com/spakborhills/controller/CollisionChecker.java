
package com.spakborhills.controller;

import com.spakborhills.model.entity.Entity;
import com.spakborhills.model.entity.PlayerView;
import com.spakborhills.model.entity.npc.NPCView;
import com.spakborhills.view.gui.GamePanel;
import com.spakborhills.model.entity.npc.NPCRegistry;

import java.awt.Rectangle;
import java.util.ArrayList;

public class CollisionChecker {

    GamePanel gp;


    public CollisionChecker(GamePanel gp) {

        this.gp = gp;

    }

    public void checkTile(Entity entity) {
        // Amankan jika tidak ada peta aktif
        if (gp.tileM.getActiveMap() == null || gp.tileM.getActiveMap().getTileData() == null) {
            entity.collisionOn = false; // Tidak ada kolisi jika tidak ada peta
            System.err.println("Error: No active map data in TileManager for tile collision check.");
            return;
        }

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.getTileSize();
        int entityRightCol = entityRightWorldX / gp.getTileSize();
        int entityTopRow = entityTopWorldY / gp.getTileSize();
        int entityBottomRow = entityBottomWorldY / gp.getTileSize();

        // Prediksi posisi setelah bergerak untuk kolisi
        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.getTileSize();
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.getTileSize();
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.getTileSize();
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.getTileSize();
                break;
        }

        // --- Perbaikan Utama: Pengecekan Batas Yang Lebih Ketat ---
        // Pastikan koordinat kolom dan baris tetap dalam batas peta
        // sebelum mencoba mengakses data tile.
        // Jika di luar batas, set collisionOn = true dan return.
        if (entityLeftCol < 0 || entityLeftCol >= gp.maxWorldCol ||
                entityRightCol < 0 || entityRightCol >= gp.maxWorldCol ||
                entityTopRow < 0 || entityTopRow >= gp.maxWorldRow ||
                entityBottomRow < 0 || entityBottomRow >= gp.maxWorldRow) {

            entity.collisionOn = true;
            System.out.println("Collision: Entity tried to move out of world bounds. Current Col/Row: " + entityLeftCol + "," + entityTopRow + " Max: " + gp.maxWorldCol + "," + gp.maxWorldRow);
            return;
        }

        // Amankan lagi jika perhitungan nextEntity...Col/Row melampaui batas setelah speed
        // Ini memastikan indeks yang digunakan untuk mengakses mapTileNum aman
        int tileCol1 = Math.max(0, Math.min(entityLeftCol, gp.maxWorldCol - 1));
        int tileRow1 = Math.max(0, Math.min(entityTopRow, gp.maxWorldRow - 1));
        int tileCol2 = Math.max(0, Math.min(entityRightCol, gp.maxWorldCol - 1));
        int tileRow2 = Math.max(0, Math.min(entityBottomRow, gp.maxWorldRow - 1));

        // --- Akses data tile dari GameMap yang aktif ---
        int tileNum1 = gp.tileM.getActiveMap().getTileData()[tileCol1][tileRow1];
        int tileNum2 = gp.tileM.getActiveMap().getTileData()[tileCol2][tileRow2];

        // Cek kolisi tile
        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            entity.collisionOn = true;
        }
    }

    /**
     * Memeriksa kolisi antara satu Entity (misalnya Player) dengan daftar NPC.
     *
     * @param entity     Entity yang sedang bergerak (misalnya PlayerView).
     * @param targetList Daftar NPCView yang mungkin berkolisi.
     * @return Indeks dari NPC yang berkolisi, atau 999 jika tidak ada kolisi.
     */
    public int checkEntity(Entity entity, ArrayList<NPCView> targetList) {
        int index = 999;

        // Simpan posisi solidArea asli (offset relatif dari worldX, worldY)
        int entitySolidAreaOriginalX = entity.solidArea.x;
        int entitySolidAreaOriginalY = entity.solidArea.y;

        // Hitung posisi solidArea entitas yang bergerak dalam koordinat dunia
        entity.solidArea.x = entity.worldX + entitySolidAreaOriginalX;
        entity.solidArea.y = entity.worldY + entitySolidAreaOriginalY;

        for (int i = 0; i < targetList.size(); i++) {
            NPCView target = targetList.get(i);
            if (target != null) {
                // Simpan posisi solidArea asli target (offset relatif dari worldX, worldY)
                int targetSolidAreaOriginalX = target.solidArea.x;
                int targetSolidAreaOriginalY = target.solidArea.y;

                // Hitung posisi solidArea entitas target (NPC) dalam koordinat dunia
                target.solidArea.x = target.worldX + targetSolidAreaOriginalX;
                target.solidArea.y = target.worldY + targetSolidAreaOriginalY;

                // Memprediksi posisi entitas yang bergerak setelah perpindahan
                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }

                // Cek apakah area solid bertabrakan dan target memiliki kolisi aktif
                if (entity.solidArea.intersects(target.solidArea) && target.collisionOn) {
                    entity.collisionOn = true; // Set collisionOn pada entity yang bergerak
                    index = i; // Mengembalikan indeks NPC yang berkolisi
                    // Tidak break, karena kita ingin entity.collisionOn tetap true jika ada NPC lain
                }

                // Reset posisi solidArea entitas yang bergerak kembali ke posisi asli untuk loop berikutnya
                // Ini penting agar perhitungan untuk NPC berikutnya dimulai dari posisi asli entitas yang bergerak
                entity.solidArea.x = entity.worldX + entitySolidAreaOriginalX;
                entity.solidArea.y = entity.worldY + entitySolidAreaOriginalY;

                // Reset posisi solidArea entitas target kembali ke posisi asli untuk loop berikutnya
                target.solidArea.x = target.worldX + targetSolidAreaOriginalX;
                target.solidArea.y = targetSolidAreaOriginalY;
            }
        }
        return index;
    }

    // Fungsi tambahan: pengecekan tabrakan dengan NPC dari daftar yang diberikan
    public boolean isCollidingWithNPC(Entity entity, ArrayList<NPCView> targetList) {
        Rectangle entityBox = new Rectangle(
                entity.worldX + entity.solidArea.x,
                entity.worldY + entity.solidArea.y,
                entity.solidArea.width,
                entity.solidArea.height
        );
        for (NPCView npc : targetList) {
            Rectangle npcBox = new Rectangle(
                    npc.worldX + npc.solidArea.x,
                    npc.worldY + npc.solidArea.y,
                    npc.solidArea.width,
                    npc.solidArea.height
            );
            if (entityBox.intersects(npcBox)) {
                return true;
            }
        }
        return false;
    }

    // Fungsi tambahan: mengembalikan NPC yang sedang ditabrak oleh player (untuk interaksi ENTER)
    public NPCView getCollidingNPC(Entity entity, ArrayList<NPCView> targetList) {
        Rectangle entityBox = new Rectangle(
                entity.worldX + entity.solidArea.x,
                entity.worldY + entity.solidArea.y,
                entity.solidArea.width,
                entity.solidArea.height
        );
        for (NPCView npc : targetList) {
            Rectangle npcBox = new Rectangle(
                    npc.worldX + npc.solidArea.x,
                    npc.worldY + npc.solidArea.y,
                    npc.solidArea.width,
                    npc.solidArea.height
            );
            if (entityBox.intersects(npcBox)) {
                return npc;
            }
        }
        return null;
    }
}
