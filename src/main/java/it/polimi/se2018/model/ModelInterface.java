package it.polimi.se2018.model;

/**
 * This interface is used by both client and server in order to validate the player actions using respectively
 * with Model and LocalModel.
 */
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
