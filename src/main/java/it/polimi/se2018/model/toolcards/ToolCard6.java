package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class ToolCard6 extends ToolCard {

    public ToolCard6() {
        super();
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) throws InvalidPlaceException {
        if(!pendingAction) {
            Die die = model.getDraftPoolDie(pa.getPosDPDie()[0]);
            die.roll();
            setPendingDie(die);
            pendingAction = true;
        }
        else {
            Die die = getPendingDie();
            wf.placeDie(die, pa.getPlaceDPDie()[0][0], pa.getPlaceDPDie()[0][1]);
            pendingAction = false;
        }
    }

    public boolean validAction(Model model, WindowFrame wf, PlayerAction pa) {
        if(!pendingAction) {
            return true;
        }
        else {
            Die die = getPendingDie();
            return wf.checkRestrictions(die, pa.getPlaceDPDie()[0][0], pa.getPlaceDPDie()[0][1]);
        }
    }
}
