package it.polimi.se2018.model;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.tceffects.AbstractTCEffect;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This is the Tool Card class
 *
 * @author Federico Ferri
 * @author Alessio Fiorentino
 * @author Simone Francavilla
 */

public class ToolCard implements Serializable {
    //Attributes
    private String name;
    private String description;
    private int tokens = 0;
    private static ToolCard pendingToolCard = null;
    private static Die pendingDie;
    private ArrayList<AbstractTCEffect> effects;
    //Methods

    /**
     * This is the constructor of the class.
     *
     * @param name that means the name of a specific ToolCard
     * @param description is the effect of the ToolCard
     * @param effects the set of effects used by the ToolCard
     */
    public ToolCard(String name, String description, ArrayList<AbstractTCEffect> effects) {
        this.name = name;
        this.description = description;
        this.effects = new ArrayList<>(effects);
    }

    /**
     * This method provides the effects of a Tool Card.
     *
     * @return a string representing the effect
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method is used to provide teh name of a Tool Card.
     *
     * @return a string representing the Tool Card's name.
     */
    public String getName() {
        return name;
    }

    /**
     * this method is used to add a specific value to the tokens.
     *
     * @param tokens is the value to add up
     */
    public void putTokens(int tokens){
        this.tokens += tokens;
    }

    /**
     * This method is used to calculate the price of a Tool Card
     *
     * @return an integer value representing tokens.
     */
    public int getPrice() {
        return ((tokens == 0)? 1: 2);
    }

    /**
     * This method executes the effect of the Tool Card.
     *
     * @param model is the model of the match.
     * @param wf is the Window Frame of the player that uses the Tool Card.
     * @param pa is the Player Action that contains the move selections for the player.
     */
    public void performAction(Model model, WindowFrame wf, PlayerAction pa) {
        if(!isPendingAction()) {
            // there is no pending actions to perform
            for(AbstractTCEffect effect : effects) {
                effect.performAction(model, wf, pa);
                if(!effect.isSingleAction()) {
                    pendingToolCard = this;
                }
            }
        }
        else {
            for(AbstractTCEffect effect : effects) {
                if(!effect.isSingleAction()) {
                    effect.performAction(model, wf, pa);
                }
            }
            resetPendingAction();
        }
    }

    /**
     * This method is used to validate the move of the player.
     *
     * @param model is the model of the match.
     * @param wf is the Window Frame of the player that uses the Tool Card.
     * @param pa is the Player Action that contains the move selections for the player.
     * @return a boolean value representing the result of the check.
     */
    public boolean validAction(ModelInterface model, WindowFrame wf, PlayerAction pa) {
        for(AbstractTCEffect effect : effects) {
            if((!effect.isSingleAction() || !ToolCard.isPendingAction())
               && !effect.validAction(model, wf, pa)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method is used to ask if a specific ToolCard is in a pending status.
     *
     * @return a boolean value representing the result of the control.
     */
    public static boolean isPendingAction() {
        return (pendingToolCard != null);
    }

    /**
     * This method is used to provide the ToolCard defined in a pending status.
     *
     * @return a Tool Card instance.
     */
    public static ToolCard getPendingToolCard() {
        return pendingToolCard;
    }

    /**
     * This method is used to provide the die related to the pending ToolCard.
     *
     * @return a Die instance.
     */
    public static Die getPendingDie() {
        return pendingDie;
    }

    /**
     * This method is used to save a die related to a pending action.
     *
     * @param die is the die to save for the pending action.
     */
    public static void setPendingDie(Die die) {
        pendingDie = die;
    }

    /**
     * This method is used to reset the pending status of a ToolCard.
     */
    public static void resetPendingAction() {
        pendingDie = null;
        pendingToolCard = null;
    }
}


