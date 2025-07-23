package com.spakborhills.model.entity;

import com.spakborhills.view.gui.GamePanel;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    public GamePanel gp;
    public int worldX, worldY;
    public int speed;

    public BufferedImage front1, front2, front3, front4, back1, back2, back3, back4, right1, right2, right3, right4, left1, left2, left3, left4;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea;
    public boolean collisionOn = false;

    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public Rectangle getSolidArea() {return solidArea;}
}
