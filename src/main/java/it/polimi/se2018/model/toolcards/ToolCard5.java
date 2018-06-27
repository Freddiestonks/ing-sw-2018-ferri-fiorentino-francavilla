package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.model.WindowFrame;

public class ToolCard5 extends ToolCard {

    public ToolCard5(String name, String description, int price, int tokens) {
        super(name, description, price, tokens);
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) {
        Die dpDie = model.removeDraftPoolDie(pa.getPosDPDie().get(0));
        Die rtDie = model.removeRoundTrackDie(pa.getPosRTDie().get(0)[0], pa.getPosRTDie().get(0)[1]);
        model.addDraftPoolDie(rtDie);
        model.addRoundTrackDie(dpDie, pa.getPosRTDie().get(0)[0]);
    }

    public boolean validAction(Model model, WindowFrame wf, PlayerAction pa) {
        if(pa.getPosDPDie().isEmpty() || pa.getPosRTDie().isEmpty()) {
            return false;
        }
        return true;
    }
}
