package it.polimi.se2018.model.tceffects;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class SwapDPAndRTDice extends AbstractTCEffect {

    public SwapDPAndRTDice() {
        singleAction = true;
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) {
        Die dpDie = model.removeDraftPoolDie(pa.getPosDPDie().get(0));
        Die rtDie = model.removeRoundTrackDie(pa.getPosRTDie().get(0)[0], pa.getPosRTDie().get(0)[1]);
        model.addDraftPoolDie(rtDie);
        model.addRoundTrackDie(dpDie, pa.getPosRTDie().get(0)[0]);
    }

    public boolean validAction(ModelInterface model, WindowFrame wf, PlayerAction pa) {
        if(pa.getPosDPDie().isEmpty() || pa.getPosRTDie().isEmpty()) {
            return false;
        }
        return true;
    }
}
