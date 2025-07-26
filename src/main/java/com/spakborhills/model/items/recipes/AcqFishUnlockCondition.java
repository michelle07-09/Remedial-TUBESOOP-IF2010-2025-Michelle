package com.spakborhills.model.items.recipes;

import com.spakborhills.controller.PlayerStats;

public class AcqFishUnlockCondition implements UnlockCondition{
    private String fishType;
    private int fishAmt;

    public AcqFishUnlockCondition(){
        this.fishType = null;
        this.fishAmt = 10;
    }

    public AcqFishUnlockCondition(String fishType){
        this.fishType = fishType;
        this.fishAmt = 1; // default
    }

    @Override
    public boolean check(String eventType, Object data, PlayerStats playerStats) {
        if (fishType == null){
            return "IKAN_DITANGKAP".equals(eventType) && data instanceof Integer && playerStats.getTotalFishCaught()>=fishAmt;
        }
        else{
            return "TIPE_IKAN_DITANGKAP".equals(eventType) && playerStats.getTotalFishCaught()>=fishAmt;
        }
    }

    @Override
    public String getRequiredEventType() {
        return (fishType == null) ? "IKAN_DITANGKAP" : "TIPE_IKAN_DITANGKAP";
    }
}
