package it.polimi.se2018.server.model;

public class ColorCell extends Cell{
    //Attributes
    private Color color;

    //Methods
    public ColorCell(Color color) {
        this.color = color;
    }

    @Override
    public boolean placeable(Die die){
        return die.getColor() == color
    }
}
