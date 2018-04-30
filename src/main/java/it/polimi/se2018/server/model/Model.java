package it.polimi.se2018.server.model;

import java.util.ArrayList;

public class Model{
    //Attributes
    final private static Model instance = new Model();
    int idMatch;
    boolean started;
    Player[] players = new Player[4];
    int round = 1;
    int turn = 1;
    boolean backward = false;
    int numPlayers = 0;
    Dicebag diceBag;
    ArrayList<Die> draftPool = new ArrayList<Die>();
    ArrayList<Die>[] roundTrack = new ArrayList<Die>()[10];
    PubObjCard[] pubOCs = new PubObjCard[10];
    PrivObjCard[] privOCs = new PrivObjCard[5];
    ToolCard[] toolCards = new ToolCard[12];
    PatternCard[] patCards = new PatternCard[12];

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
