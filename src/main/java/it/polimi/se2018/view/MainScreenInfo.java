package it.polimi.se2018.view;

import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;

import java.util.ArrayList;

public class MainScreenInfo {
    private it.polimi.se2018.model.Player player;
    private Player[] opponents;
    private int round;
    private boolean backward;
    private ArrayList<Die> draftPool;
    private ArrayList<java.util.ArrayList<Die>> roundTrack;

    public MainScreenInfo(){}

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player[] getOpponents() {
        return opponents;
    }

    public void setOpponents(Player[] opponents) {
        this.opponents = opponents;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public boolean isBackward() {
        return backward;
    }

    public void setBackward(boolean backward) {
        this.backward = backward;
    }

    public ArrayList<Die> getDraftPool() {
        return draftPool;
    }

    public void setDraftPool(ArrayList<Die> draftPool) {
        this.draftPool = draftPool;
    }

    public ArrayList<ArrayList<Die>> getRoundTrack() {
        return roundTrack;
    }

    public void setRoundTrack(ArrayList<ArrayList<Die>> roundTrack) {
        this.roundTrack = roundTrack;
    }
}
