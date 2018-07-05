package it.polimi.se2018.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.tceffects.AbstractTCEffect;
import it.polimi.se2018.utils.JsonAdapter;
import it.polimi.se2018.view.MainScreenInfo;
import it.polimi.se2018.view.ViewInterface;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This class implements the Socket communication of the View remote reference.
 */
public class SocketView implements ViewInterface {

    private Socket socket;
    private Gson gson;

    private static final String GAP = ":";
    public static final String VIEW = "VIEW";
    public static final String WR = "WR";
    public static final String MAIN_SCREEN = "MAINSCREEN";
    public static final String END_GAME = "ENDGAME";
    public static final String LOBBY = "LOBBY";
    public static final String PC = "PC";
    public static final String ENTER_ERROR = "ENTERERROR";

    public SocketView(Socket socket) {
        this.socket = socket;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Cell.class, new JsonAdapter<Cell>());
        gsonBuilder.registerTypeAdapter(PubObjCard.class, new JsonAdapter<PubObjCard>());
        gsonBuilder.registerTypeAdapter(AbstractTCEffect.class, new JsonAdapter<AbstractTCEffect>());
        this.gson = gsonBuilder.create();
    }

    public void updateWaitingRoom(boolean starting) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        String json = gson.toJson(starting);
        writer.write(VIEW + GAP + WR + "\n");
        writer.write(json + "\n");
        writer.flush();
    }

    public void updateMainScreen(MainScreenInfo mainScreenInfo) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        String json = gson.toJson(mainScreenInfo);
        writer.write(VIEW + GAP + MAIN_SCREEN + "\n");
        writer.write(json + "\n");
        writer.flush();
    }

    public void endGame(ArrayList<Player> leaderBoard, Player player) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        writer.write(VIEW + GAP + END_GAME + "\n");
        String json = gson.toJson(leaderBoard);
        writer.write(json + "\n");
        json = gson.toJson(player);
        writer.write(json + "\n");
        writer.flush();
    }

    public void updatePlayerLobby(ArrayList<String> usernames) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        String json = gson.toJson(usernames);
        writer.write(VIEW + GAP + LOBBY + "\n");
        writer.write(json + "\n");
        writer.flush();
    }

    public void patternCardChooser(ArrayList<PatternCard> pc) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        String json = gson.toJson(pc);
        writer.write(VIEW + GAP + PC + "\n");
        writer.write(json + "\n");
        writer.flush();
    }

    public void enteringError(boolean lobbyGathering) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        String json = gson.toJson(lobbyGathering);
        writer.write(VIEW + GAP + ENTER_ERROR + "\n");
        writer.write(json + "\n");
        writer.flush();
    }

    public void checkConnection() throws IOException {
        if(socket.isClosed()) {
            throw new IOException();
        }
    }

}
