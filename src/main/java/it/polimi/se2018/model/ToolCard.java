package it.polimi.se2018.model;

import it.polimi.se2018.controller.PlayerAction;

public abstract class ToolCard {
    //Attributes
    private String description;
    private int price;
    private int tokens;
    private Color color;
    protected boolean pendingAction = false;
    private int idRes;
    private Die pendingDie;
    //Methods

    //TODO: clone WindowFrame
    //TODO: update turn when necessary
    //TODO: make a player skip the second turn with TC 8
    //TODO: manage model update
    //TODO: ? pending state/die in model class

    public void putTokens(){}

    public abstract void performAction(Model model, WindowFrame wf, PlayerAction pa) throws InvalidPlaceException, EmptyDiceBagException;

    public abstract boolean validAction(Model model, WindowFrame wf, PlayerAction pa) throws InvalidPlaceException, CloneNotSupportedException;

    public Die getPendingDie() {
        return pendingDie;
    }

    public void setPendingDie(Die die) {
        pendingDie = die;
    }

}


