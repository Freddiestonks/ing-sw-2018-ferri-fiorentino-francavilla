package it.polimi.se2018.model;

import java.io.Serializable;

public class Cell implements Serializable {
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
