package it.polimi.se2018.model;

public class PrivObjCard {
    //Attributes
    private Color color;
    private boolean used = false;

    //Methods
    public PrivObjCard(Color color){
        this.color = color;
    }

    public boolean isUsed() {
        return used;
    }

    public void use(){
        this.used = true;
    }

    public Color getColor(){
        return this.color;
    }
}
