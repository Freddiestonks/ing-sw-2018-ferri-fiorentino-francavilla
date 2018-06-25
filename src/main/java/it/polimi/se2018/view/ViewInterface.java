package it.polimi.se2018.view;

import it.polimi.se2018.model.*;

import java.io.IOException;
import java.rmi.Remote;
import java.util.ArrayList;

public interface ViewInterface extends Remote {
    void updateWaitingRoom(boolean starting) throws IOException;
    void checkConnection() throws IOException;
    //TODO aggiornare ogni singolo componente della view
    void updateMainScreen(MainScreenInfo mainScreenInfo) throws IOException;
    void endGame(Player[] leaderboard, Player player, int[] score) throws IOException;
    void updatePlayerLobby(ArrayList<Player> players) throws IOException;
    void patternCardGenerator(ArrayList<PatternCard> pc) throws IOException;
}
