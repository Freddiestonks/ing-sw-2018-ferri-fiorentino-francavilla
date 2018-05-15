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

    public Cell selected(boolean wcFace, int row, int col){
        if(wcFace) {
            return front[row][col];
        }

        else {
            return back[row][col];
        }
    }



    public int getLevelF() {
        return levelF;
    }

    public int getLevelB() {
        return levelB;
    }
}
