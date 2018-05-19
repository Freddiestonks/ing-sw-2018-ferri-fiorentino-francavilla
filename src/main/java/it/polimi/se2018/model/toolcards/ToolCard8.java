package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class ToolCard8 extends ToolCard {

    public ToolCard8() {
        super();
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) throws InvalidPlaceException {
        Die die1 = model.getDraftPoolDie(pa.getPosDPDie()[0]);
        Die die2 = model.getDraftPoolDie(pa.getPosDPDie()[1]);
        wf.placeDie(die1, pa.getPlaceDPDie()[0][0], pa.getPlaceDPDie()[0][1]);
        wf.placeDie(die2, pa.getPlaceDPDie()[1][0], pa.getPlaceDPDie()[1][1]);
    }

    public boolean validAction(Model model, WindowFrame wf, PlayerAction pa) throws InvalidPlaceException {
        Die die1 = wf.getDie(pa.getPlaceDPDie()[0][0], pa.getPlaceDPDie()[0][1]);
        Die die2 = wf.getDie(pa.getPlaceDPDie()[1][0], pa.getPlaceDPDie()[1][1]);
        if(!wf.checkRestrictions(die1, pa.getPlaceDPDie()[0][0], pa.getPlaceDPDie()[0][1])) {
            return false;
        }
        wf.placeDie(die1, pa.getPlaceDPDie()[0][0], pa.getPlaceDPDie()[0][1]);
        if(!wf.checkRestrictions(die2, pa.getPlaceDPDie()[1][0], pa.getPlaceDPDie()[1][1])) {
            return false;
        }
        return true;
    }
}
