package it.polimi.se2018.model;

import java.io.Serializable;
import java.util.Random;

/**
 * This is the class used to instantiate a 'Die' object.
 *
 * @author Federico Ferri
 * @author Alessio Fiorentino
 * @author Simone Francavilla
 *
 */


public class Die implements Serializable {
    //Attributes
    private int value;
    private Color color;

    /**
     * The constructor of the class 'Die'
     *
     * @param color It's used to pass the set-up color for the die instantiation.
     */
    public Die(Color color) {
        this.roll();
        this.color = color;
    }

    public Die(Die die) {
        // create a copy of the selected die
        this.value = die.value;
        this.color = die.color;
    }

    //Methods

    /**
     * This method is used to generate a pseudo-casual number, assigned to 'value' attribute.
     */
    public void roll() {
        Random random = new Random();
        value = 1 + random.nextInt(6);
    }

    /**
     * This method is used to show up the Die face number after the rolling action.
     *
     * @return the value that represents the die's face.
     */
    public int getValue() {
        return value;
    }

    /**
     * This method is used to show up the Die face color after the rolling action.
     *
     * @return the color that represents the die's face.
     */
    public Color getColor() {
        return color;
    }

    /**
     * This method sets the number attribute.
     *
     * @param value It's used to pass the value for the die instantiation.
     */
    public void setValue(int value) {
        if((value >= 1) && (value <= 6)){
            this.value = value;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * This method is classic ToString description of a Die instance.
     *
     * @return a String instance of the description.
     */
    @Override
    public String toString(){
        return value + " " + color.toString().substring(0,1);
    }

}
