package com.spakborhills.model.items;

import java.util.*;

public class ShippingBin {
    private final int MAX_UNIQUE_ITEMS = 16;
    private final Map<Item, Integer> itemsToSell;

    public ShippingBin() {
        this.itemsToSell = new HashMap<>();
    }

    public boolean addItem(Item item, int quantity) {
        if (itemsToSell.containsKey(item)) {
            itemsToSell.put(item, itemsToSell.get(item) + quantity);
            return true;
        } else {
            if (itemsToSell.size() >= MAX_UNIQUE_ITEMS) {
                System.out.println("Shipping Bin penuh (maks 16 jenis item)!");
                return false;
            }
            itemsToSell.put(item, quantity);
            return true;
        }
    }

    public void clearBin() {
        itemsToSell.clear();
    }

    public int calculateTotalSellPrice() {
        int total = 0;
        for (Map.Entry<Item, Integer> entry : itemsToSell.entrySet()) {
            Item item = entry.getKey();
            int qty = entry.getValue();
            total += item.getSellPrice() * qty;
        }
        return total;
    }

    // Panggil method ini saat Player tidur (misalnya di Player.sleep())
    public int sellAllItemsAndReturnProfit() {
        int income = calculateTotalSellPrice();
        clearBin(); // kosongin bin setelah penjualans
        return income;
    }

    public Map<Item, Integer> getItemsToSell() {
        return Collections.unmodifiableMap(itemsToSell);
    }

    public boolean isFull() {
        return itemsToSell.size() >= MAX_UNIQUE_ITEMS;
    }
}
