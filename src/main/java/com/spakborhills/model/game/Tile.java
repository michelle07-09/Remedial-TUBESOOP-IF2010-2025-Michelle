package com.spakborhills.model.game;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public boolean collision = false;
}

// 0 = tillable land
// 1 = house
// 2 = shipping bin
// 3 = tilled land
// 4 = planted land
// 5 = pond
