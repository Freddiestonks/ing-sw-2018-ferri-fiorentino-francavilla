package it.polimi.se2018.model;

import java.util.ArrayList;

public class LocalModel implements LocalModelInterface, ModelInterface {
    private boolean started = false;
    private boolean lobbyGathering = false;
    private WindowFrame windowFrame;
    private int round = 1;
    private int turn = 1;
    private boolean backward = false;
    private ArrayList<Die> draftPool = new ArrayList<>();
    private ArrayList<ArrayList<Die>> roundTrack = new ArrayList<>();
    private PubObjCard[] pubOCs = new PubObjCard[3];
    private ToolCard[] toolCards = new ToolCard[3];
    private boolean toolCardUsed = false;
    private int playerIndex;

    public LocalModel() {

    }

    public void setState(boolean started, boolean lobbyGathering) {
        this.started = started;
        this.lobbyGathering = lobbyGathering;
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isLobbyGathering() {
        return lobbyGathering;
    }

    public WindowFrame getWindowFrame(int playerIndex) {
        return windowFrame;
    }

    public void setWindowFrame(WindowFrame wf) {
        this.windowFrame = wf;
    }

    public boolean playerHasChosenPC(int playerIndex) {
        return false;
    }

    public void updateTurn(int round, int turn, boolean backward) {
        this.round = round;
        this.turn = turn;
        this.backward = backward;
    }

    public int getRound() {
        return round;
    }

    public int getTurn() {
        return turn;
    }

    public boolean isBackward() {
        return backward;
    }

    public void setDraftPool(ArrayList<Die> draftPool) {
        this.draftPool = new ArrayList<>(draftPool);
    }

    public ArrayList<Die> getDraftPool() {
        return new ArrayList<>(draftPool);
    }

    public Die getDraftPoolDie(int pos) {
        return draftPool.get(pos);
    }

    public int getDraftPoolSize() {
        return draftPool.size();
    }

    public void setRoundTrack(ArrayList<ArrayList<Die>> roundTrack) {
        this.roundTrack.clear();
        for(int i = 0; i < 10; i++) {
            this.roundTrack.add(new ArrayList<>(roundTrack.get(i)));
        }
    }

    public Die getRoundTrackDie(int round, int i) {
        return roundTrack.get(round - 1).get(i);
    }

    /*public ArrayList<ArrayList<Die>> getRoundTrack() {
        ArrayList<ArrayList<Die>> rt = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            this.roundTrack.add(new ArrayList<>(roundTrack.get(i)));
        }
        return rt;
    }*/

    public int getRoundTrackSize(int round) {
        return roundTrack.get(round - 1).size();
    }

    public ToolCard getToolCard(int i) {
        return toolCards[i - 1];
    }

    public void setPubOCs(PubObjCard[] pubOCs) {
        this.pubOCs = pubOCs.clone();
    }

    public PubObjCard[] getPubOCs() {
        return pubOCs.clone();
    }

    public void setToolCards(ToolCard[] toolCards) {
        this.toolCards = toolCards.clone();
    }

    public ToolCard[] getToolCards() {
        return toolCards.clone();
    }

    public void setToolCardUsed(boolean toolCardUsed) {
        this.toolCardUsed = toolCardUsed;
    }

    public boolean isToolCardUsed() {
        return toolCardUsed;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void checkConnection() {

    }
}
