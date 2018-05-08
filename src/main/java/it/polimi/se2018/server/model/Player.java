package it.polimi.se2018.server.model;


public class Player {
    //Attr.
    private String username;
    private WindowFrame wf = null;
    private int tokens;
    private PrivObjCard privateCard;
    private int score = 0;
    private boolean state = true;

    //Methods

    public Player(String username) {
        this.username = username;
    }

    public void setWinFrame(WindowFrame wf){
        this.wf = wf;
        this.tokens = wf.cardDifficulty();
    }

    public void setPrivOC(PrivObjCard privoc){
        if(!privoc.isUsed()){
            this.privateCard = privoc;
        }
    }

    public void spendTokens(int tokens){

        if(this.tokens >= tokens && tokens >= 0){
            this.tokens -= tokens;
        }
    }

    public int calculateScore(PubObjCard[] pubocs){
        //TODO: score calculation missing.
        return score;
    }

    public void setConnection(boolean newState){
        this.state = newState;
    }
}
