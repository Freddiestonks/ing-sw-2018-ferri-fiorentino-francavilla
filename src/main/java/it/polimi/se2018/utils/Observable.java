package it.polimi.se2018.utils;

import java.util.ArrayList;

public abstract class Observable {

    private ArrayList<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void removeObserver(Observer observer){
        observers.remove(observer);
    }

    protected void notifyObservers(){
        for(Observer observer : observers){
            observer.update();
        }
    }
}