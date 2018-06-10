package it.polimi.se2018.network;

import com.google.gson.Gson;
import it.polimi.se2018.model.*;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class SocketLocalModel implements LocalModelInterface {
    private Socket socket;
    private Gson gson = new Gson();

    public SocketLocalModel(Socket socket) {
        this.socket = socket;
    }


    public void setWindowFrame(WindowFrame wf) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        String json = gson.toJson(wf);
        writer.write("LOCALMODEL:WF:" + json + "\n");
        writer.flush();
    }

    public void updateTurn(int round, int turn, boolean backward) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        String json = gson.toJson(round);
        writer.write("LOCALMODEL:ROUND:" + json + "\n");
        json = gson.toJson(turn);
        writer.write(json + "\n");
        json = gson.toJson(backward);
        writer.write(json + "\n");
        writer.flush();
    }

    public void setDraftPool(ArrayList<Die> draftPool) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        String json = gson.toJson(draftPool);
        writer.write("LOCALMODEL:DP:" + json + "\n");
        writer.flush();
    }

    public void setRoundTrack(ArrayList<ArrayList<Die>> roundTrack) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        String json = gson.toJson(roundTrack);
        writer.write("LOCALMODEL:RT:" + json + "\n");
        writer.flush();
    }

    public void setPubOCs(PubObjCard[] pubOCs) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        String json = gson.toJson(pubOCs);
        writer.write("LOCALMODEL:PUBOCS:" + json + "\n");
        writer.flush();
    }

    public void setToolCards(ToolCard[] toolCards) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        String json = gson.toJson(toolCards);
        writer.write("LOCALMODEL:TC:" + json + "\n");
        writer.flush();
    }

    public void checkConnection() throws IOException {
        if(socket.isClosed()) {
            throw new IOException();
        }
    }
}
