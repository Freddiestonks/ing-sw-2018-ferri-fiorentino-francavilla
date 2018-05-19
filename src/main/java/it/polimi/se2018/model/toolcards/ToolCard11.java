package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class ToolCard11 extends ToolCard {

    public ToolCard11() {
        super();
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) throws InvalidPlaceException, EmptyDiceBagException {
        Die die;
        if(!pendingAction) {
            die = model.getDraftPoolDie(pa.getPosDPDie()[0]);
            model.getDiceBag().replace(die);
            die = model.getDiceBag().extract();
            setPendingDie(die);
            pendingAction = true;
        }
        else {
            die = getPendingDie();
            die.setValue(pa.getNewDieValue());
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
            //TODO: die.clone()
            die.setValue(pa.getNewDieValue());
            return wf.checkRestrictions(die, pa.getPlaceDPDie()[0][1], pa.getPlaceDPDie()[0][1]);
        }
    }
}