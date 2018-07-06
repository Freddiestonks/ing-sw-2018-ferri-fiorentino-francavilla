package it.polimi.se2018.model;

import it.polimi.se2018.controller.ResourceLoader;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.fail;

public class TestLocalModel {
    @Test
    public void testToolCardConstraint() {
        LocalModel lm = new LocalModel();
        ResourceLoader resourceLoader = new ResourceLoader();
        ToolCard[] toolCards = new ToolCard[3];
        toolCards[0] = resourceLoader.loadToolCard(2);
        toolCards[1] = resourceLoader.loadToolCard(6);
        toolCards[2] = resourceLoader.loadToolCard(9);
        lm.setToolCards(toolCards);
        lm.setToolCardUsed(false);
        int playerIndex = 2;
        int tokens = 3;
        lm.setPlayerIndex(playerIndex);
        lm.setTokens(tokens);
        if(!lm.playerCanUseToolCard(playerIndex, 3)) {
            fail();
        }
        tokens = 1;
        lm.setTokens(tokens);
        toolCards[2].putTokens(1);
        if(lm.playerCanUseToolCard(playerIndex, 3)) {
            fail();
        }
        tokens = 7;
        lm.setTokens(tokens);
        lm.setToolCardUsed(true);
        if(lm.playerCanUseToolCard(playerIndex, 3)) {
            fail();
        }
    }

    @Test
    public void testTurnUpdate() {
        LocalModel lm = new LocalModel();
        int turn = 3;
        int round = 1;
        boolean backward = false;
        lm.updateTurn(round, turn, backward);
        if(lm.getTurn() != turn) {
            fail();
        }
        if(lm.getRound() != round) {
            fail();
        }
        if(lm.isBackward() != backward) {
            fail();
        }
        turn = 1;
        round = 10;
        backward = true;
        lm.updateTurn(round, turn, backward);
        if(lm.getTurn() != turn) {
            fail();
        }
        if(lm.getRound() != round) {
            fail();
        }
        if(lm.isBackward() != backward) {
            fail();
        }
    }

    @Test
    public void testRoundTrack() {
        LocalModel lm = new LocalModel();
        ArrayList<ArrayList<Die>> roundTrack = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            roundTrack.add(new ArrayList<>());
        }
        Die die = new Die(Color.GREEN);
        roundTrack.get(2).add(new Die(Color.GREEN));
        roundTrack.get(2).add(die);
        roundTrack.get(2).add(new Die(Color.RED));
        lm.setRoundTrack(roundTrack);
        if(!lm.getRoundTrackDie(3, 1).equals(die)) {
            fail();
        }
    }
}
