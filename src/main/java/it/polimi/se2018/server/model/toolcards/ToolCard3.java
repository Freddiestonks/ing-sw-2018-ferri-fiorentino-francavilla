package it.polimi.se2018.server.model.toolcards;

import it.polimi.se2018.server.model.Die;
import it.polimi.se2018.server.model.Model;
import it.polimi.se2018.server.model.ToolCard;
import it.polimi.se2018.server.model.WindowFrame;

public class ToolCard3 extends ToolCard {

    public ToolCard3() {
        super();
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) {
        Die die = wf.getDie(pa.getPlaceWFDie()[0]);
        // move the die
        wf.removeDie(pa.getPlaceWFDie()[0][0], pa.getPlaceWFDie()[0][1]);
        wf.placeDie(die, pa.getPlaceNewWFDie()[0][0], pa.getPlaceWFDie()[0][1]);
    }

    public boolean validAction(Model model, WindowFrame wf, PlayerAction pa) {
        //TODO: check only color and neighborhod restrctions
    }
}
