package it.polimi.se2018.model.tceffects;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class MoveTwoDice extends AbstractTCEffect {

    public MoveTwoDice() {
        singleAction = true;
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) {
        Die die1 = wf.getDie(pa.getPlaceWFDie().get(0)[0], pa.getPlaceWFDie().get(0)[1]);
        Die die2 = wf.getDie(pa.getPlaceWFDie().get(1)[0], pa.getPlaceWFDie().get(1)[1]);
        // move the first die
        wf.removeDie(pa.getPlaceWFDie().get(0)[0], pa.getPlaceWFDie().get(0)[1]);
        wf.placeDie(die1, pa.getPlaceNewWFDie().get(0)[0], pa.getPlaceNewWFDie().get(0)[1]);
        // move the second die
        wf.removeDie(pa.getPlaceWFDie().get(1)[0], pa.getPlaceWFDie().get(1)[1]);
        wf.placeDie(die2, pa.getPlaceNewWFDie().get(1)[0], pa.getPlaceNewWFDie().get(1)[1]);
    }

    public boolean validAction(ModelInterface model, WindowFrame wf, PlayerAction pa) {
        if((pa.getPlaceWFDie().size() < 2) || (pa.getPlaceNewWFDie().size() < 2)) {
            return false;
        }
        // clone the WindowFrame
        wf = new WindowFrame(wf);
        Die die1 = wf.removeDie(pa.getPlaceWFDie().get(0)[0], pa.getPlaceWFDie().get(0)[1]);
        if (!wf.checkRestrictions(die1, pa.getPlaceNewWFDie().get(0)[0], pa.getPlaceNewWFDie().get(0)[1])) {
            return false;
        }
        Die die2 = wf.removeDie(pa.getPlaceWFDie().get(1)[0], pa.getPlaceWFDie().get(1)[1]);
        if (!wf.checkRestrictions(die2, pa.getPlaceNewWFDie().get(1)[0], pa.getPlaceNewWFDie().get(1)[1])) {
            return false;
        }
        return true;
    }
}
