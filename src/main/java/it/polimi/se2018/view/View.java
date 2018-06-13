package it.polimi.se2018.view;

import it.polimi.se2018.model.*;

import java.io.IOException;
import java.util.ArrayList;

public abstract class View implements ViewInterface{

    public View(){}
    @Override
    public void updateWaitingRoom(boolean starting) {}
    @Override
    public void welcomeScreen(){}
    @Override
    public void updateRT(ArrayList<ArrayList<Die>> roundTrack,int round) {}
    @Override
    public void checkConnection() throws IOException {
    }
    @Override
    public void updateDP(ArrayList<Die> draftPool) {}
    @Override
    public void updateTokens(int tokens){}
    @Override
    public void updateRound(int round){}
    @Override
    public void updateOrder(boolean backward){}
    @Override
    public void updateInfo(int tokens, int round, boolean backward){}
    @Override
    public void updatePrivOCs(PrivObjCard privObjCard){}
    @Override
    public void updatePubOCs(PubObjCard pubObjCards[]){}
    @Override
    public void updateOpponentsWF(Player[] opponents){}
    @Override
    public void updatePlayerWF(Player player){}
    @Override
    public void updateMainScreen(MainScreenInfo mainScreenInfo){}
    @Override
    public void updateConnectionRequest(boolean success){}
    @Override
    public void endGame(Player[] leaderboard,Player player,int[] score){}
    @Override
    public void updateToolCards(ToolCard[] toolCards){}
    @Override
    public void invalidMoveError(){}
    @Override
    public String input(){
        return null;
    }
    public void help(){}
    @Override
    public void selectionMaker(String[] string){}
    @Override
    public void updatePlayerLobby(ArrayList<Player> players){}
    @Override
    public void updatePlayerState(Player player){}
    @Override
    public void patternCardGenerator(ArrayList<PatternCard> pc){}

    }
