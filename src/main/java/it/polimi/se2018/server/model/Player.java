package it.polimi.se2018.server.model;


public class Player {
    //Attr.
    private String username;
    private WindowFrame wf;
    private int tokens;
    private PrivObjCard privateCard;
    private int score = 0;
    private boolean state = true;

    //Methods

    public Player(String username) {
        this.username = username;
    }

    public void setWinFrame(WindowFrame wf){}

    public void setPrivOC(PrivObjCard privoc){}

    public void setTokens(Integer tokens){}

    public int calculateScore(PubObjCard[] pubocs){
        return score;
    }

    public void setConnection(boolean newState){
        this.state = newState;
    }
}
