package it.polimi.se2018.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Observable extends java.util.Observable {
    private Scanner userInput = new Scanner(System.in);
    private String userString = new String("");
    private List<Observer> observers = new ArrayList<Observer>();

    public void input(){
        userString = userInput.nextLine().toLowerCase();
        notifyObservers(userString);
    }

    public String getUserString() {
        return userString;
    }
}
