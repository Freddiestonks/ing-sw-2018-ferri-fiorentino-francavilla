package it.polimi.se2018.controller;

import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.view.MainScreenInfo;
import it.polimi.se2018.view.View;
import it.polimi.se2018.model.LocalModel;

import java.util.Objects;

public class ClientController {
    //attributes
    private LocalModel model;
    private View view;
    private PlayerActionInterface playerActionInterface;
    private PlayerAction playerAction = new PlayerAction();
    private MainScreenInfo mainScreenInfo;
    private static final String PLACE = "place";
    private static final String WINDOW_FRAME = "windowframe";
    private static final String ROUNDTRACK = "roundtrack";
    private static final String DRAFTPOOL = "draftpool";
    private static final String SELECT = "select";
    private static final String TOOL_CARD = "toolcard";
    private static final String NEW_VALUE = "newvalue";
    private static final String HELP = "help";
    private static final String SEPARATOR = ", ?";
    private static final String PLACEMENT ="placement";
    private static final String MAINSCREENINFO = "board";
    //Methods
    public ClientController(LocalModel model, View view){
    }
    private void parser(){
        //TODO add methods to show
        String[] string = view.input().split(SEPARATOR);
        if(string[0].equalsIgnoreCase(TOOL_CARD)){
            toolCardParser(string);
        }
        else if(string[0].equalsIgnoreCase(PLACEMENT)){
            placementParser(string);
        }
        else if(string[0].equalsIgnoreCase(HELP)){
            view.help();
        }

        else if(string[0].equalsIgnoreCase(MAINSCREENINFO)){
            view.updateMainScreen(mainScreenInfo);
        }
        else{
            view.invalidMoveError();
            parser();
        }
    }
    private void placementParser(String[] read){

        for(int i = 1;i<read.length;i++) {
            if(read[i].equalsIgnoreCase(SELECT)){
                if(read[i+1].equalsIgnoreCase(DRAFTPOOL)){
                    int element = Integer.parseInt(read[i+2]);
                    playerAction.addPosDPDie(element);
                }
            }
            else if(read[i].equalsIgnoreCase(PLACE)){
                if(read[i+1].equalsIgnoreCase(DRAFTPOOL)){
                    int row = Integer.parseInt(read[i+2]);
                    int col = Integer.parseInt(read[i+3]);
                    playerAction.addPlaceDPDie(row,col);
                }
            }
            else {
                view.invalidMoveError();
                parser();
            }
        }

    }
    private void toolCardParser(String[] string){

        switch (string[1]) {
            case "1": {
                ToolCard toolCard = model.getToolCards().get(0);
                performToolCard(toolCard,string);
                break;
            }
            case "2": {
                ToolCard toolCard = model.getToolCards().get(1);
                performToolCard(toolCard,string);
                break;
            }
            case "3": {
                ToolCard toolCard = model.getToolCards().get(2);
                performToolCard(toolCard,string);
                break;
            }
            case "all":{
                view.updateToolCards(model.getToolCards());
                break;

            }
            default:
                view.invalidMoveError();
                parser();
                break;
        }
    }
    private void performToolCard(ToolCard toolCard,String[] read) {
        playerAction.setIdToolCard(toolCard.getIdRes());
        for(int i = 0;i<read.length;i++) {
            if (read[i].equalsIgnoreCase(NEW_VALUE)) {

                int value = Integer.parseInt(read[i+1]);
                playerAction.addNewDieValue(value);
            }
            else if(read[i].equalsIgnoreCase(SELECT)){
                if(read[i+1].equalsIgnoreCase(DRAFTPOOL)){
                    int element = Integer.parseInt(read[i+2]);
                    playerAction.addPosDPDie(element);
                    }
                else if(read[i+1].equalsIgnoreCase(ROUNDTRACK)){
                    int round = Integer.parseInt(read[i+2]);
                    int row = Integer.parseInt(read[i+3]);
                    playerAction.addPosRTDie(round,row);
                }
                else if(read[i+1].equalsIgnoreCase(WINDOW_FRAME)){
                    int row = Integer.parseInt(read[i+2]);
                    int col = Integer.parseInt(read[i+3]);
                    playerAction.addPlaceWFDie(row,col);
                }
            }
            else if(read[i].equalsIgnoreCase(PLACE)) {
                if(read[i+1].equalsIgnoreCase(DRAFTPOOL)){
                    int row = Integer.parseInt(read[i+2]);
                    int col = Integer.parseInt(read[i+3]);
                    playerAction.addPlaceDPDie(row,col);
                }
                else if(read[i+1].equalsIgnoreCase(WINDOW_FRAME)){
                    int row = Integer.parseInt(read[i+2]);
                    int col = Integer.parseInt(read[i+3]);
                    playerAction.addPlaceWFDie(row,col);
                }
            }
            else {
                view.invalidMoveError();
                parser();
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
