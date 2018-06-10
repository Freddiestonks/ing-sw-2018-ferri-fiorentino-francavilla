package it.polimi.se2018.model;

import java.util.ArrayList;

public class LocalModel implements LocalModelInterface {
    private boolean started;
    private WindowFrame windowFrame;
    private int round = 1;
    private int turn = 1;
    private boolean backward = false;
    private ArrayList<Die> draftPool = new ArrayList<>();
    private ArrayList<ArrayList<Die>> roundTrack = new ArrayList<>();
    private PubObjCard[] pubOCs = new PubObjCard[3];
    private ToolCard[] toolCards = new ToolCard[3];

    public LocalModel() {

    }

    public void setWindowFrame(WindowFrame wf) {
        this.windowFrame = wf;
    }

    public WindowFrame getWindowFrame() {
        return windowFrame;
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

    public void setRoundTrack(ArrayList<ArrayList<Die>> roundTrack) {
        for(int i = 0; i < 10; i++) {
            this.roundTrack.set(i, new ArrayList<>(roundTrack.get(i)));
        }
    }

    public ArrayList<ArrayList<Die>> getRoundTrack() {
        ArrayList<ArrayList<Die>> rt = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            this.roundTrack.add(new ArrayList<>(roundTrack.get(i)));
        }
        return rt;
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

    public void checkConnection() {
    }

}
