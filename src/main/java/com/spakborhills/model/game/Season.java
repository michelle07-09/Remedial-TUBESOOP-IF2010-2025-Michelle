package com.spakborhills.model.game;

public enum Season {
    SPRING, SUMMER, FALL, WINTER;

    public String seasonToString(int season){
        return switch (season) {
            case 0 -> "Spring";
            case 1 -> "Summer";
            case 2 -> "Fall";
            case 3 -> "Winter";
            default -> "Invalid Season";
        };
    }
}


