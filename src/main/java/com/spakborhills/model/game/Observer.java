package com.spakborhills.model.game;

public interface Observer {
    void update(String eventType, Object data);
}
