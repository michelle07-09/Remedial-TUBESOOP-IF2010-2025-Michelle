package com.spakborhills.controller;

import com.spakborhills.model.game.GameTime;

public class GameLoop implements Runnable {
    private Thread gameThread; //thread utama
    private final GameTime gameTime = GameTime.getInstance(); //time utama
    private boolean isRunning = false;
    private final int fps;
    private final Runnable updateLogic;
    private final Runnable repaintPanel;
    
    public GameLoop(int fps, Runnable updateLogic, Runnable repaintPanel) {
        this.fps = fps;
        this.updateLogic = updateLogic;
        this.repaintPanel = repaintPanel;
    }

    public void startGame() {
        if (gameThread == null || !gameThread.isAlive()) {
            isRunning = true;
            gameThread = new Thread(this);
            gameThread.start();
            gameTime.setStartTime(System.nanoTime());
        }
        else{
            isRunning = true;
            gameTime.setStartTime(System.nanoTime());
        }
    }

    public void pause() {
        isRunning = false;
    }


    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / fps; //setting fps
        long lastTime = System.nanoTime();
        long currentTime;
        double delta = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (isRunning && delta >= 1) { //update logic dan paint
                updateLogic.run();
                repaintPanel.run();
                delta--;
            }

            if (!isRunning) {  //hemat CPU kalau lagi pause
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Tidur sementara untuk menjaga FPS tetap akurat
            try {
                Thread.sleep((long) (drawInterval / 1_000_000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public GameTime getGameTime() {
        return gameTime;
    }

    public boolean isRunning() {
        return isRunning;
    }
}