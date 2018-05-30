package it.polimi.se2018.model;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LocalModelInterface extends Remote {
    void checkConnection() throws IOException, RemoteException;
}
