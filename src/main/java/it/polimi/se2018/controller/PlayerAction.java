package it.polimi.se2018.controller;
import java.util.ArrayList;

public class PlayerAction implements PlayerActionInterface{
    private boolean updated = false;
    private String usernameReq;
    private String connectionType;
    private boolean quitReq = false;
    private ArrayList<Integer> newDieValue = new ArrayList<>();
    private ArrayList<Integer> posDPDie = new ArrayList<>();
    private ArrayList<int[]> posRTDie = new ArrayList<>();
    private ArrayList<int[]> placeDPDie = new ArrayList<>();
    private ArrayList<int[]> placeWFDie = new ArrayList<>(); //Take from window frame
    private ArrayList<int[]> placeNewWFDie = new ArrayList<>();
    private int idToolCard = 0;

    public void PlayerAction(){
    }

    public void update() {
        this.updated = true;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUsernameReq(String usernameReq) {
        this.usernameReq = usernameReq;
    }

    public String getUsernameReq() {
        return usernameReq;
    }

    public void setQuitReq(boolean quitReq) {
        this.quitReq = quitReq;
    }

    public boolean isQuitReq() {
        return quitReq;
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
        return posRTDie;
    }

    public void addPlaceDPDie(int row, int col) {
        this.placeDPDie.add(new int[]{row, col});
    }

    public ArrayList<int[]> getPlaceDPDie() {
        return new ArrayList<>(placeDPDie);
    }

    public void addPlaceWFDie(int row, int col) {
        this.placeWFDie.add(new int[]{row, col});
    }

    public ArrayList<int[]> getPlaceWFDie() {
        return new ArrayList<>(placeWFDie);
    }

    public void addPlaceNewWFDie(int row, int col) {
        this.placeNewWFDie.add(new int[]{row, col});
    }

    public ArrayList<int[]> getPlaceNewWFDie() {
        return new ArrayList<>(placeNewWFDie);
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

    public void checkConnection() {

    }
    public void setConnection(String type){
        connectionType = type;
    }

    public void clear() {
        //TODO: clear player actions attributes
    }
}
