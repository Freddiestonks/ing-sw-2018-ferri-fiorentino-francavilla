package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class ToolCard3 extends ToolCard {

    public ToolCard3() {
        super();
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) throws InvalidPlaceException {
        Die die = wf.getDie(pa.getPlaceWFDie()[0][0], pa.getPlaceWFDie()[0][1]);
        // move the die
        wf.removeDie(pa.getPlaceWFDie()[0][0], pa.getPlaceWFDie()[0][1]);
        wf.placeDie(die, pa.getPlaceNewWFDie()[0][0], pa.getPlaceWFDie()[0][1]);
    }

    public boolean validAction(Model model, WindowFrame wf, PlayerAction pa) {
        Die die = wf.getDie(pa.getPlaceWFDie()[0][0], pa.getPlaceWFDie()[0][1]);
        Cell cell = wf.getPCCell(pa.getPlaceWFDie()[0][0], pa.getPlaceWFDie()[0][1]);
        int row = pa.getPlaceNewWFDie()[0][0];
        int col = pa.getPlaceNewWFDie()[0][1];
        wf.removeDie(row, col);
        if(!cell.placeableColor(die)
           || !wf.crossCheck(die, row, col)
           || !wf.touchingCheck(row, col)
           || (wf.getDie(row, col) != null)) {
            return false;
        }
        return true;
    }
}
