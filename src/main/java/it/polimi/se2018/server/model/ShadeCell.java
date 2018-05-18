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
    public int getShade(){
        return shade;
    }
    @Override
    public boolean placeableShade(Die die) {
        return die.getValue() == shade;
    }
}
