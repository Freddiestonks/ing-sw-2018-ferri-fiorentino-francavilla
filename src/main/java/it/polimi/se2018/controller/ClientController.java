package it.polimi.se2018.controller;

import it.polimi.se2018.model.LocalModel;
import it.polimi.se2018.network.NetworkHandler;
import it.polimi.se2018.network.RMINetworkHandler;
import it.polimi.se2018.network.SocketNetworkHandler;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.CLIView;
import it.polimi.se2018.view.GUIView;
import it.polimi.se2018.view.MainScreenInfo;
import it.polimi.se2018.view.View;

import java.io.IOException;
import java.util.Scanner;

import static java.lang.System.out;

public class ClientController extends AbstractController implements Observer {
    //attributes
    private LocalModel model;
    private View view;
    private PlayerActionInterface playerActionInterface;
    //private PlayerAction playerAction = new PlayerAction();
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
    public ClientController(LocalModel localModel, View view) {
        super(localModel);
        this.model = localModel;
        this.view = view;
    }

    private void parser(String str) {
        PlayerAction playerAction = new PlayerAction();
        String[] string = str.split(SEPARATOR);
        if(string[0].equalsIgnoreCase(TOOL_CARD)){
            toolCardParser(playerAction, string);
        }
        else if(string[0].equalsIgnoreCase(PLACEMENT)){
            placementParser(playerAction, string);
        }
        else if(string[0].equalsIgnoreCase(HELP)){
            view.help();
        }
        else if(string[0].equalsIgnoreCase("connect")){
            //TODO: check whether user is already connect
            if(string[1].equalsIgnoreCase("socket")){
                networkHandler = new SocketNetworkHandler(string[2]);
                playerActionInterface = networkHandler.connect(model,view);
                out.println("ok");
            }
            else if(string[1].equalsIgnoreCase("rmi")){
                networkHandler = new RMINetworkHandler(string[2]);
                playerActionInterface = networkHandler.connect(model,view);
                out.println("ok");
            }
        }
        else if(string[0].equalsIgnoreCase("set")){
            if(string[1].equalsIgnoreCase("username")){
                playerAction.setUsernameReq(string[2]);
                out.println(string[2]);
                performAction(playerAction);
            }
            else if(string[1].equalsIgnoreCase("pc")){
                try {
                    playerAction.setPatternCard(Integer.parseInt(string[2]));
                } catch (NumberFormatException nfe){
                    view.invalidMoveError();
                }
                performAction(playerAction);
            }
        }
        else if(string[0].equalsIgnoreCase(MAIN_SCREEN_INFO)){
            view.showMainScreen();
        }
        else {
            view.invalidMoveError();
        }
    }

    private void placementParser(PlayerAction playerAction, String[] read){
        int i = 1;
        try {
            while (i < read.length) {
                if (read[i].equalsIgnoreCase(SELECT)) {
                    int element = Integer.parseInt(read[i + 1]);
                    playerAction.addPosDPDie(element);
                    i += 2;
                } else if (read[i].equalsIgnoreCase(PLACE)) {
                    int row = Integer.parseInt(read[i + 1]);
                    int col = Integer.parseInt(read[i + 2]);
                    playerAction.addPlaceDPDie(row, col);
                    i += 3;
                } else {
                    view.invalidMoveError();
                    return;
                }
            }
            performAction(playerAction);
        } catch (NumberFormatException nfe){
            view.invalidMoveError();
        }

    }

    private void toolCardParser(PlayerAction playerAction, String[] string){
        switch (string[1]) {
            case "1":
            case "2":
            case "3":
                //ToolCard toolCard = model.getToolCards()[2];
                performToolCard(playerAction, string);
                break;
            case "show":
                view.updateToolCards(model.getToolCards());
                break;
            default:
                view.invalidMoveError();
                break;
        }
    }

    private void performToolCard(PlayerAction playerAction, String[] read) {
        try {
            playerAction.setIdToolCard(Integer.parseInt(read[2]));
            int i = 2;
            while(i < read.length) {
                if (read[i].equalsIgnoreCase(NEW_VALUE)) {
                    int value = Integer.parseInt(read[i + 1]);
                    playerAction.addNewDieValue(value);
                    i += 2;
                }
                else if (read[i].equalsIgnoreCase(SELECT)) {
                    if (read[i + 1].equalsIgnoreCase(DRAFT_POOL)) {
                        int element = Integer.parseInt(read[i + 2]);
                        playerAction.addPosDPDie(element);
                        i += 3;
                    }
                    else if (read[i + 1].equalsIgnoreCase(ROUND_TRACK)) {
                        int round = Integer.parseInt(read[i + 2]);
                        int row = Integer.parseInt(read[i + 3]);
                        playerAction.addPosRTDie(round, row);
                        i += 4;
                    }
                    else if (read[i + 1].equalsIgnoreCase(WINDOW_FRAME)) {
                        int row = Integer.parseInt(read[i + 2]);
                        int col = Integer.parseInt(read[i + 3]);
                        playerAction.addPlaceWFDie(row, col);
                        i += 4;
                    }
                }
                else if (read[i].equalsIgnoreCase(PLACE)) {
                    if (read[i + 1].equalsIgnoreCase(DRAFT_POOL)) {
                        int row = Integer.parseInt(read[i + 2]);
                        int col = Integer.parseInt(read[i + 3]);
                        playerAction.addPlaceDPDie(row, col);
                        i += 4;
                    }
                    else if (read[i + 1].equalsIgnoreCase(WINDOW_FRAME)) {
                        int row = Integer.parseInt(read[i + 2]);
                        int col = Integer.parseInt(read[i + 3]);
                        playerAction.addPlaceNewWFDie(row, col);
                        i += 4;
                    }
                } else {
                    view.invalidMoveError();
                    return;
                }
            }
            performAction(playerAction);
        } catch (NumberFormatException nfe){
            view.invalidMoveError();
        }
    }

    protected int getPlayerIndex(PlayerAction pa) {
        return model.getPlayerIndex();
    }

    /*private void updateMainScreen(){
        mainScreenInfo.setRoundTrack(model.getRoundTrack());
        mainScreenInfo.setRound(model.getRound());
        mainScreenInfo.setDraftPool(model.getDraftPool());
        mainScreenInfo.setBackward(model.isBackward());
    }*/

    private void performAction(PlayerAction playerAction) {
        if(validAction(playerAction)) {
            try {
                playerActionInterface.setPlayerAction(playerAction);
            } catch (IOException e) {
                view.connectionError();
            }
        }
        else {
            view.invalidMoveError();
        }
    }

    /*public void setView(View view) {
        this.view = view;
    }*/

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
