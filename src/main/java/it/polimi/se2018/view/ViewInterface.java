package it.polimi.se2018.view;

import it.polimi.se2018.model.*;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ViewInterface extends Remote {
    void welcomeScreen();
    void updateWaitingRoom(boolean starting);
    void checkConnection() throws IOException,RemoteException;
    //TODO aggiornare ogni singolo componente della view
    void updateDP(ArrayList<Die> draftPool);
    void updateRT(ArrayList<ArrayList<Die>> roundTrack,int round);
    void updateTokens(int tokens);
    void updateRound(int round);
    void updateOrder(boolean backward);
    void updateInfo(int tokens, int round, boolean backward);
    void updatePrivOCs(PrivObjCard privObjCard);
    void updatePubOCs(PubObjCard[] pubObjCards);
    void updateOpponentsWF(Player[] opponents);
    void updatePlayerWF(Player player);
    void updateMainScreen(MainScreenInfo mainScreenInfo);
    void updateConnectionRequest(boolean success);
    void endGame(Player[] leaderboard,Player player,int[] score);
    void updateToolCards(ToolCard[] toolCard);
    void invalidMoveError();
    void selectionMaker(String[] string);
    void help();
    String input();



}
