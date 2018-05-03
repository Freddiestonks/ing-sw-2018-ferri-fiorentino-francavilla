package it.polimi.se2018.server.model;

public class PatternCard {
    //Attributes
    private Cell[][] front;
    private Cell[][] back;
    private int levelF;
    private int levelB;

    //Methods


    public PatternCard(int levelB, int levelF) {
        //in questa parte si potrebbe definire ogni singola cella in
        //base al fatto che sia shade o color
        this.front = new Cell[4][5];
        this.back = new Cell[4][5];
        this.levelB = levelB;
        this.levelF = levelF;
    }

    public boolean isUsed(){}

    public void use(){}

}
