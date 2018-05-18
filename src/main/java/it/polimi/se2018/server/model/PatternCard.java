package it.polimi.se2018.server.model;

public class PatternCard {
    //Attributes
    private Cell[][] front = new Cell[4][5];
    private Cell[][] back = new Cell[4][5];
    private int levelF;
    private int levelB;
    private String nameF;
    private String nameB;

    //Methods
    public PatternCard(int levelFront,int levelBack,String nameFront,String nameBack,Cell[][] frontCell,Cell[][] backCell){
        levelF = levelFront;
        levelB = levelBack;
        nameF = nameFront;
        nameB = nameBack;
        front = frontCell;
        back = backCell;
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

    public String getNameF() {
        return nameF;
    }

    public String getNameB() {
        return nameB;
    }

    public Cell[][] getBack() {
        return back;
    }

    public Cell[][] getFront() {
        return front;
    }
}
