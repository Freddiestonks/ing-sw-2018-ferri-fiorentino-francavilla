package it.polimi.se2018.model;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.utils.Observable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger;

/**
 * This class is the core of the Model, it generates all of the needed class for the application to work properly
 *
 * @author Federico Ferri
 * @author Alessio Fiorentino
 * @author Simone Francavilla
 *
 * */
public class Model extends Observable implements ModelInterface {
    //Attributes
    private static Model instance = null;
    private ArrayList<LocalModelInterface> localModels = new ArrayList<>();
    private boolean lobbyGathering = true;
    private boolean started = false;
    private ArrayList<Player> players = new ArrayList<>();
    private int round = 1;
    private int turn = 1;
    private boolean over = false;
    private boolean backward = false;
    private int numPlayers = 0;
    private DiceBag diceBag = DiceBag.instance();
    private ArrayList<Die> draftPool = new ArrayList<>();
    private ArrayList<ArrayList<Die>> roundTrack = new ArrayList<>();
    private PubObjCard[] pubOCs = new PubObjCard[3];
    //private PrivObjCard[] privOCs = new PrivObjCard[5];
    private ToolCard[] toolCards = new ToolCard[3];
    private boolean toolCardUsed = false;
    private PatternCard[] patCards = null;
    private ArrayList<Player> leaderBoard = new ArrayList<>();

    public static final Logger LOGGER = Logger.getLogger(Model.class.getName());

    //Methods
    /**
     * This is the constructor method for the model, it will initialize the whole roundTrack
     * */
    private Model() {
        for(int i = 0; i < 10; i++) {
            roundTrack.add(new ArrayList<>());
        }
    }

    /**
     * This method return the instance of the singleton Model.
     *
     * @return a Model reference to the singleton.
     */
    public static Model instance() {
        if(instance == null) {
            instance = new Model();
        }
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
    public void addPlayer(String username, LocalModelInterface localModel) {
        if(numPlayers >= 4) {
            throw new MaxNumPlayersException();
        }
        players.add(new Player(username));
        localModels.add(localModel);
        numPlayers++;
        notifyObservers();
    }

    /**
     * This method is used to remove a specific player, passing his number.
     *
     * @param playerIndex is the number of the player to be removed.
     */
    public void removePlayer(int playerIndex) {
        // method setConnection used to update the player's status (offline)
        players.get(playerIndex).setConnected(false);
        int connectedPlayers = 0;
        for(Player player : players) {
            if(player.isConnected()) {
                connectedPlayers++;
            }
        }
        LOGGER.fine("connected players: " + connectedPlayers);
        if(connectedPlayers == 1) {
            over = true;
            calculateScore();
            return;
        }
        notifyObservers();
    }

    /**
     * This method is used to reinsert in the match a player that has leaved just for a while.
     *
     * @param playerIndex is the number of the player to be reinserted.
     * @param localModel is the reference to the player's LocalModel instance.
     */
    public void reinsertPlayer(int playerIndex, LocalModelInterface localModel){
        // method setConnected used to update the player's status (online)
        localModels.set(playerIndex, localModel);
        players.get(playerIndex).setConnected(true);
        players.get(playerIndex).setSwitchingConn(false);
        updateLocalModel(playerIndex);
        notifyObservers();
    }

    /**
     * This method updates the turn.
     */
    public void updateTurn() {
        if(round == 10 && backward && turn == 1) {
            over = true;
            calculateScore();
            return;
        }
        if(!backward && (turn == numPlayers)) {
            backward = true;
        }
        else if(backward && (turn == 1)) {
            rollDraftPool();
            round++;
            backward = false;
        }
        else if(backward) {
            turn--;
        }
        else {
            turn++;
        }
        ToolCard.resetPendingAction();
        toolCardUsed = false;
        Player player = players.get(turn - 1);
        if(player.isSkip()) {
            player.setSkip(false);
            updateTurn();
            return;
        }
        else if(!player.isConnected()) {
            updateTurn();
            return;
        }
        for(LocalModelInterface localModel : localModels) {
            try {
                localModel.updateTurn(round, turn, backward);
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
        notifyObservers();
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
     * This method is used to roll the dice from the DiceBag into the DraftPool.
     */
    private void rollDraftPool() {
        while(!draftPool.isEmpty()) {
            roundTrack.get(round - 1).add(draftPool.remove(0));
        }
        for(int i = 0; i < 2 * numPlayers + 1; i++) {
            draftPool.add(diceBag.extract());
        }
        for(LocalModelInterface localModel : localModels) {
            try {
                localModel.setDraftPool(draftPool);
                localModel.setRoundTrack(roundTrack);
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
        //notifyObservers();
    }

    /**
     * This method is used to add a die instance into the draftPool, that is passed to the method itself.
     *
     * @param die a Die instance of the die to be inserted in the DraftPool
     */
    public void addDraftPoolDie(Die die) {
        draftPool.add(die);
        for(LocalModelInterface localModel : localModels) {
            try {
                localModel.setDraftPool(draftPool);
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
        //notifyObservers();
    }

    /**
     * This method is used to remove a die (of a given position) from the DraftPool.
     *
     * @param pos is an integer representing the die to be removed.
     * @return die just removed
     */
    public Die removeDraftPoolDie(int pos) {
        Die die = draftPool.remove(pos);
        for(LocalModelInterface localModel : localModels) {
            try {
                localModel.setDraftPool(draftPool);
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
        //notifyObservers();
        return die;
    }

    /**
     * This method is used to remove a die (of a given position) from the DraftPool.
     *
     * @param die represent the die to be removed.
     */
    public void removeDraftPoolDie(Die die) {
        draftPool.remove(die);
        for(LocalModelInterface localModel : localModels) {
            try {
                localModel.setDraftPool(draftPool);
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
        //notifyObservers();
    }

    /**
     * This method provides the number of dice in the Draft Pool.
     *
     * @return the size of the Draft Pool.
     */
    public int getDraftPoolSize() {
        return draftPool.size();
    }

    /**
     * This method provides a specific die from the RoundTrack.
     *
     * @param round an integer value representing the round in which the die is been used.
     * @param i an integer that represent the row value to find the die from the RoundTrack.
     * @return a Die reference to the die wanted.
     */
    public Die getRoundTrackDie(int round, int i) {
        return roundTrack.get(round - 1).get(i);
    }

    /**
     * This method is used to add a new die in the RoundTrack.
     *
     * @param die The Die instance to be added.
     * @param round is the round in which is used and so the column of the matrix representing the RoundTrack.
     */
    public void addRoundTrackDie(Die die, int round) {
        roundTrack.get(round - 1).add(die);
        for(LocalModelInterface localModel : localModels) {
            try {
                localModel.setRoundTrack(roundTrack);
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
        //notifyObservers();
    }

    /**
     * This method is used to remove a die from the RoundTrack.
     *
     * @param round an integer value representing the round in which the die is been used.
     * @param i an integer that represent the value to find the die from the RoundTrack.
     * @return die just removed
     */
    public Die removeRoundTrackDie(int round, int i) {
        Die die = roundTrack.get(round - 1).remove(i);
        for(LocalModelInterface localModel : localModels) {
            try {
                localModel.setRoundTrack(roundTrack);
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
        //notifyObservers();
        return die;
    }

    /**
     * This method provides the number of dice inserted in a Round-Track's round.
     * @param round is a round value.
     * @return the number of dice in the Round-Track relatively to the specific round.
     */
    public int getRoundTrackSize(int round) {
        return roundTrack.get(round - 1).size();
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
     * This is the method used to calculate the overall score of the game, it will order the players by score in the Leader Board.
     * */
    private void calculateScore() {
        LOGGER.fine("begin calculate score");
        int connectedPlayers = 0;
        int playerIndex = 0;
        for(int i = 0; i < numPlayers; i++) {
            if(players.get(i).isConnected()) {
                connectedPlayers++;
                playerIndex = i;
            }
        }
        if(connectedPlayers == 1) {
            LOGGER.fine("one player remained");
            leaderBoard.add(players.get(playerIndex));
        }
        else {
            leaderBoard = new ArrayList<>(players);
        }
        for (int i = 0; i < leaderBoard.size(); i++) {
            leaderBoard.get(i).calculateScore(pubOCs);
            for (int j = 0; j < i; j++) {
                if(leaderBoard.get(i).getScore() > leaderBoard.get(j).getScore()){
                    Player tempPlayer;
                    tempPlayer = leaderBoard.get(j);
                    leaderBoard.set(j, leaderBoard.get(i));
                    leaderBoard.set(i, tempPlayer);
                }
            }
        }
        LOGGER.fine("end calculate score");
        notifyObservers();
    }

    /**
     * This method is used to set the Tool-card chosen.
     */
    public void setToolCards(ToolCard[] toolCards) {
        this.toolCards = toolCards.clone();
    }

    /**
     * This method is used to provide a specific Tool-card.
     *
     * @param i is the number of the requested Tool-card.
     * @return a Tool-card reference.
     */
    public ToolCard getToolCard(int i){
        return this.toolCards[i - 1];
    }

    /**
     * This method provides the number of players that play the match.
     *
     * @return an integer value representing the number of players.
     */
    public int getNumPlayers() {
        return players.size();
    }

    /**
     * This method is used to return the leader-board of the game.
     *
     * @return an Arraylist on players that represents the leader-board.
     */
    public ArrayList<Player> getLeaderBoard() {
        return leaderBoard;
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
     * This method removes a specific player.
     *
     * @param i is the player's number to be removed in the array 'players'.
     */
    public void removeClient(int i) {
        localModels.remove(i);
        players.remove(i);
        numPlayers--;
        notifyObservers();
    }

    /**
     * This method reset the entire Model in the initial state.
     */
    public void reset() {
        localModels.clear();
        players.clear();
        diceBag.reset();
        draftPool.clear();
        roundTrack.clear();
        leaderBoard.clear();
        for(int i = 0; i < 10; i++) {
            roundTrack.add(new ArrayList<>());
        }
        patCards = null;
        toolCards = null;
        pubOCs = null;
        lobbyGathering = true;
        started = false;
        over = false;
        round = 1;
        turn = 1;
        backward = false;
        numPlayers = 0;
        toolCardUsed = false;
        ToolCard.resetPendingAction();
    }

    /**
     * This method is used to check if a player is still connected to the game.
     *
     * @param i is the number of the player ti be checked.
     * @return a boolean value representing the check result.
     */
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
     * This method is used to set the public cards will be used during the game.
     *
     * @param pubOCs is the set of public card to be set.
     */
    public void setPubOCs(PubObjCard[] pubOCs) {
        this.pubOCs = pubOCs.clone();
        //LOGGER.fine(pubOCs[0]);
        //LOGGER.fine(pubOCs[1]);
        //LOGGER.fine(pubOCs[2]);
    }

    /**
     * Thsi method provides the PatternCards used into the match.
     *
     * @return an array of Pattercards.
     */
    public PatternCard[] getPatternCards() {
        return patCards.clone();
    }

    /**
     * This method is used to set array of Patterncards will be used into the game.
     *
     * @param patCards is the array of Patterncards to use
     */
    public void setPatternCards(PatternCard[] patCards) {
        this.patCards = patCards.clone();
        notifyObservers();
    }

    /**
     * This method provides a reference to the players.
     *
     * @return an Arraylist reference of the players.
     */
    public ArrayList<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    /**
     * This method provides a reference to the DraftPool.
     *
     * @return an Arraylist reference of the DraftPool.
     */
    public ArrayList<Die> getDraftPool() {
        return new ArrayList<>(draftPool);
    }

    /**
     * This method provides a reference to the RoundTrack.
     *
     * @return an Arraylist reference of the RoundTrack.
     */
    public ArrayList<ArrayList<Die>> getRoundTrack() {
        ArrayList<ArrayList<Die>> rt = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            rt.add(new ArrayList<>(roundTrack.get(i)));
        }
        return rt;
    }

    /**
     * This method is used to set-up the player for the first time, assigning him a private card.
     */
    public void playersSetup() {
        ArrayList<Color> availableColors = new ArrayList<>(Arrays.asList(Color.values()));
        Random random = new Random();
        for(Player player : players) {
            int num = random.nextInt(availableColors.size());
            Color color = availableColors.remove(num);
            player.setPrivOC(new PrivObjCard(color));
            LOGGER.fine("privoc color: "+color);
        }
    }

    /**
     * This method checks if the game is still in the lobby phase.
     *
     * @return a boolean value representing the check result.
     */
    public boolean isLobbyGathering() {
        return lobbyGathering;
    }

    /**
     * This method set the lobby status.
     *
     * @param lobbyGathering is the boolean value that represent the lobby status
     */
    public void setLobbyGathering(boolean lobbyGathering) {
        this.lobbyGathering = lobbyGathering;
    }

    /**
     * This method is used to set in the Local Model.
     *
     * @param playerIndex is the player ordinal number in the set of players
     * @param wf is the WindowsFrame to be set.
     */
    public void setWindowFrame(int playerIndex, WindowFrame wf) {
        players.get(playerIndex).setWinFrame(wf);
        try {
            localModels.get(playerIndex).setWindowFrame(wf);
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    /**
     * This methid provide the WindowsFrame of a specific player.
     *
     * @param playerIndex is the player ordinal number in the set of players
     * @return a WindowFrame instance
     */
    public WindowFrame getWindowFrame(int playerIndex) {
        return players.get(playerIndex).getWindowFrame();
    }

    /**
     * This is used to check if for a given player, is assigned to him a WindowFrame.
     *
     * @param playerIndex is the player ordinal number in the set of players
     * @return a boolean value representing the check result.
     */
    public boolean playerHasChosenPC(int playerIndex) {
        WindowFrame wf = players.get(playerIndex).getWindowFrame();
        return (wf != null);
    }

    /**
     * This is used to check if a given player, can use a Tool Card during his turn.
     *
     * @param playerIndex is the player ordinal number in the set of players
     * @return a boolean value representing the check result.
     */
    public boolean playerCanUseToolCard(int playerIndex, int idToolCard) {
        Player player = players.get(playerIndex);
        ToolCard toolCard = toolCards[idToolCard];
        return (!toolCardUsed && (player.getTokens() >= toolCard.getPrice()));
    }

    /**
     * This method is used to place a single die in a player's WindowFrame
     *
     * @param playerIndex is the player ordinal number in the set of players
     * @param die The die passed to be positioned.
     * @param row The row of the wanted positioning place.
     * @param col The column of the wanted positioning place.
     */
    public void placeWFDie(int playerIndex, Die die, int row, int col) {
        Player player = getPlayer(playerIndex);
        WindowFrame windowFrame = player.getWindowFrame();
        windowFrame.placeDie(die, row, col);
        updateTurn();
        try {
            localModels.get(playerIndex).setWindowFrame(windowFrame);
        } catch (IOException e) {
            //e.printStackTrace();
        }
        notifyObservers();
    }

    /**
     * This method is used to execute a player move.
     *
     * @param playerIndex is the player ordinal number in the set of players
     * @param pa is the Player Action instance
     */
    public void performToolCard(int playerIndex, PlayerAction pa) {
        ToolCard toolCard = getToolCard(pa.getIdToolCard());
        int tokens = toolCard.getPrice();
        players.get(playerIndex).spendTokens(tokens);
        toolCard.putTokens(tokens);
        WindowFrame windowFrame = getPlayer(playerIndex).getWindowFrame();
        toolCard.performAction(this, windowFrame, pa);
        toolCardUsed = true;
        try {
            localModels.get(playerIndex).setWindowFrame(windowFrame);
        } catch (IOException e) {
            //e.printStackTrace();
        }
        notifyObservers();
    }

    /**
     * This method is used to update the remote Local Models.
     *
     */
    private void updateLocalModel(int playerIndex) {
        LocalModelInterface localModel = localModels.get(playerIndex);
        Player player = players.get(playerIndex);
        try {
            localModel.setWindowFrame(player.getWindowFrame());
            localModel.updateTurn(round, turn, backward);
            localModel.setDraftPool(draftPool);
            localModel.setRoundTrack(roundTrack);
            localModel.setPubOCs(pubOCs);
            localModel.setToolCards(toolCards);
            localModel.setToolCardUsed(toolCardUsed);
            localModel.setTokens(player.getTokens());
            localModel.setPlayerIndex(playerIndex);
            localModel.setState(started, lobbyGathering);
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    /**
     * This method starts the match.
     */
    public void startMatch() {
        started = true;
        rollDraftPool();
        int playerIndex = 0;
        for(int i = 0; i < numPlayers; i++) {
            updateLocalModel(i);
        }
        notifyObservers();
    }

    /**
     * This method checks if the match is over.
     *
     * @return a boolean representing the check result.
     */
    public boolean isOver(){
        return over;
    }

    /**
     * This method checks if the match is started after player selected a Pattern Card.
     *
     * @return a boolean representing the check result.
     */
    public boolean isStarted(){
        return started;
    }
}
