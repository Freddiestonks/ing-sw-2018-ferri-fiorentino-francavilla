package it.polimi.se2018.model.tceffects;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class MoveDiceAsRTDieColor extends MoveTwoDice {

    @Override
    public boolean validAction(ModelInterface model, WindowFrame wf, PlayerAction pa) {
        if(!super.validAction(model, wf, pa)) {
            return false;
        }
        if(pa.getPosRTDie().isEmpty()) {
            return false;
        }
        Die die1 = wf.getDie(pa.getPlaceWFDie().get(0)[0], pa.getPlaceWFDie().get(0)[1]);
        Die die2 = wf.getDie(pa.getPlaceWFDie().get(1)[0], pa.getPlaceWFDie().get(1)[1]);
        Die rtDie = model.getRoundTrackDie(pa.getPosRTDie().get(0)[0], pa.getPosRTDie().get(0)[1]);
        if (die1.getColor() != rtDie.getColor()) {
            return false;
        }
        if (die2.getColor() != rtDie.getColor()) {
            return false;
        }
        return true;
    }
}
