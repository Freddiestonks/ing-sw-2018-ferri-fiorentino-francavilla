package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class ToolCard6 extends ToolCard {

    public ToolCard6(String name, String description, int price, int tokens) {
        super(name, description, price, tokens);
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) throws InvalidPlaceException {
        if(!isPendingAction()) {
            Die die = model.getDraftPoolDie(pa.getPosDPDie().get(0));
            die.roll();
            pendingDie = die;
            pendingToolCard = this;
        }
        else {
            model.removeDraftPoolDie(pendingDie);
            int[] wfPlace = pa.getPlaceDPDie().get(0);
            wf.placeDie(pendingDie, wfPlace[0], wfPlace[1]);
            resetPendingAction();
            model.updateTurn();
        }
    }

    public boolean validAction(Model model, WindowFrame wf, PlayerAction pa) {
        if(!isPendingAction()) {
            return !pa.getPosDPDie().isEmpty();
        }
        else {
            if(pa.getPlaceDPDie().isEmpty()) {
                return false;
            }
            int[] wfPlace = pa.getPlaceDPDie().get(0);
            return wf.checkRestrictions(pendingDie, wfPlace[0], wfPlace[1]);
        }
    }
}
