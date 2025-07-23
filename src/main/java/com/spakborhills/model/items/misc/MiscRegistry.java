package com.spakborhills.model.items.misc;

import java.io.*;
import java.util.*;

public class MiscRegistry {
    private static final Map<String, Misc> miscPrototypes = new HashMap<>();
    static {

        try {
            MiscLoader.loadMisc("src/main/java/com/spakborhills/model/items/misc/miscslist.txt");

            List<Misc> loadedFood = MiscLoader.getMiscList();
            for (Misc misc : loadedFood) {
                if (misc != null && misc.getName() != null) {
                    miscPrototypes.put(misc.getName(), misc);
                }
            }

            // PROTOTYPE ALL MISC

            // FIREWOOD
            Misc firewoodFile = miscPrototypes.get("Firewood");
            Misc firewood = new Misc("Firewood", firewoodFile.getBuyPrice(), firewoodFile.getSellPrice());
            registerMisc(firewood);

            // COAL
            Misc coalFile = miscPrototypes.get("Coal");
            Misc coal = new Misc("Coal", coalFile.getBuyPrice(), coalFile.getSellPrice());
            registerMisc(coal);

            // PROPOSAL RING
            Misc proposalringFile = miscPrototypes.get("Proposal Ring");
            Misc proposalring = new Misc("Proposal Ring", proposalringFile.getBuyPrice(), proposalringFile.getSellPrice());
            registerMisc(proposalring);

        } catch (IOException e) {
            System.out.println("Error loading food list");
            e.printStackTrace();
        }
    }
    public static void registerMisc(Misc prototype) {
        if (prototype != null && prototype.getName() != null) {
            miscPrototypes.put(prototype.getName(), prototype);
        }
    }

    public static Misc getMiscPrototype(String name) {
        Misc prototype = miscPrototypes.get(name);
        if (prototype != null) {
            return prototype.clone();
        }
        return null;
    }

    public static Set<String> getAvailableMiscNames() {
        return new HashSet<>(miscPrototypes.keySet());
    }
}
