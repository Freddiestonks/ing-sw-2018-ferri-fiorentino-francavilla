package it.polimi.se2018.controller;

import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.view.MainScreenInfo;
import it.polimi.se2018.view.View;
import it.polimi.se2018.model.LocalModel;

import java.util.ArrayList;
import java.util.Objects;

public class ClientController {
    //attributes
    private LocalModel model;
    private View view;
    private PlayerActionInterface playerActionInterface;
    private PlayerAction playerAction = new PlayerAction();
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
            view.invalidMoveError();
            parser();
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
            //TODO Select form draftpool
            //TODO Place in WF

        }
        else{
            view.invalidMoveError();
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
                view.invalidMoveError();
                toolCardParser();
                break;
        }
    }
    private void performToolCard(ToolCard toolCard) {
        view.updateMainScreen(mainScreenInfo);
        String read = view.input();
        if(read.equalsIgnoreCase("newdievalue")){
            boolean correct = false;
            while (!correct) {
                read = view.input();
                if (read.equalsIgnoreCase("exit")) {
                    correct = true;
                    performToolCard(toolCard);
                }
                else {
                    int value = Integer.parseInt(read);
                        correct =true;
                        playerAction.addNewDieValue(value);
                }
            }
        }
        else if(read.equalsIgnoreCase("posdpdie")){
            boolean correct = false;
            while (!correct) {
                read = view.input();
                if (read.equalsIgnoreCase("exit")) {
                    correct = true;
                    performToolCard(toolCard);
                }
                else {
                    int value = Integer.parseInt(read);
                        correct=true;
                        playerAction.addPosDPDie(value);

                }
            }
        }
        else if(read.equalsIgnoreCase("posrtdie")){
            boolean correct = false;
            while (!correct) {
                read = view.input();
                if (read.equalsIgnoreCase("exit")) {
                    correct = true;
                    performToolCard(toolCard);
                }
                else {
                    int round = Integer.parseInt(read);
                    read = view.input();
                    if (read.equalsIgnoreCase("exit")) {
                       correct = true;
                            performToolCard(toolCard);
                    }
                    else {
                        int die = Integer.parseInt(read);
                        playerAction.addPosRTDie(round,die);
                    }
                }
            }
        }
        else if(read.equalsIgnoreCase("placedpdie")){
            boolean correct = false;
            while (!correct) {
                read = view.input();
                if (read.equalsIgnoreCase("exit")) {
                    correct = true;
                    performToolCard(toolCard);
                }
                else {
                    int row = Integer.parseInt(read);
                        read = view.input();
                        if (read.equalsIgnoreCase("exit")) {
                            correct = true;
                            performToolCard(toolCard);
                        }
                        else {
                            int col = Integer.parseInt(read);
                            playerAction.addPlaceDPDie(row,col);
                        }

                }
            }
        }
        else if(read.equalsIgnoreCase("placewfdie")){ //TODO RENAME THIS
            boolean correct = false;
            while (!correct) {
                read = view.input();
                if (read.equalsIgnoreCase("exit")) {
                    correct = true;
                    performToolCard(toolCard);
                }
                else {
                    int row = Integer.parseInt(read);
                    read = view.input();
                    if (read.equalsIgnoreCase("exit")) {
                        correct = true;
                        performToolCard(toolCard);
                    }
                    else {
                        int col = Integer.parseInt(read);
                        playerAction.addPlaceWFDie(row,col);
                    }

                }
            }
        }
        else if(read.equalsIgnoreCase("placenewwfdie")){
            boolean correct = false;
            while (!correct) {
                read = view.input();
                if (read.equalsIgnoreCase("exit")) {
                    correct = true;
                    performToolCard(toolCard);
                }
                else {
                    int row = Integer.parseInt(read);
                    read = view.input();
                    if (read.equalsIgnoreCase("exit")) {
                        correct = true;
                        performToolCard(toolCard);
                    }
                    else {
                        int col = Integer.parseInt(read);
                        playerAction.addPlaceNewWFDie(row,col);
                    }

                }
            }
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

    public void setView(View view) {
        this.view = view;
    }
}
