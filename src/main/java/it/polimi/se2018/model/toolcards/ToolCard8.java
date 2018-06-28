package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;

public class ToolCard8 extends ToolCard {

    public ToolCard8(String name, String description, int price) {
        super(name, description, price);
    }

    public void performAction(Model model, WindowFrame wf, PlayerAction pa) {
        Die die1 = model.getDraftPoolDie(pa.getPosDPDie().get(0));
        Die die2 = model.getDraftPoolDie(pa.getPosDPDie().get(1));
        model.removeDraftPoolDie(die1);
        model.removeDraftPoolDie(die2);
        wf.placeDie(die1, pa.getPlaceDPDie().get(0)[0], pa.getPlaceDPDie().get(0)[1]);
        wf.placeDie(die2, pa.getPlaceDPDie().get(1)[0], pa.getPlaceDPDie().get(1)[1]);
        Player player = model.getPlayer(model.getTurn());
        player.setSkip(true);
        model.updateTurn();
    }

    public boolean validAction(ModelInterface model, WindowFrame wf, PlayerAction pa) {
        if(pa.getPosDPDie().size() < 2) {
            return false;
        }
        // clone the WindowFrame
        wf = new WindowFrame(wf);
        Die die1 = model.getDraftPoolDie(pa.getPosDPDie().get(0));
        Die die2 = model.getDraftPoolDie(pa.getPosDPDie().get(1));
        if(!wf.checkRestrictions(die1, pa.getPlaceDPDie().get(0)[0], pa.getPlaceDPDie().get(0)[1])) {
            return false;
        }
        wf.placeDie(die1, pa.getPlaceDPDie().get(0)[0], pa.getPlaceDPDie().get(0)[1]);
        return wf.checkRestrictions(die2, pa.getPlaceDPDie().get(1)[0], pa.getPlaceDPDie().get(1)[1]);
    }
}
