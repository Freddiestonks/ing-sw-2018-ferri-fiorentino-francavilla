package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class ToolCard11 extends ToolCard {

    public ToolCard11(String name, String description, int price) {
        super(name, description, price);
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa){
        Die die;
        if(!isPendingAction()) {
            die = model.removeDraftPoolDie(pa.getPosDPDie().get(0));
            model.getDiceBag().replace(die);
            die = model.getDiceBag().extract();
            model.addDraftPoolDie(die);
            pendingDie = die;
            pendingToolCard = this;
        }
        else {
            model.removeDraftPoolDie(pendingDie);
            pendingDie.setValue(pa.getNewDieValue().get(0));
            wf.placeDie(pendingDie, pa.getPlaceDPDie().get(0)[0], pa.getPlaceDPDie().get(0)[1]);
            resetPendingAction();
            model.updateTurn();
        }
    }

    public boolean validAction(ModelInterface model, WindowFrame wf, PlayerAction pa) {
        if(!isPendingAction()) {
            return !pa.getPosDPDie().isEmpty();
        }
        else {
            if(pa.getNewDieValue().isEmpty() || pa.getPlaceDPDie().isEmpty()) {
                return false;
            }
            Die die = new Die(pendingDie);
            die.setValue(pa.getNewDieValue().get(0));
            return wf.checkRestrictions(die, pa.getPlaceDPDie().get(0)[0], pa.getPlaceDPDie().get(0)[1]);
        }
    }
}