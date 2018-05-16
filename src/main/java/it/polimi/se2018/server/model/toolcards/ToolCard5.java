package it.polimi.se2018.server.model.toolcards;

import it.polimi.se2018.server.controller.PlayerAction;
import it.polimi.se2018.server.model.Die;
import it.polimi.se2018.server.model.Model;
import it.polimi.se2018.server.model.ToolCard;
import it.polimi.se2018.server.model.WindowFrame;

public class ToolCard5 extends ToolCard {

    public ToolCard5() {
        super();
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) {
        Die dpDie = model.getDraftPoolDie(pa.getPosDPDie()[0]);
        model.removeDraftPoolDie(pa.getPosDPDie()[0]);
        Die rtDie = model.getRoundTrackDie(pa.getPosRTDie()[0], pa.getPosRTDie()[1]);
        model.removeRoundTrackDie(pa.getPosRTDie()[0], pa.getPosRTDie()[1]);
        model.addDraftPoolDie(rtDie);
        model.addRoundTrackDie(dpDie, pa.getPosRTDie()[0]);
    }

    public boolean validAction(Model model, WindowFrame wf, PlayerAction pa) {
        return true;
    }
}
