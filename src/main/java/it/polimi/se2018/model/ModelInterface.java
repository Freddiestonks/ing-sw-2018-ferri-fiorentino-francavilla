package it.polimi.se2018.model;

import java.util.ArrayList;

public interface ModelInterface {

    boolean isStarted();

    //boolean isLobbyGathering();

    WindowFrame getWindowFrame(int playerIndex);

    boolean playerHasChosenPC(int playerIndex);

    int getRound();

    int getTurn();

    boolean isBackward();

    ArrayList<Die> getDraftPool();

    Die getDraftPoolDie(int pos);

    int getDraftPoolSize();

    //ArrayList<ArrayList<Die>> getRoundTrack();

    Die getRoundTrackDie(int round, int i);

    int getRoundTrackSize(int round);

    ToolCard getToolCard(int i);

    boolean isToolCardUsed();
}
