package it.polimi.se2018.network;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.LocalModelInterface;
import it.polimi.se2018.view.ViewInterface;

public class ClientInfo {
    private LocalModelInterface localModel;
    private ViewInterface view;
    private PlayerAction playerAction;

    public ClientInfo(LocalModelInterface localModel, ViewInterface view, PlayerAction pa) {
        this.localModel = localModel;
        this.view = view;
        this.playerAction = pa;
    }
}
