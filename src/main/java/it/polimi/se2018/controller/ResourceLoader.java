package it.polimi.se2018.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.se2018.model.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;

public class ResourceLoader {

    private static final String CONFIG_PATH = "src/main/json/config.json";
    private static final String PCS_PATH = "src/main/json/patternCards.json";
    private static final String PUBOCS_PATH = "src/main/json/publicCards.json";

    /**
     * This method is used to translate a string to a "Color"
     * @param string this is the readInput string and it will be translated into a "Color"
     * */
    private Color colorTranslator(String string) {
        //This method is used to translate the "Colors" in the JSON file from english to a Color, ignore
        //all blank spaces and change letters to uppercase so that the process of adding cards
        //is more "user friendly"
        return Color.valueOf(string.toUpperCase().replaceAll("\\s+",""));
    }

    /**
     * This method is used to retrieve a int from a JSON object
     * @param jsonObject this is the JSON object that will be parsed
     * @param string this is the element that is looked for in the file
     * */

    private int jIntGetter(JsonObject jsonObject, String string) {
        //returns a int from a JSON object's parameter, use a different method so that in case of updates it is easier
        //to change all of the methods quickly
        return jsonObject.get(string).getAsInt();
    }

    /**
     * This method is used to retrieve a String from a JSON object
     * @param jsonObject this is the JSON object that will be parsed
     * @param string this is the element that is looked for in the file
     * */

    private String jStringGetter(JsonObject jsonObject, String string) {
        //returns a String from a JSON object's parameter, use a different method so that in case of updates it is easier
        //to change all of the methods quickly
        return jsonObject.get(string).getAsString();
    }

    /**
     * This method is used to fill a Cell object with the elements found on the JSON file
     * @param cellArray this is the matrix of cells that need to be filled
     * @param cells this is the array in the JSON file containing all of the cells that need to be updated
     * */
    private Cell[][] jCellFiller(Cell[][] cellArray, JsonArray cells) throws ResourceLoaderException {
        //this method is used to generate a ShadeCell or a ColorCell on the correct space with the correct value/color
        for(int j = 0; j < cells.size(); j++){
            JsonObject jsonCell = (JsonObject)cells.get(j);
            if (Objects.equals(jStringGetter(jsonCell, "type"), "shade")){
                //this method recognize if it is a shade type and it will fill it with the correct attributes
                int cellRow = jIntGetter(jsonCell,"row");
                int cellCol = jIntGetter(jsonCell,"col");
                int cellShade = jIntGetter(jsonCell, "value");
                cellArray[cellRow][cellCol] = new ShadeCell(cellShade);
            }
            else if (Objects.equals(jStringGetter(jsonCell, "type"), "color")){
                //this method recognize if it is a color type and it will fill it with the correct color (through the translator method)
                int cellRow = jIntGetter(jsonCell,"row");
                int cellCol = jIntGetter(jsonCell,"col");
                String cellColor = jStringGetter(jsonCell, "color");
                cellArray[cellRow][cellCol] = new ColorCell(colorTranslator(cellColor));
            }
            else {
                throw new ResourceLoaderException();
            }
        }
        return cellArray;
    }

    public int loadLobbyTimeout() throws ResourceLoaderException {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject;
        try {
            //The Json file is then transferred to an object
            Object object = jsonParser.parse(new FileReader(CONFIG_PATH));
            jsonObject = (JsonObject)object;
        } catch (FileNotFoundException e) {
            throw new ResourceLoaderException();
        }
        return jIntGetter(jsonObject, "lobbyTimeout");
    }

    public int loadTurnTimeout() throws ResourceLoaderException {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject;
        try {
            //The Json file is then transferred to an object
            Object object = jsonParser.parse(new FileReader(CONFIG_PATH));
            jsonObject = (JsonObject)object;
        } catch (FileNotFoundException e) {
            throw new ResourceLoaderException();
        }
        return jIntGetter(jsonObject, "turnTimeout");
    }

    public int loadNumPCs() throws ResourceLoaderException {
        JsonParser jsonParser = new JsonParser();
        Object object;
        try {
            object = jsonParser.parse(new FileReader(PCS_PATH));
        } catch (FileNotFoundException e) {
            throw new ResourceLoaderException();
        }
        JsonObject jsonObject = (JsonObject)object;
        //transform the Object to an array of "cards"
        JsonArray patternCards = (JsonArray)jsonObject.get("patternCards");
        return patternCards.size();
    }

    public int loadNumPubOCs() throws ResourceLoaderException {
        JsonParser jsonParser = new JsonParser();
        Object object;
        try {
            object = jsonParser.parse(new FileReader(PUBOCS_PATH));
        } catch (FileNotFoundException e) {
            throw new ResourceLoaderException();
        }
        JsonObject jsonObject = (JsonObject)object;
        //transform the Object to an array of "public cards"
        JsonArray publicCards = (JsonArray)jsonObject.get("publicCards");
        return publicCards.size();
    }

    /**
     * This method is used to load a PatternCard from the JSON file with a specific id
     * @param id this is the id of the wanted PatternCard
     * */
    public PatternCard loadPC(int id) throws ResourceLoaderException {
        // loadPC will load a pattern card from file written in JSON where cards are kept in a specific order
        // their position can be used as an "ID"
        PatternCard patternCard = null;
        //parse the json file
        JsonParser jsonParser = new JsonParser();
        try {
            //The Json file is then transferred to an object
            Object object = jsonParser.parse(new FileReader(PCS_PATH));
            JsonObject jsonObject = (JsonObject)object;
            //transform the Object to an array of "cards"
            JsonArray patternCards = (JsonArray)jsonObject.get("patternCards");
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
            for (int i = 0; i<4;i++){
                for (int j = 0;j<5;j++){
                    cellF[i][j] = new Cell();
                    cellB[i][j] = new Cell();
                }
            }
            //get the information about the "special" cells and load them in a different JSON array
            JsonArray cells = (JsonArray)jsonObject.get("cellF");
            //use the method jCellFiller to fill the top cells
            cellF = jCellFiller(cellF,cells);
            //In the same way generate the bottom part
            cells = (JsonArray)jsonObject.get("cellB");
            cellB = jCellFiller(cellB,cells);
            //create a new patterCard with the attributes loaded from the file
            patternCard = new PatternCard(levelF,levelB,nameF,nameB,cellF,cellB);
        }
        catch (FileNotFoundException nfe){
            //in case there is no JSON file we throw an exception
            throw new ResourceLoaderException();
        }
        return patternCard;
    }

    /**
     * This method is used to load a Public Objective Card
     *
     * @param id this is the id of the wanted Public Objective Card
     * */
    public PubObjCard loadPubOC(int id) throws ResourceLoaderException {
        PubObjCard pubObjCard = null;
        JsonParser jsonParser = new JsonParser();
        try {
            Object object = jsonParser.parse(new FileReader(PUBOCS_PATH));
            JsonObject jsonObject = (JsonObject) object;
            JsonArray publicCards = (JsonArray) jsonObject.get("publicCards");
            jsonObject = (JsonObject) publicCards.get(id);
            String type = jsonObject.get("type").getAsString();
            String name = jsonObject.get("name").getAsString();
            String description = jsonObject.get("description").getAsString();
            int multiplier = jsonObject.get("multiplier").getAsInt();
            String subtype = jsonObject.get("subtype").getAsString();
            if (type.equals("color")) {
                switch (subtype) {
                    case "row":
                        pubObjCard = new PubOCColorDet(description, name, true, false, false, multiplier);
                        break;
                    case "col":
                        pubObjCard = new PubOCColorDet(description, name, false, true, false, multiplier);
                        break;
                    case "diagonals":
                        pubObjCard = new PubOCColorDet(description, name, false, false, true, multiplier);
                        break;
                    case "set":
                        boolean[] colors = new boolean[5];
                        JsonArray jsonArray = jsonObject.get("values").getAsJsonArray();
                        for (int i = 0; i < 5; i++) {
                            colors[i] = jsonArray.get(i).getAsBoolean();
                        }
                        pubObjCard = new PubOCColorSet(description, name, colors, multiplier);
                        break;
                    default:
                        throw new ResourceLoaderException();
                }
            } else if (type.equals("shade")) {
                switch (subtype) {
                    case "row":
                        pubObjCard = new PubOCShadeDet(description, name, true, true, multiplier);
                        break;
                    case "col":
                        pubObjCard = new PubOCShadeDet(description, name, false, true, multiplier);
                        break;
                    case "set":
                        boolean[] shades = new boolean[6];
                        JsonArray jsonArray = jsonObject.get("values").getAsJsonArray();
                        for (int i = 0; i < 6; i++) {
                            shades[i] = jsonArray.get(i).getAsBoolean();
                        }
                        pubObjCard = new PubOCShadeSet(description, name, shades, multiplier);
                        break;
                    default:
                        throw new ResourceLoaderException();
                }
            }
        } catch (FileNotFoundException e) {
            throw new ResourceLoaderException();
        }
        return pubObjCard;
    }
}
