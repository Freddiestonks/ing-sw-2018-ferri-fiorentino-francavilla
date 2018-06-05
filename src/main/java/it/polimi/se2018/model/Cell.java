package it.polimi.se2018.model;

public class Cell {
    //Methods

    public boolean placeableShade(Die die) {
        return true;
    }

    public boolean placeableColor(Die die) {
        return true;
    }
    @Override
    public String toString(){
        return "---";
    }

}
