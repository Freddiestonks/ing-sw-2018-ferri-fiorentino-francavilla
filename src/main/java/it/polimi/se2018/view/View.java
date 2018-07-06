package it.polimi.se2018.view;

import it.polimi.se2018.model.*;
import it.polimi.se2018.utils.Observable;

import java.util.ArrayList;

public abstract class View extends Observable implements ViewInterface {

    protected String userInput;

    public View() {}

    /**
     * This method is used to get the string that contains all the information necessary to parse and perform a player action.
     *
     * @return the string representing the user input.
     */
    public String getUserInput() {
        return this.userInput;
    }

    /**
     * This method tests the network connection with a client by a remote reference.
     */
    public void checkConnection() {}

    /**
     * This method updates the RoundTrack.
     * @param roundTrack the set of dice representing the RoundTrack.
     */
    protected abstract void updateRT(ArrayList<ArrayList<Die>> roundTrack);

    /**
     * This method updates the DraftPool.
     * @param draftPool the set of dice representing the DraftPool.
     */
    protected abstract void updateDP(ArrayList<Die> draftPool);

    /**
     * This method updates the number of tokens.
     * @param tokens the number of tokens.
     */
    protected abstract void updateTokens(int tokens);

    /**
     * This method updates the Round number.
     * @param round the Round number.
     */
    protected abstract void updateRound(int round);

    /**
     * This method updates the Round number.
     * @param backward if true means that the match is in the backward order of player turns.
     */
    protected abstract void updateOrder(boolean backward);

    /**
     * This method updates the player information.
     *
     * @param tokens the number of tokens a player owns.
     * @param round the current Round.
     * @param backward true if the match is in the backward order of players.
     * @param turnPlayer the username of player that have a to perform a move.
     */
    protected abstract void updateInfo(int tokens, int round, boolean backward, String turnPlayer);

    /**
     * This method updates the Private Objective Card.
     *
     * @param privObjCard the Private Objective Card to update.
     */
    protected abstract void updatePrivOCs(PrivObjCard privObjCard);

    /**
     * This method shows the Public Objective Card.
     *
     * @param pubObjCards the list of Public Objective Cards.
     */
    public abstract void showPubOCs(PubObjCard[] pubObjCards);

    /**
     * This method shows the opponent players WindowFrames.
     *
     * @param opponents the list of opponent players.
     */
    protected abstract void updateOpponentsWF(ArrayList<Player> opponents);

    /**
     * This method shows the player WindowFrame.
     *
     * @param player whose WindowFrame has to be shown.
     */
    protected abstract void updatePlayerWF(Player player);

    /**
     * This method shows the board screen from a local cache.
     */
    public abstract void showMainScreen();

    /**
     * This method shows the board screen.
     *
     * @param mainScreenInfo information that is necessary to update the board view.
     */
    public abstract void updateMainScreen(MainScreenInfo mainScreenInfo);

    /**
     * This method shows the LeaderBoard.
     *
     * @param leaderBoard the LeaderBoard.
     * @param player the player that see the LeaderBoard, in order determine his position.
     */
    public abstract void endGame(ArrayList<Player> leaderBoard, Player player);

    /**
     * This method shows the set of ToolCards.
     *
     * @param toolCards the set of ToolCards
     */
    public abstract void showToolCards(ToolCard[] toolCards);

    /**
     * This method shows an error message related to the network connection.
     *
     */
    public abstract void connectionError();

    /**
     * This method shows an error message related to the player action to be performed.
     *
     * @param parsing true if the error is related to the user input parsing.
     */
    public abstract void invalidMoveError(boolean parsing);

    /**
     * This method shows the help menu.
     */
    public abstract void help();

    /**
     * This method updates the lobby list.
     * @param usernames the list of player usernames.
     */
    public abstract void updatePlayerLobby(ArrayList<String> usernames);

    /**
     * This method show the PatternCards that a player has to choose.
     * @param pc the set of PatternCards to show.
     */
    public abstract void patternCardChooser(ArrayList<PatternCard> pc);

    /**
     *
     * @param lobbyGathering true if during the lobby gathering
     */
    public abstract void enteringError(boolean lobbyGathering);

    /**
     * This method starts the View
     */
    public abstract void startView();
}
