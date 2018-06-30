package it.polimi.se2018.model;

import it.polimi.se2018.controller.PlayerAction;

import java.io.Serializable;

/**
 * This method implementing serializable interface, is the superclass for all the Tool-cards utilized
 * in the entire application.
 *
 * @author Federico Ferri
 * @author Alessio Fiorentino
 * @author Simone Francavilla
 */

public abstract class ToolCard implements Serializable {
    //Attributes
    private String name;
    private String description;
    private int price;
    private int tokens = 0;
    protected static ToolCard pendingToolCard = null;
    protected static Die pendingDie;
    //Methods

    /**
     * This is the constructor of the class.
     *
     * @param name that means the name of a specific ToolCard
     * @param description is the effect of the Toolcard
     * @param price is a special characteristic of each Toolcard
     */
    public ToolCard(String name, String description, int price){
        this.name = name;
        this.description = description;
        this.price = price;
    }

    /**
     * this method is used to set a specific value for the tokens.
     *
     * @param tokens is the value to be set up
     */
    public void putTokens(int tokens){
        this.tokens += tokens;
    }

    /**
     * This method is used to get the value of tokens' Toolcard
     *
     * @return an integer value representing tokens.
     */
    public int getTokens() {
        return tokens;
    }

    /**
     * This method executes the effect of the Toolcard.
     *
     * @param model is the model of the match.
     * @param wf is the Windowframe of the player that uses the Toolcard.
     * @param pa is the playeraction that contains the move selections for the player.
     */
    public abstract void performAction(Model model, WindowFrame wf, PlayerAction pa);

    /**
     * This method is used to validate the move of the player.
     *
     * @param model is the model of the match.
     * @param wf is the Windowframe of the player that uses the Toolcard.
     * @param pa is the playeraction that contains the move selections for the player.
     * @return a boolean value representing the result of the check.
     */
    public abstract boolean validAction(ModelInterface model, WindowFrame wf, PlayerAction pa);

    /**
     * This method is used to ask if a specific Toolcard is in a pending status.
     *
     * @return a boolean value representing the result of the control.
     */
    public static boolean isPendingAction() {
        return (pendingToolCard != null);
    }

    /**
     * This method is used to provide the Toolcard defined in a pending status.
     *
     * @return a Toolcard instance.
     */
    public static ToolCard getPendingToolCard() {
        return pendingToolCard;
    }

    /**
     * This method is used to rest the attribute resenting the pending status of a Toolcard.
     */
    public static void resetPendingAction() {
        pendingToolCard.pendingDie = null;
        pendingToolCard = null;
    }

    /**
     * This method provides the effects of a ToolCard.-
     *
     * @return a string representing the effect
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method is used to provide teh name of a Toolcard.
     *
     * @return a string representing the Toolcard's name.
     */
    public String getName() {
        return name;
    }
}


