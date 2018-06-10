package it.polimi.se2018.network;

import com.google.gson.Gson;
import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.controller.PlayerActionInterface;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketPlayerAction implements PlayerActionInterface {
    private Socket socket;
    private Gson gson = new Gson();

    public SocketPlayerAction(Socket socket) {
        this.socket = socket;
    }

    public void setPlayerAction(PlayerAction pa) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        String json = gson.toJson(pa);
        writer.write("PLAYERACTION:" + json + "\n");
        writer.flush();
    }

    public void checkConnection() throws IOException {
        if(socket.isClosed()) {
            throw new IOException();
        }
    }
}
