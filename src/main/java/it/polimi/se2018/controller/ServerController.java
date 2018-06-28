package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.toolcards.*;
import it.polimi.se2018.network.ClientGatherer;
import it.polimi.se2018.network.ClientInfo;
import it.polimi.se2018.utils.Timer;
import it.polimi.se2018.view.VirtualView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class ServerController extends AbstractController {
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
    public ServerController(Model model, VirtualView view) throws ResourceLoaderException {
        super(model);
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
                        if(!model.playerHasChosenPC(i)) {
                            System.out.println("NO PC: " + i);
                            PatternCard pc = model.getPatternCards()[2 * i];
                            Player player = model.getPlayer(i);
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
                        else if(!available
                           && (!model.getPlayer(i).isConnected() || model.getPlayer(i).isSwitchingConn())) {
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
            Player player = model.getPlayer(i);
            if((!model.checkConnection(i) || !view.checkConnection(i))
               && player.isConnected()
               && !player.isSwitchingConn()) {
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

    private void gameSetup() throws ResourceLoaderException {
        int numPlayer = model.getNumPlayers();
        PatternCard[] patternCards = new PatternCard[numPlayer * 2];
        PubObjCard[] pubObjCards = new PubObjCard[3];
        ToolCard[] toolCards = new ToolCard[3];
        int numPCs = resourceLoader.loadNumPCs();
        int numPubOcs = resourceLoader.loadNumPubOCs();
        int numToolCards = 12;
        ArrayList<Integer> pcIds = new ArrayList<>();
        ArrayList<Integer> pubOCIds = new ArrayList<>();
        ArrayList<Integer> toolCardIds = new ArrayList<>();
        for(int i = 0; i < numPCs; i++) {
            pcIds.add(i);
        }
        for(int i = 0; i < numPubOcs; i++) {
            pubOCIds.add(i);
        }
        for(int i = 0; i < numToolCards; i++) {
            toolCardIds.add(i);
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
        for(int i = 0; i < 3; i++) {
            int num = random.nextInt(toolCardIds.size());
            int id = toolCardIds.remove(num);
            //TODO: load tool card description from file
            switch(id) {
                case 0:
                    toolCards[i] = new ToolCard1("name", "description", 2);
                    break;
                case 1:
                    toolCards[i] = new ToolCard2("name", "description", 2);
                    break;
                case 2:
                    toolCards[i] = new ToolCard3("name", "description", 2);
                    break;
                case 3:
                    toolCards[i] = new ToolCard4("name", "description", 2);
                    break;
                case 4:
                    toolCards[i] = new ToolCard5("name", "description", 2);
                    break;
                case 5:
                    toolCards[i] = new ToolCard6("name", "description", 2);
                    break;
                case 6:
                    toolCards[i] = new ToolCard7("name", "description", 2);
                    break;
                case 7:
                    toolCards[i] = new ToolCard8("name", "description", 2);
                    break;
                case 8:
                    toolCards[i] = new ToolCard9("name", "description", 2);
                    break;
                case 9:
                    toolCards[i] = new ToolCard10("name", "description", 2);
                    break;
                case 10:
                    toolCards[i] = new ToolCard11("name", "description", 2);
                    break;
                case 11:
                    toolCards[i] = new ToolCard12("name", "description", 2);
                    break;
            }
        }
        model.setPatternCards(patternCards);
        model.setPubOCs(pubObjCards);
        model.setToolCards(toolCards);
        model.playersSetup();
    }

    protected int getPlayerIndex(PlayerAction pa) {
        return playerActions.indexOf(pa);
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
            Player player = model.getPlayer(playerIndex);
            player.setSwitchingConn(true);
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
        try {
            ServerController serverController = new ServerController(model, virtualView);
            model.addObserver(virtualView);
            serverController.gatherPlayers();
            serverController.gameSetup();
            serverController.listenPlayerActions();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ResourceLoaderException e) {
            System.out.println("ERROR LOADING RESOURCE FILES");
        }
    }
}
