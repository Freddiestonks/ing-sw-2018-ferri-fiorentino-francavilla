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
    private DiceBag diceBag = DiceBag.instance();
    private ArrayList<Die> draftPool = new ArrayList<>();
    private ArrayList<ArrayList<Die>> roundTrack = new ArrayList<>();
    private PubObjCard[] pubOCs = new PubObjCard[3];
    private PrivObjCard[] privOCs = new PrivObjCard[5];
    private ToolCard[] toolCards = new ToolCard[12];
    private PatternCard[] patCards = new PatternCard[12];
    private ArrayList<Player> leaderboard = new ArrayList<>();
    private final int numPubOCs = 10;
    //Methods
    /**
     * This is the constructor method for the model, it will generate the whole roundTrack and all of the Public Cards
     * */
    private Model() {
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

    /**
     * This method return the instance of the singleton Model.
     *
     * @return a Model reference to the singleton.
     */
    public static Model instance(){
        return instance;
    }

    /**
     * This method is used to add a player in a specific array 'players'.
     * Afterward is added a new LocalModel specific for the player that is just added.
     *
     * @param username is the player's name to be added.
     * @param localModel is the reference to the LocalModel class.
     * @throws MaxNumPlayersException
     */
    public void addPlayer(String username, LocalModelInterface localModel) throws MaxNumPlayersException{
        if(numPlayers >= 4) {
            throw new MaxNumPlayersException();
        }
        players.add(new Player(username));
        localModels.add(localModel);
        numPlayers++;
    }

    /**
     * This method is used to remove a specific player, passing his number.
     *
     * @param i is the number of the player to be removed.
     */
    public void removePlayer(int i) {
        // method setConnection used to update the player's status (offline)
        players.get(i).setConnected(false);
    }

    /**
     * This method is used to reinsert in the match a player that has leaved just for a while.
     *
     * @param i is the number of the player to be reinserted.
     * @param localModel is the reference to the player's LocalModel instance.
     */
    public void reinsertPlayer(int i, LocalModelInterface localModel){
        // method setConnected used to update the player's status (online)
        localModels.set(i, localModel);
        players.get(i).setConnected(true);
    }

    /**
     * This method updates the turn.
     *
     * @throws InvalidTurnException
     */
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

    /**
     * This method return the current round's number
     *
     * @return an integer value representing the round.
     */
    public int getRound(){
        return round;
    }

    /**
     * This method return the current turn's number
     *
     * @return an integer value representing the turn.
     */
    public int getTurn(){
        return turn;
    }

    /**
     * This method is used to query if the turning flow is backward.
     *
     * @return a boolean value, true if is backward.
     */
    public boolean isBackward() {
        return backward;
    }

    /**
     * This method gives a specific die from the draftPool
     *
     * @param pos is the value representing the position in the DraftPool.
     * @return a Die instance, more precisely a reference to the die in the DraftPool.
     */
    public Die getDraftPoolDie(int pos) {
        return draftPool.get(pos);
    }

    /**
     * This method is used to rool every die into the DraftPool.
     */
    public void rollDraftPool(){
        for (Die die: this.draftPool){
            die.roll();
        }
    }

    /**
     * This method is used to add a die instance into the draftPool, that is passed to the method itself.
     *
     * @param die a Die instance of the die to be inserted in the DraftPool
     */
    public void addDraftPoolDie(Die die) {
        draftPool.add(die);
    }

    /**
     * This method is used to remove a die (of a given position) from the DraftPool.
     *
     * @param pos is an integer representing the die to be removed.
     */
    public void removeDraftPoolDie(int pos) {
        draftPool.remove(pos);
    }

    /**
     * This method provides a specific die from the RoundTrack.
     *
     * @param round an integer value representing the round in which the die is been used.
     * @param i an integer that represent the row value to find the die from the RoundTrack.
     * @return a Die reference to the die wanted.
     */
    public Die getRoundTrackDie(int round, int i) {
        return roundTrack.get(round).get(i);
    }

    /**
     * This method provides the number of dice inserted in a Round-Track's round.
     * @param round is a round value.
     * @return the number of dice in the Round-Track relatively to the specific round.
     */
    public int getRTDieRound(int round){
        return roundTrack.get(round).size();
    }

    /**
     * This method is used to add a new die in the RoundTrack.
     *
     * @param die The Die instance to be added.
     * @param round is the round in which is used and so the column of the matrix representing the RoundTrack.
     */
    public void addRoundTrackDie(Die die, int round) {
        roundTrack.get(round).add(die);
    }

    /**
     * This method is used to remove a die from the RoundTrack.
     *
     * @param round an integer value representing the round in which the die is been used.
     * @param i an integer that represent the value to find the die from the RoundTrack.
     */
    public void removeRoundTrackDie(int round, int i) {
        roundTrack.get(round).remove(i);
    }

    /**
     * This method is used to provide the instance of the DiceBag (Singleton).
     *
     * @return a DiceBag reference.
     */
    public DiceBag getDiceBag() {
        return diceBag;
    }
    /**
     * This is the method used to calculate the overall score of the game, it will order the players by score in an
     * attribute called "leaderboard"
     * */
    public void calculateScore(){
        leaderboard = players;
        for (int i = 1; i<numPlayers;i++){
            for (int j=0; j < i;j++){
                if(leaderboard.get(i).calculateScore(pubOCs) > leaderboard.get(j).calculateScore(pubOCs)){
                    Player tempPlayer;
                    tempPlayer = leaderboard.get(j);
                    leaderboard.set(j,leaderboard.get(i));
                    leaderboard.set(i,tempPlayer);
                }
            }
        }

    }

    /**
     * This method is used to provide a specific Tool-card.
     *
     * @param id is the number of the requested Tool-card.
     * @return a Tool-card reference.
     */
    public ToolCard getToolCard(int id){
        return this.toolCards[id - 1];
    }

    /**
     * This method is used to load a Public Card
     *
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

    /**
     * This method provides the number of players that play the match.
     *
     * @return an integer value representing the number of players.
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    public ArrayList<Player> getLeaderboard() {
        return leaderboard;
    }

    /**
     * This method provides a specific reference to a player.
     *
     * @param i is the player's number in the array 'players'.
     * @return a Player reference.
     */
    public Player getPlayer(int i) {
        return players.get(i);
    }

    /**
     * This method is used to add a specific LocalModel instance to the array 'localModels'.
     *
     * @param localModel is a LocalModelInterface reference to be added.
     */
    public void addClient(LocalModelInterface localModel) {
        localModels.add(localModel);
    }

    /**
     * This method removes a specific player.
     *
     * @param i is the player's number to be removed in the array 'players'.
     */
    public void removeClient(int i) {
        localModels.remove(i);
        players.remove(i);
        numPlayers--;
    }

    /**
     * This method removes all the instances of the Singleton Model.
     */
    public void reset() {
        localModels.clear();
        players.clear();
        diceBag.reset();
        draftPool.clear();
        roundTrack.clear();
        for(int i = 0; i < 10; i++) {
            roundTrack.add(new ArrayList<>());
        }
        round = 1;
        turn = 1;
        backward = false;
        numPlayers = 0;
        //TODO: reset other game elements
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

    /**
     * This method provides the Public Cards of the Model instance.
     *
     * @return an array of Public-Cards.
     */
    public PubObjCard[] getPubOCs() {
        return pubOCs;
    }

    /**
     * This method provides a reference to the DraftPool.
     *
     * @return an Arraylist reference of the DraftPool.
     */
    public ArrayList<Die> getDraftPool() {
        return draftPool;
    }

    /**
     * This method provides a reference to the set of players.
     *
     * @return an Arraylist reference representing the list of players.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

}
