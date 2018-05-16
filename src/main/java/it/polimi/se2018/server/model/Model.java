package it.polimi.se2018.server.model;

import it.polimi.se2018.server.utils.Observable;

import java.util.ArrayList;

public class Model extends Observable {
    //Attributes
    final private static Model instance = new Model();
    private int idMatch;
    private boolean started;
    private Player[] players = new Player[4];
    private int round = 1;
    private int turn = 1;
    private boolean backward = false;
    private int numPlayers = 0;
    private DiceBag diceBag;
    private ArrayList<Die> draftPool = new ArrayList<>();
    private ArrayList<ArrayList<Die>> roundTrack = new ArrayList<>();
    private PubObjCard[] pubOCs = new PubObjCard[10];
    private PrivObjCard[] privOCs = new PrivObjCard[5];
    private ToolCard[] toolCards = new ToolCard[12];
    private PatternCard[] patCards = new PatternCard[12];
    private Player[] leaderboard = new  Player[4];

    //Methods
    private Model() {
        for(int i = 0; i < 10; i++) {
            roundTrack.add(new ArrayList<>());
        }
    }

    public static Model instance(){
        return instance;
    }

    public void addPlayer(Player player) throws MaxNumPlayersException{
        if(numPlayers >= 4) {
            throw new MaxNumPlayersException();
        }
        players[numPlayers++] = player;
    }

    public void removePlayer(Player player){
        // method setConnection used to update the player's status (offline)
        player.setConnection(false);
    }

    public void reinsertPlayer(Player player){
        // method setConnection used to update the player's status (online)
        player.setConnection(true);
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
        else if(!backward) {
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

    public void calculateScore(){
    }

    public void freeze(){
    }

    public void resume(){
    }

}
