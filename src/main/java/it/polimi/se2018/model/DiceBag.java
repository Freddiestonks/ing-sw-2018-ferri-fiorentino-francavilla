package it.polimi.se2018.model;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class represent the dice-bag of the game. Is implemented as a singleton instance.
 *
 *  @author Federico Ferri
 *  @author Alessio Fiorentino
 *  @author Simone Francavilla
 */

public class DiceBag {
    //Attributes
    private static final DiceBag instance = new DiceBag();
    private ArrayList<Color> availableColors;
    private int[] content = new int[5];

    /**
     * This is the private constructor of the Dice-Bag.
     */
    //Methods
    private DiceBag(){
        // there are 18 dice for each color
        for(int i = 0; i < 5; i++) {
            content[i] = 18;
        }
        // get all the dice colors
        availableColors = new ArrayList<>(Arrays.asList(Color.values()));
    }

    /**
     * This is method that provide the instance of the Singleton.
     *
     * @return the DiceBag instance.
     */
    public static DiceBag instance(){
        return instance;
    }

    /**
     * This method is used to extract randomly a die from the bag.
     *
     * @return a Die instance of the extracted die.
     */
    public Die extract() {
        if(availableColors.isEmpty()) {
            throw new EmptyDiceBagException();
        }
        Random random = new Random();
        // pick a color from the available ones in the dice bag
        int colorNum = random.nextInt(availableColors.size());
        Color color = availableColors.get(colorNum);
        // get the die
        Die newDie = new Die(color);
        if(content[color.ordinal()] == 1) {
            // if it is the last die of that color being extracted
            availableColors.remove(color);
        }
        // in either case one die fewer of that color
        content[color.ordinal()]--;
        return newDie;
    }

    /**
     * This method is used to replace a die into the bag.
     *
     * @param die the die instance to be replaced.
     */
    public void replace(Die die){
        // increase the dice number of that color
        content[die.getColor().ordinal()]++;
    }

    /**
     * This method is used to reset the singleton instance.
     */
    public void reset(){
        // there are 18 dice for each color
        for(int i = 0; i < 5; i++) {
            content[i] = 18;
        }
        // get all the dice colors
        availableColors = new ArrayList<>(Arrays.asList(Color.values()));
    }
}
