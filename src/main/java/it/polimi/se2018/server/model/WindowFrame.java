package it.polimi.se2018.server.model;

public class WindowFrame {
    //Attributes
    private PatternCard pc;
    private boolean wcFace;
    private Dice[][] placements;

    //Methods


    public WindowFrame(PatternCard pc) {
        this.pc = pc;
        this.placements = new Dice[4][5];
    }

    public void placeDice(Dice dice){
        //richiesta utente su posizionamento e poi inserimento
    }

    public boolean wcFace(){}
}
