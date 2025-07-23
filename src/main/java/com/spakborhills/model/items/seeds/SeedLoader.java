package com.spakborhills.model.items.seeds;

import java.io.*;
import java.util.*;

public class SeedLoader {
    private static final Map<String, Seed> seedByName = new LinkedHashMap<>();

    public SeedLoader(){}

    public static synchronized void loadSeeds(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()){
                continue;
            }

            int lastSpace = line.lastIndexOf(' ');
            if (lastSpace != -1 && lastSpace < line.length()-1) {
                String name = line.substring(0, lastSpace);
                String priceStr = line.substring(lastSpace+1);

                int buyPrice = Integer.parseInt(priceStr);
                Seed seed = new Seed(name, buyPrice);
                seedByName.put(name, seed);
            }
        }
        System.out.println("Loaded " + seedByName.size() + " seeds");
    }
    public static synchronized List<Seed> getSeedList(){
        return new ArrayList<Seed>(seedByName.values());
    }

    public static synchronized Seed getSeedName(String name){
        for (Seed s : seedByName.values()){
            if (s.getName().equalsIgnoreCase(name)){
                return s;
            }
        }
        return null;
    }
}
