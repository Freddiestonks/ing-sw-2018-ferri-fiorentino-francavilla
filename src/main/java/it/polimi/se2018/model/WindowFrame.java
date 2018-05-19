package it.polimi.se2018.model;

import com.google.gson.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;

public class WindowFrame {
    //Attributes
    private PatternCard pc = null;
    private boolean wcFace; //true for front
    private Die[][] placements = new Die[4][5];
    private boolean empty = true;
    //Methods
    public WindowFrame(int id,boolean wcFace) {
        pc = loadPC(id);
        this.wcFace = wcFace;
        for(int i=0; i<4; i++) {
            for(int j=0; j<5; j++){
                placements[i][j] = null;
            }
        }

    }
    private Color colorTranslator(String string) {
        //This method is used to translate the "Colors" in the JSON file from english to a Color, ignore
        //all blank spaces and change letters to uppercase so that the process of adding cards
        //is more "user friendly"
        return Color.valueOf(string.toUpperCase().replaceAll("\\s+",""));
        /*
        if (Objects.equals(string.toLowerCase().replaceAll("\\s+",""), "blue")){
            return Color.BLUE;
        }
        else if (Objects.equals(string.toLowerCase().replaceAll("\\s+",""), "green")) {
           return Color.GREEN;
        }
        else if (Objects.equals(string.toLowerCase().replaceAll("\\s+",""), "yellow")){
            return Color.YELLOW;
        }
        else if (Objects.equals(string.toLowerCase().replaceAll("\\s+",""), "purple")){
            return Color.PURPLE;
        }
        else if (Objects.equals(string.toLowerCase().replaceAll("\\s+",""), "red")){
            return Color.RED;
        }
        else throw new IllegalArgumentException();
        */
    }
    private int jIntGetter(JsonObject jsonObject,String string){
        //returns a int from a JSON object's parameter, use a different method so that in case of updates it is easier
        //to change all of the methods quickly
        return jsonObject.get(string).getAsInt();
    }

    private String jStringGetter(JsonObject jsonObject,String string){
        //returns a String from a JSON object's parameter, use a different method so that in case of updates it is easier
        //to change all of the methods quickly
        return jsonObject.get(string).getAsString();
    }
    private Cell[][] jCellFiller(Cell[][] cellF, JsonArray cells){
        //this method is used to generate a ShadeCell or a ColorCell on the correct space with the correct value/color
        for(int j= 0; j <cells.size();j++){
            JsonObject jsonCell = (JsonObject)cells.get(j);
            if (Objects.equals(jStringGetter(jsonCell, "type"), "shade")){
                //this method recognize if it is a shade type and it will fill it with the correct attributes
                int cellRow = jIntGetter(jsonCell,"row");
                int cellCol = jIntGetter(jsonCell,"col");
                int cellShade = jIntGetter(jsonCell, "value");
                cellF[cellRow][cellCol] = new ShadeCell(cellShade);
            }
            else if (Objects.equals(jStringGetter(jsonCell, "type"), "color")){
                //this method recognize if it is a color type and it will fill it with the correct color (through the translator method)
                int cellRow = jIntGetter(jsonCell,"row");
                int cellCol = jIntGetter(jsonCell,"col");
                String  cellColor = jStringGetter(jsonCell, "color");
                cellF[cellRow][cellCol] = new ColorCell(colorTranslator(cellColor));
            }
            else{
                //System.out.println("UnknownType");
            }
        }
        return cellF;
    }

    public PatternCard loadPC(int id) {
        // loadPC will load a pattern card from file written in JSON where cards are kept in a specific order
        // their position can be used as an "ID"
        PatternCard patternCard = null;
        String path = "src/main/json/patternCards.json";
        //I parse the json file
        JsonParser jsonParser = new JsonParser();
        try {
            //The Json file is then transferred to an object
            Object object = jsonParser.parse(new FileReader(path));
            JsonObject jsonObject = (JsonObject)object;
            //transform the Object to an array of "cards"
            JsonArray patternCards =(JsonArray)jsonObject.get("patternCards");
            //take a particular element from the array
            jsonObject = (JsonObject)patternCards.get(id);
            //find from the file the difficulty levels of both faces and the names of both sides
            int levelF = jIntGetter(jsonObject,"levelF");
            int levelB = jIntGetter(jsonObject,"levelB");
            String nameF = jStringGetter(jsonObject,"nameF");
            String nameB = jStringGetter(jsonObject,"nameB");
            //create first a matrix of "normal cells"
            Cell[][] cellF = new Cell[4][5];
            Cell[][] cellB = new Cell[4][5];
            //get the information about the "special" cells and load them in a different JSON array
            JsonArray cells = (JsonArray)jsonObject.get("cellF");
            //use the method jCellFiller to fill the top cells
            cellF = jCellFiller(cellF,cells);
            //In the same way generate the bottom part
            cells = (JsonArray)jsonObject.get("cellB");
            cellB = jCellFiller(cellB,cells);
            //create a new patterCard with the attributes loaded from the file
            patternCard =  new PatternCard(levelF,levelB,nameF,nameB,cellF,cellB);

        }
        catch (FileNotFoundException nfe){
            //in case there is no JSON file we throw an exception
            //System.out.println("File not Found, please check path or insert json in " + path);
        }

        return patternCard;
    }

    public PatternCard getPc(){
        return pc;
    }

    private boolean orthogonalDieCheck(Die die, int row, int col){
        if(placements[row][col] != null) {
            if((placements[row][col].getColor() == die.getColor())
               || (placements[row][col].getValue() == die.getValue())) {
                return false;
            }
        }
        return true;
    }

    public boolean touchingCheck(int row, int col){
        //a first check of at least one die near the focused place.
        //if there's not any die, the insertion cannot take place.
        if(row < 0 || col < 0 || row > 4 || col > 5) {
            throw new IllegalArgumentException();
        }
        boolean touching = false;
        switch(col){
            case 0:
                switch(row){
                    case 0:
                        if (placements[row][col + 1] != null ||
                                placements[row + 1][col] != null ||
                                placements[row + 1][col + 1] != null) {
                            touching = true;
                        }
                        break;
                    case 3:
                        if (placements[row][col + 1] != null ||
                                placements[row - 1][col] != null ||
                                placements[row - 1][col + 1] != null){
                            touching = true;
                        }
                        break;
                    default:
                        if (placements[row][col + 1] != null ||
                                placements[row - 1][col] != null ||
                                placements[row + 1][col] != null ||
                                placements[row + 1][col + 1] != null ||
                                placements[row - 1][col + 1] != null) {
                            touching = true;
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
                            touching = true;
                        }
                        break;
                    case 3:
                        if (placements[row][col - 1] != null ||
                                placements[row - 1][col] != null ||
                                placements[row - 1][col - 1] != null) {
                            touching = true;
                        }
                        break;
                    default:
                        if (placements[row][col + 1] != null ||
                                placements[row - 1][col] != null ||
                                placements[row + 1][col] != null ||
                                placements[row + 1][col - 1] != null ||
                                placements[row - 1][col - 1] != null) {
                            touching = true;
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
                            touching = true;
                        }
                        break;
                    case 3:
                        if (placements[row][col - 1] != null ||
                                placements[row][col + 1] != null ||
                                placements[row - 1][col] != null ||
                                placements[row - 1][col - 1] != null ||
                                placements[row + 1][col - 1] != null) {
                            touching = true;
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
                            touching = true;
                        }
                        break;
                }
                break;
        }
        return touching;
    }

    public boolean crossCheck(Die die, int row, int col) {
        //is a bool variable that helps to save the right conditions for die insertion.
        boolean crossCheck = false;
        //controls the empty case
        if(empty){
            //die must be inserted on the edge or in the corners.
            if (row == 0 || row == 3 || col == 0 || col == 4)
                crossCheck = true;
                empty = false;
        }
        //controls if row-col position is placeable for the die.
        else {
            //controls if orthogonal rules are respected.
            switch (col) {
                case 0:
                    switch (row) {
                        case 0:
                            if(orthogonalDieCheck(die,row,col+1) &&
                                    orthogonalDieCheck(die,row+1,col)){
                                crossCheck = true;
                            }
                            break;

                        case 3:
                            if (orthogonalDieCheck(die,row,col+1) &&
                                    orthogonalDieCheck(die,row-1,col)) {
                                crossCheck = true;
                            }
                            break;

                        default:
                            if (orthogonalDieCheck(die,row,col+1) &&
                                    orthogonalDieCheck(die,row-1,col) &&
                                    orthogonalDieCheck(die,row+1,col)) {
                                crossCheck = true;
                            }
                            break;
                    }
                    break;
                case 4:
                    switch (row) {
                        case 0:
                            if (orthogonalDieCheck(die,row,col-1) &&
                                    orthogonalDieCheck(die,row+1,col)) {
                                crossCheck = true;
                            }
                            break;

                        case 3:
                            if (orthogonalDieCheck(die,row,col-1) &&
                                    orthogonalDieCheck(die,row-1,col)) {
                                crossCheck = true;
                            }
                            break;

                        default:
                            if (orthogonalDieCheck(die,row,col-1) &&
                                    orthogonalDieCheck(die,row-1,col) &&
                                    orthogonalDieCheck(die,row+1,col)) {
                                crossCheck = true;
                            }
                            break;
                    }
                    break;
                default:
                    switch (row) {
                        case 0:
                            if (orthogonalDieCheck(die,row,col-1) &&
                                    orthogonalDieCheck(die,row,col+1) &&
                                    orthogonalDieCheck(die,row+1,col)) {
                                crossCheck = true;
                            }
                            break;

                        case 3:
                            if (orthogonalDieCheck(die,row,col-1) &&
                                    orthogonalDieCheck(die,row,col+1) &&
                                    orthogonalDieCheck(die,row-1,col)) {
                                crossCheck = true;
                            }
                            break;

                        default:
                            if (orthogonalDieCheck(die,row,col-1) &&
                                    orthogonalDieCheck(die,row,col+1) &&
                                    orthogonalDieCheck(die,row-1,col) &&
                                    orthogonalDieCheck(die,row+1,col)) {
                                crossCheck = true;
                            }
                            break;
                    }
                    break;
            }
        }
        return crossCheck;
    }

    public boolean checkRestrictions(Die die, int row, int col) {
        //check all the placement restrictions
        Cell cell = getPCCell(row, col);
        if(cell.placeableShade(die)
           && cell.placeableColor(die)
           && crossCheck(die, row, col)
           && touchingCheck(row, col)
           && (getDie(row, col) == null)) {
            return true;
        }
        return false;
    }

    public void placeDie(Die die, int row, int col) throws InvalidPlaceException {
        if (placements[row][col] == null) {
            placements[row][col] = die;
        } else {
            throw new InvalidPlaceException();
        }
    }

    public Die removeDie(int row, int col) {
        Die die = placements[row][col];
        placements[row][col] = null;
        return die;
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
