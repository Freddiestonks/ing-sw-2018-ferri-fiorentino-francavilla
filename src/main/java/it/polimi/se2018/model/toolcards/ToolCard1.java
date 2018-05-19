package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class ToolCard1 extends ToolCard {

    public ToolCard1() {
        super();
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) throws InvalidPlaceException {
        Die die = model.getDraftPoolDie(pa.getPosDPDie()[0]);
        die.setValue(pa.getNewDieValue());
        wf.placeDie(die, pa.getPlaceDPDie()[0][0], pa.getPlaceDPDie()[0][1]);
    }

    public boolean validAction(Model model, WindowFrame wf, PlayerAction pa) {
        Die die = model.getDraftPoolDie(pa.getPosDPDie()[0]);
        int row = pa.getPlaceWFDie()[0][0];
        int col = pa.getPlaceWFDie()[0][1];
        Cell cell = wf.getPCCell(row, col);
        if((die.getValue() != pa.getNewDieValue() - 1)
           && (die.getValue() != pa.getNewDieValue() + 1)) {
            return false;
        }
        return wf.checkRestrictions(die, row, col);
    }
}
