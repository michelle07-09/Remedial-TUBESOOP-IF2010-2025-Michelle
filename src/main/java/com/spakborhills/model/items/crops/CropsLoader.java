package com.spakborhills.model.items.crops;

import java.util.*;
import java.io.*;

public class CropsLoader {
    private static final Map<String, Crops> cropsByName = new LinkedHashMap<>();

    public CropsLoader() {}

    public static synchronized void loadCrops (String fileName) throws IOException {
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

                Crops corps = new Crops(name, buyPrice, sellPrice);
                cropsByName.put(name, corps);
                
            } else {
                System.out.println("Error: crops format incorrect");
            }
        }
        System.out.println("Loaded " + cropsByName.size() + " crops");
    }
    public static synchronized List<Crops> getCropsList(){
        return new ArrayList<Crops>(cropsByName.values());
    }

    public static synchronized Crops getCropsName(String name){
        for (Crops c : cropsByName.values()){
            if (c.getName().equalsIgnoreCase(name)){
                return c;
            }
        }
        return null;
    }
}
