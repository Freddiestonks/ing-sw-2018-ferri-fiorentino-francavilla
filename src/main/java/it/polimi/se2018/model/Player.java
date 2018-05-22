package it.polimi.se2018.model;

/**
 * This class identifies the player instantiation. It's completely controlled by the 'Model' class in which
 * is instantiated, depending on the number of player who access to a single match.
 *
 * @author Federico Ferri
 * @author Alessio Fiorentino
 * @author Simone Francavilla
 */


public class Player {
    //Attr.
    private String username;
    private WindowFrame wf = null;
    private int tokens;
    private PrivObjCard privateCard;
    private int score = 0;
    private boolean state = true;

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
     * that sets the 'tokens' attribute representing the difficulty of the selected 'Pattern-Card' of the 'Window-Frame'.
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
     * @param privoc is the Private card passed by caller
     */
    public void setPrivOC(PrivObjCard privoc){
        if(!privoc.isUsed()){
            this.privateCard = privoc;
        }
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

    public int calculateScore(PubObjCard[] pubocs){
        //TODO: score calculation missing.
        return score;
    }

    /**
     * This method is used to set the connection between Server and Player by the state that is passed by caller
     *
     * @param newState the boolean value that represents which will be te the next state connection for the player.
     */
    public void setConnection(boolean newState){
        this.state = newState;
    }
}
