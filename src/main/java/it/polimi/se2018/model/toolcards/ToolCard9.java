package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class ToolCard9 extends ToolCard {

    public ToolCard9() {
        super();
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) throws InvalidPlaceException {
        Die die = model.getDraftPoolDie(pa.getPosDPDie()[0]);
        wf.placeDie(die, pa.getPlaceDPDie()[0][0], pa.getPlaceDPDie()[0][1]);
    }

    public boolean validAction(Model model, WindowFrame wf, PlayerAction pa) {
        Die die = model.getDraftPoolDie(pa.getPosDPDie()[0]);
        int row = pa.getPlaceDPDie()[0][0];
        int col = pa.getPlaceDPDie()[1][0];
        Cell cell = wf.getPCCell(row, col);
        if(!cell.placeableShade(die)
           || !cell.placeableColor(die)
           || !wf.crossCheck(die, row, col)
           || (wf.getDie(row, col) != null)) {
            return false;
        }
        return true;
    }
}
