package it.polimi.se2018.view;

import it.polimi.se2018.model.*;

import java.io.IOException;
import java.rmi.Remote;
import java.util.ArrayList;

public interface ViewInterface extends Remote {
    void updateWaitingRoom(boolean starting) throws IOException;

    void updateMainScreen(MainScreenInfo mainScreenInfo) throws IOException;

    void endGame(ArrayList<Player> leaderBoard, Player player) throws IOException;

    void updatePlayerLobby(ArrayList<String> usernames) throws IOException;

    void patternCardChooser(ArrayList<PatternCard> pc) throws IOException;

    void enteringError(boolean lobbyGathering) throws IOException;

    void checkConnection() throws IOException;
}
