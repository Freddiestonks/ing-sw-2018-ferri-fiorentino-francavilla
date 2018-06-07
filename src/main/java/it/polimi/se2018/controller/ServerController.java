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
    public static final Object lock = new Object();
    private Model model;
    private VirtualView view;
    private ClientGatherer clientGatherer;
    private ArrayList<PlayerAction> playerActions = new ArrayList<>();
    private boolean lobbyGathering = true; // TODO: ?? model.started
    //private int numClients = 0;
    private int lobbyTimeout;

    //Methods
    public ServerController(Model model, VirtualView view){
        this.model = model;
        this.view = view;
        clientGatherer = new ClientGatherer();
        lobbyTimeout = 10; // TODO: load lobbyTimer value from file;
    }

    private boolean incomingPlayerActions() {
        Iterator<PlayerAction> iterator = playerActions.iterator();
        while(iterator.hasNext()) {
            if(iterator.next().isUpdated()) {
                return true;
            }
        }
        return false;
    }

    private void listenPlayerActions() throws InterruptedException, CloneNotSupportedException {
        Iterator<PlayerAction> iterator;
        while(!lobbyGathering) {
            synchronized (lock) {
                lock.wait();
            }
            iterator = playerActions.iterator();
            while(iterator.hasNext()) {
                PlayerAction pa = iterator.next();
                if(pa.isUpdated()) {
                    if(validAction(pa)) {
                        performAction(pa);
                    }
                    pa.clear();
                }
            }
            gatherPlayers();
        }
    }

    private void gatherPlayers() throws InterruptedException {
        synchronized (lock) {
            Timer lobbyTimer = new Timer(10, lock);
            do {
                while(lobbyGathering
                        && emptyPreLobby()
                        && !lobbyTimer.isTimeout()) {
                    lock.wait();
                }
                // check if other players are still connected by clientinfo
                checkConnections();
                if(lobbyTimer.isTimeout()) {
                    if(model.getNumPlayers() >= 2) {
                        lobbyGathering = false;
                    }
                    else {
                        lobbyTimer = new Timer(lobbyTimeout, lock);
                    }
                }

                // read prelobby clients
                Iterator<ClientInfo> iterator = clientGatherer.getIterator();
                while(iterator.hasNext()) {
                    ClientInfo clientInfo = iterator.next();
                    PlayerAction pa = clientInfo.getPlayerAction();
                    if(pa.isUpdated()) {
                        boolean available = true;
                        int i = 0;
                        String username = pa.getUsernameReq();
                        //check username and remove client from prelobby if username valid
                        while(available && (i < model.getNumPlayers())) {
                            if(model.getPlayer(i).getUsername().equals(username)) {
                                available = false;
                            }
                            else {
                                i++;
                            }
                        }
                        if(lobbyGathering && available) {
                            //add client to lobby as players
                            model.addPlayer(username, clientInfo.getLocalModel());
                            view.addClient(clientInfo.getView());
                            playerActions.add(pa);
                            //clientGatherer.remove(clientInfo);
                            iterator.remove();
                            //if there are exactly 2 players start the lobbyTimer
                            if(model.getNumPlayers() == 2) {
                                lobbyTimer.start();
                            }
                            else if(model.getNumPlayers() == 4) {
                                lobbyGathering = false;
                            }
                        }
                        else if(!available && !model.getPlayer(i).isConnected()) {
                            model.reinsertPlayer(i, clientInfo.getLocalModel());
                            view.reinsertClient(i, clientInfo.getView());
                            playerActions.set(i, pa);
                            iterator.remove();
                        }
                        pa.clear();
                    }
                }
            } while(lobbyGathering);
        }
    }

    private void checkConnections() {
        for(int i = 0; i < model.getNumPlayers(); i++) {
            if(!model.checkConnection(i) || !view.checkConnection(i)) {
                //take into account that the player has quitted
                if(lobbyGathering) {
                    model.removeClient(i);
                    view.removeClient(i);
                    playerActions.remove(i);
                    i--;
                }
                else {
                    model.removePlayer(i);
                }
            }
        }
    }

    private boolean emptyPreLobby() {
        Iterator<ClientInfo> iterator = clientGatherer.getIterator();
        while(iterator.hasNext()) {
            if(iterator.next().getPlayerAction().isUpdated()) {
                return false;
            }
        }
        return true;
    }

    private boolean validAction(PlayerAction pa) throws InvalidPlaceException, CloneNotSupportedException {

        //checking of match interruption
        if ((pa.isQuitReq()
                && (pa.getNewDieValue().get(0) >= 1 && pa.getNewDieValue().get(0) <= 6)
                && (pa.getIdToolCard() >= 1 && pa.getIdToolCard() <= 12))) {
            if (!((!pa.isQuitReq())
                    && !pa.isQuitReq())) {
                return false;
            }
        }
        else return false;

        //Checking for the position in the Draft Pool
        if (pa.getPosDPDie().get(0) >= 0 && model.getDraftPoolDie(pa.getPosDPDie().get(0)) != null) {
            if (pa.getPosDPDie().get(1) >= 0 && model.getDraftPoolDie(pa.getPosDPDie().get(1)) == null) {
                return false;
            }
        }
        else return false;

        if (pa.getPosRTDie().get(0)[0] >= 0 && pa.getPosRTDie().get(1)[0] >= 0
                && model.getRoundTrackDie(pa.getPosRTDie().get(0)[0], pa.getPosRTDie().get(0)[1]) == null) {
            return false;
        }

        //It verifies if the places in the WF are empty.
        if (pa.getPlaceDPDie().get(0)[0] >= 0 && pa.getPlaceDPDie().get(0)[0] <= 3 && pa.getPlaceDPDie().get(1)[1] >= 0 && pa.getPlaceDPDie().get(0)[1] <= 4
                && model.getPlayer(playerActions.indexOf(pa)).getWFPosition(pa.getPlaceDPDie().get(0)[0], pa.getPlaceDPDie().get(0)[1]) == null) {
            if (pa.getPlaceDPDie().get(1)[0] >= 0 && pa.getPlaceDPDie().get(1)[0] <= 3 && pa.getPlaceDPDie().get(1)[1] >= 0 && pa.getPlaceDPDie().get(1)[1] <= 4
                    && model.getPlayer(playerActions.indexOf(pa)).getWFPosition(pa.getPlaceDPDie().get(0)[0], pa.getPlaceDPDie().get(0)[1]) != null) {
                return false;
            }
        }
        else return false;

        //It controls if a designed position to be deleted is full.
        if (pa.getPlaceWFDie().get(0)[0] >= 0 && pa.getPlaceWFDie().get(0)[0] <= 3 && pa.getPlaceWFDie().get(0)[1] >= 0 && pa.getPlaceWFDie().get(0)[1] <= 4
                && model.getPlayer(playerActions.indexOf(pa)).getWFPosition(pa.getPlaceWFDie().get(0)[0], pa.getPlaceWFDie().get(0)[1]) != null) {
            if (pa.getPlaceWFDie().get(1)[0] >= 0 && pa.getPlaceWFDie().get(1)[0] <= 3 && pa.getPlaceWFDie().get(1)[1] >= 0 && pa.getPlaceWFDie().get(1)[1] <= 4
                    && model.getPlayer(playerActions.indexOf(pa)).getWFPosition(pa.getPlaceWFDie().get(1)[0], pa.getPlaceWFDie().get(1)[1]) == null) {
                return false;
            }
        }
        else return false;

        //Afterward the verification of the future placement, this code portion verifies if parameters are legal.
        if (pa.getPlaceNewWFDie().get(0)[0] >= 0 && pa.getPlaceNewWFDie().get(0)[0] <= 3 && pa.getPlaceNewWFDie().get(0)[1] >= 0 && pa.getPlaceNewWFDie().get(0)[1] <= 4
                && !(pa.getPlaceNewWFDie().get(1)[0] >= 0 && pa.getPlaceNewWFDie().get(1)[0] <= 3 && pa.getPlaceNewWFDie().get(1)[1] >= 0 && pa.getPlaceNewWFDie().get(1)[1] <= 4)) {
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
