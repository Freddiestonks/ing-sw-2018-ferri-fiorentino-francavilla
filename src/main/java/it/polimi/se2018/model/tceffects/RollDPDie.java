package it.polimi.se2018.model.tceffects;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class RollDPDie extends AbstractTCEffect {

    public RollDPDie() {
        singleAction = false;
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) {
        if(!ToolCard.isPendingAction()) {
            Die die = model.getDraftPoolDie(pa.getPosDPDie().get(0));
            die.roll();
            ToolCard.setPendingDie(die);
        }
        else {
            Die die = ToolCard.getPendingDie();
            model.removeDraftPoolDie(die);
            int[] wfPlace = pa.getPlaceDPDie().get(0);
            wf.placeDie(die, wfPlace[0], wfPlace[1]);
            ToolCard.resetPendingAction();
            model.updateTurn();
        }
    }

    public boolean validAction(ModelInterface model, WindowFrame wf, PlayerAction pa) {
        if(!ToolCard.isPendingAction()) {
            return !pa.getPosDPDie().isEmpty();
        }
        else {
            if(pa.getPlaceDPDie().isEmpty()) {
                return false;
            }
            int[] wfPlace = pa.getPlaceDPDie().get(0);
            Die die = ToolCard.getPendingDie();
            return wf.checkRestrictions(die, wfPlace[0], wfPlace[1]);
        }
    }
}
