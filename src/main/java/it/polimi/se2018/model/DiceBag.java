package it.polimi.se2018.model;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;

public class DiceBag {
    //Attributes
    final private static DiceBag instance = new DiceBag();
    private ArrayList<Color> availableColors;
    private int[] content = new int[5];

    //Methods
    private DiceBag(){
        // there are 18 dice for each color
        for(int i = 0; i < 5; i++) {
            content[i] = 18;
        }
        // get all the dice colors
        availableColors = new ArrayList<Color>(Arrays.asList(Color.values()));
    }
    public static DiceBag instance(){
        return instance;
    }

    public Die extract() throws EmptyDiceBagException{
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

    public void replace(Die die){
        // increase the dice number of that color
        content[die.getColor().ordinal()]++;
    }
}
