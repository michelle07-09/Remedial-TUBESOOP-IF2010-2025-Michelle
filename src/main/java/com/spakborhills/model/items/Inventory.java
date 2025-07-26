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

    public void remove(Item item, int quantity) {
        if (!playerInventory.containsKey(item)) {
            System.out.println("Item " + item.getName() + " not found in inventory.");
            return;
        }

        int currentQuantity = playerInventory.get(item);
        if (currentQuantity <= quantity) {
            playerInventory.remove(item);
            System.out.println("Removed all " + item.getName() + ".");
        } else {
            playerInventory.put(item, currentQuantity - quantity);
            System.out.println("Removed " + quantity + " " + item.getName() + ". Remaining: " + playerInventory.get(item));
        }
    }

    public int getItemQuantity(Item item) {
        return playerInventory.getOrDefault(item, 0);
    }

    public void use(Item item, int quantity) {
        if (playerInventory.containsKey(item)) {
            playerInventory.put(item, playerInventory.get(item) - quantity);
            if (playerInventory.get(item) <= 0) {
                playerInventory.remove(item);
            }
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

    public Item getItemByName(String name) {
        for (Item item : playerInventory.keySet()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }


}
