package it.polimi.se2018.view;

import it.polimi.se2018.controller.ResourceLoader;
import it.polimi.se2018.model.*;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.fail;

public class TestVirtualView {
    @Test
    public void testViewsUpdate() {
        VirtualView vv = new VirtualView();
        vv.addClient(new CLIView());
        vv.addClient(new CLIView());
        vv.removeClient(1);
        vv.addClient(new CLIView());
        Model modelTest = Model.instance();
        modelTest.addObserver(vv);
        vv.reinsertClient(0, new CLIView());
        modelTest.addPlayer("Niña", new LocalModel());
        modelTest.addPlayer("Pinta", new LocalModel());
        modelTest.setLobbyGathering(false);
        Player p1 = modelTest.getPlayer(0);
        Player p2 = modelTest.getPlayer(1);
        ResourceLoader resourceLoader = new ResourceLoader();
        PatternCard testPC = resourceLoader.loadPC(0);
        PatternCard[] pcs = new PatternCard[4];
        pcs[0] = testPC;
        pcs[1] = testPC;
        pcs[2] = testPC;
        pcs[3] = testPC;
        modelTest.setPatternCards(pcs);
        WindowFrame wf = new WindowFrame(testPC,true);
        PrivObjCard privObjCard = new PrivObjCard(Color.BLUE);
        PubObjCard[] pubObjCards = new PubObjCard[3];
        pubObjCards[0] = resourceLoader.loadPubOC(0);
        pubObjCards[1] = resourceLoader.loadPubOC(1);
        pubObjCards[2] = resourceLoader.loadPubOC(2);
        modelTest.setPubOCs(pubObjCards);
        p1.setWinFrame(wf);
        p2.setWinFrame(wf);
        p1.setPrivOC(privObjCard);
        p2.setPrivOC(privObjCard);
        ToolCard[] toolCards = new ToolCard[3];
        toolCards[0] = resourceLoader.loadToolCard(0);
        toolCards[0] = resourceLoader.loadToolCard(1);
        toolCards[0] = resourceLoader.loadToolCard(2);
        modelTest.setToolCards(toolCards);
        if(!vv.checkConnection(0)) {
            fail();
        }
        modelTest.startMatch();
        for (int i = 0; i < 40; i++) {
            modelTest.updateTurn();
        }
    }
    @After
    public void reset1() {
        Model.instance().reset();
    }
}
