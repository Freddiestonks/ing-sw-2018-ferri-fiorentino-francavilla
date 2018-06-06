package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class ToolCard11 extends ToolCard {

    public ToolCard11() {
        super();
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa){
        Die die;
        if(!pendingAction) {
            die = model.getDraftPoolDie(pa.getPosDPDie().get(0));
            model.getDiceBag().replace(die);
            die = model.getDiceBag().extract();
            setPendingDie(die);
            pendingAction = true;
        }
        else {
            die = getPendingDie();
            die.setValue(pa.getNewDieValue().get(0));
            wf.placeDie(die, pa.getPlaceDPDie().get(0)[0], pa.getPlaceDPDie().get(0)[1]);
            pendingAction = false;
            model.updateTurn();
        }
    }

    public boolean validAction(Model model, WindowFrame wf, PlayerAction pa) {
        if(!pendingAction) {
            return true;
        }
        else {
            Die die = new Die(getPendingDie());
            die.setValue(pa.getNewDieValue().get(0));
            return wf.checkRestrictions(die, pa.getPlaceDPDie().get(0)[0], pa.getPlaceDPDie().get(0)[1]);
        }
    }
}