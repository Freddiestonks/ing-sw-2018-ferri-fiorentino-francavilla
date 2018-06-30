package it.polimi.se2018.model;

import java.io.Serializable;

/**
 * This class identifies the player instantiation. It's completely controlled by the 'Model' class in which
 * is instantiated, depending on the number of player who access to a single match.
 *
 * @author Federico Ferri
 * @author Alessio Fiorentino
 * @author Simone Francavilla
 */


public class Player implements Serializable {
    //Attr.
    private String username;
    private WindowFrame wf = null;
    private int tokens;
    private transient PrivObjCard privObjCard;
    private transient int score = 0;
    private boolean connected = true;
    private transient boolean skip = false;
    private transient boolean switchingConn = false;

    //Methods

    /**
     * This is the constructor of a single 'Player' instance.
     *
     * @param username A string value, passed to the method, to set-up the player's name.
     */
    public Player(String username) {
        this.username = username;
    }

    /**
     * This is the method that instantiate the Window-frame attribute bringing the one passed by caller, and
     * that sets the 'tokens' attribute representing the difficulty of the getCell 'Pattern-Card' of the 'Window-Frame'.
     *
     * @param wf If the 'Window-Frame' object passed to be associated to the player.
     */
    public void setWinFrame(WindowFrame wf){
        this.wf = wf;
        this.tokens = wf.cardDifficulty();
    }

    /**
     * This method is used to sets the Private card associated to the player.
     *
     * @param privOC is the Private card passed by caller
     */
    public void setPrivOC(PrivObjCard privOC){
        this.privObjCard = privOC;
    }

    /**
     * This method is used to decrease the tokens value when user spent it for a specific move.
     *
     * @param tokens is the number of tokens to be decreased.
     * @throws IllegalArgumentException
     */
    public void spendTokens(int tokens) throws IllegalArgumentException{
        if(this.tokens >= tokens && tokens >= 0){
            this.tokens -= tokens;
        }
        else throw new IllegalArgumentException();
    }

    public int calculateScore(PubObjCard[] pubObjCard){
        if(wf==null){
        }
        System.out.println(wf);
        score = pubObjCard[0].calculateScore(wf) + pubObjCard[1].calculateScore(wf) + pubObjCard[2].calculateScore(wf);
        for (int i = 0; i<5; i++){
            for (int l = 0;l<4;l++){
                //fill check with all of the elements inside the window frame
                if(wf.getDie(l,i) == null){
                    score--;
                }
                else if(wf.getDie(l,i).getColor() == privObjCard.getColor()){
                    score++;
                }

            }
        }
        score = score + tokens;
        return score;
    }

    /**
     * This method is used to set the connection between Server and Player by the connected that is passed by caller
     *
     * @param connected the boolean value that represents which will be te the next connected connection for the player.
     */
    public void setConnected(boolean connected){
        this.connected = connected;
    }

    /**
     * Thsi method is usd to check if the player is connected.
     *
     * @return a boolean value representing the check
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * This method provide The number of tokens available for the player.
     *
     * @return an integer value representing the value
     */
    public int getTokens(){
        return tokens;
    }

    /**
     * This mehod is used to set the token's value
     *
     * @param newValue is the value to be inserted in the corresponding attribute
     */
    public void setTokens(int newValue){
        tokens = newValue;
    }

    /**
     * This method is used to provide the name of the player.
     *
     * @return a string instane rereting the player name.
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method is used to provide a die on the Windowframe of the player.
     *
     * @param row The row of the wanted positioning place.
     * @param col The column of the wanted positioning place.
     * @return a die instance corresponding to the wanted position
     */
    public Die getWinFrameDie(int row, int col){
        return this.wf.getDie(row,col);
    }

    /**
     * This method provides the player's WindowFrame.
     *
     * @return a Windowframe instance.
     */
    public WindowFrame getWindowFrame(){
        return this.wf;
    }

    /**
     * This method set the 'skip' attribute
     *
     * @param skip the boolean value to be set.
     */
    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    /**
     * This method provide the 'skip' value.
     *
     * @return a boolean instance representing the skip value
     */
    public boolean isSkip() {
        return skip;
    }

    /**
     * This method provide the possible switch decision.
     *
     * @return a boolean representing the relative selection
     */
    public boolean isSwitchingConn() {
        return switchingConn;
    }

    /**
     * This method is used to set the selection related to the switch of the connection.
     *
     * @param switchingConn is the boolean value representing the switch
     */
    public void setSwitchingConn(boolean switchingConn) {
        this.switchingConn = switchingConn;
    }

    public PrivObjCard getPrivObjCard() {
        return privObjCard;
    }

}

