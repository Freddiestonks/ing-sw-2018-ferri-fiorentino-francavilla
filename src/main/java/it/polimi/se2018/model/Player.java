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
    private PrivObjCard privObjCard;
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
     * @param privOC is the Private card passed by caller
     */
    public void setPrivOC(PrivObjCard privOC){
        if(!privOC.isUsed()){
            this.privObjCard = privOC;
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

    public int calculateScore(PubObjCard[] pubObjCard){
        score = pubObjCard[0].calculateScore(wf) + pubObjCard[1].calculateScore(wf) + pubObjCard[2].calculateScore(wf);
        for (int i = 0; i<5; i++){
            for (int l = 0;l<4;l++){
                //fill check with all of the elements inside the window frame
                if(wf.getDie(l,i).getColor() == privObjCard.getColor()){
                    score = score + 1;
                }
                if(wf.getDie(l,i) == null){
                    score = score - 1;
                }
            }
        }
        score = score + tokens;
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

    public int getTokens(){
        return tokens;
    }
    public void setTokens(int newValue){
        tokens = newValue;
    }

    public String getUsername() {
        return username;
    }

    public Die getWindowFramePosition(int row, int col){
        return this.wf.getDie(row,col);
    }

    public WindowFrame getWF(){
        return this.wf;
    }
}

