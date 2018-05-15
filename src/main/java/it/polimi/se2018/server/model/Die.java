package it.polimi.se2018.server.model;

import java.util.Random;

public class Die {
    //Attributes
    private int value;
    private Color color;

    public Die(Color color) {
        this.roll();
        this.color = color;
    }

    //Methods
    public void roll() {
        Random randomNumbers = new Random();
        value = 1 + randomNumbers.nextInt(6);
    }

    public int getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public void setValue(int number) {
        this.value = number;
    }
}
