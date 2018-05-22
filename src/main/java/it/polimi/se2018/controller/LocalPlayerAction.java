package it.polimi.se2018.controller;

public class LocalPlayerAction implements PlayerActionInterface {
    private String usernameReq;
    private boolean quitReq;
    private boolean pauseReq;
    private boolean booleanswitchConnReq;
    private int[] posDPDie = new int[2];
    private int[] posRTDie = new int[2];
    private int[][] placeDPDie = new int[2][2];
    private int[][] placeWFDie = new int[2][2];
    private int[][] placeNewWFDie = new int[2][2];
    private int idToolCard;

    //Methods
    public LocalPlayerAction(){

    }
}
