package it.polimi.se2018.model;

import java.io.Serializable;

public class PrivObjCard implements Serializable {
    //Attributes
    private Color color;

    //Methods
    public PrivObjCard(Color color){
        this.color = color;
    }

    public Color getColor(){
        return this.color;
    }

    @Override
    public String toString(){
        return color.toString().toLowerCase();
    }

}
