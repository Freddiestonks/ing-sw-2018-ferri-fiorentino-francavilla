package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class ToolCard6 extends ToolCard {

    public ToolCard6(String name, String description, int price, int tokens) {
        super(name, description, price, tokens);
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) throws InvalidPlaceException {
        if(!pendingAction) {
            Die die = model.getDraftPoolDie(pa.getPosDPDie().get(0));
            die.roll();
            setPendingDie(die);
            pendingAction = true;
        }
        else {
            Die die = getPendingDie();
            model.removeDraftPoolDie(die);
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
            Die die = getPendingDie();
            return wf.checkRestrictions(die, pa.getPlaceDPDie().get(0)[0], pa.getPlaceDPDie().get(0)[1]);
        }
    }
}
