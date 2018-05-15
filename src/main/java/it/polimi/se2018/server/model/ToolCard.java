package it.polimi.se2018.server.model;

import it.polimi.se2018.server.controller.PlayerAction;

public abstract class ToolCard {
    //Attributes
    private String description;
    private int price;
    private int tokens;
    private Color color;
    private int idRes;
    //Methods

    public void putTokens(){}

    public abstract void performAction(Model model, WindowFrame wf, PlayerAction pa);

    public abstract boolean validAction(Model model, WindowFrame wf, PlayerAction pa);
}
