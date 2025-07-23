package com.spakborhills.model.game;

import java.util.*;

public abstract class Subject {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String eventType, Object data) {
        for (Observer observer : observers) {
            observer.update(eventType, data);
        }
    }
}
