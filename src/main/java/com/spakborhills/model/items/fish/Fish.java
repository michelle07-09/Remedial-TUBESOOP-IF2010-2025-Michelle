package com.spakborhills.model.items.fish;

import com.spakborhills.model.game.Season;
import com.spakborhills.model.game.Weather;
import com.spakborhills.model.items.Item;
import com.spakborhills.model.items.Rarity;
import com.spakborhills.model.items.behavior.Edible;

import javax.swing.*;
import java.util.*;

public class Fish extends Item implements Cloneable, Edible {
    private String name;
    private List<Season> season = new ArrayList<>();
    private List<Integer> startTimes = new ArrayList<>();
    private List<Integer> endTimes = new ArrayList<>();
    private List<Weather> weather = new ArrayList<>();
    private List<String> location = new ArrayList<String>();
    private final Rarity rarity;
    private int sellPrice;
    private Random random = new Random();


    public Fish (String name,  List<Season> season, List<Integer> startTimes, List<Integer> endTimes, List<Weather> weather,
                 List<String> location, Rarity rarity){
        super();
        this.name = name;
        this.season = season;
        this.startTimes = startTimes;
        this.endTimes = endTimes;
        this.weather = weather;
        this.location = location;
        this.rarity = rarity;

        setSellPrice();
    }

    @Override
    public String getName() {
        return name;
    }

    public Fish clone(){
        try{
            Fish cloned = (Fish) super.clone();
            cloned.season = new ArrayList<>(this.season);
            cloned.startTimes = new ArrayList<>(this.startTimes);
            cloned.endTimes = new ArrayList<>(this.endTimes);
            cloned.weather = new ArrayList<>(this.weather);
            cloned.location = new ArrayList<>(this.location);
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cannot clone Fish", e);
        }
    }
    public int countSeasons() {
        return season.size();
    }
    public int countWeather(){
        return weather.size();
    }
    public int countLocation(){
        return location.size();
    }
    public int countTime() {
        int total = 0;
        for (int i = 0; i<startTimes.size(); i++) {
            int count = Math.abs(endTimes.get(i) - startTimes.get(i));
            total += count;
        }
        return total;

    }

    public Rarity getRarity(){ return rarity; }
    @Override
    public int getSellPrice() { return sellPrice;}

    public void setSellPrice() {
        int c = switch (rarity) {
            case COMMON -> 10;
            case REGULAR -> 5;
            case LEGENDARY -> 25;
        };
        int calc;
        calc = (4 / countSeasons()) * (24 / countTime()) * (2 / countWeather()) * (4 / countLocation()) * c;
        this.sellPrice = calc;
    }

    public boolean fishingGame() {
        int luckyNumber, maxAttempt = 10, bound = 10;
        switch (this.rarity) {
            case COMMON:
                luckyNumber = random.nextInt(1, 10);
            break;
            case REGULAR:
                luckyNumber = random.nextInt(1, 100);
                bound = 100;
            break;
            case LEGENDARY:
                luckyNumber = random.nextInt(1, 500);
                maxAttempt = 7;
                bound = 500;
            break;
            default:
                luckyNumber = 0;
        }
        System.out.println("Lucky number: " + luckyNumber);
        for (int i = 0 ; i < maxAttempt ; i++) {
            String guess = JOptionPane.showInputDialog(null, "Enter your guess (1-" + bound + "): ", "Fishing Game", JOptionPane.QUESTION_MESSAGE);
            int guessInt;
            try {
                guessInt = Integer.parseInt(guess);
            } catch (NumberFormatException e) {
                System.out.println("Guess must be an integer inside range");
                return false;
            }

            if (guessInt == luckyNumber) {
                return true;
            }
        }
        return false;
    }

    public List<Season> getSeason() {
        return season;
    }

    public int getStartSpawnTime(){
        return startTimes.getFirst();
    }
    public int getEndSpawnTime(){
        return endTimes.getFirst();
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public List<String> getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fish fish = (Fish) o;
        return Objects.equals(name, fish.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

