package it.polimi.se2018.server.model;

public class Die {
    //Attributes
    private int number;
    private Color color;

    public Die(Color color) {
        //TODO: generates number randomly
        this.color = color;
    }

    //Methods
    public int roll() {

    }

    public int getNumber() {
        return number;
    }

    public Color getColor() {
        return color;
    }
}
