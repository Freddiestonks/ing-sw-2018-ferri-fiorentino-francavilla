package it.polimi.se2018.network;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.LocalModel;
import it.polimi.se2018.view.View;

import java.io.IOException;
import java.net.Socket;

public class SocketReceiver extends Thread {
    private LocalModel localModel;
    private View view;
    private PlayerAction playerAction;
    private ClientGatherer clientGatherer;
    private Socket socket;

    public SocketReceiver(Socket socket) {
        this.socket = socket;
    }

    public void setLocalModel(LocalModel localModel) {
        this.localModel = localModel;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setPlayerAction(PlayerAction playerAction) {
        this.playerAction = playerAction;
    }

    public void dismiss() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        interrupt();
    }
}
