package it.polimi.se2018.model;

import it.polimi.se2018.controller.PlayerAction;

import java.io.Serializable;

public abstract class ToolCard implements Serializable {
    //Attributes
    private String name = "test";
    private String description = "test";
    private int price = 4;
    private int tokens = 0;
    protected static ToolCard pendingToolCard = null;
    protected static Die pendingDie;
    //Methods

    public ToolCard(String name, String description, int price){
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public void putTokens(int tokens){
        this.tokens += tokens;
    }

    public int getTokens() {
        return tokens;
    }

    public abstract void performAction(Model model, WindowFrame wf, PlayerAction pa);

    public abstract boolean validAction(ModelInterface model, WindowFrame wf, PlayerAction pa);

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
}


