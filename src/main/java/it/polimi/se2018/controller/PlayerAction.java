package it.polimi.se2018.controller;

public class PlayerAction implements PlayerActionInterface{
    private  String usernameReq;
    private boolean quitReq;
    private boolean pauseReq;
    private boolean switchConnReq;
    private int[] posDPDie = new int[2];
    private int[] posRTDie = new int[2];
    private int[][] placeDPDie = new int[2][2];
    private int[][] placeWFDie = new int[2][2];
    private int[][] placeNewWFDie = new int[2][2];
    private int idToolCard;
    //TODO add socket attribute

    // Methods

    public void  PlayerAction(){}

    public int getNewDieValue() {
        int newDieValue = 0;
        return newDieValue;
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
}
