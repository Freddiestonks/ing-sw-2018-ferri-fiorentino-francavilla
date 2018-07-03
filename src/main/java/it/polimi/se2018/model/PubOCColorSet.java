package it.polimi.se2018.model;

import java.util.ArrayList;

public class PubOCColorSet extends PubObjCard {
    //attributes
    private ArrayList<Integer> check = new ArrayList<>();
    private boolean[] activeColors;

    //methods
    /**
     * This is the constructor method of the class
     * @param desc this is the description of the card
     * @param name this is the name of the card
     * @param color indicates which of the colors are wanted to complete a set
     * @param score this indicates how many points each set gives to the player
     * */
    public PubOCColorSet(String desc, String name, boolean[] color, int score){
        super(desc, name, score);
        if(!color[0] && !color[1] && !color[2] && !color[3] && !color[4]){
            throw new IllegalArgumentException();
        }
        activeColors = color.clone();
    }
    /**
     * This method is used to update the ArrayList that is counting the colors
     * @param row this is the row of the analyzed cell
     * @param col this is the column of the analyzed cell
     * @param wf this is the WindowFrame on which the counting is happening
     * */
    private void updateCheck(int row , int col, WindowFrame wf){
        //UpdateCheck is used to fill the "Check" vector with how many times each color appears on a line
        if(wf.getDie(row, col) != null){
            Color color = wf.getDie(row,col).getColor();
            check.set(color.ordinal(), check.get(color.ordinal()) + 1);
        }
    }

    /**
     * This method is used to calculate the score of a specific player throughout his WindowFrame
     *
     * @param wf is the Player's Window Frame on which the with dice are on
     * @return score this method returns the score that a player made with the public card
     * */
    public int calculateScore(WindowFrame wf){
        for (int i = 0; i < 5; i++){
            //add 5 elements initialized to 0 to the ArrayList (as many elements as the colors)
            check.add(0);
        }
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 4; j++){
                //fill check with all of the elements inside the window frame
                updateCheck(j, i, wf);
            }
        }
        //look for the color that appeared the least(because it is going to be equal to the number of sets of colors)
        int min = 21;
        for (int j = 0; j <5 ; j++){
            if(check.get(j) < min && activeColors[j]){
                min = check.get(j);
            }
            //update the score it is equal to the number of sets times 4
        }
        check.clear();
        return min * points;
    }
}
