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
    private BufferedImage standSprite, jumpSprite;
    private int initialWorldX, initialWorldY;

    private int animationCounter = 0;
    private int animationSpeed = 20;
    private boolean isJumpingSprite = false;

    private String facingDirection = "down";

    public int solidAreaDefaultX;
    public int solidAreaDefaultY;

    public NPCView(GamePanel gp, String name, int worldX, int worldY) {
        super(gp);
        this.npc = NPCRegistry.getNPCPrototype(name);
        this.initialWorldX = gp.getTileSize() * worldX;
        this.initialWorldY = gp.getTileSize() * worldY;
        this.worldX = this.initialWorldX;
        this.worldY = this.initialWorldY;

        this.collisionOn = true;
        solidArea = new Rectangle(12, 18, 24, 24);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        this.currentMapName = "house";

        // --- BAGIAN PENTING: PEMUATAN SPRITE DI CONSTRUCTOR ---
        // NullPointerException terjadi di sini jika gambar tidak ditemukan.
        // Anda HARUS memastikan jalur ini benar di proyek Anda.
        try {
            // Contoh jalur yang sering digunakan: src/main/resources/res/npc/
            // Sesuaikan bagian "/res/npc/" ini jika lokasi file Anda berbeda.
            String baseResPath = "/assets/sprites/npc/"; // <--- SESUAIKAN JALUR INI
            String standSpriteFilename = name.toLowerCase() + "_berdiri.png";
            String jumpSpriteFilename = name.toLowerCase() + "_pendek.png";

            String standSpritePath = baseResPath + standSpriteFilename;
            String jumpSpritePath = baseResPath + jumpSpriteFilename;

            // Baris debugging ini SANGAT PENTING. Cek output konsol Anda!
            System.out.println("DEBUG (Constructor): Attempting to load stand sprite from: " + standSpritePath);
            System.out.println("DEBUG (Constructor): Attempting to load jump sprite from: " + jumpSpritePath);

            standSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(standSpritePath),
                    "Sprite 'stand' tidak ditemukan di: " + standSpritePath));
            jumpSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(jumpSpritePath),
                    "Sprite 'jump' tidak ditemukan di: " + jumpSpritePath));

        } catch (IOException | NullPointerException e) { // Tangkap NullPointerException juga
            System.err.println("ERROR: Could not load NPC sprite for: " + name + ". Please check file paths and names. Detail: " + e.getMessage());
            // Fallback atau penanganan error: coba muat sprite default
            try {
                System.out.println("DEBUG: Falling back to default player sprite...");
                standSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_down_1.png"),
                        "Fallback 'boy_down_1.png' tidak ditemukan!"));
                jumpSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_jump.png"),
                        "Fallback 'boy_jump.png' tidak ditemukan!"));
            } catch (IOException | NullPointerException ex) {
                System.err.println("FATAL ERROR: Failed to load even default player sprite. Game might not display correctly. Detail: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }


    public void getNPCImage() {
        // --- BAGIAN PENTING: PEMUATAN SPRITE DI getNPCImage() ---
        // Perhatikan bahwa jalur di sini berbeda dari constructor.
        // Pastikan jalur ini juga benar jika metode ini dipanggil.
        try {
            // Contoh jalur yang sering digunakan: src/main/resources/assets/sprites/npc/
            // Sesuaikan bagian "/assets/sprites/npc/" ini jika lokasi file Anda berbeda.
            String baseAssetsPath = "/assets/sprites/npc/"; // <--- SESUAIKAN JALUR INI
            String standFilename = npc.getName().split(" ")[0].toLowerCase() + "_berdiri.png";
            String jumpFilename = npc.getName().split(" ")[0].toLowerCase() + "_pendek.png";

            String standPath = baseAssetsPath + standFilename;
            String jumpPath = baseAssetsPath + jumpFilename;

            // Baris debugging tambahan
            System.out.println("DEBUG (getNPCImage): Attempting to load NPCImage (stand) from: " + standPath);
            System.out.println("DEBUG (getNPCImage): Attempting to load NPCImage (jump) from: " + jumpPath);

            standSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(standPath),
                    "Sprite '_berdiri' tidak ditemukan di: " + standPath));
            jumpSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(jumpPath),
                    "Sprite '_pendek' tidak ditemukan di: " + jumpPath));

            front1 = standSprite; // Asumsikan front1 didefinisikan di kelas Entity

        } catch (IOException | NullPointerException e) { // Tangkap NullPointerException juga
            System.out.println("ERROR (getNPCImage): Gagal load gambar NPC: " + npc.getName() + ". Detail: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void update() {
        animationCounter++;
        if (animationCounter >= animationSpeed) {
            animationCounter = 0;
            isJumpingSprite = !isJumpingSprite;
        }
        worldX = initialWorldX;
        worldY = initialWorldY;
    }

    public boolean isPlayerInInteractionRange(int playerWorldX, int playerWorldY, int tileSize, String playerDirection) {
        int playerCenterX = playerWorldX + tileSize / 2;
        int playerCenterY = playerWorldY + tileSize / 2;

        // Interaksi dalam jangkauan 2 tile (sesuaikan jika perlu)
        int interactionRange = tileSize * 2;
        Rectangle playerInteractionArea = new Rectangle();
        int playerReachX = playerCenterX;
        int playerReachY = playerCenterY;

        switch (playerDirection) {
            case "up": playerReachY -= tileSize; break;
            case "down": playerReachY += tileSize; break;
            case "left": playerReachX -= tileSize; break;
            case "right": playerReachX += tileSize; break;
        }

        playerInteractionArea.x = playerReachX - interactionRange / 2;
        playerInteractionArea.y = playerReachY - interactionRange / 2;
        playerInteractionArea.width = interactionRange;
        playerInteractionArea.height = interactionRange;

        Rectangle npcSolidAreaWorld = new Rectangle(worldX + solidArea.x, worldY + solidArea.y, solidArea.width, solidArea.height);

        return playerInteractionArea.intersects(npcSolidAreaWorld);
    }

    public NPC getNPCModel() {
        return npc;
    }
    public String getCurrentMapName() {
        return currentMapName;
    }

    public BufferedImage getImageToDraw() {
        if (isJumpingSprite) {
            return jumpSprite;
        } else {
            return standSprite;
        }
    }
    public String getDirection() {
        return this.direction;
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.getPlayerView().worldX + gp.getPlayerView().getScreenX();
        int screenY = worldY - gp.getPlayerView().worldY + gp.getPlayerView().getScreenY();

        if (worldX + gp.getTileSize() > gp.getPlayerView().worldX - gp.getPlayerView().getScreenX() &&
                worldX - gp.getTileSize() < gp.getPlayerView().worldX + gp.getPlayerView().getScreenX() &&
                worldY + gp.getTileSize() > gp.getPlayerView().worldY - gp.getPlayerView().getScreenY() &&
                worldY - gp.getTileSize() < gp.getPlayerView().worldY + gp.getPlayerView().getScreenY()) {
            g2.drawImage(getImageToDraw(), screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
        }
    }
}