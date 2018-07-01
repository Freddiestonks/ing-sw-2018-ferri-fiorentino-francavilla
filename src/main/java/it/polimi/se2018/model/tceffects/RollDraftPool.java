package it.polimi.se2018.model.tceffects;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.ModelInterface;
import it.polimi.se2018.model.WindowFrame;

public class RollDraftPool extends AbstractTCEffect {

    public RollDraftPool() {
        singleAction = true;
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) {
        for(int i = 0; i < model.getDraftPoolSize(); i++) {
            model.getDraftPoolDie(i).roll();
        }
    }

    public boolean validAction(ModelInterface model, WindowFrame wf, PlayerAction pa) {
        return model.isBackward();
    }
}

