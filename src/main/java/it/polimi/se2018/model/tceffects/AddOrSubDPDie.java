package it.polimi.se2018.model.tceffects;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class AddOrSubDPDie extends AbstractTCEffect {

    public AddOrSubDPDie() {
        singleAction = true;
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) {
        Die die = model.removeDraftPoolDie(pa.getPosDPDie().get(0));
        die.setValue(pa.getNewDieValue().get(0));
        wf.placeDie(die, pa.getPlaceDPDie().get(0)[0], pa.getPlaceDPDie().get(0)[1]);
        model.updateTurn();
    }

    public boolean validAction(ModelInterface model, WindowFrame wf, PlayerAction pa) {
        if(pa.getPosDPDie().isEmpty()
                || pa.getPlaceDPDie().isEmpty()
                || pa.getNewDieValue().isEmpty()) {
            return false;
        }
        Die die = new Die(model.getDraftPoolDie(pa.getPosDPDie().get(0)));
        int newValue = pa.getNewDieValue().get(0);
        if((die.getValue() != newValue - 1)
                && (die.getValue() != newValue + 1)) {
            return false;
        }
        die.setValue(newValue);
        int row = pa.getPlaceDPDie().get(0)[0];
        int col = pa.getPlaceDPDie().get(0)[1];
        return wf.checkRestrictions(die, row, col);
    }
}
