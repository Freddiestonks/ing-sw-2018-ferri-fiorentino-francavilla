package it.polimi.se2018.controller;
import java.security.InvalidParameterException;

public class PlayerAction implements PlayerActionInterface{
    private boolean updated;
    private String usernameReq;
    private String connectionType;
    private boolean quitReq;
    private boolean switchConnReq;
    private int[] posDPDie = new int[2];
    private int[] posRTDie = new int[2];
    private int[][] placeDPDie = new int[2][2];
    private int[][] placeWFDie = new int[2][2];
    private int[][] placeNewWFDie = new int[2][2];
    private int idToolCard;
    //TODO: arraylist of array

    public void PlayerAction(String username){
        this.usernameReq = username;
        this.quitReq = false;
        this.switchConnReq = false;
    }

    // Setter methods
    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    public boolean getUpdated() {
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

    public void setSwitchConnReq(boolean switchConnReq) {
        this.switchConnReq = switchConnReq;
    }

    public void setPosDraftPoolDie(int first, int second) {
        this.posDPDie[0] = first;
        this.posDPDie[1] = second;
    }

    public void setPosRoundTrackDie(int first, int second) {
        this.posRTDie[0] = first;
        this.posRTDie[1] = second;
    }

    public void setPlaceDraftPoolDie(int row1, int col1, int row2, int col2) {
        this.placeDPDie[0][0] = row1;
        this.placeDPDie[0][1] = col1;
        this.placeDPDie[1][0] = row2;
        this.placeDPDie[1][1] = col2;
    }

    public void setPlaceWFDie(int row1, int col1, int row2, int col2) {
        this.placeWFDie[0][0] = row1;
        this.placeWFDie[0][1] = col1;
        this.placeWFDie[1][0] = row2;
        this.placeWFDie[1][1] = col2;
    }

    public void setPlaceNewWFDie(int row1, int col1, int row2, int col2) {
        this.placeNewWFDie[0][0] = row1;
        this.placeNewWFDie[0][1] = col1;
        this.placeNewWFDie[1][0] = row2;
        this.placeNewWFDie[1][1] = col2;
    }

    public void setIdToolCard(int idToolCard) {
        if(idToolCard>=0 && idToolCard<=12) {
            this.idToolCard = idToolCard;
        }
        else {
            throw new InvalidParameterException();
        }
    }

    // Getter methods
    public int getNewDieValue() {
        //TODO: to implement the method.
        return 0;
    }

    public boolean isQuitReq() {
        return quitReq;
    }

    public boolean isSwitchConnReq() {
        return switchConnReq;
    }

    public int getIdToolCard() {
        return idToolCard;
    }

    public int[] getPosDPDie() {
        return posDPDie;
    }

    public int[] getPosRTDie() {
        return posRTDie;
    }

    public int[][] getPlaceWFDie() {
        return placeWFDie;
    }

    public int[][] getPlaceNewWFDie() {
        return placeNewWFDie;
    }

    public int[][] getPlaceDPDie() {
        return placeDPDie;
    }

    @Override
    public void checkConnection() {

    }
    public void setConnection(String type){
        connectionType = type;
    }
}
