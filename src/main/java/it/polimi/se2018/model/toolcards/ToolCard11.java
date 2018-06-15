package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class ToolCard11 extends ToolCard {

    public ToolCard11(String name, String description, int price, int tokens) {
        super(name, description, price, tokens);
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa){
        Die die;
        if(!pendingAction) {
            die = model.removeDraftPoolDie(pa.getPosDPDie().get(0));
            model.getDiceBag().replace(die);
            die = model.getDiceBag().extract();
            model.addDraftPoolDie(die);
            setPendingDie(die);
            pendingAction = true;
        }
        else {
            die = getPendingDie();
            model.removeDraftPoolDie(die);
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