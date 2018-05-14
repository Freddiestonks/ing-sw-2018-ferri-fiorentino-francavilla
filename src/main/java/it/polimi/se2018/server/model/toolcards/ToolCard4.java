package it.polimi.se2018.server.model.toolcards;

import it.polimi.se2018.server.model.Die;
import it.polimi.se2018.server.model.Model;
import it.polimi.se2018.server.model.ToolCard;
import it.polimi.se2018.server.model.WindowFrame;

public class ToolCard4 extends ToolCard {

    public ToolCard4() {
        super();
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) {
        Die die1 = wf.getDie(pa.getPlaceWFDie()[0]);
        Die die2 = wf.getDie(pa.getPlaceWFDie()[1]);
        // move the first die
        wf.removeDie(pa.getPlaceWFDie()[0][0], pa.getPlaceWFDie()[0][1]);
        wf.placeDie(die1, pa.getPlaceNewWFDie()[0][0], pa.getPlaceNewWFDie()[0][1]);
        // move the second die
        wf.removeDie(pa.getPlaceWFDie()[1][0], pa.getPlaceWFDie()[1][1]);
        wf.placeDie(die2, pa.getPlaceNewWFDie()[1][0], pa.getPlaceNewWFDie()[1][1]);
    }

    public boolean validAction(Model model, WindowFrame wf, PlayerAction pa) {
        //TODO: check all the restrctions
    }
}
