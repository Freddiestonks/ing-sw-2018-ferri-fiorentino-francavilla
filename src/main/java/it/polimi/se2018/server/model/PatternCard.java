package it.polimi.se2018.server.model;

public class PatternCard {
    //Attributes
    private Cell[][] front;
    private Cell[][] back;
    private int levelF;
    private int levelB;
    private boolean usedCard;

    //Methods
    public PatternCard(int levelB, int levelF) {
        this.front = new Cell[4][5];
        this.back = new Cell[4][5];
        this.levelB = levelB;
        this.levelF = levelF;
    }

    public boolean isUsed(){
        return usedCard;
    }

    public void use(boolean selection){
        usedCard = selection;
    }

    public Cell[][] selected(){
        if(isUsed()) {
            return front;
        }

        else {
            return back;
        }
    }



    public int getLevelF() {
        return levelF;
    }

    public int getLevelB() {
        return levelB;
    }
}
