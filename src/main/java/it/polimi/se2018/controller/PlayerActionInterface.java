package it.polimi.se2018.controller;

import java.rmi.Remote;

public interface PlayerActionInterface extends Remote {
    void checkConnection();
}
