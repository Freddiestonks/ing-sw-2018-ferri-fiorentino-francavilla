package it.polimi.se2018.controller;

import java.rmi.Remote;

public interface PlayerActionInterface extends Remote {
    void update();

    void setUsernameReq(String usernameReq);

    void setQuitReq(boolean quitReq);

    void addPosDPDie(int pos);

    void addPosRTDie(int round, int die);

    void addPlaceDPDie(int row, int col);

    void addPlaceWFDie(int row, int col);

    void addPlaceNewWFDie(int row, int col);

    void addNewDieValue(int value);

    void setIdToolCard(int idToolCard);

    void checkConnection();

    void setConnection(String type);
}
