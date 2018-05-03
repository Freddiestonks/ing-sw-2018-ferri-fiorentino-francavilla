package it.polimi.se2018.server.model;

public class Cell {
    //Attributes
    private Color color;
    private int shade;

    //Methods


    protected Cell(Color color, int shade) {
        this.color = color;

        if(shade > 0){
            this.shade = shade;
        }

    }

    protected boolean placeable(Die dice){}
}
