package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.network.ClientGatherer;
import it.polimi.se2018.network.ClientInfo;
import it.polimi.se2018.utils.Timer;
import it.polimi.se2018.view.VirtualView;

import java.util.ArrayList;
import java.util.Iterator;

public class ServerController {
    //Attributes
    private Model model;
    private VirtualView view;
    private ClientGatherer clientGatherer;
    private ArrayList<PlayerAction> playerActions;
    private boolean lobbyGathering = true; // TODO: ?? model.started
    //private int numClients = 0;
    private Timer lobbyTimer;

    //Methods
    public ServerController(Model model, VirtualView view){
        this.model = model;
        this.view = view;
        lobbyTimer = new Timer(); // TODO: load lobbyTimer value from file;
    }

    private void listenPlayerActions() {

    }

    private void gatherPlayers() throws InterruptedException {
        synchronized (clientGatherer) {
            do {
                while(lobbyGathering && clientGatherer.isEmpty()) {
                    wait();
                }
                if(lobbyTimer.isTimeout() && (model.getNumPlayers() >= 2)) {
                    lobbyGathering = false;
                }
                // check if other players are still connected by clientinfo
                checkConnections();
                // read prelobby clients
                Iterator<ClientInfo> iterator = clientGatherer.getIterator();
                while(iterator.hasNext()) {
                    ClientInfo clientInfo = iterator.next();
                    boolean available = true;
                    int i = 0;
                    String username = clientInfo.getPlayerAction().getUsernameReq();
                    //check username and remove client from prelobby if username valid
                    while(available && (i < model.getNumPlayers())) {
                        if(model.getPlayer(i).getUsername() == username) {
                            available = false;
                        }
                    }
                    if(available) {
                        //add client to lobby as players
                        //TODO: add localmodel interface to model
                        //TODO: add view interface to virtualview
                        playerActions.add(clientInfo.getPlayerAction());
                        model.addPlayer(username);
                        //clientGatherer.remove(clientInfo);
                        iterator.remove();
                        //if there are exactly 2 players start the lobbyTimer
                        if(model.getNumPlayers() == 2) {
                            lobbyTimer.start();
                        }
                    }
                    else if(!model.getPlayer(i).isConnected()) {
                        //TODO: set localmodel interface to model
                        //TODO: set view interface to virtualview
                        playerActions.set(i, clientInfo.getPlayerAction());
                        model.reinsertPlayer(i);
                        iterator.remove();
                    }
                    if(model.getNumPlayers() == 4) {
                        lobbyGathering = false;
                    }
                }
            } while(lobbyGathering);
        }
    }

    private void checkConnections() {
        for(int i = 0; i < model.getNumPlayers(); i++) {
            if(model.checkConnection(i) || view.checkConnection(i)) {
                //take into account that the player has quitted
                if(lobbyGathering) {
                    model.removeClient(i);
                    view.removeClient(i);
                }
                else {
                    model.removePlayer(i);
                }
            }

        }
    }

    private boolean validAction(PlayerAction pa) throws InvalidPlaceException {

        //checking of match interruption
        if ((pa.isQuitReq() || pa.isSwitchConnReq())
                && (pa.getNewDieValue() >= 1 && pa.getNewDieValue() <= 6)
                && (pa.getIdToolCard() >= 1 && pa.getIdToolCard() <= 12)) {
            if (!((!pa.isSwitchConnReq() || !pa.isQuitReq())
                    && !pa.isQuitReq()
                    && !pa.isSwitchConnReq())) {
                return false;
            }
        }
        else return false;

        //I'm assuming that the default value is '-1'.
        //Checking for the position in the Draft Pool
        if (pa.getPosDPDie()[0] >= 0 && model.getDraftPoolDie(pa.getPosDPDie()[0]) != null) {
            if (pa.getPosDPDie()[1] >= 0 && model.getDraftPoolDie(pa.getPosDPDie()[1]) == null) {
                return false;
            }
        }
        else return false;

        if (pa.getPosRTDie()[0] >= 0 && pa.getPosRTDie()[1] >= 0
                && model.getRoundTrackDie(pa.getPosRTDie()[0], pa.getPosRTDie()[1]) == null) {
            return false;
        }

        //It verifies if the places in the WF are empty.
        if (pa.getPlaceDPDie()[0][0] >= 0 && pa.getPlaceDPDie()[0][0] <= 3 && pa.getPlaceDPDie()[0][1] >= 0 && pa.getPlaceDPDie()[0][1] <= 4
                && model.getPlayer(playerActions.indexOf(pa)).getWindowFramePosition(pa.getPlaceDPDie()[0][0], pa.getPlaceDPDie()[0][1]) == null) {
            if (pa.getPlaceDPDie()[1][0] >= 0 && pa.getPlaceDPDie()[1][0] <= 3 && pa.getPlaceDPDie()[1][1] >= 0 && pa.getPlaceDPDie()[1][1] <= 4
                    && model.getPlayer(playerActions.indexOf(pa)).getWindowFramePosition(pa.getPlaceDPDie()[0][0], pa.getPlaceDPDie()[0][1]) != null) {
                return false;
            }
        }
        else return false;

        //It controls if a designed position to be deleted is full.
        if (pa.getPlaceWFDie()[0][0] >= 0 && pa.getPlaceWFDie()[0][0] <= 3 && pa.getPlaceWFDie()[0][1] >= 0 && pa.getPlaceWFDie()[0][1] <= 4
                && model.getPlayer(playerActions.indexOf(pa)).getWindowFramePosition(pa.getPlaceWFDie()[0][0], pa.getPlaceWFDie()[0][1]) != null) {
            if (pa.getPlaceWFDie()[1][0] >= 0 && pa.getPlaceWFDie()[1][0] <= 3 && pa.getPlaceWFDie()[1][1] >= 0 && pa.getPlaceWFDie()[1][1] <= 4
                    && model.getPlayer(playerActions.indexOf(pa)).getWindowFramePosition(pa.getPlaceWFDie()[1][0], pa.getPlaceWFDie()[1][1]) == null) {
                return false;
            }
        }
        else return false;

        //Afterward the verification of the future placement, this code portion verifies if parameters are legal.
        if (pa.getPlaceNewWFDie()[0][0] >= 0 && pa.getPlaceNewWFDie()[0][0] <= 3 && pa.getPlaceNewWFDie()[0][1] >= 0 && pa.getPlaceNewWFDie()[0][1] <= 4
                && !(pa.getPlaceNewWFDie()[1][0] >= 0 && pa.getPlaceNewWFDie()[1][0] <= 3 && pa.getPlaceNewWFDie()[1][1] >= 0 && pa.getPlaceNewWFDie()[1][1] <= 4)) {
            return false;
        }

        return ((pa.getIdToolCard() > 0)
                && model.getToolCard(pa.getIdToolCard()).validAction(model, model.getPlayer(playerActions.indexOf(pa)).getWF(), pa));
    }

    private void performAction(PlayerAction pa) throws EmptyDiceBagException, InvalidPlaceException {
        if(pa.getIdToolCard() == 0) {
            // regular turn without choosing a tool card
        }
        else {
            ToolCard toolCard = model.getToolCard(pa.getIdToolCard());
            WindowFrame windowFrame = model.getPlayer(playerActions.indexOf(pa)).getWF();
            toolCard.performAction(model, windowFrame, pa);
        }
    }
}
