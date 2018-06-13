package it.polimi.se2018.model;

public class PrivObjCard {
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
        return color.toString().substring(0,1).toUpperCase();
    }

}
