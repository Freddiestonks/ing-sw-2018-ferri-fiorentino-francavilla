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
    @Override
    public void welcomeScreen(){}
    @Override
    public void updateDP(ArrayList<Die> draftPool) {
    }
    @Override
    public void updateRT(ArrayList<ArrayList<Die>> roundTrack, int round) {
    }

    @Override
    public void updateTokens(int tokens) {

    }

    @Override
    public void updateRound(int round) {

    }

    @Override
    public void updateOrder(boolean backward) {

    }

    @Override
    public void updateInfo(int tokens, int round, boolean backward) {

    }

    @Override
    public void updatePrivOCs(PrivObjCard privObjCard) {

    }

    @Override
    public void updatePubOCs(PubObjCard[] pubObjCards) {

    }

    @Override
    public void updateOpponentsWF(Player[] opponents) {

    }

    @Override
    public void updatePlayerWF(Player player) {

    }

    @Override
    public void updateMainScreen(MainScreenInfo mainScreenInfo) {

    }
    @Override
    public void updateConnectionRequest(boolean success) {
    }
    @Override
    public void endGame(Player[] leaderboard,Player player,int score[]){}
    @Override
    public void updateToolCards(ArrayList<ToolCard> toolCard){}
    @Override
    public void invalidMoveError(){}

    @Override
    public void selectionMaker(String[] string) {

    }
    @Override
    public String input(){
        return null;
    }

}
