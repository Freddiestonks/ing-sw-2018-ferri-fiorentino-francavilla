package it.polimi.se2018.model;

public interface ModelInterface {

    boolean isStarted();

    boolean isLobbyGathering();

    WindowFrame getWindowFrame(int playerIndex);

    boolean playerHasChosenPC(int playerIndex);

    boolean playerCanUseToolCard(int playerIndex, int idToolCard);

    int getRound();

    int getTurn();

    boolean isBackward();

    Die getDraftPoolDie(int pos);

    int getDraftPoolSize();

    Die getRoundTrackDie(int round, int i);

    int getRoundTrackSize(int round);

    ToolCard getToolCard(int i);
}
