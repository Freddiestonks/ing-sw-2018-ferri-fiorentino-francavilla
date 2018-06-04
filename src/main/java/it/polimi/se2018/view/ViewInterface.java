package it.polimi.se2018.view;

import it.polimi.se2018.model.*;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ViewInterface extends Remote {
    void checkConnection() throws IOException,RemoteException;
    //TODO aggiornare ogni singolo componente della view
    void updateDP(ArrayList<Die> draftPool);
    void updateRT();
    void updateTokens(int tokens);
    public void updateRound(int round);
    public void updateOrder(boolean backward);
    public void updateInfos(int tokens,int round,boolean backward);
    public void updatePrivOCs(PrivObjCard privObjCard);
    public void updatePubOCs(PubObjCard pubObjCards[]);
    public void updateOpponentsWF(Player[] opponents);
    public void updatePlayerWF(Player player);
    public void updateMainScreen(Player player,Player[] opponents,int round,boolean backward);

}
