package it.polimi.se2018.controller;
import java.io.Serializable;
import java.util.ArrayList;

public class PlayerAction implements PlayerActionInterface, Serializable {
    // this object is used to synchronize the requests among players
    private transient Object lock = new Object();
    // this boolean, if true, indicates that a PlayerAction instance is ready to be performed
    private boolean updated = false;
    // the string used by a player to enter in the game
    private String usernameReq = null;
    // this boolean, if true, indicates that a player asks to switch connection type
    private boolean switchConnReq = false;
    // the PatternCard id that a player select before the match starts
    private int patternCard = 0;
    // this boolean, if true, indicates that a player asks to skip his turn
    private boolean skipTurn = false;
    // the list of values that a player asks to set to some selected dice
    private ArrayList<Integer> newDieValue = new ArrayList<>();
    // the list of position of DraftPool dice that a player selects
    private ArrayList<Integer> posDPDie = new ArrayList<>();
    // the list of position of RoundTrack dice that a player selects, indicated by Round index and a second index
    private ArrayList<int[]> posRTDie = new ArrayList<>();
    // the list of placements of WindowFrame that a player selects in order to place DraftPool dice
    private ArrayList<int[]> placeDPDie = new ArrayList<>();
    // the list of placements of WindowFrame dice that a player selects in order to move
    private ArrayList<int[]> placeWFDie = new ArrayList<>();
    // the list of placements of WindowFrame that a player selects in order to move WindowFrame dice to
    private ArrayList<int[]> placeNewWFDie = new ArrayList<>();
    // this integer represents the ToolCard selected to be used (0 means not used)
    private int idToolCard = 0;

    public PlayerAction() {
    }

    public PlayerAction(Object lock) {
        this.lock = lock;
    }

    /**
     * This method is used to support the copy of an ArrayList composed of static Array.
     * @param arrayList the ArrayList to be copied.
     * @return the ArrayList copy.
     */
    private ArrayList<int[]> copy(ArrayList<int[]> arrayList) {
        ArrayList<int[]> newArrayList = new ArrayList<>();
        for(int[] array : arrayList) {
            newArrayList.add(array.clone());
        }
        return newArrayList;
    }

    /**
     * This method is used to set the current PlayerAction into the one given by parameter.
     *
     * @param pa the PlayerAction whose parameters have to to be set in the current one.
     */
    public void setPlayerAction(PlayerAction pa) {
        synchronized (lock) {
            if(!updated) {
                this.usernameReq = pa.usernameReq;
                this.switchConnReq = pa.switchConnReq;
                this.patternCard = pa.patternCard;
                this.skipTurn = pa.skipTurn;
                this.newDieValue = pa.getNewDieValue();
                this.posDPDie = pa.getPosDPDie();
                this.posRTDie = pa.getPosRTDie();
                this.placeDPDie = pa.getPlaceDPDie();
                this.placeWFDie = pa.getPlaceWFDie();
                this.placeNewWFDie = pa.getPlaceNewWFDie();
                this.idToolCard = pa.idToolCard;
                this.updated = true;
                lock.notifyAll();
            }
        }
    }

    /**
     * This method checks if the PlayerAction instance has to be read by the server.
     *
     * @return true if there are a new player action request.
     */
    public boolean isUpdated() {
        synchronized (lock) {
            return updated;
        }
    }

    public void setUsernameReq(String usernameReq) {
        this.usernameReq = usernameReq;
    }

    public String getUsernameReq() {
        return usernameReq;
    }

    public void setSwitchConnReq(boolean switchConnReq) {
        this.switchConnReq = switchConnReq;
    }

    public boolean isSwitchConnReq() {
        return switchConnReq;
    }

    public void setPatternCard(int pc) {
        this.patternCard = pc;
    }

    public int getPatternCard() {
        return patternCard;
    }

    public void setSkipTurn(boolean skipTurn) {
        this.skipTurn = skipTurn;
    }

    public boolean isSkipTurn() {
        return skipTurn;
    }

    public void addPosDPDie(int pos) {
        this.posDPDie.add(pos);
    }

    public ArrayList<Integer> getPosDPDie() {
        return new ArrayList<>(posDPDie);
    }

    public void addPosRTDie(int round, int die) {
        this.posRTDie.add(new int[]{round, die});
    }

    public ArrayList<int[]> getPosRTDie() {
        return copy(posRTDie);
    }

    public void addPlaceDPDie(int row, int col) {
        this.placeDPDie.add(new int[]{row, col});
    }

    public ArrayList<int[]> getPlaceDPDie() {
        return copy(placeDPDie);
    }

    public void addPlaceWFDie(int row, int col) {
        this.placeWFDie.add(new int[]{row, col});
    }

    public ArrayList<int[]> getPlaceWFDie() {
        return copy(placeWFDie);
    }

    public void addPlaceNewWFDie(int row, int col) {
        this.placeNewWFDie.add(new int[]{row, col});
    }

    public ArrayList<int[]> getPlaceNewWFDie() {
        return copy(placeNewWFDie);
    }

    public void addNewDieValue(int value) {
        newDieValue.add(value);
    }

    public ArrayList<Integer> getNewDieValue() {
        return new ArrayList<>(newDieValue);
    }

    public void setIdToolCard(int idToolCard) {
        this.idToolCard = idToolCard;
    }

    public int getIdToolCard() {
        return idToolCard;
    }

    /**
     * This method checks whether the PlayerAction remote reference is connected.
     */
    public void checkConnection() {}

    /**
     * This method clear all the PlayerAction parameters setting them to the default ones.
     */
    public void clear() {
        synchronized (lock) {
            this.usernameReq = null;
            this.switchConnReq = false;
            this.patternCard = 0;
            this.skipTurn = false;
            this.newDieValue.clear();
            this.posDPDie.clear();
            this.posRTDie.clear();
            this.placeDPDie.clear();
            this.placeWFDie.clear();
            this.placeNewWFDie.clear();
            this.idToolCard = 0;
            this.updated = false;
        }
    }
}
