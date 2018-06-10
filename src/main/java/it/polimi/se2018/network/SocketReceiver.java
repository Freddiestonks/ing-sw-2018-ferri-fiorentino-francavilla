package it.polimi.se2018.network;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;
import it.polimi.se2018.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;

public class SocketReceiver extends Thread {
    private LocalModel localModel;
    private View view;
    private PlayerAction playerAction;
    private ClientGatherer clientGatherer;
    private Socket socket;
    private Gson gson = new Gson();

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

    @Override
    public void run() {
        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message;
            //TODO: use constant values
            while(true) {
                message = is.readLine();
                if(message.startsWith("LOCALMODEL:")) {
                    if(message.substring(11).startsWith("WF:")) {
                        message = message.substring(14, message.length() - 1);
                        WindowFrame wf = gson.fromJson(message, WindowFrame.class);
                        localModel.setWindowFrame(wf);
                    }
                    else if(message.substring(11).startsWith("TURN:")) {
                        message = message.substring(16, message.length() - 1);
                        int round = gson.fromJson(message, int.class);
                        message = is.readLine();
                        message = message.substring(0, message.length() - 1);
                        int turn = gson.fromJson(message, int.class);
                        message = is.readLine();
                        message = message.substring(0, message.length() - 1);
                        boolean backward = gson.fromJson(message, boolean.class);
                        localModel.updateTurn(round, turn, backward);
                    }
                    else if(message.substring(11).startsWith("DP:")) {
                        message = message.substring(14, message.length() - 1);
                        Type type = new TypeToken<ArrayList<Die>>(){}.getType();
                        ArrayList<Die> dp = gson.fromJson(message, type);
                        localModel.setDraftPool(dp);
                    }
                    else if(message.substring(11).startsWith("RT:")) {
                        message = message.substring(14, message.length() - 1);
                        Type type = new TypeToken<ArrayList<ArrayList<Die>>>(){}.getType();
                        ArrayList<ArrayList<Die>> rt = gson.fromJson(message, type);
                        localModel.setRoundTrack(rt);
                    }
                    else if(message.substring(11).startsWith("PUBOCS:")) {
                        message = message.substring(18, message.length() - 1);
                        PubObjCard[] pubOCs = gson.fromJson(message, PubObjCard[].class);
                        localModel.setPubOCs(pubOCs);
                    }
                    else if(message.substring(11).startsWith("TC:")) {
                        message = message.substring(14, message.length() - 1);
                        ToolCard[] toolCards = gson.fromJson(message, ToolCard[].class);
                        localModel.setToolCards(toolCards);
                    }
                }
                else if(message.startsWith("VIEW:")) {

                }
                else if(message.startsWith("PLAYERACTION:")) {
                    message = message.substring(13, message.length() - 1);
                    PlayerAction pa = gson.fromJson(message, PlayerAction.class);
                    this.playerAction.setPlayerAction(pa);
                }
            }
        } catch (IOException e) {
            dismiss();
            e.printStackTrace();
        }
    }
}