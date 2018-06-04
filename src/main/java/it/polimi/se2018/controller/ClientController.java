package it.polimi.se2018.controller;

import it.polimi.se2018.network.SocketReceiver;
import it.polimi.se2018.view.View;
import it.polimi.se2018.model.LocalModel;

public class ClientController {
    //attributes
    private LocalModel model;
    private View view;
    private PlayerActionInterface playeraction;

    //Methods
    public ClientController(LocalModel model, View view){
    }

    private boolean validAction(PlayerAction pa){

        return true;
    }

    private void performAction(PlayerAction pa){

    }
}
