package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class ToolCard9 extends ToolCard {

    public ToolCard9(String name, String description, int price) {
        super(name, description, price);
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) throws InvalidPlaceException {
        Die die = model.removeDraftPoolDie(pa.getPosDPDie().get(0));
        wf.placeDie(die, pa.getPlaceDPDie().get(0)[0], pa.getPlaceDPDie().get(0)[1]);
        model.updateTurn();
    }

    public boolean validAction(ModelInterface model, WindowFrame wf, PlayerAction pa) {
        if(pa.getPosDPDie().isEmpty() || pa.getPlaceDPDie().isEmpty()) {
            return false;
        }
        Die die = model.getDraftPoolDie(pa.getPosDPDie().get(0));
        int row = pa.getPlaceDPDie().get(0)[0];
        int col = pa.getPlaceDPDie().get(0)[1];
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
