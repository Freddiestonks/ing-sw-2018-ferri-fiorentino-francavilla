package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class ToolCard9 extends ToolCard {

    public ToolCard9(String username, String description, int price,int tokens) {
        super( username,  description,  price, tokens);
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) throws InvalidPlaceException {
        Die die = model.getDraftPoolDie(pa.getPosDPDie().get(0));
        wf.placeDie(die, pa.getPlaceDPDie().get(0)[0], pa.getPlaceDPDie().get(0)[1]);
        model.updateTurn();
    }

    public boolean validAction(Model model, WindowFrame wf, PlayerAction pa) {
        Die die = model.getDraftPoolDie(pa.getPosDPDie().get(0));
        int row = pa.getPlaceDPDie().get(0)[0];
        int col = pa.getPlaceDPDie().get(1)[0];
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
