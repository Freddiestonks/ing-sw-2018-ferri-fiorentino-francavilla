package it.polimi.se2018.model;

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
        Random random = new Random();
        value = 1 + random.nextInt(6);
    }

    public int getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public void setValue(int value) {
        if((value >= 1) && (value <= 6)){
            this.value = value;
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}
