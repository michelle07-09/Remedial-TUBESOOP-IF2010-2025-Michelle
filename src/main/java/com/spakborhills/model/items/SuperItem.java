package com.spakborhills.model.items;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import com.spakborhills.view.gui.GamePanel;

public class SuperItem {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
//    public Rectangle solidArea;
//    public int solidAreaDefaultX = 0;
//    public int solidAreaDefaultY = 0;

    public void draw(Graphics2D g2, GamePanel gp, int scale) {
        int screenX = worldX - gp.getPlayerView().worldX + gp.getPlayerView().getScreenX();
        int screenY = worldY - gp.getPlayerView().worldY + gp.getPlayerView().getScreenY();
        int objSize = gp.getTileSize() * scale;

//        Rectangle solidArea = new Rectangle(worldX, worldY, objSize, objSize);

        if (worldX + objSize > gp.getPlayerView().worldX - gp.getPlayerView().getScreenX() &&
                worldX - objSize < gp.getPlayerView().worldX + gp.getPlayerView().getScreenX() &&
                worldY + objSize > gp.getPlayerView().worldY - gp.getPlayerView().getScreenY() &&
                worldY - objSize < gp.getPlayerView().worldY + gp.getPlayerView().getScreenY()) {
            g2.drawImage(image, gp.getPlayerView().getScreenX(), gp.getPlayerView().getScreenY(), objSize, objSize, null);
        }
    }
}

