package it.polimi.se2018.view;

import it.polimi.se2018.controller.GUIcontroller;
import it.polimi.se2018.model.*;

import java.util.ArrayList;


public class GUIView extends View{
    GUIcontroller guIcontroller = new GUIcontroller();
    private boolean connected;
    public void readInput(){
        notifyObservers();
    }
    public void updateWaitingRoom(boolean starting) {

    }

    public void welcomeScreen() {

    }

    public void updateRT(ArrayList<ArrayList<Die>> roundTrack) {

    }

    public void updateDP(ArrayList<Die> draftPool) {

    }

    public void updateTokens(int tokens) {

    }

    public void updateRound(int round) {

    }

    public void updateOrder(boolean backward) {

    }

    public void updateInfo(int tokens, int round, boolean backward, String turnPlayer) {

    }

    public void updatePrivOCs(PrivObjCard privObjCard) {

    }

    public void showPubOCs(PubObjCard[] pubObjCards) {

    }

    public void updateOpponentsWF(ArrayList<Player> opponents) {

    }

    public void updatePlayerWF(Player player) {

    }

    public void showMainScreen() {

    }

    public void updateMainScreen(MainScreenInfo mainScreenInfo) {

    }

    public void updateConnectionRequest(boolean success) {

    }

    public void endGame(ArrayList<Player> leaderBoard, Player player) {

    }

    public void showToolCards(ToolCard[] toolCards) {

    }

    public void connectionError() {
        connected=false;
    }

    public void invalidMoveError(boolean parsing) {

    }

    public void help() {

    }

    public void selectionMaker(String[] string) {

    }

    public void updatePlayerLobby(ArrayList<String> usernames) {
    }

    public void updatePlayerState(Player player) {

    }

    public void patternCardChooser(ArrayList<PatternCard> pc) {

    }

    public synchronized void enteringError(boolean lobbyGathering) {

    }

    public void startView() {
        guIcontroller.setView(this);
        guIcontroller.main(null);
    }
    public synchronized void setUserInput(String input){
        userInput = input;
        readInput();
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
