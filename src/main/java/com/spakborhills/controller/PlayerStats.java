package com.spakborhills.controller;

import com.spakborhills.model.game.Subject;
import com.spakborhills.model.items.crops.Crops;
import com.spakborhills.model.items.fish.Fish;
import com.spakborhills.model.items.foods.Food;
import com.spakborhills.model.items.Item;
import com.spakborhills.model.game.Season;
import com.spakborhills.model.game.GameTime;
import com.spakborhills.model.entity.Entity;
import com.spakborhills.model.entity.RelationshipStatus;
import com.spakborhills.model.entity.npc.NPCRegistry;
import com.spakborhills.model.entity.npc.NPC;
import com.spakborhills.model.items.Rarity;

import java.util.*;

enum NPCInteractionType{
    CHATTING,
    GIFTING,
    VISITING;
}

class NPCInteractionTracker<T extends Enum<T>>{
    private final Map<String, EnumMap<T, Integer>> interactions;
    private final Class<T> typeClass;

    public NPCInteractionTracker(Class<T> typeClass){
        this.typeClass = typeClass;
        this.interactions = new HashMap<>();
    }

    public void trackInteraction(String npcName, T type){
        EnumMap<T, Integer> npcInteractions;
        if (!interactions.containsKey(npcName)) {
            npcInteractions = new EnumMap<>(typeClass);
            interactions.put(npcName, npcInteractions);
        } else {
            npcInteractions = interactions.get(npcName);
        }

        // Update interaction count
        int currentCount = npcInteractions.getOrDefault(type, 0);
        npcInteractions.put(type, currentCount+1);
    }

    public int getInteractionCount(String npcName, T type){
        if (interactions.containsKey(npcName)){
            return interactions.get(npcName).getOrDefault(type, 0);
        }
        return 0;
    }

    public Map<T, Integer> getAllInteractions(String npcName){
        if (interactions.containsKey(npcName)){
            return new EnumMap<>(interactions.get(npcName));
        }
        return new EnumMap<>(typeClass);
    }

    public Set<String> getAllNPCs(){
        return new HashSet<>(interactions.keySet());
    }
}

public class PlayerStats extends Subject {
    private static PlayerStats instance;

    private int totalIncome = 0;
    private int totalOutcome = 0;
    private int currentSeasonIncome = 0;
    private int currentSeasonOutcome = 0;
    private Map<Season, Integer> seasonalIncome = new HashMap<>();
    private Map<Season, Integer> seasonalOutcome = new HashMap<>();
    private double avgSeasonIncome = 0.0;
    private double avgSeasonOutcome = 0.0;

    GameTime gametime = GameTime.getInstance();

    private Map<String, RelationshipStatus> allNPCRelations = new HashMap<>();
    private NPCInteractionTracker<NPCInteractionType> npcTracker = new NPCInteractionTracker<>(NPCInteractionType.class);

    private int totalHarvest = 0;
    
    private int totalFishCaught = 0;
    private Map<Rarity, Integer> fishTypeAmt = new HashMap<>();
    //private int currentTypeFishAmt = 0;

    public static PlayerStats getInstance(){
        if (instance == null){
            instance = new PlayerStats();
        }
        return instance;
    }

    // STATS METHODS

    // total gold yang didapatkan player
    public void addToIncome(Item item){ 
        int income = item.getSellPrice();
        totalIncome += income;
        currentSeasonIncome += income;
    }

    // total gold yang dikeluarkan player
    public void addToOutcome(Item item){
        int outcome = item.getBuyPrice();
        totalOutcome += outcome;
        currentSeasonOutcome += outcome;
    }

    // total rata-rata gold yang didapatkan player per season
    public void addToAvgSeasonIncome(Season season){

        seasonalIncome.put(season, currentSeasonIncome);

        if(seasonalIncome.size() > 0){
            int sum = 0;
            for (Integer income : seasonalIncome.values()) {
                sum += income;
            }
            avgSeasonIncome = (double) sum / seasonalIncome.size();
        }
    
        currentSeasonIncome = 0; //reset ketika berganti season
    }

    // total rata-rata gold yang dikeluarkan player per season
    public void addToAvgSeasonOutcome(Season season){

        seasonalOutcome.put(season, currentSeasonOutcome);

        if (seasonalOutcome.size() > 0){
            int sum = 0;
            for (Integer outcome : seasonalOutcome.values()) {
                sum += outcome;
            }
            avgSeasonOutcome = (double) sum / seasonalOutcome.size();
        }
        currentSeasonOutcome = 0; //reset ketika berganti season
    }

    // NPC STATUS

    // RELATIONSHIP STATUS
    public Map<String, RelationshipStatus> allRelStatus(){
        for (String name : NPCRegistry.getAvailableNPCs()){
            NPC npc = NPCRegistry.getNPCPrototype(name);
            allNPCRelations.put(npc.getName(), npc.getRelationshipStatus());
        }
        return allNPCRelations;
    }

    // INTERACTIONS
    public void addNPCInteraction(String npcName, NPCInteractionType type) {
        npcTracker.trackInteraction(npcName, type);
    }

    
    // FISH
    public void fishCaught(Fish fish){
        totalFishCaught++; 
        
        int prev = fishTypeAmt.getOrDefault(fish, 0);
        fishTypeAmt.put(fish.getRarity(), prev++);
        
        // NOTIFY
        notifyObservers("IKAN_DITANGKAP", totalFishCaught);
        notifyObservers("TIPE_IKAN_DITANGKAP", fish.getName());
        
    }
    
    // CROPS
    public void cropsHarvest(Crops crops) {
        boolean isFirstHarvest = (totalHarvest == 0);
        totalHarvest++;
    
        if (isFirstHarvest) {
            notifyObservers("PERTAMA_PANEN", totalHarvest);
        }
        notifyObservers("TIPE_CROPS", crops.getName());
    }
    


    // STORE
    public void foodPurchased(Food food){
        notifyObservers("FOOD_PURCHASED", food.getName());
    }
    
    
    // GETTERS
    public int getTotalIncome(){ return totalIncome; }
    public int getTotalOutcome(){ return totalOutcome; }
    public double getAvgSeasonIncome(){ return avgSeasonIncome; }
    public double getAvgSeasonOutcome() { return avgSeasonOutcome; }
    public int getTotalDays(){ return (gametime.getTotalGameMinutes() / 60 / 24); }
    public Map<NPCInteractionType, Integer> getNPCAllInteractions(String npcName) { return npcTracker.getAllInteractions(npcName);}
    public int getTotalHarvest(){ return totalHarvest; }
    public int getTotalFishCaught(){ return totalFishCaught; }

    // DISPLAY
    public void displayEndGame() {
        System.out.println("╔════════════════════ STATISTICS ════════════════════╗");
        
        // Gold Info
        System.out.println("║                     GOLD INFO                      ║");
        System.out.println("╠════════════════════════════════════════════════════╣");
        System.out.printf("║ Total Income: %-38d ║%n", getTotalIncome());
        System.out.printf("║ Total Expenditure: %-33d ║%n", getTotalOutcome());
        System.out.printf("║ Average Season Income: %-30.2f ║%n", getAvgSeasonIncome());
        System.out.printf("║ Average Season Expenditure: %-25.2f ║%n", getAvgSeasonOutcome());
        System.out.printf("║ Total Days Played: %-33d ║%n", getTotalDays());
        
        // NPC Info
        System.out.println("╠════════════════════════════════════════════════════╣");
        System.out.println("║                    NPCs STATUS                     ║");
        System.out.println("╠════════════════════════════════════════════════════╣");
        
        Map<String, RelationshipStatus> relations = allRelStatus();
        for (String npcName : relations.keySet()) {
            System.out.printf("║ %s:%n", npcName);
            System.out.printf("║   Relationship Status: %-29s ║%n", 
                relations.get(npcName));
            
            Map<NPCInteractionType, Integer> interactions = getNPCAllInteractions(npcName);
            System.out.printf("║   Chatting Frequency: %-30d ║%n", 
                interactions.getOrDefault(NPCInteractionType.CHATTING, 0));
            System.out.printf("║   Gifting Frequency: %-31d ║%n", 
                interactions.getOrDefault(NPCInteractionType.GIFTING, 0));
            System.out.printf("║   Visiting Frequency: %-30d ║%n", 
                interactions.getOrDefault(NPCInteractionType.VISITING, 0));
        }
        
        // Activities Info
        System.out.println("╠════════════════════════════════════════════════════╣");
        System.out.println("║                   ACTIVITIES                       ║");
        System.out.println("╠════════════════════════════════════════════════════╣");
        System.out.printf("║ Total Crops Harvested: %-29d ║%n", getTotalHarvest());
        System.out.printf("║ Total Fish Caught: %-33d ║%n", getTotalFishCaught());
        System.out.println("║ Fish Caught By Rarity:                              ║");
        for (Rarity rarity : Rarity.values()) {
            int count = fishTypeAmt.getOrDefault(rarity, 0);
            System.out.printf("║   %-10s: %-37d ║%n", rarity.name(), count);
        }
        System.out.println("╚════════════════════════════════════════════════════╝");
    }
}

