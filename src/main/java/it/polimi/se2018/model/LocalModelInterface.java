package it.polimi.se2018.model;

import java.io.IOException;
import java.rmi.Remote;
import java.util.ArrayList;

public interface LocalModelInterface extends Remote {
    void setWindowFrame(WindowFrame wf) throws IOException;

    void updateTurn(int round, int turn, boolean backward) throws IOException;

    void setDraftPool(ArrayList<Die> draftPool) throws IOException;

    void setRoundTrack(ArrayList<ArrayList<Die>> roundTrack) throws IOException;

    void setPubOCs(PubObjCard[] pubOCs) throws IOException;

    void setToolCards(ToolCard[] toolCards) throws IOException;

    void checkConnection() throws IOException;
}