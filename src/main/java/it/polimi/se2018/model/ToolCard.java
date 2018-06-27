package it.polimi.se2018.model;

import it.polimi.se2018.controller.PlayerAction;

public abstract class ToolCard {
    //Attributes
    private String name;
    private String description;
    private int price;
    private int tokens;
    private int idRes;
    ////TODO: to be verified effective use of this attributes.
    /*private Color color;*/
    protected static ToolCard pendingToolCard = null;
    protected static Die pendingDie;
    //Methods

    public ToolCard(String name, String description, int price, int tokens){
        this.name = name;
        this.description = description;
        this.price = price;
        this.tokens = tokens;
    }

    public void putTokens(){}

    public abstract void performAction(Model model, WindowFrame wf, PlayerAction pa);

    public abstract boolean validAction(Model model, WindowFrame wf, PlayerAction pa);

    public static boolean isPendingAction() {
        return (pendingToolCard != null);
    }

    public static ToolCard getPendingToolCard() {
        return pendingToolCard;
    }

    public static void resetPendingAction() {
        pendingToolCard.pendingDie = null;
        pendingToolCard = null;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getIdRes() {
        return idRes;
    }
}


