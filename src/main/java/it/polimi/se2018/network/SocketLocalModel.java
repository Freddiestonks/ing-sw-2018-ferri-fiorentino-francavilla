package it.polimi.se2018.network;

import it.polimi.se2018.model.LocalModelInterface;

import java.net.Socket;

public class SocketLocalModel implements LocalModelInterface {
    private Socket socket;

    public SocketLocalModel(Socket socket) {
        this.socket = socket;
    }
}
