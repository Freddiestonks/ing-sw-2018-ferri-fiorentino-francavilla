package it.polimi.se2018.server.model;

public class ShadeCell extends Cell {
    //Mehods

    public ShadeCell(int num) {
        super(null, num);
    }

    @Override
    public boolean placeable(Die dice) {
        return super.placeable(dice);
    }
}