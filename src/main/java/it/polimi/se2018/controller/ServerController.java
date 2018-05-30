package it.polimi.se2018.controller;

import it.polimi.se2018.model.EmptyDiceBagException;
import it.polimi.se2018.model.InvalidPlaceException;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.toolcards.*;
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
        this.model = model;
        this.view = view;
        for(int i=0; i<model.getNumPlayers(); i++){
            playerActions.add(0, new PlayerAction());
            socketReceivers.add(0,new SocketReceiver());
        }
    }

    private boolean validAction(PlayerAction pa) throws InvalidPlaceException {
        boolean check1 = true;
        boolean ck1 = false;
        boolean ck2 = false;
        boolean ck3 = false;
        boolean ck4 = false;
        boolean ck5 = false;
        boolean check2;

        //checking of match interruption
        if(pa.isPauseReq() || pa.isQuitReq() || pa.isSwitchConnReq()){
            if(!((!pa.isSwitchConnReq() || !pa.isQuitReq())
                    && (!pa.isQuitReq() || !pa.isPauseReq()))
                    && (!pa.isSwitchConnReq() || !pa.isPauseReq())){
                check1 = false;
            }
        }

        //I'm assuming that the default value is '-1'.
        //Checking for the position in the Draft Pool
        if(pa.getPosDPDie()[0] >= 0) {
            if (model.getDraftPoolDie(pa.getPosDPDie()[0]) != null){
                ck1 = true;
                if(pa.getPosDPDie()[1] >= 0){
                    if (model.getDraftPoolDie(pa.getPosDPDie()[1]) == null){
                        ck1 = false;
                    }
                }
            }
        }

        if(pa.getPosRTDie()[0] >= 0 && pa.getPosRTDie()[1] >= 0){
            if(model.getRoundTrackDie(pa.getPosRTDie()[0],pa.getPosRTDie()[1]) != null){
                ck2 = true;
            }
        }

        //It verifies if the places in the WF are empty.
        if(ck1) {
            if((pa.getPlaceDPDie()[0][0] >= 0) && (pa.getPlaceDPDie()[0][1] >= 0)){
                if(model.getPlayers()[playerActions.indexOf(pa)].getWindowFramePosition(pa.getPlaceDPDie()[0][0],pa.getPlaceDPDie()[0][1]) == null){
                    ck3 = true;
                    if(pa.getPlaceDPDie()[1][0] >= 0 && pa.getPlaceDPDie()[1][1] >= 0){
                        if (model.getPlayers()[playerActions.indexOf(pa)].getWindowFramePosition(pa.getPlaceDPDie()[0][0], pa.getPlaceDPDie()[0][1]) != null) {
                            ck3 = false;
                        }
                    }
                }
            }
        }

        //It controls if a designed position to be deleted is full.
        if(pa.getPlaceWFDie()[0][0] >= 0 && pa.getPlaceWFDie()[0][1] >= 0){
            if(model.getPlayers()[playerActions.indexOf(pa)].getWindowFramePosition(pa.getPlaceWFDie()[0][0],pa.getPlaceWFDie()[0][1]) != null){
                ck4 = true;
                if(pa.getPlaceWFDie()[1][0] >= 0 && pa.getPlaceWFDie()[1][1] >= 0) {
                    if (model.getPlayers()[playerActions.indexOf(pa)].getWindowFramePosition(pa.getPlaceWFDie()[1][0], pa.getPlaceWFDie()[1][1]) == null){
                        ck4 = false;
                    }
                }
            }
        }

        //Afterward the verification of the future placement, this code portion verifies if parameters are legal.
        if(ck4) {
            if (pa.getPlaceNewWFDie()[0][0] >= 0 && pa.getPlaceNewWFDie()[0][1] >= 0) {
                ck5 = true;
                if (!(pa.getPlaceNewWFDie()[1][0] >= 0 && pa.getPlaceNewWFDie()[1][1] >= 0)) {
                    ck5 = false;
                }
            }
        }
        check2 = ck1 && ck2 && ck3 && ck4 && ck5;

        if(check1 && check2 && pa.getIdToolCard() > 0) {
            switch(pa.getIdToolCard()){
                case 1: {
                    if (new ToolCard1().validAction(model, model.getPlayers()[playerActions.indexOf(pa)].getWF(), pa)){
                        return true;
                    }
                    break;
                }
                case 2: {
                    if (new ToolCard2().validAction(model, model.getPlayers()[playerActions.indexOf(pa)].getWF(), pa)){
                        return true;
                    }
                    break;
                }
                case 3:{
                    if (new ToolCard3().validAction(model,model.getPlayers()[playerActions.indexOf(pa)].getWF(),pa)){
                        return true;
                    }
                    break;
                }
                case 4:{
                    if(new ToolCard4().validAction(model,model.getPlayers()[playerActions.indexOf(pa)].getWF(),pa)){
                        return true;
                    }
                    break;
                }
                case 5:{
                    if (new ToolCard5().validAction(model,model.getPlayers()[playerActions.indexOf(pa)].getWF(),pa)){
                        return true;
                    }
                    break;
                }
                case 6:{
                    if (new ToolCard6().validAction(model,model.getPlayers()[playerActions.indexOf(pa)].getWF(),pa)){
                        return true;
                    }
                    break;
                }
                case 7:{
                    if (new ToolCard7().validAction(model,model.getPlayers()[playerActions.indexOf(pa)].getWF(),pa)){
                        return true;
                    }
                    break;
                }
                case 8:{
                    if (new ToolCard8().validAction(model,model.getPlayers()[playerActions.indexOf(pa)].getWF(),pa)){
                        return true;
                    }
                    break;
                }
                case 9:{
                    if (new ToolCard9().validAction(model,model.getPlayers()[playerActions.indexOf(pa)].getWF(),pa)){
                        return true;
                    }
                    break;
                }
                case 10:{
                    if (new ToolCard10().validAction(model,model.getPlayers()[playerActions.indexOf(pa)].getWF(),pa)){
                        return true;
                    }
                    break;
                }
                case 11:{
                    if (new ToolCard11().validAction(model,model.getPlayers()[playerActions.indexOf(pa)].getWF(),pa)){
                        return true;
                    }
                    break;
                }
                case 12:{
                    if (new ToolCard12().validAction(model,model.getPlayers()[playerActions.indexOf(pa)].getWF(),pa)){
                        return true;
                    }
                    break;
                }
            }
            return false;
        }
        else {
            return false;
        }
    }

    private void performAction(PlayerAction pa) throws InvalidPlaceException, EmptyDiceBagException {
        switch (pa.getIdToolCard()){
            case 0:{
                break;
            }

            case 1:{
                new ToolCard1().performAction(model,model.getPlayers()[playerActions.indexOf(pa)].getWF(),pa);
                break;
            }

            case 2:{
                new ToolCard2().performAction(model,model.getPlayers()[playerActions.indexOf(pa)].getWF(),pa);
                break;
            }

            case 3:{
                new ToolCard3().performAction(model,model.getPlayers()[playerActions.indexOf(pa)].getWF(),pa);
                break;
            }

            case 4:{
                new ToolCard4().performAction(model,model.getPlayers()[playerActions.indexOf(pa)].getWF(),pa);
                break;
            }

            case 5:{
                new ToolCard5().performAction(model,model.getPlayers()[playerActions.indexOf(pa)].getWF(),pa);
                break;
            }

            case 6:{
                new ToolCard6().performAction(model,model.getPlayers()[playerActions.indexOf(pa)].getWF(),pa);
                break;
            }

            case 7:{
                new ToolCard7().performAction(model,model.getPlayers()[playerActions.indexOf(pa)].getWF(),pa);
                break;
            }

            case 8:{
                new ToolCard8().performAction(model,model.getPlayers()[playerActions.indexOf(pa)].getWF(),pa);
                break;
            }

            case 9:{
                new ToolCard9().performAction(model,model.getPlayers()[playerActions.indexOf(pa)].getWF(),pa);
                break;
            }

            case 10:{
                new ToolCard10().performAction(model,model.getPlayers()[playerActions.indexOf(pa)].getWF(),pa);
                break;
            }

            case 11:{
                new ToolCard11().performAction(model,model.getPlayers()[playerActions.indexOf(pa)].getWF(),pa);
                break;
            }

            case 12:{
                new ToolCard12().performAction(model,model.getPlayers()[playerActions.indexOf(pa)].getWF(),pa);
                break;
            }
        }
    }
}
