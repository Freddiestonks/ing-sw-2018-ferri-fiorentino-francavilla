package it.polimi.se2018.network;

import it.polimi.se2018.controller.PlayerActionInterface;
import it.polimi.se2018.model.LocalModelInterface;
import it.polimi.se2018.view.ViewInterface;

import java.io.IOException;
import java.rmi.Remote;

/**
 * This is the remote interface used by RMI clients in order to establish a connection wih the server.
 */
public interface ClientGathererInterface extends Remote {

    PlayerActionInterface connectRMI(LocalModelInterface localModel, ViewInterface view) throws IOException;

}
