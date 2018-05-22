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
    private boolean set;
    private ArrayList<Integer> check = new ArrayList<>(0);
    //methods
    /**

     * This is the constructor method of the class
     *
     * @param desc it is simply the description of the public card
     * @param row triggers on or off the public card that calculates the score of different colors by row
     * @param column triggers on or off the public card that calculates the score of different colors by column
     * @param diagonal triggers on or off the public card that calculates the score of different colors by diagonals
     * @param sets triggers on or off the public card that calculates the score of number of color sets
     *
     * */
    public PubOCColorDet(String desc,Boolean row, Boolean column,Boolean diagonal,Boolean sets) {
        super(desc);
        rows = row;
        col = column;
        diagonals = diagonal;
        set = sets;
    }

    /**
     * This method is used to "fill" an ArrayList that will count which <b>Die</b> Color appears on a specific <b>Cell/b>
     *
     * @param localCheck this parameter is the ArrayList that we need to fill
     * @param row this parameter is the row in which the cell is in
     * @param col this parameter is the column in which the cell is in
     * @param wf here the WindowFrame of the specific Player is sent to the method, in this way we can
     *           find out how the dice are placed onto the WindowFrame
     * */
    private void updateCheck(ArrayList<Integer> localCheck, int row , int col, WindowFrame wf){
        //UpdateCheck is used to fill the "Check" vector with how many times each color appears on a line
        if(wf.getDie(row,col).getColor() == Color.BLUE){
            localCheck.set(0,localCheck.get(0) + 1);
        }
        else if(wf.getDie(row,col).getColor() == Color.YELLOW){
            localCheck.set(1,localCheck.get(1) + 1);
        }
        else if(wf.getDie(row,col).getColor() == Color.RED){
            localCheck.set(2,localCheck.get(2) + 1);
        }
        else if(wf.getDie(row,col).getColor() == Color.GREEN){
            localCheck.set(3,localCheck.get(3) + 1);
        }
        else if(wf.getDie(row,col).getColor() == Color.PURPLE){
            localCheck.set(4,localCheck.get(4) + 1);
        }
    }

    private int getScoreRows(WindowFrame wf){
        int score = 0;
        for (int i = 0; i<5; i++){
            //I added 5 elements initialized to 0 to the arraylist
            check.add(0);
        }
        for (int i = 0; i< 4; i++){
            for(int j = 0; j<5; j++){
                //we run the method to see how many times a certain color appears on a single row
                updateCheck(check,i,j,wf);
            }
            if(check.get(0) == 1 && check.get(1) == 1 && check.get(2) == 1 && check.get(3) == 1 && check.get(4) == 1){
                //if in one row each color came out once then we can add 6 points to the score
                score = score + 6;
            }
            for(int l = 0; l<5;l++){
                //we need to reset the elements of the ArrayList to 0
                check.set(l, 0);
            }
        }
        //We need to deconstruct the ArrayList
        check.clear();
        return score;
    }

    private int getScoreCol(WindowFrame wf){
        int score = 0;
        for (int i = 0; i<5; i++){
            //I added 5 elements initialized to 0 to the arraylist
            check.add(0);
        }
        for (int i = 0; i< 5; i++) {
            for (int j = 0; j < 4; j++) {
                //we run the method to see how many times a certain color appears on a single column
                updateCheck(check, j, i, wf);
            }
            if (check.get(0) <= 1 && check.get(1) <= 1 && check.get(2) <= 1 &&
                    check.get(3) <= 1 && check.get(4) <= 1
                    && (check.get(0) + check.get(1) + check.get(2) + check.get(3) + check.get(4)) == 4) {
                //if in one column each color came out at most once but none of the spaces are empty
                // then we can add 5 points to the score
                score = score + 5;
            }
            for (int l = 0; l<5; l++){
                //I reset the 5 elements to 0 in the arraylist
                check.set(l,0);
            }
        }
        //We need to deconstruct the ArrayList
        check.clear();
        return score;
    }

    private int getScoreDiag(WindowFrame wf){
        //Start from dice (0,0)
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

    private int getScoreSet(WindowFrame wf){
        for (int i = 0; i<5; i++){
            //I added 5 elements initialized to 0 to the arraylist ( as many elements as the colors)
            check.add(0);
        }
        for (int i = 0; i<5; i++){
            for (int l = 0;l<4;l++){
                //fill check with all of the elements inside the window frame
                updateCheck(check,l,i,wf);
            }
        }
        //i look for the color that appeared the least(because it is going to be equal to the number of sets of colors)
        int min = check.get(0);
        for (int j=1; j<5;j++){
            if(check.get(j) < min ){
                min = check.get(j);
            }
            //i update the score it is equal to the number of sets times 4

        }
        check.clear();
        return min*4;
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
        int score = 0;

        if (rows){
            //IF true it will count on how many rows each dice color is different from the others
            score = score + getScoreRows(wf);
        }
        if (col){
            //IF true it will count on how many columns each dice color is different from the others
            score = score + getScoreCol(wf);
        }
        if(diagonals){
            score = score + getScoreDiag(wf);
        }

        if(set){
            score = score + getScoreSet(wf);
        }
        return score;
    }

}
