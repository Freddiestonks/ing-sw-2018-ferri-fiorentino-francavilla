package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.network.ClientGatherer;
import it.polimi.se2018.network.ClientInfo;
import it.polimi.se2018.utils.Timer;
import it.polimi.se2018.view.VirtualView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Logger;

public class ServerController extends AbstractController {
    //Attributes
    private final Object lock = new Object();
    private final ResourceLoader resourceLoader = new ResourceLoader();
    private Model model;
    private VirtualView view;
    private ClientGatherer clientGatherer;
    private ArrayList<PlayerAction> playerActions = new ArrayList<>();
    //private int numClients = 0;
    private int lobbyTimeout;
    private int turnTimeout;
    private int readyPlayers = 0;

    public static final Logger LOGGER = Logger.getLogger(ServerController.class.getName());

    //Methods
    public ServerController(Model model, VirtualView view) {
        super(model);
        this.model = model;
        this.view = view;
        clientGatherer = new ClientGatherer(this.lock);
    }

    private void waitForPlayerActions() throws InterruptedException {
        Timer turnTimer = new Timer(turnTimeout, lock);
        turnTimer.start();
        while(!model.isOver()) {
            LOGGER.fine("match begin WAIT");
            while(!updatedPlayerActions()
               && emptyPreLobby()
               && !turnTimer.isTimeout()) {
                synchronized (lock) {
                    lock.wait();
                }
            }
            LOGGER.fine("match end WAIT");
            if(turnTimer.isTimeout()) {
                LOGGER.fine("TIMEOUT");
                if(model.isStarted()) {
                    model.updateTurn();
                }
                else {
                    for(int i = 0; i < model.getNumPlayers(); i++) {
                        if(!model.playerHasChosenPC(i)) {
                            LOGGER.fine("Not selected PC: " + i);
                            PatternCard pc = model.getPatternCards()[2 * i];
                            Player player = model.getPlayer(i);
                            player.setWinFrame(new WindowFrame(pc, true));
                        }
                    }
                    model.startMatch();
                }
                turnTimer = new Timer(turnTimeout, lock);
                turnTimer.start();
            }
            for(PlayerAction pa : playerActions) {
                if(pa.isUpdated()) {
                    if(validAction(pa)) {
                        performAction(pa);
                        if((playerActions.indexOf(pa) + 1 == model.getTurn())
                           && !pa.isSwitchConnReq()) {
                            turnTimer = new Timer(turnTimeout, lock);
                            turnTimer.start();
                        }
                    }
                    pa.clear();
                }
            }
            gatherPlayers();
        }
        LOGGER.fine("match ended");
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
        LOGGER.fine("begin gathering");
        if(model.isLobbyGathering()) {
            lobbyTimeout = resourceLoader.loadLobbyTimeout();
            turnTimeout = resourceLoader.loadTurnTimeout();
        }
        synchronized (lock) {
            Timer lobbyTimer = new Timer(lobbyTimeout, lock);
            do {
                LOGGER.fine("lobby begin WAIT");
                while(model.isLobbyGathering()
                        && emptyPreLobby()
                        && !lobbyTimer.isTimeout()) {
                    lock.wait();
                }
                LOGGER.fine("lobby end WAIT");
                // check if other players are still connected by clientinfo
                LOGGER.fine("begin check");
                checkConnections();
                LOGGER.fine("end check");
                if(lobbyTimer.isTimeout()) {
                    if(model.getNumPlayers() >= 2) {
                        model.setLobbyGathering(false);
                    }
                    else {
                        lobbyTimer = new Timer(lobbyTimeout, lock);
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
                            LOGGER.fine("PLAYER");
                            view.addClient(clientInfo.getView());
                            playerActions.add(pa);
                            //clientGatherer.remove(clientInfo);
                            model.addPlayer(username, clientInfo.getLocalModel());
                            LOGGER.fine(username + " joined the game");
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
                            LOGGER.fine(model.getPlayer(i).getUsername() + " REINSERTED");
                            view.reinsertClient(i, clientInfo.getView());
                            playerActions.set(i, pa);
                            model.reinsertPlayer(i, clientInfo.getLocalModel());
                            iterator.remove();
                        }
                        else {
                            try {
                                clientInfo.getView().enteringError(model.isLobbyGathering());
                            } catch (IOException e) {
                                //e.printStackTrace();
                            }
                        }
                        //LOGGER.fine("+");
                        pa.clear();
                        //LOGGER.fine("-");
                    }
                }
            } while(model.isLobbyGathering());
        }
        LOGGER.fine("end gathering");
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
                LOGGER.fine(player.getUsername() + " disconnected");
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
        int numToolCards = resourceLoader.loadNumToolCards();
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
            LOGGER.fine("pattern card id:" + id);
            patternCards[i] = resourceLoader.loadPC(id);
        }
        for(int i = 0; i < 3; i++) {
            int num = random.nextInt(pubOCIds.size());
            int id = pubOCIds.remove(num);
            LOGGER.fine("pubOC id:" + id);
            pubObjCards[i] = resourceLoader.loadPubOC(id);
        }
        for(int i = 0; i < 3; i++) {
            int num = random.nextInt(toolCardIds.size());
            int id = toolCardIds.remove(num);
            LOGGER.fine("tool card id:" + id);
            toolCards[i] = resourceLoader.loadToolCard(id);
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
        int playerIndex = playerActions.indexOf(pa);
        if(!model.isLobbyGathering() && !model.isStarted()) {
            int index = pa.getPatternCard() / 2 + 2 * playerIndex;
            boolean wcFace = (pa.getPatternCard() % 2 == 0);
            PatternCard pc = model.getPatternCards()[index];
            model.setWindowFrame(playerIndex, new WindowFrame(pc, wcFace));
            readyPlayers++;
            if(readyPlayers == model.getNumPlayers()) {
                model.startMatch();
            }
        }
        else if(pa.isSwitchConnReq()) {
            Player player = model.getPlayer(playerIndex);
            player.setSwitchingConn(true);
        }
        else if(model.isStarted()) {
            if(pa.isSkipTurn()) {
                model.updateTurn();
            }
            else if((pa.getIdToolCard() > 0)
               || ToolCard.isPendingAction()) {
                model.performToolCard(playerIndex, pa);
            }
            else {
                // regular turn without choosing a tool card
                Die die = model.removeDraftPoolDie(pa.getPosDPDie().get(0));
                int[] place = pa.getPlaceDPDie().get(0);
                model.placeWFDie(playerIndex, die, place[0], place[1]);
            }
        }
    }

    private void reset() {
        LOGGER.fine("begin reset");
        model.reset();
        view.reset();
        playerActions.clear();
        LOGGER.fine("end reset");
    }

    public static void main(String[] args) {
        Model model = Model.instance();
        VirtualView virtualView = new VirtualView();
        ServerController serverController = new ServerController(model, virtualView);
        model.addObserver(virtualView);
        boolean loop = true;
        try {
            while(loop) {
                serverController.gatherPlayers();
                serverController.gameSetup();
                serverController.waitForPlayerActions();
                serverController.reset();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ResourceLoaderException e) {
            LOGGER.fine("ERROR LOADING RESOURCE FILES");
        }
    }
}
