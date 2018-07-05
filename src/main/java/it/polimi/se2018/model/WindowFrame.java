package it.polimi.se2018.model;

import java.io.Serializable;

/**
 * This class consists both of public and private methods it is used to create the WindowFrame that each Player has
 * it will need to contain a PatternCard which will be randomly getCell from a pool found inside the JSON file
 *
 * @author Federico Ferri
 * @author Alessio Fiorentino
 * @author Simone Francavilla
 */

public class WindowFrame implements Serializable {
    //Attributes
    private PatternCard pc;
    private boolean wcFace; //true for front
    private Die[][] placements = new Die[4][5];
    private int numDice = 0;
    //Methods

    /**
     * This is the constructor method of the class
     *
     * @param pc the PatternCard to set to the WindowFrame.
     * @param wcFace if this parameter is true the player chose the front "map" of the PatternCard otherwise it will be the back
     * */
    public WindowFrame(PatternCard pc, boolean wcFace) {
        this.pc = pc;
        this.wcFace = wcFace;
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 5; j++){
                placements[i][j] = null;
            }
        }
    }

    /**
     * This method is used to clone the WindowFrame
     *
     * @param wf is the WindowFrame instance to be cloned.
     */
    public WindowFrame(WindowFrame wf) {
        // create a copy of the selected WindowFrame
        this.pc = wf.pc;
        this.wcFace = wf.wcFace;
        this.numDice = wf.numDice;
        this.placements = new Die[4][];
        for(int i = 0; i < 4; i++) {
            this.placements[i] = wf.placements[i].clone();
        }
    }

    /**
     * This method controls if the positioning principles are respected, so there cannot be
     * a place in which in the same row or in the same column I've a same color or a same value die.
     *
     * @param die The die passed to be positioned.
     * @param row The row passed to be controlled.
     * @param col The column passed to be controlled.
     * @return a boolean value that justifies the correctness of the position inserted, if is it.
     */
    private boolean orthogonalDieCheck(Die die, int row, int col){
        if(placements[row][col] != null) {
            if((placements[row][col].getColor() == die.getColor())
               || (placements[row][col].getValue() == die.getValue())) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method controls if there's at least one die near the position in which
     * I want to insert the die instantiated.
     *
     * @param row The row of the wanted positioning place.
     * @param col The column of the wanted positioning place.
     * @return a boolean value expresses if there's at least one die near the wanted position.
     */
    public boolean touchingCheck(int row, int col){
        //a first check of at least one die near the focused place.
        //if there's not any die, the insertion cannot take place.
        if(numDice == 0){
            return true;
        }
        if(row < 0 || col < 0 || row > 4 || col > 5) {
            throw new IllegalArgumentException();
        }
        boolean touching = false;
        if (((col - 1 >= 0) && (placements[row][col - 1] != null))
           || ((col + 1 < 5) && (placements[row][col + 1] != null))
           || ((row - 1 >= 0) && (placements[row - 1][col] != null))
           || ((row + 1 < 4) && (placements[row + 1][col] != null))
           || ((row + 1 < 4) && (col - 1 >= 0) && (placements[row + 1][col - 1] != null))
           || ((row + 1 < 4) && (col + 1 < 5) && (placements[row + 1][col + 1] != null))
           || ((row - 1 >= 0) && (col - 1 >= 0) && (placements[row - 1][col - 1] != null))
           || ((row - 1 >= 0) && (col + 1 < 5) && (placements[row - 1][col + 1] != null))) {
            touching = true;
        }
        return touching;
    }

    /**
     * This method controls the correctness of a die positioning:
     * if the window-frame is empty;
     * if the window-frame is not empty, using 'OrthogonalCheck' and 'TouchingCheck';
     *
     * @param die The die passed to be positioned.
     * @param row The row of the wanted positioning place.
     * @param col The column of the wanted positioning place.
     * @return true if the rule is met.
     */
    public boolean crossCheck(Die die, int row, int col) {
        //is a bool variable that helps to save the right conditions for die insertion.
        boolean crossCheck = false;
        //controls the empty case
        if(numDice == 0){
            //die must be inserted on the edge or in the corners.
            if (row == 0 || row == 3 || col == 0 || col == 4)
                crossCheck = true;
        }
        else {
            //control if orthogonal rules are respected.
            if (((col - 1 < 0) || orthogonalDieCheck(die, row,col - 1))
            && ((col + 1 > 4) || orthogonalDieCheck(die, row,col + 1))
            && ((row - 1 < 0) || orthogonalDieCheck(die,row - 1, col))
            && ((row + 1 > 3) || orthogonalDieCheck(die,row + 1, col))) {
                crossCheck = true;
            }
        }
        return crossCheck;
    }

    /**
     * This method checks whether all the placement rules are met
     *
     * @param die the die to be placed.
     * @param row the row position.
     * @param col the column position.
     * @return true if the rules are met.
     */
    public boolean checkRestrictions(Die die, int row, int col) {
        //check all the placement restrictions
        Cell cell = getPCCell(row, col);
        if(cell.placeableShade(die)
            && cell.placeableColor(die)
            && touchingCheck(row, col)
            && crossCheck(die, row, col)
            && (getDie(row, col) == null)) {
            return true;
        }
        return false;
    }

    /**
     * This method is used to insert the die into the selected position.
     *
     * @param die The die passed to be positioned.
     * @param row The row of the wanted positioning place.
     * @param col The column of the wanted positioning place.
     */
    public void placeDie(Die die, int row, int col) {
        if (placements[row][col] == null) {
            placements[row][col] = die;
            numDice++;
        } else {
            throw new InvalidPlaceException();
        }
    }

    /**
     * This method is used to remove a die in a cell in the WindowFrame.
     *
     * @param row The row of the wanted positioning place.
     * @param col The column of the wanted positioning place.
     * @return the die that is removed from its original position.
     */
    public Die removeDie(int row, int col) {
        Die die = placements[row][col];
        if(placements[row][col] != null) {
            placements[row][col] = null;
            numDice--;
        }
        return die;
    }

    /**
     * This method is used to check the PatternCard difficulty.
     *
     * @return The PatternCard's level.
     */
    public int cardDifficulty(){
        return (wcFace ? pc.getLevelF() : pc.getLevelB());
    }

    /**
     * This method is used to show up a die in an inserted position.
     *
     * @param row The row of the wanted positioning place.
     * @param col The column of the wanted positioning place.
     * @return a Die instance, that's the one placed on the frame.
     */
    public Die getDie(int row, int col){
        return placements[row][col];
    }

    /**
     * This method is used to provide the Cell of the selected WindowFrame.
     *
     * @param row The row of the wanted positioning place.
     * @param col The column of the wanted positioning place.
     * @return a Cell instance, relative to the selected position.
     */
    public Cell getPCCell(int row, int col){
        return pc.getCell(wcFace, row, col);
    }

    /**
     * This method provides the set of placements of the player
     *
     * @return the matrix instance of the placements' set.
     */
    public Die[][] getPlacements() {
        return placements;
    }

}

