package it.polimi.se2018.view;

import it.polimi.se2018.model.*;

import java.io.IOException;
import java.rmi.Remote;
import java.util.ArrayList;

public interface ViewInterface extends Remote {
    void welcomeScreen() throws IOException;
    void updateWaitingRoom(boolean starting) throws IOException;
    void checkConnection() throws IOException;
    //TODO aggiornare ogni singolo componente della view
    void updateDP(ArrayList<Die> draftPool) throws IOException;
    void updateRT(ArrayList<ArrayList<Die>> roundTrack,int round) throws IOException;
    void updateTokens(int tokens) throws IOException;
    void updateRound(int round) throws IOException;
    void updateOrder(boolean backward) throws IOException;
    void updateInfo(int tokens, int round, boolean backward) throws IOException;
    void updatePrivOCs(PrivObjCard privObjCard) throws IOException;
    void updatePubOCs(PubObjCard[] pubObjCards) throws IOException;
    void updateOpponentsWF(Player[] opponents) throws IOException;
    void updatePlayerWF(Player player) throws IOException;
    void updateMainScreen(MainScreenInfo mainScreenInfo) throws IOException;
    void endGame(Player[] leaderboard,Player player,int[] score) throws IOException;
    void updateToolCards(ToolCard[] toolCard) throws IOException;
    void selectionMaker(String[] string) throws IOException;
    void updatePlayerLobby(ArrayList<Player> players) throws IOException;
    void updatePlayerState(Player player) throws IOException;
    void patternCardGenerator(ArrayList<PatternCard> pc) throws IOException;
}
