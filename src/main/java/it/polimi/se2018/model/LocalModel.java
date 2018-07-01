package it.polimi.se2018.model;

import it.polimi.se2018.view.MainScreenInfo;

import java.util.ArrayList;

public class LocalModel implements LocalModelInterface, ModelInterface {
    private boolean started = false;
    private boolean lobbyGathering = true;
    private WindowFrame windowFrame;
    private int tokens = 0;
    private int round = 1;
    private int turn = 1;
    private boolean backward = false;
    private ArrayList<Die> draftPool = new ArrayList<>();
    private ArrayList<ArrayList<Die>> roundTrack = new ArrayList<>();
    private PubObjCard[] pubOCs = new PubObjCard[3];
    private ToolCard[] toolCards = new ToolCard[3];
    private boolean toolCardUsed = false;
    private int playerIndex;
    private MainScreenInfo mainScreenInfo;

    public LocalModel() {

    }

    public synchronized void setState(boolean started, boolean lobbyGathering) {
        this.started = started;
        this.lobbyGathering = lobbyGathering;
    }

    public synchronized boolean isStarted() {
        return started;
    }

    public synchronized boolean isLobbyGathering() {
        return lobbyGathering;
    }

    public synchronized WindowFrame getWindowFrame(int playerIndex) {
        return windowFrame;
    }

    public synchronized void setWindowFrame(WindowFrame wf) {
        this.windowFrame = wf;
    }

    public synchronized boolean playerHasChosenPC(int playerIndex) {
        return (windowFrame != null);
    }

    public synchronized boolean playerCanUseToolCard(int playerIndex, int idToolCard) {
        ToolCard toolCard = toolCards[idToolCard];
        return (!toolCardUsed && (tokens >= toolCard.getPrice()));
    }

    public synchronized void updateTurn(int round, int turn, boolean backward) {
        this.round = round;
        this.turn = turn;
        this.backward = backward;
    }

    public synchronized int getRound() {
        return round;
    }

    public synchronized int getTurn() {
        return turn;
    }

    public synchronized boolean isBackward() {
        return backward;
    }

    public synchronized void setDraftPool(ArrayList<Die> draftPool) {
        this.draftPool = new ArrayList<>(draftPool);
    }

    public synchronized Die getDraftPoolDie(int pos) {
        return draftPool.get(pos);
    }

    public synchronized int getDraftPoolSize() {
        return draftPool.size();
    }

    public synchronized void setRoundTrack(ArrayList<ArrayList<Die>> roundTrack) {
        this.roundTrack.clear();
        for(int i = 0; i < 10; i++) {
            this.roundTrack.add(new ArrayList<>(roundTrack.get(i)));
        }
    }

    public synchronized Die getRoundTrackDie(int round, int i) {
        return roundTrack.get(round - 1).get(i);
    }

    /*public ArrayList<ArrayList<Die>> getRoundTrack() {
        ArrayList<ArrayList<Die>> rt = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            this.roundTrack.add(new ArrayList<>(roundTrack.get(i)));
        }
        return rt;
    }*/

    public synchronized int getRoundTrackSize(int round) {
        return roundTrack.get(round - 1).size();
    }

    public synchronized ToolCard getToolCard(int i) {
        return toolCards[i - 1];
    }

    public synchronized void setPubOCs(PubObjCard[] pubOCs) {
        this.pubOCs = pubOCs.clone();
    }

    public synchronized PubObjCard[] getPubOCs() {
        return pubOCs.clone();
    }

    public synchronized void setToolCards(ToolCard[] toolCards) {
        this.toolCards = toolCards.clone();
    }

    public synchronized ToolCard[] getToolCards() {
        return toolCards.clone();
    }

    public synchronized void setToolCardUsed(boolean toolCardUsed) {
        this.toolCardUsed = toolCardUsed;
    }

    public synchronized void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public synchronized void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public synchronized int getPlayerIndex() {
        return playerIndex;
    }

    public void checkConnection() {

    }
}
