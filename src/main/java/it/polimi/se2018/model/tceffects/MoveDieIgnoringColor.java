package it.polimi.se2018.model.tceffects;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class MoveDieIgnoringColor extends MoveDie {

    public boolean validAction(ModelInterface model, WindowFrame wf, PlayerAction pa) {
        if(!super.validAction(model, wf, pa)) {
            return false;
        }
        // clone the WindowFrame
        wf = new WindowFrame(wf);
        Die die = wf.removeDie(pa.getPlaceWFDie().get(0)[0], pa.getPlaceWFDie().get(0)[1]);
        int row = pa.getPlaceNewWFDie().get(0)[0];
        int col = pa.getPlaceNewWFDie().get(0)[1];
        Cell cell = wf.getPCCell(row, col);
        if(!cell.placeableColor(die)
           || !wf.crossCheck(die, row, col)
           || !wf.touchingCheck(row, col)) {
            return false;
        }
        return true;
    }
}
