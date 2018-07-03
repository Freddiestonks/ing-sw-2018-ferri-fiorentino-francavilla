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
    private int score = 0;
    private transient PrivObjCard privObjCard;
    private transient boolean connected = true;
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
     * @param privOC is the set of Private Objective Cards
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

    /**
     * This method return the calculated score.
     *
     * @return the calculated score.
     */
    public int getScore() {
        return score;
    }

    /**
     * This method is used to calculate the player's score with the Public Objective Cards selected.
     *
     * @param pubObjCard is the set of Public Objective Cards.
     * @return the calculated score.
     */
    public int calculateScore(PubObjCard[] pubObjCard){
        score = pubObjCard[0].calculateScore(wf) + pubObjCard[1].calculateScore(wf) + pubObjCard[2].calculateScore(wf);
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 4; j++){
                if(wf.getDie(j, i) == null){
                    score--;
                }
                else if(wf.getDie(j, i).getColor() == privObjCard.getColor()){
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
     * This method is used to provide the name of the player.
     *
     * @return a string representing the player name.
     */
    public String getUsername() {
        return username;
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
     * This method set the player to skip his next turn
     *
     * @param skip the boolean value to be set.
     */
    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    /**
     * This method determine if the player must skip his turn
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

