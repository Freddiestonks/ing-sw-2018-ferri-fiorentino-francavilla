package it.polimi.se2018.model.tceffects;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class DiceBagPlace extends AbstractTCEffect {

    public DiceBagPlace() {
        singleAction = false;
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa){
        Die die;
        if(!ToolCard.isPendingAction()) {
            die = model.removeDraftPoolDie(pa.getPosDPDie().get(0));
            model.getDiceBag().replace(die);
            die = model.getDiceBag().extract();
            model.addDraftPoolDie(die);
            ToolCard.setPendingDie(die);
        }
        else {
            die = ToolCard.getPendingDie();
            model.removeDraftPoolDie(die);
            die.setValue(pa.getNewDieValue().get(0));
            wf.placeDie(die, pa.getPlaceDPDie().get(0)[0], pa.getPlaceDPDie().get(0)[1]);
            ToolCard.resetPendingAction();
            model.updateTurn();
        }
    }

    public boolean validAction(ModelInterface model, WindowFrame wf, PlayerAction pa) {
        if(!ToolCard.isPendingAction()) {
            return !pa.getPosDPDie().isEmpty();
        }
        else {
            if(pa.getNewDieValue().isEmpty() || pa.getPlaceDPDie().isEmpty()) {
                return false;
            }
            Die die = new Die(ToolCard.getPendingDie());
            die.setValue(pa.getNewDieValue().get(0));
            return wf.checkRestrictions(die, pa.getPlaceDPDie().get(0)[0], pa.getPlaceDPDie().get(0)[1]);
        }
    }
}