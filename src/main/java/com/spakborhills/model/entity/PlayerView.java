package com.spakborhills.model.entity;

import com.spakborhills.view.gui.GamePanel;
import com.spakborhills.controller.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class PlayerView extends Entity{
    private int screenX, screenY;
    private Player player;
    private String currentMap;
    private String currentTileType;
    private String frontTileType;

    KeyHandler keyH;

    public PlayerView(GamePanel gp, KeyHandler keyH, Player player) {
       super(gp);

       this.keyH = keyH;
       this.player = player;
       this.currentMap = gp.getCurrentMap();

       screenX = gp.screenWidth/2 - gp.getTileSize()/2;
       screenY = gp.screenHeight/2 - gp.getTileSize()/2;

       solidArea = new Rectangle(12, 18, 18, 27);

       if (currentMap.equals("farm")) {
           setDefaultValues(118, 120);
       } else if (currentMap.equals("world")) {
           setDefaultValues(216, 151);
       }
       getPlayerImage();
    }

    public void setDefaultValues(int x, int y){
        setWorldX(gp.getTileSize() * x);
        setWorldY(gp.getTileSize() * y);
        speed = 7;
        direction = "down";
    }

    public void getPlayerImage(){
        try {
            front1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/downidle.png")));
            front2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/down01.png")));
            front3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/downidle.png")));
            front4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/down02.png")));
            back1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/upidle.png")));
            back2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/up01.png")));
            back3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/upidle.png")));
            back4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/up02.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/leftidle.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/left01.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/leftidle.png")));
            left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/left02.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/rightidle.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/right01.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/rightidle.png")));
            right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/sprites/player/right02.png")));

        } catch (IOException e) {
            System.out.println("Gagal load gambar!");
            e.printStackTrace();
        }
    }

    public void update(){

        if(keyH.isUpPressed() || keyH.isDownPressed() || keyH.isLeftPressed() || keyH.isRightPressed()) {

            if (keyH.isUpPressed()) {
                direction = "up";
            } else if (keyH.isLeftPressed()) {
                direction = "left";
            } else if (keyH.isDownPressed()) {
                direction = "down";
            } else if (keyH.isRightPressed()) {
                direction = "right";
            }

            collisionOn = false;
            gp.cChecker.checkTile(this);

            if (!collisionOn){
                switch (direction){
                    case "up" : setWorldY(getWorldY() - speed); break;
                    case "down" : setWorldY(getWorldY() + speed); break;
                    case "left" : setWorldX(getWorldX() - speed); break;
                    case "right" : setWorldX(getWorldX() + speed); break;
                    default: break;
                }
            }

            // Dapatkan jenis tile yang sedang diinjak pemain
            currentTileType = gp.tileM.getTileTypeAtPlayerPosition(getWorldX(), getWorldY());
            frontTileType = gp.tileM.getTileTypeInFrontOfPlayer(getWorldX(), getWorldY(), direction);

            spriteCounter++;
            if (!collisionOn){
                if (spriteCounter > 10){
                    if (spriteNum == 1) {
                        spriteNum = 2;
                    } else if (spriteNum == 2) {
                        spriteNum = 3;
                    } else if (spriteNum == 3) {
                        spriteNum = 4;
                    } else if (spriteNum == 4) {
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
            }
        }
        else {
            spriteNum = 1;
        }
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;

        switch (direction) {
            case "down" :
                if (spriteNum == 1) {
                    image = front1;
                } else if (spriteNum == 2) {
                    image = front2;
                } else if (spriteNum == 3) {
                    image = front3;
                } else {
                    image = front4;
                }
                break;
            case "up" :
                if (spriteNum == 1) {
                    image = back1;
                } else if (spriteNum == 2) {
                    image = back2;
                } else if (spriteNum == 3) {
                    image = back3;
                } else {
                    image = back4;
                }
                break;
            case "left" :
                if (spriteNum == 1) {
                    image = left1;
                } else if (spriteNum == 2) {
                    image = left2;
                } else if (spriteNum == 3) {
                    image = left3;
                } else {
                    image = left4;
                }
                break;
            case "right" :
                if (spriteNum == 1) {
                    image = right1;
                } else if (spriteNum == 2) {
                    image = right2;
                } else if (spriteNum == 3) {
                    image = right3;
                } else {
                    image = right4;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public String getCurrentTileType() {
        return currentTileType;
    }

    public String getFrontTileType() {return frontTileType;}
}
