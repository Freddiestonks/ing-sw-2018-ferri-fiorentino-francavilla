package it.polimi.se2018.controller;
import java.util.ArrayList;

public class PlayerAction implements PlayerActionInterface {
    private Object lock = new Object();
    private boolean updated = false;
    private String usernameReq = null;
    private String connectionType = null;
    private boolean quitReq = false;
    private int patternCard = 0;
    private ArrayList<Integer> newDieValue = new ArrayList<>();
    private ArrayList<Integer> posDPDie = new ArrayList<>();
    private ArrayList<int[]> posRTDie = new ArrayList<>();
    private ArrayList<int[]> placeDPDie = new ArrayList<>();
    private ArrayList<int[]> placeWFDie = new ArrayList<>(); //Take from window frame
    private ArrayList<int[]> placeNewWFDie = new ArrayList<>();
    private int idToolCard = 0;

    public void PlayerAction() {
    }

    public void PlayerAction(Object lock) {
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
                this.connectionType = pa.connectionType;
                this.quitReq = pa.quitReq;
                this.patternCard = pa.patternCard;
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

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setQuitReq(boolean quitReq) {
        this.quitReq = quitReq;
    }

    public boolean isQuitReq() {
        return quitReq;
    }

    public void setPatternCard(int pc) {
        this.patternCard = pc;
    }

    public int getPatternCard() {
        return patternCard;
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
            this.connectionType = null;
            this.quitReq = false;
            this.patternCard = 0;
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
