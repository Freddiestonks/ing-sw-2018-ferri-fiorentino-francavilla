package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.ModelInterface;
import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.model.WindowFrame;

public class ToolCard7 extends ToolCard {

    public ToolCard7(String name, String description, int price) { super(name, description, price);}

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) {
        for(int i = 0; i < model.getDraftPoolSize(); i++) {
            model.getDraftPoolDie(i).roll();
        }
    }

    public boolean validAction(ModelInterface model, WindowFrame wf, PlayerAction pa) {
        return model.isBackward();
    }
}

