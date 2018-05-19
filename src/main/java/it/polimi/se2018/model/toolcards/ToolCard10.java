package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class ToolCard10 extends ToolCard {

    public ToolCard10() {

    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) throws InvalidPlaceException {
        Die die = model.getDraftPoolDie(pa.getPosDPDie()[0]);
        die.setValue(7 - die.getValue());
        wf.placeDie(die, pa.getPlaceDPDie()[0][0], pa.getPlaceDPDie()[0][1]);
    }

    public boolean validAction(Model model, WindowFrame wf, PlayerAction pa) {
        Die die = wf.getDie(pa.getPlaceDPDie()[0][0], pa.getPlaceDPDie()[0][1]);
        //TODO: die.clone();
        die.setValue(7 - die.getValue());
        return wf.checkRestrictions(die, pa.getPlaceDPDie()[0][0], pa.getPlaceDPDie()[0][1]);
    }
}
