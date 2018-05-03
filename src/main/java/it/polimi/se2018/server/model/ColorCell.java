package it.polimi.se2018.server.model;

public class ColorCell extends Cell{
    //Methods

    public ColorCell(Color color) {
        super(color, 0);
    }

    @Override
    public boolean placeable(Dice dice){
        return super.placeable(dice);
    }
}
