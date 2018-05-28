package it.polimi.se2018.controller;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.network.ClientGatherer;
import it.polimi.se2018.network.ClientInfo;
import it.polimi.se2018.network.SocketReceiver;
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
    private ArrayList<SocketReceiver> socketReceivers;
    private boolean lobbyGathering = true; // TODO: ?? model.started
    private int numClients = 0;
    private Timer timer;

    //Methods
    public ServerController(Model model, VirtualView view){

    }

    private void readPlayerActions() {

    }

    private void gatherPlayers() throws InterruptedException {
        synchronized (clientGatherer) {
            while(lobbyGathering) {
                while(clientGatherer.isEmpty()) {
                    wait();
                }
                // read prelobby clients
                Iterator<ClientInfo> iterator = clientGatherer.getIterator();
                while(iterator.hasNext()) {
                    ClientInfo clientInfo = iterator.next();
                    //check username and remove client from prelobby if username valid
                    //add them to lobby as players
                    //check if other players are still connected by clientinfo
                    //if there are exactly 2 players start the timer
                    if(numClients == 4) {
                        lobbyGathering = false;
                    }
                }
                // begin/continue match
            }
        }
    }

    private boolean validAction(PlayerAction pa){
        return true;
    }

    private void performAction(PlayerAction pa){

    }
}
