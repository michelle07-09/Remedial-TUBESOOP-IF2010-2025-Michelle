package com.spakborhills.model.items.misc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MiscLoader {
    private static final Map<String, Misc> miscByName = new LinkedHashMap<>();

    public MiscLoader() {}

    public static synchronized void loadMisc (String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\s+");

            if (parts.length >= 3) {
                int sellPrice = Integer.parseInt(parts[parts.length-1]);
                int buyPrice = Integer.parseInt(parts[parts.length-2]);
                String name = String.join(" ", Arrays.copyOfRange(parts, 0, parts.length-2));

                Misc corps = new Misc(name, buyPrice, sellPrice);
                miscByName.put(name, corps);

            } else {
                System.out.println("Error: misc format incorrect");
            }
        }
        System.out.println("Loaded " + miscByName.size() + " misc");
    }
    public static synchronized List<Misc> getMiscList(){
        return new ArrayList<Misc>(miscByName.values());
    }

    public static synchronized Misc getMiscName(String name){
        for (Misc c : miscByName.values()){
            if (c.getName().equalsIgnoreCase(name)){
                return c;
            }
        }
        return null;
    }
}
