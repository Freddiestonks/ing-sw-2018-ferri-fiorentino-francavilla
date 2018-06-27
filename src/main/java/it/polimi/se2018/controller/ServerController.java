package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.network.ClientGatherer;
import it.polimi.se2018.network.ClientInfo;
import it.polimi.se2018.utils.Timer;
import it.polimi.se2018.view.VirtualView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class ServerController {
    //Attributes
    public static final Object LOCK = new Object();
    private Model model;
    private VirtualView view;
    private ClientGatherer clientGatherer;
    private ArrayList<PlayerAction> playerActions = new ArrayList<>();
    //private int numClients = 0;
    private int lobbyTimeout;
    private int turnTimeout;
    private int readyPlayers = 0;
    private ResourceLoader resourceLoader = new ResourceLoader();

    //Methods
    public ServerController(Model model, VirtualView view){
        this.model = model;
        this.view = view;
        clientGatherer = new ClientGatherer();
        lobbyTimeout = resourceLoader.loadLobbyTimeout();
        turnTimeout = resourceLoader.loadTurnTimeout();
    }

    private void listenPlayerActions() throws InterruptedException {
        Timer turnTimer = new Timer(turnTimeout, LOCK);
        turnTimer.start();
        while(!model.isLobbyGathering()) {
            while(!updatedPlayerActions()
               && emptyPreLobby()
               && !turnTimer.isTimeout()) {
                synchronized (LOCK) {
                    LOCK.wait();
                }
            }
            if(turnTimer.isTimeout()) {
                if(model.isStarted()) {
                    model.updateTurn();
                }
                else {
                    for(int i = 0; i < model.getNumPlayers(); i++) {
                        Player player = model.getPlayer(i);
                        if(!player.hasChosenPC()) {
                            System.out.println("NO PC: " + i);
                            PatternCard pc = model.getPatternCards()[2 * i];
                            player.setWinFrame(new WindowFrame(pc, true));
                        }
                    }
                    model.startMatch();
                }
                turnTimer = new Timer(turnTimeout, LOCK);
                turnTimer.start();
            }
            for(PlayerAction pa : playerActions) {
                if(pa.isUpdated()) {
                    if(validAction(pa)) {
                        performAction(pa);
                        if((playerActions.indexOf(pa) + 1 == model.getTurn())
                           && !pa.isSwitchConnReq()) {
                            turnTimer = new Timer(turnTimeout, LOCK);
                            turnTimer.start();
                        }
                    }
                    pa.clear();
                }
            }
            gatherPlayers();
        }
    }

    private boolean updatedPlayerActions() {
        for(PlayerAction pa : playerActions) {
            if(pa.isUpdated()) {
                return true;
            }
        }
        return false;
    }

    private void gatherPlayers() throws InterruptedException {
        synchronized (LOCK) {
            Timer lobbyTimer = new Timer(lobbyTimeout, LOCK);
            do {
                System.out.println("1 WAIT");
                while(model.isLobbyGathering()
                        && emptyPreLobby()
                        && !lobbyTimer.isTimeout()) {
                    LOCK.wait();
                }
                System.out.println("2 WAIT");
                // check if other players are still connected by clientinfo
                System.out.println("check +");
                checkConnections();
                System.out.println("check -");
                if(lobbyTimer.isTimeout()) {
                    if(model.getNumPlayers() >= 2) {
                        model.setLobbyGathering(false);
                    }
                    else {
                        lobbyTimer = new Timer(lobbyTimeout, LOCK);
                    }
                }
                // read prelobby clients
                Iterator<ClientInfo> iterator = clientGatherer.iterator();
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
                        if(model.isLobbyGathering() && available) {
                            //add client to lobby as players
                            System.out.println("PLAYER");
                            view.addClient(clientInfo.getView());
                            playerActions.add(pa);
                            //clientGatherer.remove(clientInfo);
                            model.addPlayer(username, clientInfo.getLocalModel());
                            System.out.println(username + "joined the game");
                            iterator.remove();
                            //if there are exactly 2 players start the lobbyTimer
                            if(model.getNumPlayers() == 2) {
                                lobbyTimer.start();
                            }
                            else if(model.getNumPlayers() == 4) {
                                model.setLobbyGathering(false);
                            }
                        }
                        else if(!available && !model.getPlayer(i).isConnected()) {
                            System.out.println("REINSERTED");
                            model.reinsertPlayer(i, clientInfo.getLocalModel());
                            view.reinsertClient(i, clientInfo.getView());
                            playerActions.set(i, pa);
                            iterator.remove();
                        }
                        System.out.println("+");
                        pa.clear();
                        System.out.println("-");
                    }
                }
            } while(model.isLobbyGathering());
        }
        System.out.println("END LOBBY");
    }

    private void checkConnections() {
        int i = 0;
        while(i < model.getNumPlayers()) {
            if(!model.checkConnection(i) || !view.checkConnection(i)) {
                //take into account that the player has quitted
                if(model.isLobbyGathering()) {
                    model.removeClient(i);
                    view.removeClient(i);
                    playerActions.remove(i);
                }
                else {
                    model.removePlayer(i);
                    i++;
                }
            }
            else {
                i++;
            }
        }
    }

    private boolean emptyPreLobby() {
        Iterator<ClientInfo> iterator = clientGatherer.iterator();
        while(iterator.hasNext()) {
            if(iterator.next().getPlayerAction().isUpdated()) {
                return false;
            }
        }
        return true;
    }

    private void gameSetup() {
        int numPlayer = model.getNumPlayers();
        PatternCard[] patternCards = new PatternCard[numPlayer * 2];
        PubObjCard[] pubObjCards = new PubObjCard[3];
        int numPCs = resourceLoader.loadNumPCs();
        int numPubOcs = resourceLoader.loadNumPubOCs();
        ArrayList<Integer> pcIds = new ArrayList<>();
        ArrayList<Integer> pubOCIds = new ArrayList<>();
        for(int i = 0; i < numPCs; i++) {
            pcIds.add(i);
        }
        for(int i = 0; i < numPubOcs; i++) {
            pubOCIds.add(i);
        }
        Random random = new Random();
        for(int i = 0; i < numPlayer * 2; i++) {
            int num = random.nextInt(pcIds.size());
            int id = pcIds.remove(num);
            System.out.println("PC:"+ id);
            patternCards[i] = resourceLoader.loadPC(id);
        }
        for(int i = 0; i < 3; i++) {
            int num = random.nextInt(pubOCIds.size());
            int id = pubOCIds.remove(num);
            pubObjCards[i] = resourceLoader.loadPubOC(id);
        }
        model.setPatternCards(patternCards);
        model.setPubOCs(pubObjCards);
        model.playersSetup();
    }

    private boolean rangeCheck(int[] array){
        if(array.length == 2){
            return (array[0] >= 0 && array[0] <= 3 && array[1] >= 0 && array[1] <= 4);
        }
        else return false;
    }

    private boolean emptyWFPlaceCheck(PlayerAction pa, int[] array){
        if(array.length == 2){
            return model.getPlayer(playerActions.indexOf(pa)).getWinFrameDie(array[0], array[1]) == null;
        }
        else return false;
    }

    private boolean validAction(PlayerAction pa) {
        int playerIndex = playerActions.indexOf(pa);

        if(!model.isStarted()) {
            Player player = model.getPlayer(playerIndex);
            return !player.hasChosenPC();
        }

        if(playerIndex + 1 != model.getTurn()) {
            return false;
        }

        if (!(pa.getIdToolCard()>=0 && pa.getIdToolCard()<=12)){
            return false;
        }

        for(int dieFace : pa.getNewDieValue()){
            if(!(dieFace >= 1 && dieFace <= 6)){
                return false;
            }
        }

        //Checking for the position in the Draft Pool
        for(int value : pa.getPosDPDie()){
            if(!(value >= 0 && value < model.getDraftPoolSize())){
                return false;
            }
        }

        for(int[] posRT : pa.getPosRTDie()){
            if(!(posRT[0] >=0
               && posRT[0] < model.getRound()
               && posRT[1] >=0
               && posRT[1] < model.getRoundTrackSize(posRT[0]))){
                return false;
            }
        }

        //verify if the places in the WF are empty.
        for(int[] array: pa.getPlaceDPDie()){
            if(!rangeCheck(array)){
                return false;
            }
        }

        //control if a designed position to be deleted is full.
        for(int[] array: pa.getPlaceWFDie()){
            if(!rangeCheck(array)){
                return false;
            }
        }

        //Afterward the verification of the future placement, this code portion verifies if parameters are legal.
        for(int[] array: pa.getPlaceNewWFDie()){
            if(!rangeCheck(array)){
                return false;
            }
        }

        if(pa.getIdToolCard() > 3) {
            return false;
        }
        if(model.isStarted()) {
            WindowFrame wf = model.getPlayer(playerActions.indexOf(pa)).getWindowFrame();
            if((pa.getIdToolCard() > 0) && !model.isToolCardUsed()) {
                // turn using tool card
                ToolCard toolCard = model.getToolCard(pa.getIdToolCard());
                return toolCard.validAction(model, wf, pa);
            }
            else if(ToolCard.isPendingAction()) {
                ToolCard toolCard = ToolCard.getPendingToolCard();
                return toolCard.validAction(model, wf, pa);
            }
            else if(!pa.getPosDPDie().isEmpty() && !pa.getPlaceDPDie().isEmpty()) {
                // regular turn
                if(pa.getPosDPDie().get(0) >= 0){
                    Die die = model.getDraftPoolDie(pa.getPosDPDie().get(0));
                    int[] wfPlace = pa.getPlaceDPDie().get(0);
                    return (rangeCheck(pa.getPlaceDPDie().get(0))
                            && wf.checkRestrictions(die, wfPlace[0], wfPlace[1]));
                }
                else return false;
            }
            else {
                return false;
            }
        }
        return true;
    }

    private void performAction(PlayerAction pa) {
        if(!model.isLobbyGathering() && !model.isStarted()) {
            Player player = model.getPlayer(playerActions.indexOf(pa));
            int index = pa.getPatternCard() / 2 + 2 * playerActions.indexOf(pa);
            boolean wcFace = (pa.getPatternCard() % 2 == 0);
            PatternCard pc = model.getPatternCards()[index];
            player.setWinFrame(new WindowFrame(pc, wcFace));
            readyPlayers++;
            if(readyPlayers == model.getNumPlayers()) {
                model.startMatch();
            }
        }
        else if(pa.isSwitchConnReq()) {
            int playerIndex = playerActions.indexOf(pa);
            model.removePlayer(playerIndex);
        }
        else if(model.isStarted()) {
            if((pa.getIdToolCard() > 0)
               || ToolCard.isPendingAction()) {
                int playerIndex = playerActions.indexOf(pa);
                model.performToolCard(playerIndex, pa);
            }
            else {
                // regular turn without choosing a tool card
                Die die = model.removeDraftPoolDie(pa.getPosDPDie().get(0));
                int[] place = pa.getPlaceDPDie().get(0);
                int playerIndex = playerActions.indexOf(pa);
                model.placeWFDie(playerIndex, die, place[0], place[1]);
            }
        }
    }

    public static void main(String[] args) {
        Model model = Model.instance();
        VirtualView virtualView = new VirtualView();
        ServerController serverController = new ServerController(model, virtualView);
        model.addObserver(virtualView);
        try {
            serverController.gatherPlayers();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        serverController.gameSetup();
        try {
            serverController.listenPlayerActions();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
