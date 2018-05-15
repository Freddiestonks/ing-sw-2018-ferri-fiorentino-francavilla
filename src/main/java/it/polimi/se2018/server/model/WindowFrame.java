package it.polimi.se2018.server.model;

public class WindowFrame {
    //Attributes
    private PatternCard pc;
    private boolean wcFace; //true for front
    private Die[][] placements = new Die[4][5];
    private boolean empty = true;

    //Methods
    public WindowFrame(PatternCard pc, boolean wcFace) {
        this.pc = pc;
        this.wcFace = wcFace;
        for(int i=0; i<4; i++) {
            for(int j=0; j<5; j++){
                placements[i][j] = null;
            }
        }

    }

    public boolean smallCheck(Die die, int row, int col){
        boolean checkedOK = true;
        if(placements[row][col] != null){
            if (!(placements[row][col].getColor() != die.getColor() &&
                    placements[row][col].getValue() != die.getValue())){
                checkedOK = false;
            }
        }
        return checkedOK;
    }

    public boolean checkNeighborhood(int row, int col){
        //a first check of at least one die near the focused place.
        //if there's not any die, the insertion cannot take place.
        if(row < 0 || col < 0 || row > 4 || col > 5) {
            throw new IllegalArgumentException();
        }
        boolean atLeastOne = false;
        switch(col){
            case 0:
                switch(row){
                    case 0:
                        if (placements[row][col + 1] != null ||
                                placements[row + 1][col] != null ||
                                placements[row + 1][col + 1] != null) {
                            atLeastOne = true;
                        }
                        break;
                    case 3:
                        if (placements[row][col + 1] != null ||
                                placements[row - 1][col] != null ||
                                placements[row - 1][col + 1] != null){
                            atLeastOne = true;
                        }
                        break;
                    default:
                        if (placements[row][col + 1] != null ||
                                placements[row - 1][col] != null ||
                                placements[row + 1][col] != null ||
                                placements[row + 1][col + 1] != null ||
                                placements[row - 1][col + 1] != null) {
                            atLeastOne = true;
                        }
                        break;
                }
                break;
            case 4:
                switch(row){
                    case 0:
                        if (placements[row][col - 1] != null ||
                                placements[row + 1][col] != null ||
                                placements[row + 1][col - 1] != null) {
                            atLeastOne = true;
                        }
                        break;
                    case 3:
                        if (placements[row][col - 1] != null ||
                                placements[row - 1][col] != null ||
                                placements[row - 1][col - 1] != null) {
                            atLeastOne = true;
                        }
                        break;
                    default:
                        if (placements[row][col + 1] != null ||
                                placements[row - 1][col] != null ||
                                placements[row + 1][col] != null ||
                                placements[row + 1][col - 1] != null ||
                                placements[row - 1][col - 1] != null) {
                            atLeastOne = true;
                        }
                        break;
                }
                break;
            default:
                switch(row){
                    case 0:
                        if (placements[row][col - 1] != null ||
                                placements[row][col + 1] != null ||
                                placements[row + 1][col] != null ||
                                placements[row + 1][col + 1] != null ||
                                placements[row + 1][col - 1] != null) {
                            atLeastOne = true;
                        }
                        break;
                    case 3:
                        if (placements[row][col - 1] != null ||
                                placements[row][col + 1] != null ||
                                placements[row - 1][col] != null ||
                                placements[row - 1][col - 1] != null ||
                                placements[row + 1][col - 1] != null) {
                            atLeastOne = true;
                        }
                        break;
                    default:
                        if (placements[row][col - 1] != null ||
                                placements[row][col + 1] != null ||
                                placements[row - 1][col] != null ||
                                placements[row + 1][col] != null ||
                                placements[row + 1][col - 1] != null ||
                                placements[row + 1][col + 1] != null ||
                                placements[row - 1][col - 1] != null ||
                                placements[row - 1][col + 1] != null) {
                            atLeastOne = true;
                        }
                        break;
                }
                break;
        }
        return atLeastOne;
    }

    public void placeDie(Die die, int row, int col) throws DieNotValidException {
        //is a bool variable that helps to save the right conditions for die insertion.
        boolean feelRight = false;
        //controls the empty case
        if(empty){
            //die must be inserted on the edge or in the corners.
            if (row == 0 || row == 3 || col == 0 || col == 4)
                feelRight = true;
        }
        //controls if row-col position is placeable for the die.
        else {
            //controls if orthogonal rules are respected.
            switch (col) {
                case 0:
                    switch (row) {
                        case 0:
                            if(smallCheck(die,row,col+1) &&
                                    smallCheck(die,row+1,col)){
                                feelRight = true;
                            }
                            break;

                        case 3:
                            if (smallCheck(die,row,col+1) &&
                                    smallCheck(die,row-1,col)) {
                                feelRight = true;
                            }
                            break;

                        default:
                            if (smallCheck(die,row,col+1) &&
                                    smallCheck(die,row-1,col) &&
                                    smallCheck(die,row+1,col)) {
                                feelRight = true;
                            }
                            break;
                    }
                    break;
                case 4:
                    switch (row) {
                        case 0:
                            if (smallCheck(die,row,col-1) &&
                                    smallCheck(die,row+1,col)) {
                                feelRight = true;
                            }
                            break;

                        case 3:
                            if (smallCheck(die,row,col-1) &&
                                    smallCheck(die,row-1,col)) {
                                feelRight = true;
                            }
                            break;

                        default:
                            if (smallCheck(die,row,col-1) &&
                                    smallCheck(die,row-1,col) &&
                                    smallCheck(die,row+1,col)) {
                                feelRight = true;
                            }
                            break;
                    }
                    break;
                default:
                    switch (row) {
                        case 0:
                            if (smallCheck(die,row,col-1) &&
                                    smallCheck(die,row,col+1) &&
                                    smallCheck(die,row+1,col)) {
                                feelRight = true;
                            }
                            break;

                        case 3:
                            if (smallCheck(die,row,col-1) &&
                                    smallCheck(die,row,col+1) &&
                                    smallCheck(die,row-1,col)) {
                                feelRight = true;
                            }
                            break;

                        default:
                            if (smallCheck(die,row,col-1) &&
                                    smallCheck(die,row,col+1) &&
                                    smallCheck(die,row-1,col) &&
                                    smallCheck(die,row+1,col)) {
                                feelRight = true;
                            }
                            break;
                    }
                    break;
            }
        }

        if (feelRight && placements[row][col] == null) {
            placements[row][col] = die;
        } else {
            throw new DieNotValidException();
        }
    }

    public void removeDie(int row, int col) {
        placements[row][col] = null;
    }

    public boolean getWCFace(){
        return wcFace;
    }

    public int cardDifficulty(){
        return (wcFace ? pc.getLevelF() : pc.getLevelB());
    }

    public Die getDie(int row, int col){
        return placements[row][col];
    }

    public Cell getPCCell(int row, int col){
        return pc.selected(wcFace, row, col);
    }
}
