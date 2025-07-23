package com.spakborhills.model.items;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<Item, Integer> playerInventory; //<item, jumlah>

    public Inventory() {
        playerInventory = new HashMap<Item, Integer>();
    }

    public void add(Item item, int quantity) {
        if (item == null) {
            System.out.println("Cannot add null item to inventory!");
            return;
        }
        playerInventory.put(item, playerInventory.getOrDefault(item, 0) + quantity);
    }

    public void use(Item item, int quantity) {
        if (playerInventory.containsKey(item)) {
            playerInventory.put(item, playerInventory.get(item) - quantity);
            playerInventory.remove(item, 0);
        } else {
            System.out.println("Item not found");
        }
    }

    public boolean contains(Item item) {
        return playerInventory.containsKey(item);
    }
    
    public Map<Item, Integer> getPlayerInventory() {
        return playerInventory;
    }


}
