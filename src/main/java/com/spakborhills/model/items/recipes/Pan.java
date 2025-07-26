package com.spakborhills.model.items.recipes; // Pastikan ini adalah package yang benar

import com.spakborhills.model.items.Item;
import com.spakborhills.model.items.foods.Food;
import com.spakborhills.model.items.recipes.Recipe;
import com.spakborhills.model.items.recipes.IngredientPlaceholder;
import com.spakborhills.model.items.fish.Fish;
import com.spakborhills.model.items.crops.Crops;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a cooking pan where ingredients are added and a recipe is prepared.
 */
public class Pan {
    private Recipe currentRecipe;
    private final Map<Item, Integer> ingredientsInPan;
    private int cookingProgress; // Percentage, 0-100
    private BufferedImage panImage;
    private final int COOKING_TIME_REQUIRED = 60;

    // ✅ Path default untuk gambar pan kosong/dengan bahan
    private static final String DEFAULT_PAN_IMAGE_PATH = "/assets/Cooking/pan.png";
    // ✅ Path untuk pan sedang dimasak
    private static final String PAN_COOKING_IMAGE_PATH = "/assets/Cooking/pancooking.png";
    // ✅ Path untuk makanan sudah matang di pan
    private static final String PAN_COOKED_IMAGE_PATH = "/assets/Cooking/foodcooking.png";


    public Pan() {
        this.ingredientsInPan = new HashMap<>();
        this.cookingProgress = 0;
        loadPanImage(DEFAULT_PAN_IMAGE_PATH); // Muat gambar pan kosong saat inisialisasi
    }

    private void loadPanImage(String path) {
        try {
            // Perbaikan kecil: Pastikan path dimulai dengan "/" untuk resource loading
            String correctedPath = path.startsWith("/") ? path : "/" + path;
            panImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(correctedPath)));
            System.out.println("Loaded pan image: " + correctedPath);
        } catch (IOException | NullPointerException e) {
            System.err.println("Failed to load pan image from " + path + ": " + e.getMessage());
            panImage = null; // Set to null if loading fails
        }
    }

    /**
     * Sets the recipe to be cooked in this pan.
     * Clears any existing ingredients if a new recipe is set.
     * @param recipe The recipe to set.
     */
    public void setRecipe(Recipe recipe) {
        this.currentRecipe = recipe;
        this.ingredientsInPan.clear(); // Clear ingredients for new recipe
        this.cookingProgress = 0; // Reset progress
        // ✅ Saat resep diatur, kita akan menggunakan gambar pan default (yang sekarang juga bisa berarti ada bahan di dalamnya)
        if (recipe != null) {
            loadPanImage(DEFAULT_PAN_IMAGE_PATH); // Menggunakan pan.png untuk pan yang siap atau sudah ada bahan
        } else {
            loadPanImage(DEFAULT_PAN_IMAGE_PATH); // Kembali ke gambar pan kosong/default jika resep dihapus
        }
        System.out.println("Pan is now set to cook: " + (recipe != null ? recipe.getRecipeName() : "No Recipe"));
    }

    /**
     * Attempts to add an ingredient to the pan for the current recipe.
     * @param item The item to add.
     * @param quantity The quantity of the item to add.
     * @return true if the item was successfully added, false otherwise (e.g., not needed for recipe, or already enough).
     */
    public boolean addIngredient(Item item, int quantity) {
        if (currentRecipe == null) {
            System.out.println("No recipe selected for the pan.");
            return false;
        }

        Map<Item, Integer> requiredIngredients = currentRecipe.getIngredients();

        boolean isRequired = false;
        Item matchedRequiredItem = null;

        for (Map.Entry<Item, Integer> entry : requiredIngredients.entrySet()) {
            Item requiredItem = entry.getKey();
            int requiredQty = entry.getValue();

            if (requiredItem instanceof IngredientPlaceholder) {
                IngredientPlaceholder placeholder = (IngredientPlaceholder) requiredItem;
                if (matchesPlaceholder(placeholder, item)) {
                    int currentQtyInPan = ingredientsInPan.getOrDefault(item, 0);
                    if (currentQtyInPan + quantity <= requiredQty) {
                        isRequired = true;
                        matchedRequiredItem = requiredItem;
                        break;
                    }
                }
            } else if (requiredItem.equals(item)) {
                int currentQtyInPan = ingredientsInPan.getOrDefault(item, 0);
                if (currentQtyInPan + quantity <= requiredQty) {
                    isRequired = true;
                    matchedRequiredItem = item;
                    break;
                }
            }
        }

        if (isRequired) {
            ingredientsInPan.merge(matchedRequiredItem != null ? matchedRequiredItem : item, quantity, Integer::sum);
            System.out.println("Added " + quantity + " " + item.getName() + " to pan.");
            // ✅ Gambar pan tidak perlu berubah di sini jika DEFAULT_PAN_IMAGE_PATH sudah cukup
            // loadPanImage(DEFAULT_PAN_IMAGE_PATH); // Tetap gunakan pan.png setelah bahan ditambahkan
            return true;
        } else {
            System.out.println(item.getName() + " is not a required ingredient or already enough for this recipe.");
            return false;
        }
    }

    private boolean matchesPlaceholder(IngredientPlaceholder placeholder, Item item) {
        String category = placeholder.getName();
        return switch (category) {
            case "Any Fish" -> item instanceof Fish;
            case "Any Crop" -> item instanceof Crops;
            default -> false;
        };
    }

    public boolean isReadyToCook(Recipe recipe) {
        return allIngredientsPresent();
    }

    public boolean allIngredientsPresent() {
        if (currentRecipe == null) {
            return false;
        }

        Map<Item, Integer> requiredIngredients = currentRecipe.getIngredients();

        for (Map.Entry<Item, Integer> requiredEntry : requiredIngredients.entrySet()) {
            Item requiredItem = requiredEntry.getKey();
            int requiredQty = requiredEntry.getValue();

            if (requiredItem instanceof IngredientPlaceholder) {
                IngredientPlaceholder placeholder = (IngredientPlaceholder) requiredItem;
                int currentGenericQty = 0;

                for (Map.Entry<Item, Integer> inPanEntry : ingredientsInPan.entrySet()) {
                    Item actualItemInPan = inPanEntry.getKey();
                    int actualQtyInPan = inPanEntry.getValue();

                    if (matchesPlaceholder(placeholder, actualItemInPan)) {
                        currentGenericQty += actualQtyInPan;
                    }
                }

                if (currentGenericQty < requiredQty) {
                    return false;
                }

            } else {
                int actualQtyInPan = ingredientsInPan.getOrDefault(requiredItem, 0);
                if (actualQtyInPan < requiredQty) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Advances the cooking progress.
     * @return true if cooking is complete (progress reaches 100%), false otherwise.
     */
    public boolean progressCooking() {
        if (currentRecipe == null || !allIngredientsPresent()) {
            return false;
        }

        if (cookingProgress < 100) {
            cookingProgress += 20; // Example: increase by 20% per call
            if (cookingProgress >= 100) {
                cookingProgress = 100; // Cap at 100
                System.out.println("Cooking " + currentRecipe.getRecipeName() + " is complete!");
                loadPanImage(PAN_COOKED_IMAGE_PATH); // ✅ Gambar pan matang (foodcooking.png)
                return true;
            }
            System.out.println("Cooking " + currentRecipe.getRecipeName() + " is at " + cookingProgress + "%");
            loadPanImage(PAN_COOKING_IMAGE_PATH); // ✅ Gambar pan sedang dimasak (pancooking.png)
            return false;
        }
        return true; // Already 100%
    }
    public void progressCooking(int timeElapsed) {
        if (currentRecipe == null) {
            System.out.println("Belum ada resep yang sedang dimasak.");
            return;
        }

        // Ganti gambar jika belum dimasak
        if (cookingProgress == 0) {
            loadPanImage("/assets/Cooking/pancooking.png");
        }

        cookingProgress += timeElapsed;
        System.out.println("Progress memasak: " + cookingProgress + " / " + COOKING_TIME_REQUIRED);

        if (cookingProgress >= COOKING_TIME_REQUIRED) {
            loadPanImage("/assets/Cooking/foodcooking.png");
        }
    }

    /**
     * Completes the cooking process, removes ingredients, and returns the cooked food.
     * @return The cooked Food item, or null if cooking is not complete.
     */
// Menyelesaikan masakan jika progress cukup
    public Food finishCooking() {
        if (currentRecipe == null) {
            System.out.println("Tidak ada resep yang sedang dimasak.");
            return null;
        }

        if (cookingProgress < COOKING_TIME_REQUIRED) {
            System.out.println("Masakan belum selesai. Progress: " + cookingProgress + " / " + COOKING_TIME_REQUIRED);
            return null;
        }

        Food resultFood = currentRecipe.getResultFood();
        Food result = new Food(
                resultFood.getName(),
                resultFood.getBuyPrice(),
                resultFood.getSellPrice(),
                resultFood.getEnergy()
        );
        clear(); // Kosongkan pan dan reset gambar ke pan.png
        return result;
    }

    // Tetap gunakan clear() yang ini:
    public void clear() {
        ingredientsInPan.clear();
        currentRecipe = null;
        cookingProgress = 0;
        loadPanImage(DEFAULT_PAN_IMAGE_PATH); // kembalikan ke pan kosong
        System.out.println("Pan dibersihkan.");
    }

    // --- Getters ---
    public Recipe getCurrentRecipe() {
        return currentRecipe;
    }

    public Map<Item, Integer> getIngredientsInPan() {
        return ingredientsInPan;
    }

    public int getCookingProgress() {
        return cookingProgress;
    }

    // ✅ Getter baru untuk gambar pan
    public BufferedImage getPanImage() {
        return panImage;
    }
}