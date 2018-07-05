package it.polimi.se2018.view;

import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PrivObjCard;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represent all the information necessary to update a client View about the board screen.
 */
public class MainScreenInfo implements Serializable {

    private Player player;
    private ArrayList<Player> opponents;
    private int round;
    private String turnPlayer;
    private boolean backward;
    private ArrayList<Die> draftPool;
    private ArrayList<ArrayList<Die>> roundTrack;
    private PrivObjCard privObjCard;

    public MainScreenInfo() {

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Player> getOpponents() {
        return new ArrayList<>(opponents);
    }

    public void setOpponents(ArrayList<Player> opponents) {
        this.opponents = new ArrayList<>(opponents);
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getTurnPlayer() {
        return turnPlayer;
    }

    public void setTurnPlayer(String turnPlayer) {
        this.turnPlayer = turnPlayer;
    }

    public boolean isBackward() {
        return backward;
    }

    public void setBackward(boolean backward) {
        this.backward = backward;
    }

    public ArrayList<Die> getDraftPool() {
        return new ArrayList<>(draftPool);
    }

    public void setDraftPool(ArrayList<Die> draftPool) {
        this.draftPool = new ArrayList<>(draftPool);
    }

    public ArrayList<ArrayList<Die>> getRoundTrack() {
        return new ArrayList<>(roundTrack);
    }

    public void setRoundTrack(ArrayList<ArrayList<Die>> roundTrack) {
        this.roundTrack = new ArrayList<>(roundTrack);
    }

    public PrivObjCard getPrivObjCard() {
        return privObjCard;
    }

    public void setPrivObjCard(PrivObjCard privObjCard) {
        this.privObjCard = privObjCard;
    }
}
