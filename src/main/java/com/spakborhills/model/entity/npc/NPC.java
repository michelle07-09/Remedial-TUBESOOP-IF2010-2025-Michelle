package com.spakborhills.model.entity.npc;

import java.util.*;

import com.spakborhills.model.entity.RelationshipStatus;
import com.spakborhills.model.items.AllGameItems;
import com.spakborhills.model.items.Item;

public class NPC implements Cloneable {
    private String name;
    private int heartPoints;
    private static int maxHeartPoints = 150;
    private List<Item> lovedItems;
    private List<Item> hatedItems;
    private List<Item> likedItems = new ArrayList<Item>();
    private RelationshipStatus relationshipStatus;

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

    // builder methods
    public NPC addLovedItem(Item... items) {
        if (items != null) {
            Collections.addAll(lovedItems, items);
        }
        return this;
    }

    public NPC addHatedItem(Item... items) {
        if (items != null) {
            Collections.addAll(hatedItems, items);
        }
        return this;
    }
    public NPC addLikedItem(Item... items) {
        if (items != null) {
            Collections.addAll(likedItems, items);
        }
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

    // setters
    public void setHeartPoints(int heartPoints) {
        if (heartPoints > maxHeartPoints) {
            this.heartPoints = maxHeartPoints;
        }
        else {
            this.heartPoints = Math.max(heartPoints, 0);
        }
    }
    public void setRelationshipStatus(RelationshipStatus relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }
}
