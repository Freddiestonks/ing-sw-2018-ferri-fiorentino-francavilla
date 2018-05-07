package it.polimi.se2018.server.model;

public class PatternCard {
    //Attributes
    private Cell[][] front;
    private Cell[][] back;
    private int levelF;
    private int levelB;

    //Methods
    public PatternCard(int levelB, int levelF) {
        this.front = new Cell[4][5];
        this.back = new Cell[4][5];
        this.levelB = levelB;
        this.levelF = levelF;
    }
    //TODO: implement front-back generation
    public boolean isUsed(){}

    public void use(){}

}
