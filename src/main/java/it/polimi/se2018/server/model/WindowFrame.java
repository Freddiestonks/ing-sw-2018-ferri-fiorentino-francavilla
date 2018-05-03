package it.polimi.se2018.server.model;

public class WindowFrame {
    //Attributes
    private PatternCard pc;
    private boolean wcFace;
    private Die[][] placements;

    //Methods


    public WindowFrame(PatternCard pc) {
        this.pc = pc;
        this.placements = new Die[4][5];
    }

    public void placeDice(Die dice){
        //richiesta utente su posizionamento e poi inserimento
    }

    public boolean wcFace(){
        return wcFace;
    }
}
