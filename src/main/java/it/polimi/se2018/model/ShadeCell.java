package it.polimi.se2018.model;
/**
 * This is the class representing a single place in the Pattern Cards representing a shade-type cell.
 *
 * @author Federico Ferri
 * @author Alessio Fiorentino
 * @author Simone Francavilla
 */
public class ShadeCell extends Cell {
    //Attributes
    private int shade;

    //Methods

    /**
     * This method is the constructor of the class that instantiate a singe 'Cell' object by passing his color.
     *
     * @param shade is simply the number that expresses a type of shade on a specific cell.
     */
    public ShadeCell(int shade) {
        if((shade >= 1) && (shade <= 6)){
            this.shade = shade;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * This method is used to control if a cell is accessible for the passed die,
     * so if the cell is represented by the same color.
     *
     * @param die the die passed to be controlled if can be positioned in the cell.
     * @return a boolean value representing the correctness of the placing move.
     */
    @Override
    public boolean placeableShade(Die die) {
        return die.getValue() == shade;
    }

    @Override
    public String toString(){
        return "-" + shade + "-";
    }
}
