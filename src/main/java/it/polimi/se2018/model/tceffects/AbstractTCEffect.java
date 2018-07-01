package it.polimi.se2018.model.tceffects;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.ModelInterface;
import it.polimi.se2018.model.WindowFrame;

import java.io.Serializable;

public abstract class AbstractTCEffect implements Serializable {

    protected boolean singleAction;

    public boolean isSingleAction() {
        return singleAction;
    }

    public abstract void performAction(Model model, WindowFrame wf, PlayerAction pa);

    public abstract boolean validAction(ModelInterface model, WindowFrame wf, PlayerAction pa);

}
