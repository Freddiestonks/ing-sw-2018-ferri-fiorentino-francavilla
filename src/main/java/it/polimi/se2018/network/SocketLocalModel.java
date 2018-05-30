package it.polimi.se2018.network;

import it.polimi.se2018.model.LocalModelInterface;

import java.io.IOException;
import java.net.Socket;

public class SocketLocalModel implements LocalModelInterface {
    private Socket socket;

    public SocketLocalModel(Socket socket) {
        this.socket = socket;
    }

    public void checkConnection() throws IOException {
        if(socket.isClosed()) {
            throw new IOException();
        }
    }
}
