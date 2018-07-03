package it.polimi.se2018.network;

import it.polimi.se2018.controller.PlayerActionInterface;
import it.polimi.se2018.model.LocalModel;
import it.polimi.se2018.view.View;

import java.io.IOException;
import java.net.Socket;

public class SocketNetworkHandler extends NetworkHandler {

    private SocketReceiver socketReceiver = null;

    public SocketNetworkHandler(String host) {
        super(host);
    }

    public PlayerActionInterface connect(LocalModel localModel, View view) throws IOException {
        int port = ClientGatherer.SOCKET_PORT;
        if(socketReceiver != null) {
            socketReceiver.dismiss();
        }
        Socket socket = new Socket(host, port);
        socketReceiver = new SocketReceiver(socket);
        socketReceiver.setLocalModel(localModel);
        socketReceiver.setView(view);
        socketReceiver.start();
        return new SocketPlayerAction(socket);
    }

}
