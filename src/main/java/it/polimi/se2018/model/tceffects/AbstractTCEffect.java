package it.polimi.se2018.model.tceffects;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.ModelInterface;
import it.polimi.se2018.model.WindowFrame;

import java.io.Serializable;

public abstract class AbstractTCEffect implements Serializable {

    protected boolean singleAction;

    /**
     * This method checks if the current effect can be performed in one single player action.
     * @return true if is a single action effect.
     */
    public boolean isSingleAction() {
        return singleAction;
    }

    /**
     * This method performs the ToolCard effect.
     * @param model Model reference.
     * @param wf WindowFrame where perform the effect.
     * @param pa PlayerAction to perform.
     */
    public abstract void performAction(Model model, WindowFrame wf, PlayerAction pa);

    /**
     * This method checks if the player action can be performed.
     * @param model Model reference.
     * @param wf WindowFrame where perform the effect.
     * @param pa PlayerAction to perform.
     * @return true if the action can be performed.
     */
    public abstract boolean validAction(ModelInterface model, WindowFrame wf, PlayerAction pa);

}
