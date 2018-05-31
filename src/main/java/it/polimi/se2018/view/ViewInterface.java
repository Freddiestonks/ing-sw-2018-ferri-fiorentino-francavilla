package it.polimi.se2018.view;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ViewInterface extends Remote {
    void checkConnection() throws IOException,RemoteException;
    //Aggiorna la Window Frame
    void updatePlayerFrames();
    //TODO aggiornare ogni singolo componente della view
    //DRaftpool
    void updateDF();
    void updateRT();
    void updateRoundIndicatror();


}
