package it.polimi.se2018.model;

import java.io.Serializable;

/**
 * This class represent the single place of a match table, the so called 'cell'.
 * It figures out all the features of this place on the match table.
 *
 *  @author Federico Ferri
 *  @author Alessio Fiorentino
 *  @author Simone Francavilla
 */

public class Cell implements Serializable {
    /**
     * This method checks if a die is positionable on a specific 'Shade' cell.
     *
     * @param die is the de to be checked for the positioning
     * @return a boolean vale expressing the correctness of the positioning.
     */
    public boolean placeableShade(Die die) {
        return true;
    }

    /**
     * This method checks if a die is positionable on a specific 'Color' cell.
     *
     * @param die is the de to be checked for the positioning
     * @return a boolean vale expressing the correctness of the positioning.
     */
    public boolean placeableColor(Die die) {
        return true;
    }

    @Override
    public String toString(){
        return "---";
    }

}
