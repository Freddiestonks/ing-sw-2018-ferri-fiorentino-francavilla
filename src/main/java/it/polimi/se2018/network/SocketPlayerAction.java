package it.polimi.se2018.network;

import it.polimi.se2018.controller.PlayerActionInterface;

import java.net.Socket;

public class SocketPlayerAction implements PlayerActionInterface {
    private Socket socket;

    public SocketPlayerAction(Socket socket) {
        this.socket = socket;
    }
}
