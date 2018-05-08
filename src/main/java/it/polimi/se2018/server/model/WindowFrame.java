package it.polimi.se2018.server.model;

public class WindowFrame {
    //Attributes
    private PatternCard pc;
    private boolean wcFace; //true for front
    private Die[][] placements;

    //Methods
    public WindowFrame(PatternCard pc) {
        this.pc = pc;
        this.placements = new Die[4][5];
    }

    public void placeDice(Die die){
        //TODO: implement user request about positioning and insertion
    }

    public boolean wcFace(){
        return wcFace;
    }

    public int cardDifficulty(){
        return (wcFace ? pc.getLevelF() : pc.getLevelB());
    }
}
