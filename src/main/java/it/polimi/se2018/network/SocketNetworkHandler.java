package it.polimi.se2018.network;

import it.polimi.se2018.controller.PlayerActionInterface;
import it.polimi.se2018.model.LocalModel;
import it.polimi.se2018.view.View;

import java.io.IOException;
import java.net.Socket;

public class SocketNetworkHandler extends NetworkHandler {

    private final int PORT = 1111;
    private SocketReceiver socketReceiver = null;

    public SocketNetworkHandler(String host) {
        super(host);
    }

    public PlayerActionInterface connect(LocalModel localModel, View view) throws IOException {
        if(socketReceiver != null) {
            socketReceiver.dismiss();
        }
        Socket socket = new Socket(host, PORT);
        socketReceiver = new SocketReceiver(socket);
        socketReceiver.setLocalModel(localModel);
        socketReceiver.setView(view);
        socketReceiver.start();
        return new SocketPlayerAction(socket);
    }

}
