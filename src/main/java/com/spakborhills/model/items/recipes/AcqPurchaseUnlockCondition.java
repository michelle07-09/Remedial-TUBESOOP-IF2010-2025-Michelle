package com.spakborhills.model.items.recipes;

import com.spakborhills.controller.PlayerStats;

public class AcqPurchaseUnlockCondition implements UnlockCondition{

    String foodName;
    public AcqPurchaseUnlockCondition(String foodName){
        this.foodName = foodName;
    }

    @Override
    public boolean check(String eventType, Object data, PlayerStats playerStats){
        return "FOOD_PURCHASED".equals(eventType);
    }

    @Override
    public String getRequiredEventType(){
        return "FOOD_PURCHASED";
    }
}
