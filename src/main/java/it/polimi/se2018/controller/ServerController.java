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
    // this object is used to synchronize the requests among players
    private final Object lock = new Object();
    private final ResourceLoader resourceLoader = new ResourceLoader();
    private Model model;
    private VirtualView view;
    private ClientGatherer clientGatherer;
    // the list of Player Action instance related to the players requests
    private ArrayList<PlayerAction> playerActions = new ArrayList<>();
    // initialized from file
    private int lobbyTimeout;
    private int turnTimeout;
    // count how many players has chosen a pattern card after entering the match
    private int readyPlayers = 0;

    public static final Logger LOGGER = Logger.getLogger(ServerController.class.getName());

    //Methods
    public ServerController(Model model, VirtualView view) {
        super(model);
        this.model = model;
        this.view = view;
        clientGatherer = new ClientGatherer(this.lock);
    }

    /**
     * This method listen to the player actions in order to execute the requests.
     *
     * @throws InterruptedException
     */
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

    /**
     * This method checks if there are player actions that are not execute.
     *
     * @return true if there are a new player action request.
     */
    private boolean updatedPlayerActions() {
        for(PlayerAction pa : playerActions) {
            if(pa.isUpdated()) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method during the lobby gathering collect up to four players that have a valid username (not already used),
     * instead during the match started is intended to reinsert players that may have been disconnected from the game.
     * Also checks if all the players are still connected.
     *
     * @throws InterruptedException
     */
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
                LOGGER.fine("begin check");
                // check if other players are still connected by clientinfo
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
                            // the client cannot be inserted in the game
                            try {
                                clientInfo.getView().enteringError(model.isLobbyGathering());
                            } catch (IOException e) {
                                //e.printStackTrace();
                            }
                        }
                        pa.clear();
                    }
                }
            } while(model.isLobbyGathering());
        }
        LOGGER.fine("end gathering");
    }

    /**
     * This method checks whether all the players are connected.
     */
    private void checkConnections() {
        int i = 0;
        while(i < model.getNumPlayers()) {
            Player player = model.getPlayer(i);
            if((!model.checkConnection(i) || !view.checkConnection(i))
               && player.isConnected()
               && !player.isSwitchingConn()) {
                //take into account that the player has quit
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

    /**
     * This method checks whether there are clients that request to enter (or reenter) the game with a specific username.
     *
     * @return
     */
    private boolean emptyPreLobby() {
        Iterator<ClientInfo> iterator = clientGatherer.iterator();
        while(iterator.hasNext()) {
            if(iterator.next().getPlayerAction().isUpdated()) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method load and shuffle all game elements in order to start a match.
     *
     * @throws ResourceLoaderException
     */
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
            LOGGER.fine("pattern card id: " + id);
            patternCards[i] = resourceLoader.loadPC(id);
        }
        for(int i = 0; i < 3; i++) {
            int num = random.nextInt(pubOCIds.size());
            int id = pubOCIds.remove(num);
            LOGGER.fine("pubOC id: " + id);
            pubObjCards[i] = resourceLoader.loadPubOC(id);
        }
        for(int i = 0; i < 3; i++) {
            int num = random.nextInt(toolCardIds.size());
            int id = toolCardIds.remove(num);
            LOGGER.fine("tool card id: " + id);
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

    /**
     * This method perform the action requested by a player.
     *
     * @param pa the action that a player requests to perform.
     */
    private void performAction(PlayerAction pa) {
        int playerIndex = playerActions.indexOf(pa);
        if(!model.isLobbyGathering() && !model.isStarted()) {
            // set the PatternCard selected by the player
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
            // take into account that a player want to reconnect to the game switching connection type
            Player player = model.getPlayer(playerIndex);
            player.setSwitchingConn(true);
        }
        else if(model.isStarted()) {
            if(pa.isSkipTurn()) {
                // the player skip his turn
                model.updateTurn();
            }
            else if((pa.getIdToolCard() > 0)
               || ToolCard.isPendingAction()) {
                // the player complete the pending action of a selected ToolCard
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

    /**
     * This method resets the whole game state into the initial one.
     */
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
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } catch (ResourceLoaderException e) {
            LOGGER.severe("ERROR LOADING RESOURCE FILES");
        }
    }
}
