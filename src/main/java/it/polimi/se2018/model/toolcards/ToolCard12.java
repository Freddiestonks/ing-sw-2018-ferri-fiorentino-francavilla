package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class ToolCard12 extends ToolCard {

    public ToolCard12(String name, String description, int price, int tokens) {
        super(name, description, price, tokens);
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) throws InvalidPlaceException {
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
        Die die1 = wf.getDie(pa.getPlaceWFDie().get(0)[0], pa.getPlaceWFDie().get(0)[1]);
        Die die2 = wf.getDie(pa.getPlaceWFDie().get(1)[0], pa.getPlaceWFDie().get(1)[1]);
        Die rtDie = model.getRoundTrackDie(pa.getPosRTDie().get(0)[0], pa.getPosRTDie().get(0)[1]);
        if (die1.getColor() != rtDie.getColor()) {
            return false;
        }
        if(!wf.checkRestrictions(die1, pa.getPlaceNewWFDie().get(0)[0], pa.getPlaceNewWFDie().get(0)[1])) {
            return false;
        }
        if (die2.getColor() != rtDie.getColor()) {
            return false;
        }
        if(!wf.checkRestrictions(die2, pa.getPlaceNewWFDie().get(1)[0], pa.getPlaceNewWFDie().get(1)[1])) {
            return false;
        }
        return true;
    }
}
