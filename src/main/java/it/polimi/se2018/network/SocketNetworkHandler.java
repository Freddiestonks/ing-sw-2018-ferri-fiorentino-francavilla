package it.polimi.se2018.network;

import it.polimi.se2018.controller.PlayerActionInterface;
import it.polimi.se2018.model.LocalModel;
import it.polimi.se2018.view.View;

import java.io.IOException;
import java.net.Socket;

public class SocketNetworkHandler extends NetworkHandler {

    private final int PORT = 1111;
    private SocketReceiver socketReceiver;

    public SocketNetworkHandler(String host) {
        super(host);
    }

    public PlayerActionInterface connect(LocalModel localModel, View view) {
        Socket socket = null;
        try {
            socket = new Socket(host, PORT);
        } catch (IOException e) {
           //TODO REMOVE THIS
           e.printStackTrace();
        }
        socketReceiver = new SocketReceiver(socket);
        socketReceiver.setLocalModel(localModel);
        socketReceiver.setView(view);
        socketReceiver.start();
        return new SocketPlayerAction(socket);
    }

}
