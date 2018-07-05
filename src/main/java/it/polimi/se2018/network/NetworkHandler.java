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

    /**
     * This method connect a client to the selected host.
     * @param localModel reference in order to receive data from the server
     * @param view reference in order to receive data from the server
     * @return the PlayerAction remote reference in order to send request to the server
     * @throws IOException
     */
    public abstract PlayerActionInterface connect(LocalModel localModel, View view) throws IOException;
}
