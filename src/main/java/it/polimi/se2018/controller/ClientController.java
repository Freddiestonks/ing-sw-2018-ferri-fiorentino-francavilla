package it.polimi.se2018.controller;

import it.polimi.se2018.model.LocalModel;
import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.network.NetworkHandler;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.CLIView;
import it.polimi.se2018.view.GUIView;
import it.polimi.se2018.view.MainScreenInfo;
import it.polimi.se2018.view.View;

import java.util.Scanner;

import static java.lang.System.out;

public class ClientController implements Observer {
    //attributes
    private LocalModel model;
    private View view;
    private PlayerActionInterface playerActionInterface;
    private PlayerAction playerAction = new PlayerAction();
    private MainScreenInfo mainScreenInfo;
    private NetworkHandler networkHandler;
    private static final String PLACE = "place";
    private static final String WINDOW_FRAME = "windowframe";
    private static final String ROUND_TRACK = "roundtrack";
    private static final String DRAFT_POOL = "draftpool";
    private static final String SELECT = "select";
    private static final String TOOL_CARD = "toolcard";
    private static final String NEW_VALUE = "newvalue";
    private static final String HELP = "help";
    private static final String SEPARATOR = ", ?";
    private static final String PLACEMENT ="placement";
    private static final String MAIN_SCREEN_INFO = "board";
    //Methods
    public ClientController(LocalModel localModel, View view){
        this.model = localModel;
        this.view = view;
    }
    private void parser(String str){
        String[] string = str.split(SEPARATOR);
        if(string[0].equalsIgnoreCase(TOOL_CARD)){
            toolCardParser(string);
        }
        else if(string[0].equalsIgnoreCase(PLACEMENT)){
            placementParser(string);
        }
        else if(string[0].equalsIgnoreCase(HELP)){
            view.help();
        }

        else if(string[0].equalsIgnoreCase(MAIN_SCREEN_INFO)){
            view.updateMainScreen(mainScreenInfo);
        }
        else{
            view.invalidMoveError();
        }
    }
    private void placementParser(String[] read){
        int i = 1;
        try {
            while (i < read.length) {
                if (read[i].equalsIgnoreCase(SELECT)) {
                    if (read[i + 1].equalsIgnoreCase(DRAFT_POOL)) {
                        int element = Integer.parseInt(read[i + 2]);
                        playerAction.addPosDPDie(element);
                        i=i+3;
                    }
                } else if (read[i].equalsIgnoreCase(PLACE)) {
                    if (read[i + 1].equalsIgnoreCase(DRAFT_POOL)) {
                        int row = Integer.parseInt(read[i + 2]);
                        int col = Integer.parseInt(read[i + 3]);
                        playerAction.addPlaceDPDie(row, col);
                        i=i+4;
                    }
                } else {
                    view.invalidMoveError();
                }
            }
        }catch (NumberFormatException nfe){
            view.invalidMoveError();
        }

    }
    private void toolCardParser(String[] string){

        switch (string[1]) {
            case "1": {
                ToolCard toolCard = model.getToolCards()[0];
                performToolCard(toolCard,string);
                break;
            }
            case "2": {
                ToolCard toolCard = model.getToolCards()[1];
                performToolCard(toolCard,string);
                break;
            }
            case "3": {
                ToolCard toolCard = model.getToolCards()[2];
                performToolCard(toolCard,string);
                break;
            }
            case "all":{
                view.updateToolCards(model.getToolCards());
                break;

            }
            default:
                view.invalidMoveError();
                break;
        }
    }
    private void performToolCard(ToolCard toolCard,String[] read) {
        try {
            playerAction.setIdToolCard(toolCard.getIdRes());
            int i = 2;
            while(i<read.length){
                if (read[i].equalsIgnoreCase(NEW_VALUE)) {
                    int value = Integer.parseInt(read[i + 1]);
                    playerAction.addNewDieValue(value);
                    i=i+2;
                }
                else if (read[i].equalsIgnoreCase(SELECT)) {
                    if (read[i + 1].equalsIgnoreCase(DRAFT_POOL)) {
                        int element = Integer.parseInt(read[i + 2]);
                        playerAction.addPosDPDie(element);
                        i = i+3;
                    }
                    else if (read[i + 1].equalsIgnoreCase(ROUND_TRACK)) {
                        int round = Integer.parseInt(read[i + 2]);
                        int row = Integer.parseInt(read[i + 3]);
                        playerAction.addPosRTDie(round, row);
                        i = i+4;
                    }
                    else if (read[i + 1].equalsIgnoreCase(WINDOW_FRAME)) {
                        int row = Integer.parseInt(read[i + 2]);
                        int col = Integer.parseInt(read[i + 3]);
                        playerAction.addPlaceWFDie(row, col);
                        i = i+4;
                    }
                }
                else if (read[i].equalsIgnoreCase(PLACE)) {
                    if (read[i + 1].equalsIgnoreCase(DRAFT_POOL)) {
                        int row = Integer.parseInt(read[i + 2]);
                        int col = Integer.parseInt(read[i + 3]);
                        playerAction.addPlaceDPDie(row, col);
                        i=i+4;
                    }
                    else if (read[i + 1].equalsIgnoreCase(WINDOW_FRAME)) {
                        int row = Integer.parseInt(read[i + 2]);
                        int col = Integer.parseInt(read[i + 3]);
                        playerAction.addPlaceNewWFDie(row, col);
                        i=i+4;
                    }
                } else {
                    view.invalidMoveError();
                }
            }
        }catch (NumberFormatException nfe){
            view.invalidMoveError();
        }
    }

    private boolean validAction(PlayerAction pa){
        return true;
    }

    private void updateMainScreen(){
        mainScreenInfo.setRoundTrack(model.getRoundTrack());
        mainScreenInfo.setRound(model.getRound());
        mainScreenInfo.setDraftPool(model.getDraftPool());
        mainScreenInfo.setBackward(model.isBackward());
    }
    private void performAction(PlayerAction pa){
    }

    public void setView(View view) {
        this.view = view;
    }

    public void update() {
        String userInput = view.getUserInput();
        parser(userInput);
    }

    public static void main(String[] args) {
        View view = null;
        Scanner user_input = new Scanner(System.in);
        boolean correct = false;
        out.println("Welcome to Sagrada\nHow would you like to play? (CLI/GUI)");
        while (!correct){
            String input = user_input.next().toLowerCase();
            switch (input) {
                case "cli":
                    view = new CLIView();
                    correct = true;
                    break;
                case "gui":
                    view = new GUIView();
                    correct = true;
                    break;
                default:
                    out.println("Please insert a valid command (GUI or CLI)");
                    break;
            }
        }
        LocalModel localModel = new LocalModel();
        ClientController clientController = new ClientController(localModel, view);
        view.addObserver(clientController);
        view.startView();
    }
}
