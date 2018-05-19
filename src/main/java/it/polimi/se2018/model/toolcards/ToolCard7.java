package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.model.WindowFrame;

public class ToolCard7 extends ToolCard {

    public ToolCard7() { super();}

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) {
        model.rollDraftPool();
    }

    public boolean validAction(Model model, WindowFrame wf, PlayerAction pa) {
        return model.isBackward();
    }
}

