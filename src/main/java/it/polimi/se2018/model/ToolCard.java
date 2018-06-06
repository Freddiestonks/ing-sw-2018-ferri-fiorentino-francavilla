package it.polimi.se2018.model;

import it.polimi.se2018.controller.PlayerAction;

public abstract class ToolCard {
    //Attributes
    private String name;
    private String description;
    private int price;
    private int tokens;
    private Color color;
    protected boolean pendingAction = false;
    private int idRes;
    private Die pendingDie;
    //Methods

    //TODO: make a player skip the second turn with TC 8
    //TODO: manage model update
    //TODO: ? pending state/die in model class

    public void putTokens(){}

    public abstract void performAction(Model model, WindowFrame wf, PlayerAction pa);

    public abstract boolean validAction(Model model, WindowFrame wf, PlayerAction pa);

    public Die getPendingDie() {
        return pendingDie;
    }

    public void setPendingDie(Die die) {
        pendingDie = die;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}


