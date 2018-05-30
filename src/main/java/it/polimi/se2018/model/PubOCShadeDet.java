package it.polimi.se2018.model;

import java.util.ArrayList;

/**
 * This class consists of only one public method (other than the constructor) used to calculate the score of one Player
 * according to the one specified onto the active Public Cards.
 *
 * @author Federico Ferri
 * @author Alessio Fiorentino
 * @author Simone Francavilla
 *
 * */

public class PubOCShadeDet extends PubObjCard {
    //attributes
    private boolean rows;
    private boolean col;
    private ArrayList<Integer> check = new ArrayList<>(0);


    //methods
    /**

     * This is the constructor method of the class
     *
     * @param desc it is simply the description of the public card
     * @param row triggers on or off the public card that calculates the score of different shades by row
     * @param column triggers on or off the public card that calculates the score of different shades by column
     * */
    public PubOCShadeDet(String desc,String name,boolean row,boolean column, int multiplier) {
        super(desc,name);
        points = multiplier;
        rows = row;
        col = column;

    }
    /**
     * This method is used to update the ArrayList that is counting the shades
     * @param row this is the row of the analyzed cell
     * @param col this is the column of the analyzed cell
     * @param wf this is the WindowFrame on which the counting is happening
     * */
    private void updateCheck(int row , int col, WindowFrame wf){
        //UpdateCheck is used to fill the "Check" vector with how many times each shade appears on a line
        int value = wf.getDie(row,col).getValue() - 1;
        check.set(value,check.get(value) + 1);
    }
    /**
     * This method calculates how many rows have no repetition of shades
     * @param wf This is the WindowFrame on which is the calculation is needed
     * @return Returns the number of rows with no repetition of shade
     * */
    private int getScoreRows(WindowFrame wf){
        int score = 0;
        for (int i = 0; i<6; i++){
            //I added 6 elements initialized to 0 to the ArrayList
            check.add(0);
        }
        for (int i = 0; i< 4; i++){
            for(int j = 0; j<5; j++){
                //we run the method to see how many times a certain shade appears on a single row
                updateCheck(i,j,wf);
            }
            if(check.get(0) <= 1 && check.get(1) <= 1 && check.get(2) <= 1 &&
                    check.get(3) <= 1 && check.get(4) <= 1 && check.get(5)<=1
                    &&(check.get(0) + check.get(1) + check.get(2) + check.get(3) +  check.get(4) + check.get(5)) == 5){
                //if in one row each shade came out once then we can add 5 points to the score
                score = score + 5;
            }
            for(int l = 0; l<6;l++){
                //we need to reset the elements of the ArrayList to 0
                check.set(l, 0);
            }
        }
        check.clear();

        return  score;
    }
    /**
     * This method calculates how many columns have no repetition of shades
     * @param wf This is the WindowFrame on which is the calculation is needed
     * @return Returns the number of column with no repetition of shades
     * */
    private int getScoreCol(WindowFrame wf){
        int score = 0;
        for (int i = 0; i < 6; i++) {
            //I added 6 elements initialized to 0 to the arraylist
            check.add(0);
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                //we run the method to see how many times a certain shade appears on a single column
                updateCheck(j, i, wf);
            }
            if (check.get(0) <= 1 && check.get(1) <= 1 && check.get(2) <= 1 &&
                    check.get(3) <= 1 && check.get(4) <= 1 && check.get(5) <= 1
                    && (check.get(0) + check.get(1) + check.get(2) + check.get(3) + check.get(4) + check.get(5)) == 4) {
                //if in one column each shade came out at most once but none of the spaces are empty
                // then we can add 4 points to the score
                score = score + 4;
            }

        }
        check.clear();
        return score;
    }

    /**
     * This method is used to calculate the score of a specific player throughout his WindowFrame
     * inside it there are if statements that can be triggered on or off by the attributes of the class
     *
     * @param wf It is used to pass the Player's WireFrame on which the with dice are on
     * @return score this method returns the score that a player made with the public card
     * */
    public int calculateScore(WindowFrame wf) {
        int score = 0;
        if (rows){
            //IF true it will count on how many rows each dice shade is different from the others
            score = score + getScoreRows(wf);
        }
        else if (col) {
            //IF true it will count on how many columns each dice shade  is different from the others
            score = score + getScoreCol(wf);
        }

        return score;
    }
}
