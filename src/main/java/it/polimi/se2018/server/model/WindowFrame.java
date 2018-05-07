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
        //TODO: implement user request about positioning and insertion
    }

    public boolean wcFace(){
        return wcFace;
    }
}
