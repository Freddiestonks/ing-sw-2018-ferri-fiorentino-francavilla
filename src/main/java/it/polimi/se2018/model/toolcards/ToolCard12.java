package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class ToolCard12 extends ToolCard {

    public ToolCard12() {
        super();
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) throws InvalidPlaceException {
        Die die1 = wf.getDie(pa.getPlaceWFDie()[0][0], pa.getPlaceWFDie()[0][1]);
        Die die2 = wf.getDie(pa.getPlaceWFDie()[1][0], pa.getPlaceWFDie()[1][1]);
        // move the first die
        wf.removeDie(pa.getPlaceWFDie()[0][0], pa.getPlaceWFDie()[0][1]);
        wf.placeDie(die1, pa.getPlaceNewWFDie()[0][0], pa.getPlaceNewWFDie()[0][1]);
        // move the second die
        wf.removeDie(pa.getPlaceWFDie()[1][0], pa.getPlaceWFDie()[1][1]);
        wf.placeDie(die2, pa.getPlaceNewWFDie()[1][0], pa.getPlaceNewWFDie()[1][1]);
    }

    public boolean validAction(Model model, WindowFrame wf, PlayerAction pa) {
        Die die1 = wf.getDie(pa.getPlaceWFDie()[0][0], pa.getPlaceWFDie()[0][1]);
        Die die2 = wf.getDie(pa.getPlaceWFDie()[1][0], pa.getPlaceWFDie()[1][1]);
        Die rtDie = model.getRoundTrackDie(pa.getPosRTDie()[0], pa.getPosRTDie()[0]);
        if (die1.getColor() != rtDie.getColor()) {
            return false;
        }
        if(!wf.checkRestrictions(die1, pa.getPlaceNewWFDie()[0][0], pa.getPlaceNewWFDie()[0][1])) {
            return false;
        }
        if (die2.getColor() != rtDie.getColor()) {
            return false;
        }
        if(!wf.checkRestrictions(die2, pa.getPlaceNewWFDie()[1][0], pa.getPlaceNewWFDie()[1][1])) {
            return false;
        }
        return true;
    }
}
