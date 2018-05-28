package it.polimi.se2018.network;

import it.polimi.se2018.view.ViewInterface;

import java.net.Socket;

public class SocketView implements ViewInterface {
    private Socket socket;

    public SocketView(Socket socket) {
        this.socket = socket;
    }
}
