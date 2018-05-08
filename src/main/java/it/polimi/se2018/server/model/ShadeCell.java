package it.polimi.se2018.server.model;

public class ShadeCell extends Cell {
    //Attributes
    private int shade;

    //Methods
    public ShadeCell(int shade) {
        if((shade >= 1) && (shade <= 6)){
            this.shade = shade;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean placeable(Die die) {
        if (die.getNumber() == shade)
            return true;
        else
            return false;
    }
}
