package it.polimi.se2018.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.tceffects.AbstractTCEffect;
import it.polimi.se2018.utils.JsonAdapter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This class implements the Socket communication of the LocalModel remote reference.
 */
public class SocketLocalModel implements LocalModelInterface {

    private Socket socket;
    private Gson gson;

    private static final String GAP = ":";
    public static final String LOCAL_MODEL = "LOCALMODEL";
    public static final String STATE = "STATE";
    public static final String WF = "WF";
    public static final String TURN = "TURN";
    public static final String DP = "DP";
    public static final String RT = "RT";
    public static final String PUBOCS = "PUBOCS";
    public static final String TC = "TC";
    public static final String TC_USED = "TC_USED";
    public static final String TOKENS = "TOKENS";
    public static final String PLAYER_INDEX = "PLAYERINDEX";

    public SocketLocalModel(Socket socket) {
        this.socket = socket;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Cell.class, new JsonAdapter<Cell>());
        gsonBuilder.registerTypeAdapter(PubObjCard.class, new JsonAdapter<PubObjCard>());
        gsonBuilder.registerTypeAdapter(AbstractTCEffect.class, new JsonAdapter<AbstractTCEffect>());
        this.gson = gsonBuilder.create();
    }

    public void setState(boolean started, boolean lobbyGathering) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        String json = gson.toJson(started);
        writer.write(LOCAL_MODEL + GAP + STATE + "\n");
        writer.write(json + "\n");
        json = gson.toJson(lobbyGathering);
        writer.write(json + "\n");
        writer.flush();
    }

    public void setWindowFrame(WindowFrame wf) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        String json = gson.toJson(wf);
        writer.write(LOCAL_MODEL + GAP + WF + "\n");
        writer.write(json + "\n");
        writer.flush();
    }

    public void updateTurn(int round, int turn, boolean backward) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        String json = gson.toJson(round);
        writer.write(LOCAL_MODEL + GAP + TURN + "\n");
        writer.write(json + "\n");
        json = gson.toJson(turn);
        writer.write(json + "\n");
        json = gson.toJson(backward);
        writer.write(json + "\n");
        writer.flush();
    }

    public void setDraftPool(ArrayList<Die> draftPool) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        String json = gson.toJson(draftPool);
        writer.write(LOCAL_MODEL + GAP + DP + "\n");
        writer.write(json + "\n");
        writer.flush();
    }

    public void setRoundTrack(ArrayList<ArrayList<Die>> roundTrack) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        String json = gson.toJson(roundTrack);
        writer.write(LOCAL_MODEL + GAP + RT + "\n");
        writer.write(json + "\n");
        writer.flush();
    }

    public void setPubOCs(PubObjCard[] pubOCs) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        String json = gson.toJson(pubOCs);
        writer.write(LOCAL_MODEL + GAP + PUBOCS + "\n");
        writer.write(json + "\n");
        writer.flush();
    }

    public void setToolCards(ToolCard[] toolCards) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        String json = gson.toJson(toolCards);
        writer.write(LOCAL_MODEL + GAP + TC + "\n");
        writer.write(json + "\n");
        writer.flush();
    }

    public void setToolCardUsed(boolean toolCardUsed) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        String json = gson.toJson(toolCardUsed);
        writer.write(LOCAL_MODEL + GAP + TC_USED + "\n");
        writer.write(json + "\n");
        writer.flush();
    }

    public void setTokens(int tokens) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        String json = gson.toJson(tokens);
        writer.write(LOCAL_MODEL + GAP + TOKENS + "\n");
        writer.write(json + "\n");
        writer.flush();
    }

    public void setPlayerIndex(int playerIndex) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        String json = gson.toJson(playerIndex);
        writer.write(LOCAL_MODEL + GAP + PLAYER_INDEX + "\n");
        writer.write(json + "\n");
        writer.flush();
    }

    public void checkConnection() throws IOException {
        if(socket.isClosed()) {
            throw new IOException();
        }
    }
}
