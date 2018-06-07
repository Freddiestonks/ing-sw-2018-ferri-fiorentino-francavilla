package it.polimi.se2018.controller;

import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.network.SocketReceiver;
import it.polimi.se2018.view.MainScreenInfo;
import it.polimi.se2018.view.View;
import it.polimi.se2018.model.LocalModel;

import java.util.Objects;

import static java.lang.System.in;
import static java.lang.System.out;

public class ClientController {
    //attributes
    private LocalModel model;
    private View view;
    private PlayerActionInterface playeraction;
    private MainScreenInfo mainScreenInfo;
    //Methods
    public ClientController(LocalModel model, View view){
    }
    private void parser(){
        //TODO ask if mainWindow is necessary
        String string = view.input();
        if(Objects.equals(string, "toolcard")){
            toolCardParser();
        }
        else if(string.equals("place")){
            view.updateDP(mainScreenInfo.getDraftPool());
            placementParser();
        }
        else{
            view.errorMessage("command does not exist");
            view.input();
        }
    }
    private void placementParser(){
        String read = view.input();
        if(read.equalsIgnoreCase("exit")){
            view.updateMainScreen(mainScreenInfo);
            parser();
        }
        int dpDie = Integer.parseInt(read);
        if(dpDie>=0&&dpDie<mainScreenInfo.getDraftPool().size()){
            //TODO GET DIE
        }
        else{
            view.errorMessage("command does not exist");
            placementParser();
        }

    }
    private void toolCardParser(){
        String string = view.input();
        PlayerAction pa = new PlayerAction();
        view.updateToolCards(model.getToolCards());

        switch (string) {
            case "1": {
                ToolCard toolCard = model.getToolCards().get(0);
                pa.setIdToolCard(toolCard.getIdRes());
                performToolCard(toolCard);
                break;
            }
            case "2": {
                ToolCard toolCard = model.getToolCards().get(1);
                pa.setIdToolCard(toolCard.getIdRes());
                performToolCard(toolCard);
                break;
            }
            case "3": {
                ToolCard toolCard = model.getToolCards().get(2);
                pa.setIdToolCard(toolCard.getIdRes());
                performToolCard(toolCard);
                break;
            }
            case "exit":
                updateMainScreen();
                view.updateMainScreen(mainScreenInfo);
                parser();
                break;
            default:
                view.errorMessage("command does not exist");
                toolCardParser();
                break;
        }
    }
    private void performToolCard(ToolCard toolCard){
        switch (toolCard.getIdRes()) {
            case 1:
                view.updateDP(mainScreenInfo.getDraftPool());
                String input = view.input();
                int intPut = Integer.parseInt(input);
                if(intPut>=0 && intPut<mainScreenInfo.getDraftPool().size()){
                    //TODO change die value in playeraction
                }
                else {
                    view.errorMessage("command not recognized");
                }
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
            default:
                break;
        }
    }
    private boolean validAction(PlayerAction pa){
        return true;
    }
    private void updateMainScreen(){
        //TODO update main screen from model
    }
    private void performAction(PlayerAction pa){

    }
}
