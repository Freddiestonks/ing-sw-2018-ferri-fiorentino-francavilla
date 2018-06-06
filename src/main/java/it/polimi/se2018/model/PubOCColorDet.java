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

public class PubOCColorDet extends PubObjCard {
    //attributes
    private boolean rows;
    private boolean col;
    private boolean diagonals;
    private ArrayList<Integer> check = new ArrayList<>(0);
    //methods
    /**

     * This is the constructor method of the class
     *
     * @param desc it is simply the description of the public card
     * @param row triggers on or off the public card that calculates the score of different colors by row
     * @param column triggers on or off the public card that calculates the score of different colors by column
     * @param diagonal triggers on or off the public card that calculates the score of different colors by diagonals
     *
     * */
    public PubOCColorDet(String desc,String name,boolean row, boolean column,boolean diagonal,int multiplier) {
        super(desc,name);
        rows = row;
        col = column;
        diagonals = diagonal;
        points = multiplier;
    }

    /**
     * This method is used to "fill" an ArrayList that will count which Die Color appears on a specific Cell
     *
     * @param row this parameter is the row in which the cell is in
     * @param col this parameter is the column in which the cell is in
     * @param wf here the WindowFrame of the specific Player is sent to the method, in this way we can
     *           find out how the dice are placed onto the WindowFrame
     * */
    private void updateCheck(int row , int col, WindowFrame wf){
        //UpdateCheck is used to fill the "Check" vector with how many times each color appears on a line

        Color color = wf.getDie(row,col).getColor();
        check.set(color.ordinal(),check.get(color.ordinal()) + 1);

    }
    /**
     * This method calculates how many rows have no repetition of color
     * @param wf This is the WindowFrame on which is the calculation is needed
     * @return  Returns the number of rows with no repetition of color
     * */
    private int getScoreRows(WindowFrame wf){
        int score = 0;
        for (int i = 0; i<5; i++){
            //add 5 elements initialized to 0 to the ArrayList
            check.add(0);
        }
        for (int i = 0; i< 4; i++){
            for(int j = 0; j<5; j++){
                //run the method to see how many times a certain color appears on a single row
                updateCheck(i,j,wf);
            }
            if(check.get(0) == 1 && check.get(1) == 1 && check.get(2) == 1 && check.get(3) == 1 && check.get(4) == 1){
                //if in one row each color came out once then we can add 6 points to the score
                score++;
            }
            for(int l = 0; l<5;l++){
                //reset the elements of the ArrayList to 0
                check.set(l, 0);
            }
        }
        //deconstruct the ArrayList
        check.clear();
        return score;
    }
    /**
     * This method calculates how many columns have no repetition of color
     * @param wf This is the WindowFrame on which is the calculation is needed
     * @return Returns the number of column with no repetition of color
     * */
    private int getScoreCol(WindowFrame wf){
        int score = 0;
        for (int i = 0; i<5; i++){
            //add 5 elements initialized to 0 to the ArrayList
            check.add(0);
        }
        for (int i = 0; i< 5; i++) {
            for (int j = 0; j < 4; j++) {
                //run the method to see how many times a certain color appears on a single column
                updateCheck(j, i, wf);
            }
            if (check.get(0) <= 1 && check.get(1) <= 1 && check.get(2) <= 1 &&
                    check.get(3) <= 1 && check.get(4) <= 1
                    && (check.get(0) + check.get(1) + check.get(2) + check.get(3) + check.get(4)) == 4) {
                //if in one column each color came out at most once but none of the spaces are empty
                // then we can add 5 points to the score
                score++;
            }
            for (int l = 0; l<5; l++){
                //reset the 5 elements to 0 in the ArrayList
                check.set(l,0);
            }
        }
        //deconstruct the ArrayList
        check.clear();
        return score;
    }
    /**
     * This method calculates how many diagonals have the same color
     * @param wf This is the WindowFrame on which is the calculation is needed
     * @return returns the number of dice that are the same color on a diagonal line
     * */
    private int getScoreDiag(WindowFrame wf){
        //Start from dice (0,0) (top-left)
        int score = 0;
        for (int i=0; i<4;i++){
            for (int l=0;l<5;l++){
                if((i-1)>=0&&(l -1)>=0&&(wf.getDie(i, l).getColor()==wf.getDie(i-1, l -1).getColor())||(
                        (i-1)>=0&&(l +1)<5&&(wf.getDie(i, l).getColor()==wf.getDie(i-1, l +1).getColor()))||
                        ((i+1)<4&&(l -1)>=0&&(wf.getDie(i, l).getColor()==wf.getDie(i+1, l -1).getColor()))||
                        ((i+1)<4&&(l +1)<5&&(wf.getDie(i, l).getColor()==wf.getDie(i+1, l +1).getColor()))){
                    score++;
                }
            }
        }

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
        //calculateScore is used to calculate the current score of the player
        int score;

        if (rows){
            //IF true it will count on how many rows each dice color is different from the others
            score = getScoreRows(wf)*points;
        }
        else if (col){
            //IF true it will count on how many columns each dice color is different from the others
            score = getScoreCol(wf)*points;
        }
        else if(diagonals){
            score = getScoreDiag(wf)*points;
        }
        else {
            throw new IllegalArgumentException();
        }

        return score;
    }

}
