package com.spakborhills.model.items.recipes;

import com.spakborhills.controller.PlayerStats;

public class AcqHarvestUnlockCondition implements UnlockCondition{
    private String cropsType;

    public AcqHarvestUnlockCondition(String cropsType){
        this.cropsType = cropsType;
    }

    @Override
    public boolean check(String eventType, Object data, PlayerStats playerStats){
        if (cropsType == null){
            return "PERTAMA_PANEN".equals(eventType);
        }
        else{
            return "TIPE_CROPS".equals(eventType);
        }
    }
    @Override
    public String getRequiredEventType(){
        return (cropsType==null) ? "PERTAMA_PANEN" : "TIPE_CROPS";
    }
}
