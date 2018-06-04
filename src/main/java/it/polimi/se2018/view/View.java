package it.polimi.se2018.view;

import it.polimi.se2018.model.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public abstract class View implements ViewInterface{

    public View(){}


    @Override
    public void updateRT() {

    }

    @Override
    public void checkConnection() throws IOException {
    }
    @Override
    public void updateDP(ArrayList<Die> draftPool) {
    }
    @Override
    public void updateTokens(int tokens){
    }
    @Override
    public void updateRound(int round){
    }
    @Override
    public void updateOrder(boolean backward){}
    @Override
    public void updateInfos(int tokens,int round,boolean backward){}
    @Override
    public void updatePrivOCs(PrivObjCard privObjCard){}
    @Override
    public void updatePubOCs(PubObjCard pubObjCards[]){}
    @Override
    public void updateOpponentsWF(Player[] opponents){}
    @Override
    public void updatePlayerWF(Player player){}
    @Override
    public void updateMainScreen(Player player,Player[] opponents,int round,boolean backward){}
    private void layoutFormatter(String[] string,int numCards) {}
    private void windowFrameGenerator(WindowFrame[] wf){}
}
