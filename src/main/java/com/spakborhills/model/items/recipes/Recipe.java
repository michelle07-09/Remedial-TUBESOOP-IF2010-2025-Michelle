//package com.spakborhills.model.items.recipes;
//
//import com.spakborhills.model.items.Item;
//import com.spakborhills.model.items.foods.Food;
//import com.spakborhills.model.items.foods.FoodRegistry;
//import java.util.*;
//
//public class Recipe implements Cloneable {
//    private String id;
//    private String recipeName;
//    private Food foodProduct;
//    private Map<Item, Integer> ingredients;
//    private boolean isUnlocked;
//    private UnlockCondition unlockCondition;
//
//    public Recipe (String id, Food foodProduct, Map<Item, Integer> ingredients, UnlockCondition unlockCondition, boolean defaultUnlocked){
//        this.id = id;
//        this.recipeName = foodProduct.getName();
//        this.foodProduct = foodProduct;
//        this.ingredients = new HashMap<>(ingredients);
//        this.isUnlocked = defaultUnlocked;
//
//        if (defaultUnlocked && this.unlockCondition == null){
//            this.isUnlocked = true;
//        }
//    }
//
//    @Override
//    public Recipe clone(){
//        try {
//            Recipe cloned = (Recipe) super.clone();
//            return cloned;
//        }
//        catch (CloneNotSupportedException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public String getId() {return id;}
//    public String getRecipeName() {return recipeName;}
//    public Food getProduct() {return foodProduct;}
//    public Map<Item, Integer> getIngredients() {return ingredients;}
//    public UnlockCondition getUnlockCondition() {return unlockCondition;}
//    public boolean isUnlocked() {return isUnlocked;}
//
//    public void unlock(){
//        if (!this.isUnlocked){
//            this.isUnlocked = true;
//            System.out.println("Resep " + recipeName + " telah dibuka.");
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "Recipe{" +
//                "id='" + id + '\'' +
//                ", recipeName='" + recipeName + '\'' +
//                ", ingredients=" + ingredients + '\'' +
//                '}';
//    }
//}
//
//
