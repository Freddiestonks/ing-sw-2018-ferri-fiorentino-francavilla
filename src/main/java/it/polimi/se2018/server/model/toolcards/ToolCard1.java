package it.polimi.se2018.server.model.toolcards;

import it.polimi.se2018.server.model.Die;
import it.polimi.se2018.server.model.Model;
import it.polimi.se2018.server.model.ToolCard;
import it.polimi.se2018.server.model.WindowFrame;

import java.util.ArrayList;

public class ToolCard1 extends ToolCard {

    public ToolCard1() {
        super();
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) {
        Die die = model.getDraftPoolDie(pa.getPosDPDie());
        die.setValue(pa.getNewDieValue());
    }

    public boolean validAction(Model model, WindowFrame wf, PlayerAction pa) {
        Die die = model.getDraftPoolDie(pa.getPosDPDie());
        if((die.getValue() != pa.getNewDieValue() - 1)
               && (die.getValue() != pa.getNewDieValue() + 1)) {
            return false;
        }
        return true;
    }
}
