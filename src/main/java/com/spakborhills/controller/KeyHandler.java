package com.spakborhills.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, spacePressed, inventoryPressed;

    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_W, KeyEvent.VK_UP:
                upPressed = true;
                break;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN:
                downPressed = true;
                break;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT:
                leftPressed = true;
                break;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT:
                rightPressed = true;
                break;
            case KeyEvent.VK_ENTER:
                enterPressed = true;
                break;
            case KeyEvent.VK_SPACE :
                spacePressed = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_W:
                upPressed = false;
                break;
            case KeyEvent.VK_S:
                downPressed = false;
                break;
            case KeyEvent.VK_A:
                leftPressed = false;
                break;
            case KeyEvent.VK_D:
                rightPressed = false;
                break;
            case KeyEvent.VK_UP:
                upPressed = false;
                break;
            case KeyEvent.VK_DOWN:
                downPressed = false;
                break;
            case KeyEvent.VK_LEFT:
                leftPressed = false;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = false;
                break;
            case KeyEvent.VK_ENTER:
                enterPressed = false;
                break;
            case KeyEvent.VK_SPACE :
                spacePressed = false;
                break;
            case KeyEvent.VK_I:
                inventoryPressed = !inventoryPressed;
                break;
        }
    }

    public boolean isUpPressed() {
        return upPressed;
    }
    public boolean isDownPressed() {
        return downPressed;
    }
    public boolean isLeftPressed() {
        return leftPressed;
    }
    public boolean isRightPressed() {
        return rightPressed;
    }
    public boolean isEnterPressed() {return enterPressed;}
    public boolean isSpacePressed() {return spacePressed;}
    public boolean isInventoryPressed() {return inventoryPressed;}
    public void setInventoryPressed(boolean inventoryPressed) {this.inventoryPressed = inventoryPressed;}
    public void setEnterPressed(boolean enterPressed) {this.enterPressed = enterPressed;}
    public void setSpacePressed(boolean spacePressed) {this.spacePressed = spacePressed;}

    public void resetKeys(){
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
    }
}
