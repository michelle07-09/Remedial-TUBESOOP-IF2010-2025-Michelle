package com.spakborhills.model.entity.npc;

import com.spakborhills.model.entity.Entity;
import com.spakborhills.view.gui.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class NPCView extends Entity {
    private NPC npc;
    private String currentMapName;
    private BufferedImage standSprite, jumpSprite;  // Sprite saat melompat
    private int initialWorldX, initialWorldY;

    private int animationCounter = 0;
    private int animationSpeed = 20; // Kecepatan animasi (frame per fase)
    private boolean isJumpingSprite = false;

    private String facingDirection = "down";

    public NPCView(GamePanel gp, String name, int worldX, int worldY) { // <-- Tambah spritePathJump
        super(gp);
        this.npc = NPCRegistry.getNPCPrototype(name);
        this.initialWorldX = gp.getTileSize() * worldX;
        this.initialWorldY = gp.getTileSize() * worldY;
        this.worldX = this.initialWorldX;
        this.worldY = this.initialWorldY;

        this.collisionOn = false;

        this.currentMapName = "house";

        solidArea = new Rectangle(12, 18, 18, 27);
        solidArea.x = (gp.getTileSize() - solidArea.width) / 2;
        solidArea.y = (gp.getTileSize() - solidArea.height);

        getNPCImage();
        direction = "down";
    }

    public void getNPCImage() {
        try {
            standSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/npc/" + npc.getName().split(" ")[0] + "_berdiri.png")));
            jumpSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/npc/" + npc.getName().split(" ")[0] + "_pendek.png"))); //

            front1 = standSprite;

        } catch (IOException e) {
            System.out.println("Gagal load gambar NPC: " + npc.getName());
            e.printStackTrace();
        }
    }

    public void update() {
        animationCounter++;
        if (animationCounter >= animationSpeed) { // Setiap 'animationSpeed' frame
            animationCounter = 0; // Reset counter
            isJumpingSprite = !isJumpingSprite; // Toggle antara sprite berdiri dan sprite lompat
        }
        worldX = initialWorldX;
        worldY = initialWorldY;
    }

    public void draw(Graphics2D g2) {
        BufferedImage imageToDraw;

        if (isJumpingSprite) {
            imageToDraw = jumpSprite; // Gunakan sprite lompat saat flag true
        } else {
            imageToDraw = standSprite; // Gunakan sprite berdiri saat flag false
        }

        int screenX = worldX - gp.getPlayerView().worldX + gp.getPlayerView().getScreenX();
        int screenY = worldY - gp.getPlayerView().worldY + gp.getPlayerView().getScreenY();

        // Hanya gambar jika NPC berada di area layar yang terlihat
        if (worldX + gp.getTileSize() > gp.getPlayerView().worldX - gp.getPlayerView().getScreenX() &&
                worldX - gp.getTileSize() < gp.getPlayerView().worldX + gp.getPlayerView().getScreenX() &&
                worldY + gp.getTileSize() > gp.getPlayerView().worldY - gp.getPlayerView().getScreenY() &&
                worldY - gp.getTileSize() < gp.getPlayerView().worldY + gp.getPlayerView().getScreenY()) {
            g2.drawImage(imageToDraw, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null); // Gambar sprite yang dipilih
        }
    }


    public boolean isPlayerInInteractionRange(int playerWorldX, int playerWorldY, int tileSize, String playerDirection) {
        int playerCenterX = playerWorldX + tileSize / 2;
        int playerCenterY = playerWorldY + tileSize / 2;

        int detectionAreaSize = tileSize * 3; // 3 tile

        int detectionAreaX = playerCenterX - detectionAreaSize / 2;
        int detectionAreaY = playerCenterY - detectionAreaSize / 2;

        Rectangle playerDetectionArea = new Rectangle(detectionAreaX, detectionAreaY, detectionAreaSize, detectionAreaSize);

        Rectangle npcSolidAreaWorld = new Rectangle(worldX + solidArea.x, worldY + solidArea.y, solidArea.width, solidArea.height);

        // Debugging print
        // System.out.println("Player Detection Area: " + playerDetectionArea);
        // System.out.println("NPC Solid Area (World): " + npcSolidAreaWorld);

        return playerDetectionArea.intersects(npcSolidAreaWorld);
    }

    public NPC getNPCModel() {
        return npc;
    }
    public String getCurrentMapName() {
        return currentMapName;
    }
    public int getInitialWorldX() {
        return initialWorldX;
    }
    public int getInitialWorldY() {
        return initialWorldY;
    }
}