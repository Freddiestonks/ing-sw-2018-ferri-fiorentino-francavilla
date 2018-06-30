package it.polimi.se2018.model;

/**
 * This is the class representing a single place in the Pattern Cards representing a colored cell.
 *
 * @author Federico Ferri
 * @author Alessio Fiorentino
 * @author Simone Francavilla
 */
public class ColorCell extends Cell{
    //Attributes
    private Color color;

    //Methods


    /**
     * This method is the constructor of the class that instantiate a singe 'Cell' object by passing his color.
     *
     * @param color is the set-up color of a single cell.
     */
    public ColorCell(Color color) {
        this.color = color;
    }

    /**
     * This method is used to control if a cell is accessible for the passed die,
     * so if the cell is represented by the same color.
     *
     * @param die the die passed to be controlled if can be positioned in the cell.
     * @return a boolean value representing the correctness of the placing move.
     */
    @Override
    public boolean placeableColor(Die die){
        return (die.getColor() == color);
    }

    /**
     * Is the to string implementation.
     *
     * @return a string instance.
     */
    @Override
    public String toString(){
        return "-" + color.toString().substring(0,1) + "-";
    }
}
