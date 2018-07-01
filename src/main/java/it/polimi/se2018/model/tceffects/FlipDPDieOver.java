package it.polimi.se2018.model.tceffects;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class FlipDPDieOver extends AbstractTCEffect {

    public FlipDPDieOver() {
        singleAction = true;
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa){
        Die die = model.removeDraftPoolDie(pa.getPosDPDie().get(0));
        die.setValue(7 - die.getValue());
        wf.placeDie(die, pa.getPlaceDPDie().get(0)[0], pa.getPlaceDPDie().get(0)[1]);
        model.updateTurn();
    }

    public boolean validAction(ModelInterface model, WindowFrame wf, PlayerAction pa) {
        if(pa.getPosDPDie().isEmpty() || pa.getPlaceDPDie().isEmpty()) {
            return false;
        }
        // create a copy of the die
        Die die = new Die(model.getDraftPoolDie(pa.getPosDPDie().get(0)));
        die.setValue(7 - die.getValue());
        int row = pa.getPlaceDPDie().get(0)[0];
        int col = pa.getPlaceDPDie().get(0)[1];
        return wf.checkRestrictions(die, row, col);
    }
}
