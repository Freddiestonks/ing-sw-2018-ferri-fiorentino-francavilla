package it.polimi.se2018.server.model;

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
}
