package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class ToolCard10 extends ToolCard {

    public ToolCard10(String name, String description, int price, int tokens) {
        super(name, description, price, tokens);
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa){
        Die die = model.removeDraftPoolDie(pa.getPosDPDie().get(0));
        die.setValue(7 - die.getValue());
        wf.placeDie(die, pa.getPlaceDPDie().get(0)[0], pa.getPlaceDPDie().get(0)[1]);
        model.updateTurn();
    }

    public boolean validAction(Model model, WindowFrame wf, PlayerAction pa) {
        if(pa.getPosDPDie().isEmpty() || pa.getPlaceDPDie().isEmpty()) {
            return false;
        }
        // create a copy of the die
        Die die = new Die(model.getDraftPoolDie(pa.getPosDPDie().get(0)));
        die.setValue(7 - die.getValue());
        return wf.checkRestrictions(die, pa.getPlaceDPDie().get(0)[0], pa.getPlaceDPDie().get(0)[1]);
    }
}
