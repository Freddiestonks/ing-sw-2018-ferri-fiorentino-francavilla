package it.polimi.se2018.server.model.toolcards;

import it.polimi.se2018.server.controller.PlayerAction;
import it.polimi.se2018.server.model.*;

public class ToolCard3 extends ToolCard {

    public ToolCard3() {
        super();
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) throws DieNotValidException {
        Die die = wf.getDie(pa.getPlaceWFDie()[0][0], pa.getPlaceWFDie()[0][1]);
        // move the die
        wf.removeDie(pa.getPlaceWFDie()[0][0], pa.getPlaceWFDie()[0][1]);
        wf.placeDie(die, pa.getPlaceNewWFDie()[0][0], pa.getPlaceWFDie()[0][1]);
    }

    public boolean validAction(Model model, WindowFrame wf, PlayerAction pa) {
        Die die = wf.getDie(pa.getPlaceWFDie()[0][0], pa.getPlaceWFDie()[0][1]);
        Cell cell = wf.getPCCell(pa.getPlaceWFDie()[0][0], pa.getPlaceWFDie()[0][1]);

        if (cell.placeableColor(die) && wf.checkNeighborhood(pa.getPlaceNewWFDie()[0][0], pa.getPlaceNewWFDie()[0][1])) {
            return true;
        }
        return false;
    }
}
