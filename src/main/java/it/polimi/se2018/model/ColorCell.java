package it.polimi.se2018.model;

public class ColorCell extends Cell{
    //Attributes
    private Color color;

    //Methods
    public ColorCell(Color color) {
        this.color = color;
    }

    @Override
    public boolean placeableColor(Die die){
        return (die.getColor() == color);
    }
}