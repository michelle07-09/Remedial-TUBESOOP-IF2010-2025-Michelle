package com.spakborhills.model.items.fish;

import com.spakborhills.model.game.Season;
import com.spakborhills.model.game.Weather;
import com.spakborhills.model.items.Rarity;

import java.util.*;

public class FishRegistry {
    private static final Map<String, Fish> fishPrototypes = new HashMap<>();

    public static void registerFish(Fish prototype) {
        fishPrototypes.put(prototype.getName(), prototype);
        //System.out.println("Berhasil naro " + prototype.getName());
    }

    public static Fish getFishPrototype(String name) {
        return fishPrototypes.get(name);
    }

    public static Set<String> getAvailableFishNames() {
        return new HashSet<>(fishPrototypes.keySet());
    }

    static {
        List<Season> anyFishSeasons = Arrays.asList(Season.values());
        List<Weather> anyFishWeather = Arrays.asList(Weather.values());
        List<Integer> anyFishStartTime = Arrays.asList(0);
        List<Integer> anyFishEndTime = Arrays.asList(24);

        Fish bullhead = new Fish("Bullhead", anyFishSeasons, anyFishStartTime, anyFishEndTime, anyFishWeather, Arrays.asList("Mountain Lake"), Rarity.COMMON);
        registerFish(bullhead);

        Fish carp = new Fish("Carp", anyFishSeasons, anyFishStartTime, anyFishEndTime, anyFishWeather, Arrays.asList("Mountain Lake", "Pond"), Rarity.COMMON);
        registerFish(carp);

        Fish chub = new Fish("Chub", anyFishSeasons, anyFishStartTime, anyFishEndTime, anyFishWeather, Arrays.asList("Forest River", "Mountain Lake"), Rarity.COMMON);
        registerFish(chub);

        Fish largemouthBass = new Fish("Largemouth Bass", anyFishSeasons, Arrays.asList(6), Arrays.asList(18), anyFishWeather, Arrays.asList("Mountain Lake"), Rarity.REGULAR);
        registerFish(largemouthBass);

        Fish rainbowTrout = new Fish("Rainbow Trout", Arrays.asList(Season.SUMMER), Arrays.asList(6), Arrays.asList(18), Arrays.asList(Weather.SUNNY), Arrays.asList("Forest River", "Mountain Lake"), Rarity.REGULAR);
        registerFish(rainbowTrout);

        Fish sturgeon = new Fish("Sturgeon", Arrays.asList(Season.SUMMER, Season.WINTER), anyFishStartTime, anyFishEndTime, anyFishWeather, Arrays.asList("Mountain Lake"), Rarity.REGULAR);
        registerFish(sturgeon);

        Fish midnightCarp = new Fish("Midnight Carp", Arrays.asList(Season.WINTER, Season.FALL), Arrays.asList(20), Arrays.asList(2), anyFishWeather, Arrays.asList("Mountain Lake", "Pond"), Rarity.REGULAR);
        registerFish(midnightCarp);

        Fish flounder = new Fish("Flounder", Arrays.asList(Season.SPRING, Season.SUMMER), Arrays.asList(6), Arrays.asList(22), anyFishWeather, Arrays.asList("Ocean"), Rarity.REGULAR);
        registerFish(flounder);

        Fish halibut = new Fish("Hallibut", anyFishSeasons, Arrays.asList(6, 19), Arrays.asList(11, 22), anyFishWeather, Arrays.asList("Ocean"), Rarity.REGULAR);
        registerFish(halibut);

        Fish octopus = new Fish("Octopus", Arrays.asList(Season.SUMMER), Arrays.asList(6), Arrays.asList(22), anyFishWeather, Arrays.asList("Ocean"), Rarity.REGULAR);
        registerFish(octopus);

        Fish pufferfish = new Fish("Pufferfish", Arrays.asList(Season.SUMMER), anyFishStartTime, Arrays.asList(16), Arrays.asList(Weather.SUNNY), Arrays.asList("Ocean"), Rarity.REGULAR);
        registerFish(pufferfish);

        Fish sardine = new Fish("Sardine", anyFishSeasons, Arrays.asList(6), Arrays.asList(18), anyFishWeather, Arrays.asList("Ocean"), Rarity.REGULAR);
        registerFish(sardine);

        Fish supercucumber = new Fish("Super Cucumber", Arrays.asList(Season.SUMMER, Season.FALL, Season.WINTER), Arrays.asList(18), Arrays.asList(2), anyFishWeather, Arrays.asList("Ocean"), Rarity.REGULAR);
        registerFish(supercucumber);

        Fish catfish = new Fish("Catfish", Arrays.asList(Season.SPRING, Season.FALL, Season.WINTER), Arrays.asList(6), Arrays.asList(22), Arrays.asList(Weather.RAINY), Arrays.asList("Forest River", "Pond"), Rarity.REGULAR);
        registerFish(catfish);

        Fish salmon = new Fish("Salmon", Arrays.asList(Season.FALL), Arrays.asList(6), Arrays.asList(18), anyFishWeather, Arrays.asList("Forest River"), Rarity.REGULAR);
        registerFish(salmon);

        List<Integer> legendaryFishStartTime = Arrays.asList(8);
        List<Integer> legendaryFishEndTime = Arrays.asList(20);

        Fish angler = new Fish("Angler", Arrays.asList(Season.FALL), legendaryFishStartTime, legendaryFishEndTime, anyFishWeather, Arrays.asList("Pond"), Rarity.LEGENDARY);
        registerFish(angler);

        Fish crimsonfish = new Fish("Crimsonfish", Arrays.asList(Season.SUMMER), legendaryFishStartTime, legendaryFishEndTime, anyFishWeather, Arrays.asList("Ocean"), Rarity.LEGENDARY);
        registerFish(crimsonfish);

        Fish glacierfish = new Fish("Glacierfish", Arrays.asList(Season.WINTER), legendaryFishStartTime, legendaryFishEndTime, anyFishWeather, Arrays.asList("Forest River"), Rarity.LEGENDARY);
        registerFish(glacierfish);

        Fish legend = new Fish("Legend", Arrays.asList(Season.SPRING), legendaryFishStartTime, legendaryFishEndTime, Arrays.asList(Weather.RAINY), Arrays.asList("Mountain Lake"), Rarity.LEGENDARY);
        registerFish(legend);
    }
}
