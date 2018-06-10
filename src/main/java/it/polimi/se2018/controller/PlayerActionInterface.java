package it.polimi.se2018.controller;

import java.io.IOException;
import java.rmi.Remote;

public interface PlayerActionInterface extends Remote {

    void setPlayerAction(PlayerAction pa) throws IOException;

    void checkConnection() throws IOException;
}
