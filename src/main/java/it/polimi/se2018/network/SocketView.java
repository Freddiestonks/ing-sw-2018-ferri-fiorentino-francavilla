package it.polimi.se2018.network;

import it.polimi.se2018.model.*;
import it.polimi.se2018.view.MainScreenInfo;
import it.polimi.se2018.view.ViewInterface;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class SocketView implements ViewInterface {
    private Socket socket;

    public SocketView(Socket socket) {
        this.socket = socket;
    }

    public void checkConnection() throws IOException {
        if(socket.isClosed()) {
            throw new IOException();
        }
    }
    @Override
    public void updateWaitingRoom(boolean starting){}

    public void welcomeScreen(){}

    public void updateDP(ArrayList<Die> draftPool) {
    }

    public void updateRT(ArrayList<ArrayList<Die>> roundTrack, int round) {
    }

    public void updateTokens(int tokens) {

    }

    public void updateRound(int round) {

    }

    public void updateOrder(boolean backward) {

    }

    public void updateInfo(int tokens, int round, boolean backward) {

    }

    public void updatePrivOCs(PrivObjCard privObjCard) {

    }

    public void updatePubOCs(PubObjCard[] pubObjCards) {

    }

    public void updateOpponentsWF(Player[] opponents) {

    }

    public void updatePlayerWF(Player player) {

    }

    @Override
    public void updateMainScreen(MainScreenInfo mainScreenInfo) {

    }
    //public void updateConnectionRequest(boolean success) {}
    @Override
    public void endGame(Player[] leaderboard,Player player,int[] score){}

    public void updateToolCards(ToolCard[] toolCard) {

    }

    public void invalidMoveError(){}

    public void selectionMaker(String[] string) {

    }

    public void help(){}

    @Override
    public void updatePlayerLobby(ArrayList<Player> players) {

    }

    public void updatePlayerState(Player player) {

    }

    @Override
    public void patternCardGenerator(ArrayList<PatternCard> pc) {

    }

}
