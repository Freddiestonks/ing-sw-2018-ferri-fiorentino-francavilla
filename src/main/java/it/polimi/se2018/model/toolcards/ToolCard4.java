package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class ToolCard4 extends ToolCard {

    public ToolCard4(String username, String description, int price, int tokens) {
        super(username,description,price,tokens);
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) {
        Die die1 = wf.getDie(pa.getPlaceWFDie().get(0)[0], pa.getPlaceWFDie().get(0)[1]);
        Die die2 = wf.getDie(pa.getPlaceWFDie().get(1)[0], pa.getPlaceWFDie().get(1)[1]);
        // move the first die
        wf.removeDie(pa.getPlaceWFDie().get(0)[0], pa.getPlaceWFDie().get(0)[1]);
        wf.placeDie(die1, pa.getPlaceNewWFDie().get(0)[0], pa.getPlaceNewWFDie().get(0)[1]);
        // move the second die
        wf.removeDie(pa.getPlaceWFDie().get(1)[0], pa.getPlaceWFDie().get(1)[1]);
        wf.placeDie(die2, pa.getPlaceNewWFDie().get(1)[0], pa.getPlaceNewWFDie().get(1)[1]);
    }

    public boolean validAction(Model model, WindowFrame wf, PlayerAction pa) {
        // clone the WindowFrame
        wf = new WindowFrame(wf);
        Die die1 = wf.removeDie(pa.getPlaceWFDie().get(0)[0], pa.getPlaceWFDie().get(0)[1]);
        if (!wf.checkRestrictions(die1, pa.getPlaceWFDie().get(0)[0], pa.getPlaceWFDie().get(0)[1])) {
            return false;
        }
        Die die2 = wf.removeDie(pa.getPlaceWFDie().get(1)[0], pa.getPlaceWFDie().get(1)[1]);
        if (!wf.checkRestrictions(die2, pa.getPlaceWFDie().get(1)[0], pa.getPlaceWFDie().get(1)[1])) {
            return false;
        }
        return true;
    }
}
