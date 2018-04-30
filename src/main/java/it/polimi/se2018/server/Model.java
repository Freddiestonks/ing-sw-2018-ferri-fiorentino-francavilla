package it.polimi.se2018.server;

import java.util.ArrayList;

public class Model {
    //Attributes
    final private static Model instance = new Model();
    int idMatch;
    boolean started;
    Player[] players = new Player[4];
    int round;
    int turn;
    boolean backward;
    int numPlayers;
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

    public void addPlayer(Player player){
    }

    public void removePlayer(Player player){
    }

    public void updateTurn(){
    }

    public int getRound(){
    }

    public int getTurn(){
    }

    public void calculateScore(){
    }

    public void freeze(){
    }

    public void resume(){
    }

}
