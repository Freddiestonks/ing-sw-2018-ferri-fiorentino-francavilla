package it.polimi.se2018.network;

import it.polimi.se2018.controller.PlayerActionInterface;
import it.polimi.se2018.model.LocalModel;
import it.polimi.se2018.view.View;

import java.io.IOException;

public abstract class NetworkHandler {

    protected String host;

    public NetworkHandler(String host) {
        this.host = host;
    }

    public abstract PlayerActionInterface connect(LocalModel localModel, View view) throws IOException;
}
