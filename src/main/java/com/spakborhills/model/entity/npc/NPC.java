// File: NPC.java (Updated Snippet)

package com.spakborhills.model.entity.npc;

import java.util.*;

import com.spakborhills.model.entity.RelationshipStatus; // Make sure this enum exists
import com.spakborhills.model.items.AllGameItems;
import com.spakborhills.model.items.Item;

public class NPC implements Cloneable {
    private String name;
    private int heartPoints;
    private static int maxHeartPoints = 150; // Max heart points
    private List<Item> lovedItems;
    private List<Item> hatedItems;
    private List<Item> likedItems = new ArrayList<Item>();
    private RelationshipStatus relationshipStatus;
    private long fiancéDate = 0; // To store game time when became fiancé

    public NPC(String name) {
        this.name = name;
        this.heartPoints = 0;
        this.lovedItems = new ArrayList<>();
        this.hatedItems = new ArrayList<>();
        this.likedItems = new ArrayList<>();
        this.relationshipStatus = RelationshipStatus.SINGLE;
    }

    // method clone
    @Override
    public NPC clone(){
        try {
            NPC cloned = (NPC) super.clone();
            cloned.lovedItems = new ArrayList<>(this.lovedItems);
            cloned.hatedItems = new ArrayList<>(this.hatedItems);
            cloned.likedItems = new ArrayList<>(this.likedItems);
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    // Builder pattern for adding items
    public NPC addLovedItem(Item... items) {
        Collections.addAll(this.lovedItems, items);
        return this;
    }

    public NPC addLikedItem(Item... items) {
        Collections.addAll(this.likedItems, items);
        return this;
    }

    public NPC addHatedItem(Item... items) {
        Collections.addAll(this.hatedItems, items);
        return this;
    }

    public List<Item> setHatedItemList(){ // khusus buat mayor tadi
        Set<Item> allGameItems = AllGameItems.getAllGameItems();
        Set<Item> preferred = new HashSet<>(this.lovedItems);
        preferred.addAll(this.likedItems);

        List<Item> hatedItems = new ArrayList<>();
        for (Item gameItem : allGameItems) {
            if(!preferred.contains(gameItem)) {
                hatedItems.add(gameItem);
            }
        }
        return hatedItems;
    }
    // getters
    public String getName() {
        return name;
    }
    public int getHeartPoints() {
        return heartPoints;
    }
    public static int getMaxHeartPoints() {
        return maxHeartPoints;
    }
    public RelationshipStatus getRelationshipStatus() {return relationshipStatus;}
    public List<Item> getLovedItems() {
        return lovedItems;
    }
    public List<Item> getHatedItems() {
        return hatedItems;
    }
    public List<Item> getLikedItems() {
        return likedItems;
    }
    public long getFiancéDate() {
        return fiancéDate;
    }

    // setters
    public void setHeartPoints(int heartPoints) {
        this.heartPoints = heartPoints;
        if (this.heartPoints > maxHeartPoints) {
            this.heartPoints = maxHeartPoints;
        } else if (this.heartPoints < 0) {
            this.heartPoints = 0; // Prevent negative heart points
        }
        updateRelationshipStatus(); // Update status based on heart points
    }

    public void setRelationshipStatus(RelationshipStatus relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

    public void setFiancéDate(long fiancéDate) {
        this.fiancéDate = fiancéDate;
    }

    private void updateRelationshipStatus() {
        // You can define thresholds for different relationship statuses here
        // For now, let's keep it simple: max heart points for proposing
        if (this.heartPoints == maxHeartPoints && this.relationshipStatus == RelationshipStatus.SINGLE) {
            // Optional: You might want a "friend" or "close friend" status before FIANCE
        }
    }
}