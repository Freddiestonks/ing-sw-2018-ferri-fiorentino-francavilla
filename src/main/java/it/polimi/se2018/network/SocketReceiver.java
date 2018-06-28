package it.polimi.se2018.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.controller.PlayerActionInterface;
import it.polimi.se2018.model.*;
import it.polimi.se2018.view.MainScreenInfo;
import it.polimi.se2018.view.View;
import it.polimi.se2018.view.ViewInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;

public class SocketReceiver extends Thread {
    private LocalModelInterface localModel;
    private ViewInterface view;
    private PlayerActionInterface playerAction;
    private Socket socket;
    private Gson gson;

    private static final String GAP = ":";

    public SocketReceiver(Socket socket) {
        this.socket = socket;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Cell.class, new JsonAdapter<Cell>());
        gsonBuilder.registerTypeAdapter(PubObjCard.class, new JsonAdapter<PubObjCard>());
        gsonBuilder.registerTypeAdapter(ToolCard.class, new JsonAdapter<ToolCard>());
        this.gson = gsonBuilder.create();
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

    @Override
    public void run() {
        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message;
            int length;
            while(true) {
                message = is.readLine();
                if(message.startsWith(SocketLocalModel.LOCAL_MODEL + GAP)) {
                    length = SocketLocalModel.LOCAL_MODEL.length() + GAP.length();
                    if(message.substring(length).equals(SocketLocalModel.STATE)) {
                        message = is.readLine();
                        boolean started = gson.fromJson(message, boolean.class);
                        message = is.readLine();
                        boolean lobbyGathering = gson.fromJson(message, boolean.class);
                        localModel.setState(started, lobbyGathering);

                    }
                    else if(message.substring(length).equals(SocketLocalModel.WF)) {
                        message = is.readLine();
                        WindowFrame wf = gson.fromJson(message, WindowFrame.class);
                        localModel.setWindowFrame(wf);
                    }
                    else if(message.substring(length).equals(SocketLocalModel.TURN)) {
                        message = is.readLine();
                        int round = gson.fromJson(message, int.class);
                        message = is.readLine();
                        int turn = gson.fromJson(message, int.class);
                        message = is.readLine();
                        boolean backward = gson.fromJson(message, boolean.class);
                        localModel.updateTurn(round, turn, backward);
                    }
                    else if(message.substring(length).equals(SocketLocalModel.DP)) {
                        message = is.readLine();
                        Type type = new TypeToken<ArrayList<Die>>(){}.getType();
                        ArrayList<Die> dp = gson.fromJson(message, type);
                        localModel.setDraftPool(dp);
                    }
                    else if(message.substring(length).equals(SocketLocalModel.RT)) {
                        message = is.readLine();
                        Type type = new TypeToken<ArrayList<ArrayList<Die>>>(){}.getType();
                        ArrayList<ArrayList<Die>> rt = gson.fromJson(message, type);
                        localModel.setRoundTrack(rt);
                    }
                    else if(message.substring(length).equals(SocketLocalModel.PUBOCS)) {
                        message = is.readLine();
                        PubObjCard[] pubOCs = gson.fromJson(message, PubObjCard[].class);
                        localModel.setPubOCs(pubOCs);
                    }
                    else if(message.substring(length).equals(SocketLocalModel.TC)) {
                        message = is.readLine();
                        ToolCard[] toolCards = gson.fromJson(message, ToolCard[].class);
                        localModel.setToolCards(toolCards);
                    }
                    else if(message.substring(length).equals(SocketLocalModel.TC_USED)) {
                        message = is.readLine();
                        boolean toolCardUsed = gson.fromJson(message, boolean.class);
                        localModel.setToolCardUsed(toolCardUsed);
                    }
                    else if(message.substring(length).equals(SocketLocalModel.PLAYER_INDEX)) {
                        message = is.readLine();
                        int playerIndex = gson.fromJson(message, int.class);
                        localModel.setPlayerIndex(playerIndex);
                    }
                }
                else if(message.startsWith(SocketView.VIEW + GAP)) {
                    length = SocketView.VIEW.length() + GAP.length();
                    if(message.substring(length).equals(SocketView.WR)) {
                        message = is.readLine();
                        boolean starting = gson.fromJson(message, boolean.class);
                        view.updateWaitingRoom(starting);
                    }
                    else if(message.substring(length).equals(SocketView.MAIN_SCREEN)) {
                        message = is.readLine();
                        MainScreenInfo msi = gson.fromJson(message, MainScreenInfo.class);
                        view.updateMainScreen(msi);
                    }
                    else if(message.substring(length).equals(SocketView.END_GAME)) {
                        message = is.readLine();
                        Player[] leaderboard = gson.fromJson(message, Player[].class);
                        message = is.readLine();
                        Player player = gson.fromJson(message, Player.class);
                        message = is.readLine();
                        int[] score = gson.fromJson(message, int[].class);
                        view.endGame(leaderboard, player, score);
                    }
                    else if(message.substring(length).equals(SocketView.LOBBY)) {
                        message = is.readLine();
                        Type type = new TypeToken<ArrayList<String>>(){}.getType();
                        ArrayList<String> usernames = gson.fromJson(message, type);
                        view.updatePlayerLobby(usernames);
                    }
                    else if(message.substring(length).equals(SocketView.PC)) {
                        message = is.readLine();
                        Type type = new TypeToken<ArrayList<PatternCard>>(){}.getType();
                        System.out.println(message);
                        ArrayList<PatternCard> patternCards = gson.fromJson(message, type);
                        view.patternCardGenerator(patternCards);
                    }
                }
                else if(message.equals(SocketPlayerAction.PLAYER_ACTION)) {
                    message = is.readLine();
                    PlayerAction pa = gson.fromJson(message, PlayerAction.class);
                    playerAction.setPlayerAction(pa);
                }
            }
        } catch (IOException | JsonSyntaxException e) {
            dismiss();
            e.printStackTrace();
        }
    }
}