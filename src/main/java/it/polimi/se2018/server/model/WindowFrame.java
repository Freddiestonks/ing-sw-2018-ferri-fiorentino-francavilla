package it.polimi.se2018.server.model;

import java.util.ArrayList;

public class WindowFrame {
    //Attributes
    private PatternCard pc;
    private boolean wcFace; //true for front
    private ArrayList<ArrayList<Die>> placements;
    private boolean empty = true;

    //Methods
    public WindowFrame(PatternCard pc) {
        this.pc = pc;
        this.placements = new ArrayList<>();
        for(int i=0; i<5; i++) {
            placements.add(new ArrayList<Die>());
            for(int j=0; j<4; j++){
                placements.get(i).add(j,null);
            }
        }

    }

    public boolean smallCheck(Die die, int row, int col){
        boolean checkedOK = true;
        if(placements.get(col).get(row) != null){
            if (!(placements.get(col).get(row).getColor() != die.getColor() &&
                    placements.get(col).get(row).getNumber() != die.getNumber())){
                checkedOK = false;
            }
        }
        return checkedOK;
    }

    public boolean checkNeighborhood(int row, int col){
        //a first check of at least one die near the focused place.
        //if there's not any die, the insertion cannot take place.
        boolean atLeastOne = false;
        switch(col){
            case 0:{
                switch(row){
                    case 0:{
                        if (placements.get(col + 1).get(row) != null ||
                                placements.get(col).get(row + 1) != null ||
                                placements.get(col+1).get(row + 1) != null) {
                            atLeastOne = true;
                        }
                        break;
                    }
                    case 3:{
                        if (placements.get(col + 1).get(row) != null ||
                                placements.get(col).get(row - 1) != null ||
                                placements.get(col+1).get(row - 1) != null){
                            atLeastOne = true;
                        }
                        break;
                    }
                    default:{
                        if (placements.get(col + 1).get(row) != null ||
                                placements.get(col).get(row - 1) != null ||
                                placements.get(col).get(row + 1) != null ||
                                placements.get(col+1).get(row + 1) != null ||
                                placements.get(col+1).get(row - 1) != null) {
                            atLeastOne = true;
                        }
                        break;
                    }
                }
                break;
            }
            case 4:{
                switch(row){
                    case 0:{
                        if (placements.get(col - 1).get(row) != null ||
                                placements.get(col).get(row + 1) != null ||
                                placements.get(col-1).get(row + 1) != null) {
                            atLeastOne = true;
                        }
                        break;
                    }
                    case 3:{
                        if (placements.get(col - 1).get(row) != null ||
                                placements.get(col).get(row - 1) != null ||
                                placements.get(col-1).get(row - 1) != null) {
                            atLeastOne = true;
                        }
                        break;
                    }
                    default:{
                        if (placements.get(col + 1).get(row) != null ||
                                placements.get(col).get(row - 1) != null ||
                                placements.get(col).get(row + 1) != null ||
                                placements.get(col-1).get(row + 1) != null ||
                                placements.get(col-1).get(row - 1) != null) {
                            atLeastOne = true;
                        }
                        break;
                    }
                }
                break;
            }
            default:{
                switch(row){
                    case 0:{
                        if (placements.get(col - 1).get(row) != null ||
                                placements.get(col + 1).get(row) != null ||
                                placements.get(col).get(row + 1) != null ||
                                placements.get(col+1).get(row + 1) != null ||
                                placements.get(col-1).get(row + 1) != null) {
                            atLeastOne = true;
                        }
                        break;
                    }
                    case 3:{
                        if (placements.get(col - 1).get(row) != null ||
                                placements.get(col + 1).get(row) != null ||
                                placements.get(col).get(row - 1) != null ||
                                placements.get(col-1).get(row - 1) != null ||
                                placements.get(col+1).get(row - 1) != null) {
                            atLeastOne = true;
                        }
                        break;
                    }
                    default:{
                        if (placements.get(col - 1).get(row) != null ||
                                placements.get(col + 1).get(row) != null ||
                                placements.get(col).get(row - 1) != null ||
                                placements.get(col).get(row + 1) != null ||
                                placements.get(col-1).get(row + 1) != null ||
                                placements.get(col+1).get(row + 1) != null ||
                                placements.get(col-1).get(row - 1) != null ||
                                placements.get(col+1).get(row - 1) != null) {
                            atLeastOne = true;
                        }
                        break;
                    }
                }
                break;
            }
        }
        return atLeastOne;
    }

    public void placeDice(Die die, int row, int col) throws DieNotValidException {
        //is a bool variable that helps to save the right conditions for die insertion.
        boolean feelRight = false;
        //controls the empty case
        if(empty){
            //die must be inserted on the edge or in the corners.
            if(row == 0 || row == 3 || col == 0 || col == 4){
                if (pc.selected()[row][col].placeable(die)){
                    feelRight = true;
                }
            }
        }
        //controls if row-col position is placeable for the die.
        else if (pc.selected()[row][col].placeable(die)) {
            if(checkNeighborhood(row,col)){
            //controls if orthogonal rules are respected.
                switch (col) {
                    case 0: {
                        switch (row) {
                            case 0: {
                                if(smallCheck(die,row,col+1) &&
                                        smallCheck(die,row+1,col)){
                                    feelRight = true;
                                }
                                break;
                            }

                            case 3: {
                                if (smallCheck(die,row,col+1) &&
                                        smallCheck(die,row-1,col)) {
                                    feelRight = true;
                                }
                                break;
                            }

                            default: {
                                if (smallCheck(die,row,col+1) &&
                                        smallCheck(die,row-1,col) &&
                                        smallCheck(die,row+1,col)) {
                                    feelRight = true;
                                }
                                break;
                            }
                        }
                        break;
                    }
                    case 4: {
                        switch (row) {
                            case 0: {
                                if (smallCheck(die,row,col-1) &&
                                        smallCheck(die,row+1,col)) {
                                    feelRight = true;
                                }
                                break;
                            }

                            case 3: {
                                if (smallCheck(die,row,col-1) &&
                                        smallCheck(die,row-1,col)) {
                                    feelRight = true;
                                }
                                break;
                            }

                            default: {
                                if (smallCheck(die,row,col-1) &&
                                        smallCheck(die,row-1,col) &&
                                        smallCheck(die,row+1,col)) {
                                    feelRight = true;
                                }
                                break;
                            }
                        }
                        break;
                    }
                    default: {
                        switch (row) {
                            case 0: {
                                if (smallCheck(die,row,col-1) &&
                                        smallCheck(die,row,col+1) &&
                                        smallCheck(die,row+1,col)) {
                                    feelRight = true;
                                }
                                break;
                            }

                            case 3: {
                                if (smallCheck(die,row,col-1) &&
                                        smallCheck(die,row,col+1) &&
                                        smallCheck(die,row-1,col)) {
                                    feelRight = true;
                                }
                                break;
                            }

                            default: {
                                if (smallCheck(die,row,col-1) &&
                                        smallCheck(die,row,col+1) &&
                                        smallCheck(die,row-1,col) &&
                                        smallCheck(die,row+1,col)) {
                                    feelRight = true;
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }

        if (feelRight && placements.get(col).get(row) == null) {
            placements.get(col).add(row, die);
        } else {
            throw new DieNotValidException();
        }
    }


    public boolean wcFace(){
        return wcFace;
    }

    public int cardDifficulty(){
        return (wcFace ? pc.getLevelF() : pc.getLevelB());
    }

    public Die getDie(int row, int col){
        return placements.get(col).get(row);
    }
}
