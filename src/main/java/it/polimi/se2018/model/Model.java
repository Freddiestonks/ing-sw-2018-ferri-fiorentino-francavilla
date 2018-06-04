package it.polimi.se2018.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.se2018.utils.Observable;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

//@Singleton
/**
 * This class is the core of the Model, it generates all of the needed class for the application to work properly
 *
 * @author Federico Ferri
 * @author Alessio Fiorentino
 * @author Simone Francavilla
 *
 * */
public class Model extends Observable {
    //Attributes
    private static final Model instance = new Model();
    private ArrayList<LocalModelInterface> localModels = new ArrayList<>();
    private int idMatch;
    private boolean started;
    private ArrayList<Player> players = new ArrayList<>();
    private int round = 1;
    private int turn = 1;
    private boolean backward = false;
    private int numPlayers = 0;
    private DiceBag diceBag;
    private ArrayList<Die> draftPool = new ArrayList<>();
    private ArrayList<ArrayList<Die>> roundTrack = new ArrayList<>();
    private PubObjCard[] pubOCs = new PubObjCard[3];
    private PrivObjCard[] privOCs = new PrivObjCard[5];
    private ToolCard[] toolCards = new ToolCard[12];
    private PatternCard[] patCards = new PatternCard[12];
    //private ArrayList<Player> leaderboard = new ArrayList<>();
    private final int numPubOCs = 10;
    //Methods
    /**
     * This is the constructor method for the model, it will generate the whole roundTrack and all of the Public Cards
     * */
    Model() {
        for(int i = 0; i < 10; i++) {
            roundTrack.add(new ArrayList<>());
        }

        boolean[] usedId = new boolean[numPubOCs];

        for (int i = 0; i < numPubOCs; i++){
            usedId[i] = false;
        }

        for(int i = 0; i < 3;i++){
            Random rand = new Random();
            int id = rand.nextInt(numPubOCs - 1);
            while (usedId[id]){
                id = rand.nextInt(numPubOCs - 1);
            }
            usedId[id] = true;
            pubOCs[i] = loadPC(id);
        }
    }

    public static Model instance(){
        return instance;
    }

    public void addPlayer(String username) throws MaxNumPlayersException{
        if(numPlayers >= 4) {
            throw new MaxNumPlayersException();
        }
        players.add(new Player(username));
        numPlayers++;
    }

    public void removePlayer(int i) {
        // method setConnection used to update the player's status (offline)
        players.get(i).setConnection(false);
    }

    public void reinsertPlayer(int i){
        // method setConnection used to update the player's status (online)
        players.get(i).setConnection(true);
    }

    public void updateTurn() throws InvalidTurnException{
        if(round == 10 && backward && turn == 1) {
            throw new InvalidTurnException();
        }
        if(!backward && (turn == numPlayers)) {
            backward = true;
        }
        else if(backward && (turn == 1)) {
            backward = false;
            round++;
        }
        else if(backward) {
            turn--;
        }
        else {
            turn++;
        }
    }

    public int getRound(){
        return round;
    }

    public int getTurn(){
        return turn;
    }

    public boolean isBackward() {
        return backward;
    }

    public Die getDraftPoolDie(int pos) {
        return draftPool.get(pos);
    }

    public void rollDraftPool(){
        for (Die die: this.draftPool){
            die.roll();
        }
    }

    public void addDraftPoolDie(Die die) {
        draftPool.add(die);
        //TODO: increase DP counter
    }

    public void removeDraftPoolDie(int pos) {
        draftPool.remove(pos);
        //TODO: decrease DP counter
    }

    public Die getRoundTrackDie(int round, int i) {
        return roundTrack.get(round).get(i);
    }

    public void addRoundTrackDie(Die die, int round) {
        roundTrack.get(round).add(die);
    }

    public void removeRoundTrackDie(int round, int i) {
        roundTrack.get(round).remove(i);
    }

    public DiceBag getDiceBag() {
        return diceBag;
    }
    /**
     * This is the method used to calculate the overall score of the game, it will order the players by score in an
     * attribute called "leaderboard"
     * */
    public void calculateScore(){
        /*
        leaderboard = players;
        for (int i = 1; i<numPlayers;i++){
            for (int j=0; j < i;j++){
                if(leaderboard[i].calculateScore(pubOCs) > leaderboard[j].calculateScore(pubOCs)){
                    Player tempPlayer;
                    tempPlayer = leaderboard[j];
                    leaderboard[j] = leaderboard [i];
                    leaderboard[i] = tempPlayer;
                }
            }
        }*/
    }

    public ToolCard getToolCard(int id){
        return this.toolCards[id - 1];
    }

    /**
     * This method is used to load a Public Card
     * @param publicId this is the id of the wanted Public Card
     * */
    private PubObjCard loadPC(int publicId){

        PubObjCard pubObjCard = null;
        String path = "src/main/json/publicCards.json";
        JsonParser jsonParser = new JsonParser();

        try {
            Object object = jsonParser.parse(new FileReader(path));
            JsonObject jsonObject = (JsonObject) object;
            JsonArray publicCards = (JsonArray) jsonObject.get("publicCards");
            jsonObject = (JsonObject) publicCards.get(publicId);
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
                        throw new IllegalArgumentException();
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
                        throw new IllegalArgumentException();
                }
            }

        } catch (FileNotFoundException ignored) {
        }

        return pubObjCard;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    /*public Player[] getLeaderboard() {
        return leaderboard;
    }*/

    public Player getPlayer(int i) {
        return players.get(i);
    }

    public void addClient(LocalModelInterface localModel) {
        localModels.add(localModel);
    }

    public void removeClient(int i) {
        localModels.remove(i);
        players.remove(i);
        numPlayers--;
    }

    public void reset() {
        localModels.clear();
        players.clear();
        numPlayers = 0;
    }

    public boolean checkConnection(int i) {
        boolean check = true;
        try {
            localModels.get(i).checkConnection();
        } catch (IOException e) {
            check = false;
        } finally {
            return check;
        }
    }
    public ArrayList<Die> getDraftPool() {
        return draftPool;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }
}
