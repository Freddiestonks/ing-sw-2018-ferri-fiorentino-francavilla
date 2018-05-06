package it.polimi.se2018.server.model;

public class ShadeCell extends Cell {
    //Attributes
    private int shade;

    //Methods
    public ShadeCell(int shade) throws InvalidValueException {
        if((shade >= 1) && (shade <= 6)){
            this.shade = shade;
        }
        else {
            throw new InvalidValueException();
        }
    }

    @Override
    public boolean placeable(Die die) {
        return dice.getNumber() == shade;
    }
}
