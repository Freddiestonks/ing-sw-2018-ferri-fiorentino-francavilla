package it.polimi.se2018.server.model;

import java.util.Random;

public class Die {
    //Attributes
    private int number;
    private Color color;

    public Die(Color color) {
        this.color = color;
    }

    //Methods
    public void roll() {
        Random randomNumbers = new Random();
        number = 1 + randomNumbers.nextInt(5);
    }

    public int getNumber() {
        return number;
    }

    public Color getColor() {
        return color;
    }
}
