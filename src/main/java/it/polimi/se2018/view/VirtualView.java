package it.polimi.se2018.view;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.utils.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * This class aims to update the players' View after a Model update.
 */
public class VirtualView implements Observer {
    // the list of Views remote reference
    private ArrayList<ViewInterface> views = new ArrayList<>();
    private Model model = Model.instance();

    public static final Logger LOGGER = Logger.getLogger(VirtualView.class.getName());

    public VirtualView() {

    }

    /**
     * This method add a client to the Views list.
     * @param view related to a client.
     */
    public void addClient(ViewInterface view) {
        views.add(view);
    }

    /**
     * This method remove a client from the View list.
     * @param i the index of the client in the list.
     */
    public void removeClient(int i) {
        views.remove(i);
    }

    /**
     * This method reinsert a client to the Views list.
     * @param i the index of the client in the list.
     * @param view the View remote reference to substitute to the current one.
     */
    public void reinsertClient(int i, ViewInterface view) {
        views.set(i, view);
    }

    /**
     * This method update the lobby state for all the connected players.
     */
    private void updateLobby() {
        ArrayList<String> usernames = new ArrayList<>();
        for(int i = 0; i < model.getNumPlayers(); i++) {
            usernames.add(model.getPlayer(i).getUsername());
        }
        for(ViewInterface view : views) {
            try {
                view.updatePlayerLobby(usernames);
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
    }

    /**
     * This method update the LeaderBoard for all the connected players.
     */
    private void updateMatchOver() {
        ArrayList<Player> leaderBoard = model.getLeaderBoard();
        int playerIndex = 0;
        for(ViewInterface view : views) {
            try {
                view.endGame(leaderBoard, model.getPlayer(playerIndex));
            } catch (IOException e) {
                //e.printStackTrace();
            }
            playerIndex++;
        }
    }

    /**
     * This method update the PatternCard to choose for all the connected players.
     */
    private void patternCardChooser() {
        ArrayList<PatternCard> patternCards = new ArrayList<>(Arrays.asList(model.getPatternCards()));
        int offset = 0;
        for(ViewInterface view : views) {
            ArrayList<PatternCard> playerPCs = new ArrayList<>(patternCards.subList(offset, offset + 2));
            try {
                view.patternCardChooser(playerPCs);
            } catch (IOException e) {
                //e.printStackTrace();
            }
            offset += 2;
        }
    }

    /**
     * This method update the board state for all the connected players.
     */
    private void updateBoardScreen() {
        MainScreenInfo msi = new MainScreenInfo();
        String turnPlayer = model.getPlayer(model.getTurn() - 1).getUsername();
        msi.setRound(model.getRound());
        msi.setTurnPlayer(turnPlayer);
        msi.setBackward(model.isBackward());
        msi.setDraftPool(model.getDraftPool());
        msi.setRoundTrack(model.getRoundTrack());
        int playerIndex = 0;
        for(ViewInterface view : views) {
            ArrayList<Player> opponents = model.getPlayers();
            Player player = opponents.remove(playerIndex);
            msi.setPlayer(player);
            msi.setPrivObjCard(player.getPrivObjCard());
            msi.setOpponents(opponents);
            try {
                view.updateMainScreen(msi);
            } catch (IOException e) {
                //e.printStackTrace();
            }
            playerIndex++;
        }
    }

    public void update() {
        LOGGER.fine("begin views update");
        if(model.isLobbyGathering()) {
            updateLobby();
        }
        else if(model.isOver()) {
            updateMatchOver();
        }
        else if(!model.isStarted()) {
            patternCardChooser();
        }
        else {
            updateBoardScreen();
        }
        LOGGER.fine("end views update");
    }

    /**
     * This method resets the View list.
     */
    public void reset() {
        views.clear();
    }

    /**
     * This method checks whether the View remote reference are connected.
     *
     * @param i the index of client in the View list.
     * @return true if the selected client is connected.
     */
    public boolean checkConnection(int i) {
        boolean check = true;
        try {
            views.get(i).checkConnection();
        } catch (IOException e) {
            check = false;
        } finally {
            return check;
        }
    }
}
