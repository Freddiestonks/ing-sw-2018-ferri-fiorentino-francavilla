package it.polimi.se2018.view;

import it.polimi.se2018.model.*;
import it.polimi.se2018.utils.Observable;

import java.util.ArrayList;

public abstract class View extends Observable implements ViewInterface {

    protected String userInput;

    public View(){}

    public String getUserInput() {
        return this.userInput;
    }

    @Override
    public void checkConnection() {}

    @Override
    public abstract void updateWaitingRoom(boolean starting);

    public abstract void welcomeScreen();

    public abstract void updateRT(ArrayList<ArrayList<Die>> roundTrack,int round);

    public abstract void updateDP(ArrayList<Die> draftPool);

    public abstract void updateTokens(int tokens);

    public abstract void updateRound(int round);

    public abstract void updateOrder(boolean backward);

    public abstract void updateInfo(int tokens, int round, boolean backward);

    public abstract void updatePrivOCs(PrivObjCard privObjCard);

    public abstract void updatePubOCs(PubObjCard pubObjCards[]);

    public abstract void updateOpponentsWF(Player[] opponents);

    public abstract void updatePlayerWF(Player player);
    @Override
    public abstract void updateMainScreen(MainScreenInfo mainScreenInfo);

    public abstract void updateConnectionRequest(boolean success);
    @Override
    public abstract void endGame(Player[] leaderboard,Player player,int[] score);

    public abstract void updateToolCards(ToolCard[] toolCards);

    public abstract void invalidMoveError();

    public abstract void help();

    public abstract void selectionMaker(String[] string);
    @Override
    public abstract void updatePlayerLobby(ArrayList<Player> players);

    public abstract void updatePlayerState(Player player);
    @Override
    public abstract void patternCardGenerator(ArrayList<PatternCard> pc);

    public abstract void startView();
}
