package com.spakborhills.model.game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class GameTime extends Subject {
    private static GameTime instance; //singleton

    private Season season = Season.SPRING;
    private long startTime;
    private int totalGameMinutes;

    private final int gameMinutesPerSecond = 5; //konversi waktu 1 detik =  5 menit in game
    double timePerInGameMinute = 1.0 / gameMinutesPerSecond;
    private double carryOverTime = 0; //waktu sisa update, buat presisi

    //waktu in game
    private int inGameMinutes;
    private int inGameHours;
    private int inGameDays;
    private int inGameSeason;

    private Weather weather;
    private Random random = new Random();
    private ArrayList<Integer> rainyDays = new ArrayList<>();
    private int nextRainyDayIdx = 0;

    private GameTime() {
        randomizeRainyDay();
        changeWeather();
        totalGameMinutes = 0; //testing purpose
        startTime = 0;
        inGameMinutes = 0;
        inGameHours = 6;
        inGameDays = 1;
        inGameSeason = 0;
    }

    public static synchronized GameTime getInstance() {
        if (instance == null) {
            instance = new GameTime();
        }
        return instance;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void updateGameTime() {
        long currentTime = System.nanoTime();
        double deltaTime = (double) (currentTime - startTime) / 1_000_000_000;
        deltaTime += carryOverTime;

        //waktu diupdate tiap 0.2 detik alias tiap 1 menit in game
        while(deltaTime >= timePerInGameMinute) {
            inGameMinutes += 1;
            totalGameMinutes += 1;
            normalizeTime();
            deltaTime -= timePerInGameMinute;
        }

        carryOverTime = deltaTime;
        startTime = currentTime;
    }

    public void displayGameTime(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.setFont(new Font("Comic Sans", Font.PLAIN, 30));
        g2.drawString(season.seasonToString(inGameSeason) + ", " + inGameDays, 400, 30);
        g2.drawString(inGameHours + ":" + inGameMinutes, 500, 60);
        g2.drawString(weather.toString(), 480, 90);
    }

    public void advanceGameTime(int gameMinutes){
        totalGameMinutes += gameMinutes;
        inGameMinutes += gameMinutes;
        normalizeTime();
    }

    private void normalizeTime(){ //normalisasi satuan waktu
        if (inGameMinutes >= 60) {
            inGameHours += inGameMinutes / 60;
            inGameMinutes %= 60;
        }

        if (inGameHours >= 24) {
            inGameDays += 1;
            inGameHours = 0;
            changeWeather();
            notifyObservers("DAYS CHANGED", weather);
        }

        if (inGameDays >= 11) {
            inGameSeason += 1;
            inGameDays = 1;
            randomizeRainyDay();
            notifyObservers("SEASON CHANGED", season);
        }

        if (inGameSeason >= 4) {
            inGameSeason = 0;
        }
    }

    private void randomizeRainyDay() {
        System.out.println("Randomizing rainy day");
        rainyDays.clear();
        rainyDays.add(random.nextInt(10) + 1);
        int secondRainyDay;
        do{
            secondRainyDay = random.nextInt(10) + 1;
        } while(rainyDays.get(0) == secondRainyDay);
        rainyDays.add(secondRainyDay);
        Collections.sort(rainyDays);

        nextRainyDayIdx = 0;
    }

    private void changeWeather(){
        System.out.println("Changing weather");
        if(nextRainyDayIdx < rainyDays.size() && inGameDays==rainyDays.get(nextRainyDayIdx)){
            weather = Weather.RAINY;
            nextRainyDayIdx++;
        }
        else{
            if (random.nextDouble() < 0.2){
                weather = Weather.RAINY;
            }
            else{
                weather = Weather.SUNNY;
            }
        }
    }

    public Weather getWeather() {
        return weather;
    }

    public int getTotalGameMinutes() {
        return totalGameMinutes;
    }

    public int getInGameHours(){
        return inGameHours;
    }

    public int getInGameMinutes() {
        return inGameMinutes;
    }

    public Season getSeason() {
        return season;
    }

    public void startNewDay(int minuteTo2){
        advanceGameTime(2400 + minuteTo2);
        normalizeTime();
        inGameHours = 6;
        inGameMinutes = 0;
        totalGameMinutes += 2400 + minuteTo2;
    }
}
