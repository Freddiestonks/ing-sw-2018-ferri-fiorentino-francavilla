package it.polimi.se2018.controller;
import java.io.Serializable;
import java.util.ArrayList;

public class PlayerAction implements PlayerActionInterface, Serializable {
    private transient Object lock = new Object();
    private boolean updated = false;
    private String usernameReq = null;
    private boolean switchConnReq = false;
    private int patternCard = 0;
    private boolean skipTurn = false;
    private ArrayList<Integer> newDieValue = new ArrayList<>();
    private ArrayList<Integer> posDPDie = new ArrayList<>();
    private ArrayList<int[]> posRTDie = new ArrayList<>();
    private ArrayList<int[]> placeDPDie = new ArrayList<>();
    private ArrayList<int[]> placeWFDie = new ArrayList<>(); //Take from window frame
    private ArrayList<int[]> placeNewWFDie = new ArrayList<>();
    private int idToolCard = 0;

    public PlayerAction() {
    }

    public PlayerAction(Object lock) {
        this.lock = lock;
    }

    private ArrayList<int[]> copy(ArrayList<int[]> arrayList) {
        ArrayList<int[]> newArrayList = new ArrayList<>();
        for(int[] array : arrayList) {
            newArrayList.add(array.clone());
        }
        return newArrayList;
    }

    public void setPlayerAction(PlayerAction pa) {
        synchronized (lock) {
            if(!updated) {
                this.usernameReq = pa.usernameReq;
                this.switchConnReq = pa.switchConnReq;
                this.patternCard = pa.patternCard;
                this.skipTurn = pa.skipTurn;
                this.newDieValue = pa.getNewDieValue();
                this.posDPDie = pa.getPosDPDie();
                this.posRTDie = pa.getPosRTDie();
                this.placeDPDie = pa.getPlaceDPDie();
                this.placeWFDie = pa.getPlaceWFDie();
                this.placeNewWFDie = pa.getPlaceNewWFDie();
                this.idToolCard = pa.idToolCard;
                this.updated = true;
                lock.notifyAll();
            }
        }
    }

    public boolean isUpdated() {
        synchronized (lock) {
            return updated;
        }
    }

    public void setUsernameReq(String usernameReq) {
        this.usernameReq = usernameReq;
    }

    public String getUsernameReq() {
        return usernameReq;
    }

    public void setSwitchConnReq(boolean switchConnReq) {
        this.switchConnReq = switchConnReq;
    }

    public boolean isSwitchConnReq() {
        return switchConnReq;
    }

    public void setPatternCard(int pc) {
        this.patternCard = pc;
    }

    public int getPatternCard() {
        return patternCard;
    }

    public void setSkipTurn(boolean skipTurn) {
        this.skipTurn = skipTurn;
    }

    public boolean isSkipTurn() {
        return skipTurn;
    }

    public void addPosDPDie(int pos) {
        this.posDPDie.add(pos);
    }

    public ArrayList<Integer> getPosDPDie() {
        return new ArrayList<>(posDPDie);
    }

    public void addPosRTDie(int round, int die) {
        this.posRTDie.add(new int[]{round, die});
    }

    public ArrayList<int[]> getPosRTDie() {
        return copy(posRTDie);
    }

    public void addPlaceDPDie(int row, int col) {
        this.placeDPDie.add(new int[]{row, col});
    }

    public ArrayList<int[]> getPlaceDPDie() {
        return copy(placeDPDie);
    }

    public void addPlaceWFDie(int row, int col) {
        this.placeWFDie.add(new int[]{row, col});
    }

    public ArrayList<int[]> getPlaceWFDie() {
        return copy(placeWFDie);
    }

    public void addPlaceNewWFDie(int row, int col) {
        this.placeNewWFDie.add(new int[]{row, col});
    }

    public ArrayList<int[]> getPlaceNewWFDie() {
        return copy(placeNewWFDie);
    }

    public void addNewDieValue(int value) {
        newDieValue.add(value);
    }

    public ArrayList<Integer> getNewDieValue() {
        return new ArrayList<>(newDieValue);
    }

    public void setIdToolCard(int idToolCard) {
        this.idToolCard = idToolCard;
    }

    public int getIdToolCard() {
        return idToolCard;
    }

    public void checkConnection() {}

    public void clear() {
        synchronized (lock) {
            this.usernameReq = null;
            this.switchConnReq = false;
            this.patternCard = 0;
            this.skipTurn = false;
            this.newDieValue.clear();
            this.posDPDie.clear();
            this.posRTDie.clear();
            this.placeDPDie.clear();
            this.placeWFDie.clear();
            this.placeNewWFDie.clear();
            this.idToolCard = 0;
            this.updated = false;
        }
    }
}
