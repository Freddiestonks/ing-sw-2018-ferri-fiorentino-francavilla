package it.polimi.se2018.model.tceffects;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public abstract class MoveDie extends AbstractTCEffect {

    public MoveDie() {
        singleAction = true;
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) throws InvalidPlaceException {
        Die die = wf.getDie(pa.getPlaceWFDie().get(0)[0], pa.getPlaceWFDie().get(0)[1]);
        // move the die
        wf.removeDie(pa.getPlaceWFDie().get(0)[0], pa.getPlaceWFDie().get(0)[1]);
        wf.placeDie(die, pa.getPlaceNewWFDie().get(0)[0], pa.getPlaceWFDie().get(0)[1]);
    }

    public boolean validAction(ModelInterface model, WindowFrame wf, PlayerAction pa) {
        if(pa.getPlaceWFDie().isEmpty() || pa.getPlaceNewWFDie().isEmpty()) {
            return false;
        }
        // clone the WindowFrame
        wf = new WindowFrame(wf);
        int row = pa.getPlaceNewWFDie().get(0)[0];
        int col = pa.getPlaceNewWFDie().get(0)[1];
        return (wf.getDie(row, col) == null);
    }

}
