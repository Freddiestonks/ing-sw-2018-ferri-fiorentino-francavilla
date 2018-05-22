package it.polimi.se2018.controller;

import it.polimi.se2018.model.Model;
import it.polimi.se2018.network.SocketReceiver;
import it.polimi.se2018.view.VirtualView;

import java.util.ArrayList;

public class ServerController {
    //Attributes
    private Model model;
    private VirtualView view;
    private ArrayList<PlayerAction> playerActions;
    private ArrayList<SocketReceiver> socketReceivers;

    //Methods
    public ServerController(Model model, VirtualView view){

    }

    private boolean validAction(PlayerAction pa){
        return true;
    }

    private void performAction(PlayerAction pa){

    }
}
