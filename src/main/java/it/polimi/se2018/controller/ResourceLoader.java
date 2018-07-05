package it.polimi.se2018.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.tceffects.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This class load the resources that compose the game elements and other configurations.
 */
public class ResourceLoader {

    private final JsonParser jsonParser;

    private static final String CONFIG_PATH = "src/main/json/config.json";
    private static final String PCS_PATH = "src/main/json/patternCards.json";
    private static final String PUBOCS_PATH = "src/main/json/publicCards.json";
    private static final String TOOL_CARDS_PATH = "src/main/json/toolCards.json";

    public ResourceLoader() {
        jsonParser = new JsonParser();
    }

    /**
     * This method is used to translate a string to a Color.
     * @param string this is the readInput string and it will be translated into a Color.
     * @return a color instance.
     */
    private Color colorTranslator(String string) {
        //This method is used to translate the "Colors" in the JSON file from english to a Color, ignore
        //all blank spaces and change letters to uppercase so that the process of adding cards
        //is more "user friendly"
        return Color.valueOf(string.toUpperCase().replaceAll("\\s+",""));
    }

    /**
     * This method is used to retrieve a int from a JSON object.
     * @param jsonObject this is the JSON object that will be parsed.
     * @param string this is the element that is looked for in the file.
     * @return an integer value.
     */
    private int jIntGetter(JsonObject jsonObject, String string) {
        //returns a int from a JSON object's parameter, use a different method so that in case of updates it is easier
        //to change all of the methods quickly
        return jsonObject.get(string).getAsInt();
    }

    /**
     * This method is used to retrieve a String from a JSON object.
     * @param jsonObject this is the JSON object that will be parsed.
     * @param string this is the element that is looked for in the file.
     * @return a string instance.
     */
    private String jStringGetter(JsonObject jsonObject, String string) {
        //returns a String from a JSON object's parameter, use a different method so that in case of updates it is easier
        //to change all of the methods quickly
        return jsonObject.get(string).getAsString();
    }

    /**
     *
     * This method is used to fill a Cell object with the elements found on the JSON file.
     * @param cellArray this is the matrix of cells that need to be filled.
     * @param cells this is the array in the JSON file containing all of the cells that need to be updated.
     * @return a matrix of cell values.
     * @throws ResourceLoaderException
     */
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

    /**
     * This method load the value of the lobby timeout (in seconds).
     *
     * @return an integer representing the value.
     * @throws ResourceLoaderException
     */
    public int loadLobbyTimeout() throws ResourceLoaderException {
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

    /**
     * This method load the value of the turn timeout (in seconds).
     *
     * @return an integer representing the value.
     * @throws ResourceLoaderException
     */
    public int loadTurnTimeout() throws ResourceLoaderException {
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

    /**
     * This method load the number of PatternCard from file.
     *
     * @return an integer representing the number of PatternCard.
     * @throws ResourceLoaderException
     */
    public int loadNumPCs() throws ResourceLoaderException {
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

    /**
     * This method load the number of Public Objective Card from file.
     *
     * @return an integer representing the number of Public Objective Card
     * @throws ResourceLoaderException
     */
    public int loadNumPubOCs() throws ResourceLoaderException {
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
     * This method load the number of ToolCard from file.
     *
     * @return an integer representing the number of Public ToolCard.
     * @throws ResourceLoaderException
     */
    public int loadNumToolCards() throws ResourceLoaderException {
        Object object;
        try {
            object = jsonParser.parse(new FileReader(TOOL_CARDS_PATH));
        } catch (FileNotFoundException e) {
            throw new ResourceLoaderException();
        }
        JsonObject jsonObject = (JsonObject)object;
        //transform the Object to an array of "tool cards"
        JsonArray publicCards = (JsonArray)jsonObject.get("toolCards");
        return publicCards.size();
    }

    /**
     * This method is used to load a PatternCard from the JSON file with a specific id.
     * @param id this is the id of the wanted PatternCard.
     * @return a PatternCard instance
     * @throws ResourceLoaderException
     */
    public PatternCard loadPC(int id) throws ResourceLoaderException {
        // loadPC will load a pattern card from file written in JSON where cards are kept in a specific order
        // their position can be used as an "ID"
        PatternCard patternCard;
        //parse the json file
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
            for (int i = 0; i < 4; i++){
                for (int j = 0; j < 5; j++){
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
     * @param id this is the id of the wanted Public Objective Card
     * @return a PublicObjCard instance.
     * @throws ResourceLoaderException
     */
    public PubObjCard loadPubOC(int id) throws ResourceLoaderException {
        PubObjCard pubObjCard = null;
        try {
            Object object = jsonParser.parse(new FileReader(PUBOCS_PATH));
            JsonObject jsonObject = (JsonObject) object;
            JsonArray publicCards = (JsonArray) jsonObject.get("publicCards");
            jsonObject = (JsonObject) publicCards.get(id);
            String type = jStringGetter(jsonObject, "type");
            String name = jStringGetter(jsonObject, "name");
            String description = jStringGetter(jsonObject, "description");
            int multiplier = jIntGetter(jsonObject, "multiplier");
            String subtype = jStringGetter(jsonObject, "subtype");
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

    /**
     * This method is used to load a ToolCard
     * @param id this is the id of the wanted ToolCard
     * @return a ToolCard instance.
     * @throws ResourceLoaderException
     */
    public ToolCard loadToolCard(int id) throws ResourceLoaderException {
        ToolCard toolCard;
        String name;
        String description;
        ArrayList<AbstractTCEffect> effects = new ArrayList<>();
        JsonArray jsonArray;
        try {
            Object object;
            object = jsonParser.parse(new FileReader(TOOL_CARDS_PATH));
            JsonObject jsonObject = (JsonObject) object;
            JsonArray toolCards = (JsonArray) jsonObject.get("toolCards");
            jsonObject = (JsonObject) toolCards.get(id);
            name = jStringGetter(jsonObject, "name");
            description = jStringGetter(jsonObject, "description");
            jsonArray = (JsonArray) jsonObject.get("effects");
        } catch (FileNotFoundException e) {
            throw new ResourceLoaderException();
        }
        for(JsonElement effect : jsonArray) {
            switch (effect.getAsString()) {
                case "addOrSubDPDie":
                    effects.add(new AddOrSubDPDie());
                    break;
                case "moveDieIgnoringColor":
                    effects.add(new MoveDieIgnoringColor());
                    break;
                case "moveDieIgnoringValue":
                    effects.add(new MoveDieIgnoringValue());
                    break;
                case "moveTwoDice":
                    effects.add(new MoveTwoDice());
                    break;
                case "swapDPAndRTDice":
                    effects.add(new SwapDPAndRTDice());
                    break;
                case "rollDPDie":
                    effects.add(new RollDPDie());
                    break;
                case "rollDraftPool":
                    effects.add(new RollDraftPool());
                    break;
                case "doubleTurn":
                    effects.add(new DoubleTurn());
                    break;
                case "notAdjPlace":
                    effects.add(new NotAdjPlace());
                    break;
                case "flipDPDieOver":
                    effects.add(new FlipDPDieOver());
                    break;
                case "diceBagPlace":
                    effects.add(new DiceBagPlace());
                    break;
                case "moveDiceAsRTDieColor":
                    effects.add(new MoveDiceAsRTDieColor());
                    break;
                default:
                    throw new ResourceLoaderException();
            }
        }
        toolCard = new ToolCard(name, description, effects);
        return toolCard;
    }
}
