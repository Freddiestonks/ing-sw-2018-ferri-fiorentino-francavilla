package it.polimi.se2018.view;

import it.polimi.se2018.model.*;
import it.polimi.se2018.utils.Observable;

import java.util.ArrayList;

public abstract class View extends Observable implements ViewInterface {

    protected String userInput;

    public View(){}

    public String getUserInput() {
        return this.userInput;
    }

    public void checkConnection() {}

    public abstract void updateWaitingRoom(boolean starting);

    protected abstract void updateRT(ArrayList<ArrayList<Die>> roundTrack);

    protected abstract void updateDP(ArrayList<Die> draftPool);

    protected abstract void updateTokens(int tokens);

    protected abstract void updateRound(int round);

    protected abstract void updateOrder(boolean backward);

    protected abstract void updateInfo(int tokens, int round, boolean backward, String turnPlayer);

    protected abstract void updatePrivOCs(PrivObjCard privObjCard);

    public abstract void showPubOCs(PubObjCard pubObjCards[]);

    protected abstract void updateOpponentsWF(ArrayList<Player> opponents);

    protected abstract void updatePlayerWF(Player player);

    public abstract void showMainScreen();

    public abstract void updateMainScreen(MainScreenInfo mainScreenInfo);

    public abstract void endGame(ArrayList<Player> leaderBoard, Player player);

    public abstract void showToolCards(ToolCard[] toolCards);

    public abstract void connectionError();

    public abstract void invalidMoveError();

    public abstract void help();

    public abstract void updatePlayerLobby(ArrayList<String> usernames);

    public abstract void patternCardChooser(ArrayList<PatternCard> pc);

    public abstract void enteringError(boolean lobbyGathering);

    public abstract void startView();
}
