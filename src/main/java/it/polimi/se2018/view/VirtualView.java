package it.polimi.se2018.view;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.utils.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class VirtualView implements Observer {
    
    ArrayList<ViewInterface> views = new ArrayList<>();
    Model model = Model.instance();

    public static final Logger LOGGER = Logger.getLogger(VirtualView.class.getName());

    public VirtualView() {

    }

    public void addClient(ViewInterface view) {
        views.add(view);
    }

    public void removeClient(int i) {
        views.remove(i);
    }

    public void reinsertClient(int i, ViewInterface view) {
        views.set(i, view);
    }

    public ViewInterface getView(int i) {
        return views.get(i);
    }

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

    public void reset() {
        views.clear();
    }

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
