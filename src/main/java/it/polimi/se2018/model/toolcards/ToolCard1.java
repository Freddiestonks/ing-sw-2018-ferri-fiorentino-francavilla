package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class ToolCard1 extends ToolCard {

    public ToolCard1(String name, String description, int price, int tokens) {
        super(name, description, price, tokens);
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) {
        Die die = model.removeDraftPoolDie(pa.getPosDPDie().get(0));
        die.setValue(pa.getNewDieValue().get(0));
        wf.placeDie(die, pa.getPlaceDPDie().get(0)[0], pa.getPlaceDPDie().get(0)[1]);
        model.updateTurn();
    }

    public boolean validAction(Model model, WindowFrame wf, PlayerAction pa) {
        Die die = model.getDraftPoolDie(pa.getPosDPDie().get(0));
        int row = pa.getPlaceWFDie().get(0)[0];
        int col = pa.getPlaceWFDie().get(0)[1];
        if((die.getValue() != pa.getNewDieValue().get(0) - 1)
           && (die.getValue() != pa.getNewDieValue().get(0) + 1)) {
            return false;
        }
        return wf.checkRestrictions(die, row, col);
    }
}
