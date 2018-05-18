package it.polimi.se2018.server.model;

import java.util.ArrayList;

public class PubOCShadeDet extends PubObjCard {
    //attributes
    private boolean rows;
    private boolean col;
    private boolean shadeLight;
    private boolean shadeMedium;
    private boolean shadeDark;
    private boolean allShades;
    private ArrayList<Integer> check = new ArrayList<>(0);


    //methods
    public PubOCShadeDet(String desc,boolean row,boolean column,boolean light,boolean medium,boolean dark, boolean all) {
        super(desc);
        rows = row;
        col = column;
        shadeLight = light;
        shadeMedium = medium;
        shadeDark = dark;
        allShades = all;
    }

    private void updateCheck(ArrayList<Integer> localCheck, int row , int col, WindowFrame wf){
        //UpdateCheck is used to fill the "Check" vector with how many times each shade appears on a line
        if(wf.getDie(row,col).getValue() == 1){
            localCheck.set(0,localCheck.get(0) + 1);
        }
        else if(wf.getDie(row,col).getValue() == 2){
            localCheck.set(1,localCheck.get(1) + 1);
        }
        else if(wf.getDie(row,col).getValue() == 3){
            localCheck.set(2,localCheck.get(2) + 1);
        }
        else if(wf.getDie(row,col).getValue() == 4){
            localCheck.set(3,localCheck.get(3) + 1);
        }
        else if(wf.getDie(row,col).getValue() == 5){
            localCheck.set(4,localCheck.get(4) + 1);
        }
        else if(wf.getDie(row,col).getValue() == 6){
            localCheck.set(5,localCheck.get(5) + 1);
        }
    }

    private int getScoreRows(WindowFrame wf){
        int score = 0;
        for (int i = 0; i<6; i++){
            //I added 6 elements initialized to 0 to the arraylist
            check.add(0);
        }
        for (int i = 0; i< 4; i++){
            for(int j = 0; j<5; j++){
                //we run the method to see how many times a certain shade appears on a single row
                updateCheck(check,i,j,wf);
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
        check.removeAll(check);

        return  score;
    }

    private int getScoreCol(WindowFrame wf){
        int score = 0;
        for (int i = 0; i < 6; i++) {
            //I added 6 elements initialized to 0 to the arraylist
            check.add(0);
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                //we run the method to see how many times a certain shade appears on a single column
                updateCheck(check, j, i, wf);
            }
            if (check.get(0) <= 1 && check.get(1) <= 1 && check.get(2) <= 1 &&
                    check.get(3) <= 1 && check.get(4) <= 1 && check.get(5) <= 1
                    && (check.get(0) + check.get(1) + check.get(2) + check.get(3) + check.get(4) + check.get(5)) == 4) {
                //if in one column each shade came out at most once but none of the spaces are empty
                // then we can add 4 points to the score
                score = score + 4;
            }

        }
        check.removeAll(check);
        return score;
    }

    private int getScoreLightShades(WindowFrame wf){
        for (int i = 0; i < 6; i++) {
            //I added 6 elements initialized to 0 to the arraylist
            check.add(0);
        }
        for (int i = 0; i<5; i++){
            for (int l = 0;l<4;l++){
                //fill check with all of the shades inside the window frame
                updateCheck(check,l,i,wf);
            }
        }
        int min = check.get(0);
        //I look up which shade appeared the least, that will be equal to the number of sets
        if(check.get(1)< min){
            min = check.get(1);
        }
        check.removeAll(check);
        return min*2;
    }

    private int getScoreMediumShades(WindowFrame wf){
        for (int i = 0; i < 6; i++) {
            //I added 6 elements initialized to 0 to the arraylist
            check.add(0);
        }
        for (int i = 0; i<5; i++){
            for (int l = 0;l<4;l++){
                //fill check with all of the shades inside the window frame
                updateCheck(check,l,i,wf);
            }
        }
        int min = check.get(2);
        if(check.get(3)< min){
            min = check.get(3);
        }
        check.removeAll(check);
        return min*2;
    }

    private int getScoreDarkShades(WindowFrame wf){
        for (int i = 0; i < 6; i++) {
            //I added 6 elements initialized to 0 to the arraylist
            check.add(0);
        }
        for (int i = 0; i<5; i++){
            for (int l = 0;l<4;l++){
                //fill check with all of the shades inside the window frame
                updateCheck(check,l,i,wf);
            }
        }
        int min = check.get(4);
        if(check.get(5)< min){
            min = check.get(5);
        }
        check.removeAll(check);
        return min*2;
    }

    private int getScoreAllShades(WindowFrame wf){
        for (int i = 0; i < 6; i++) {
            //I added 6 elements initialized to 0 to the arraylist
            check.add(0);
        }
        for (int i = 0; i<5; i++){
            for (int l = 0;l<4;l++){
                //fill check with all of the shades inside the window frame
                updateCheck(check,l,i,wf);
            }
        }
        int min = check.get(0);
        for(int i =  1; i<6; i++){
            if(check.get(i)<min){
                min = check.get(i);
            }
        }
        check.removeAll(check);
        return min*5;
    }

    public int calculateScore(WindowFrame wf) {
        int score = 0;
        if (rows){
            //IF true it will count on how many rows each dice shade is different from the others
            score = score + getScoreRows(wf);
        }
        if (col) {
            //IF true it will count on how many columns each dice shade  is different from the others
            score = score + getScoreCol(wf);
        }
        if(shadeLight){
            //i look for light shade that least appeared (because it is going to be equal to the number of sets of shades)
             score = score + getScoreLightShades(wf);
        }
        if(shadeMedium){
            //i look for medium shade that least appeared (because it is going to be equal to the number of sets of shades)
            score = score + getScoreMediumShades(wf);
        }
        if(shadeDark){
            //i look for dark shade that least appeared (because it is going to be equal to the number of sets of shades)
            score = score + getScoreDarkShades(wf);
        }
        if(allShades){
                //i look for the shade that least appeared (because it is going to be equal to the number of sets of shades)
             score = score + getScoreAllShades(wf);
        }
        return score;
    }
}
