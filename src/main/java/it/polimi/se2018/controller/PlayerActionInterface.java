package it.polimi.se2018.controller;

import java.io.IOException;
import java.rmi.Remote;

/**
 * This is the PlayerAction remote interface.
 */
public interface PlayerActionInterface extends Remote {

    void setPlayerAction(PlayerAction pa) throws IOException;

    void checkConnection() throws IOException;
}
