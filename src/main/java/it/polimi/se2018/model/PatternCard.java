package it.polimi.se2018.model;

import java.io.Serializable;

/**
 * It's a class that represents the board inserted into the WindowFrame.
 * A single Pattern-Card has two different faces and conventionally are named 'front' and 'back' sides.
 *
 * @author Federico Ferri
 * @author Alessio Fiorentino
 * @author Simone Francavilla
 *
 */
public class PatternCard implements Serializable {
    //Attributes
    private Cell[][] front;
    private Cell[][] back;
    private int levelF;
    private int levelB;
    private String nameF;
    private String nameB;

    //Methods

    /**
     * Constructor
     *
     * @param levelFront It represents the level of one board's face, in this case is the 'front'.
     * @param levelBack It represents the level of one board's face, in this case is the 'back'.
     * @param nameFront It represent one board's face name (front).
     * @param nameBack It represent one board's face name (back).
     * @param frontCell Is the given matrix with witch the proper cells representing the specific map (front).
     * @param backCell Is the given matrix with witch the proper cells representing the specific map (back).
     */
    public PatternCard(int levelFront,int levelBack,String nameFront,String nameBack,Cell[][] frontCell,Cell[][] backCell){
        levelF = levelFront;
        levelB = levelBack;
        nameF = nameFront;
        nameB = nameBack;
        front = frontCell;
        back = backCell;
    }

    /**
     * This method is used to return a specific cell from PatternCard afterward is selected the face.
     *
     * @param wcFace representing the face, conventionally is true for the front side.
     * @param row the row value of the board.
     * @param col the column value of the board.
     * @return a cell instance.
     */
    public Cell getCell(boolean wcFace, int row, int col){
        if(wcFace) {
            return front[row][col];
        }

        else {
            return back[row][col];
        }
    }

    /**
     * This method is used to return the level of the front side of PatternCard.
     *
     * @return an integer value representing the level.
     */
    public int getLevelF() {
        return levelF;
    }

    /**
     * This method is used to return the level of the back side of PatternCard.
     *
     * @return an integer value representing the level.
     */
    public int getLevelB() {
        return levelB;
    }

    /**
     * This method return the name of the front-side card.
     *
     * @return a string reference to the name front-side's name.
     */
    public String getNameF() {
        return nameF;
    }

    /**
     * This method return the name of the back-side card.
     *
     * @return a string reference to the name back-side's name.
     */
    public String getNameB() {
        return nameB;
    }
}


