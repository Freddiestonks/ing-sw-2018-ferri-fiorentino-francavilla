package it.polimi.se2018.network;

import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PrivObjCard;
import it.polimi.se2018.model.PubObjCard;
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
    public void updateDP(ArrayList<Die> draftPool) {

    }

    @Override
    public void updateRT() {

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
    public void updateInfos(int tokens, int round, boolean backward) {

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
    public void updateMainScreen(Player player, Player[] opponents, int round, boolean backward) {

    }
}
