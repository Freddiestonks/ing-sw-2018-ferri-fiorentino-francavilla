package it.polimi.se2018.network;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.LocalModelInterface;
import it.polimi.se2018.view.ViewInterface;

public class ClientInfo {
    /**
     * This class collect all the client information in order to establish a connection
     */

    // remote reference used by the server to communicate with the client
    private LocalModelInterface localModel;
    private ViewInterface view;
    // the PlayerAction created by the server that receive all the player requests
    private PlayerAction playerAction;

    public ClientInfo(LocalModelInterface localModel, ViewInterface view, PlayerAction pa) {
        this.localModel = localModel;
        this.view = view;
        this.playerAction = pa;
    }

    public LocalModelInterface getLocalModel() {
        return localModel;
    }

    public ViewInterface getView() {
        return view;
    }

    public PlayerAction getPlayerAction() {
        return playerAction;
    }
}
