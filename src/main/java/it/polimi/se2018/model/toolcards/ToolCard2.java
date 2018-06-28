package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class ToolCard2 extends ToolCard {

    public ToolCard2(String name, String description, int price) {
        super(name, description, price);
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
        Die die = wf.getDie(pa.getPlaceWFDie().get(0)[0], pa.getPlaceWFDie().get(0)[1]);
        Cell cell = wf.getPCCell(pa.getPlaceWFDie().get(0)[0], pa.getPlaceWFDie().get(0)[1]);
        int row = pa.getPlaceNewWFDie().get(0)[0];
        int col = pa.getPlaceNewWFDie().get(0)[1];
        wf.removeDie(row, col);
        if(!cell.placeableShade(die)
           || !wf.crossCheck(die, row, col)
           || !wf.touchingCheck(row, col)
           || (wf.getDie(row, col) != null)) {
            return false;
        }
        return true;
    }
}
