package it.polimi.se2018.view;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.PatternCard;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.utils.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class VirtualView implements Observer {
    ArrayList<ViewInterface> views = new ArrayList<>();
    Model model = Model.instance();

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

    public void update() {
        System.out.println("begin views update");
        if(model.isLobbyGathering()) {
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
        else if(model.isOver()){
            /*ArrayList<Player> leaderBoard= model.getLeaderBoard();
            int[] score = new int[model.getPlayers().size()];
            for(int i = 0; i<model.getPlayers().size();i++){
                score[i] = leaderBoard.get(i).calculateScore(model.getPubOCs());
            }*/
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
        else if(!model.isStarted()) {
            ArrayList<PatternCard> patternCards = new ArrayList<>(Arrays.asList(model.getPatternCards()));
            int offset = 0;
            for(ViewInterface view : views) {
                ArrayList<PatternCard> playerPCs = new ArrayList<>(patternCards.subList(offset, offset + 2));
                try {
                    view.patternCardGenerator(playerPCs);
                } catch (IOException e) {
                    //e.printStackTrace();
                }
                offset += 2;
            }
        }
        else {
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
        System.out.println("end views update");
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
