package it.polimi.se2018.server.model;

import java.util.ArrayList;

public class Model{
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
    private ArrayList<Die> draftPool = new ArrayList<Die>();
    private ArrayList<Die>[] roundTrack = new ArrayList<Die>()[10];
    private PubObjCard[] pubOCs = new PubObjCard[10];
    private PrivObjCard[] privOCs = new PrivObjCard[5];
    private ToolCard[] toolCards = new ToolCard[12];
    private PatternCard[] patCards = new PatternCard[12];

    //Methods
    private Model() {
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
        // method setConnection used to update the player's status (online/offline)
        player.setConnection(false);
    }

    public void reinsertPlayer(){

    }

    public void updateTurn(){
        if(round == 10 && backward && turn == 1) {
            // TODO: exception
        }
        if(!backward && (turn == numPlayers)){
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

    public void calculateScore(){
    }

    public void freeze(){
    }

    public void resume(){
    }

}