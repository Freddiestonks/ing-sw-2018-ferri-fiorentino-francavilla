package it.polimi.se2018.model;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.controller.ResourceLoader;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.fail;

public class TestModel {
    @Test
    public void testAddingPlayers() throws MaxNumPlayersException {
        //tests the maximum number of players is respected.
        Model modelExample = Model.instance();
        modelExample.addPlayer("Alessio", null);
        modelExample.addPlayer("Sfidante", null);
        modelExample.addPlayer("Tizio", null);
        modelExample.addPlayer("Caio", null);
    }
    @After
    public void reset1() {
        Model.instance().reset();
    }

    @Test
    public void testUpdatingTurn() throws MaxNumPlayersException {
        //tests if the end of the match is respected and if the order of some casual turn are respected too.
        Model modelExample = Model.instance();
        modelExample.addPlayer("Alessio", new LocalModel());
        modelExample.addPlayer("Sfidante", new LocalModel());
        modelExample.addPlayer("Tizio", new LocalModel());

        for(int i=1; i<=59; i++){
            if(i==3){
                if(!(modelExample.getTurn()==3)){
                    fail();
                }
            }
            if(i==4){
                if(!(modelExample.getTurn()==3)){
                    fail();
                }
            }
            if(i==6){
                if(!(modelExample.getTurn()==1)){
                    fail();
                }
            }
            modelExample.updateTurn();
        }
    }
    @After
    public void reset2() {
        Model.instance().reset();
    }

    @Test
    public void testCalculateScore() throws MaxNumPlayersException, InvalidPlaceException {
        Model modelTest = Model.instance();
        modelTest.addPlayer("Niña", new LocalModel());
        modelTest.addPlayer("Pinta", new LocalModel());
        modelTest.addPlayer("Santa Maria", new LocalModel());
        Player p1 = modelTest.getPlayer(0);
        Player p2 = modelTest.getPlayer(1);
        Player p3 = modelTest.getPlayer(2);
        ResourceLoader resourceLoader = new ResourceLoader();
        PatternCard testPC1 = resourceLoader.loadPC(0);
        PatternCard testPC2 = resourceLoader.loadPC(0);
        PatternCard testPC3 = resourceLoader.loadPC(2);
        WindowFrame wf1 = new WindowFrame(testPC1,true);
        WindowFrame wf2 = new WindowFrame(testPC2,false);
        WindowFrame wf3 = new WindowFrame(testPC3,true);
        PrivObjCard privObjCard = new PrivObjCard(Color.BLUE);
        PubObjCard[] pubObjCards = new PubObjCard[3];
        pubObjCards[0] = resourceLoader.loadPubOC(0);
        pubObjCards[1] = resourceLoader.loadPubOC(1);
        pubObjCards[2] = resourceLoader.loadPubOC(2);
        modelTest.setPubOCs(pubObjCards);
        p1.setWinFrame(wf1);
        p2.setWinFrame(wf2);
        p3.setWinFrame(wf3);
        p1.setPrivOC(privObjCard);
        p2.setPrivOC(privObjCard);
        p3.setPrivOC(privObjCard);

        Die[][] dice = new Die[4][5];
        dice[0][0] = new Die(Color.PURPLE);
        dice[0][0].setValue(4);
        dice[0][1] = new Die(Color.BLUE);
        dice[0][1].setValue(3);
        dice[0][2] = new Die(Color.RED);
        dice[0][2].setValue(2);
        dice[0][3] = new Die(Color.YELLOW);
        dice[0][3].setValue(5);
        dice[0][4] = new Die(Color.GREEN);
        dice[0][4].setValue(6);
        dice[1][0] = new Die(Color.RED);
        dice[1][0].setValue(2);
        dice[1][1] = new Die(Color.YELLOW);
        dice[1][1].setValue(5);
        dice[1][2] = new Die(Color.BLUE);
        dice[1][2].setValue(6);
        dice[1][3] = new Die(Color.GREEN);
        dice[1][3].setValue(3);
        dice[1][4] = new Die(Color.PURPLE);
        dice[1][4].setValue(2);
        dice[2][0] = new Die(Color.YELLOW);
        dice[2][0].setValue(1);
        dice[2][1] = new Die(Color.BLUE);
        dice[2][1].setValue(3);
        dice[2][2] = new Die(Color.GREEN);
        dice[2][2].setValue(2);
        dice[2][3] = new Die(Color.YELLOW);
        dice[2][3].setValue(4);
        dice[2][4] = new Die(Color.GREEN);
        dice[2][4].setValue(1);
        dice[3][0] = new Die(Color.BLUE);
        dice[3][0].setValue(5);
        dice[3][1] = new Die(Color.GREEN);
        dice[3][1].setValue(4);
        dice[3][2] = new Die(Color.YELLOW);
        dice[3][2].setValue(1);
        dice[3][3] = new Die(Color.GREEN);
        dice[3][3].setValue(3);
        dice[3][4] = new Die(Color.YELLOW);
        dice[3][4].setValue(2);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                wf1.placeDie(dice[i][j], i, j);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                wf2.placeDie(dice[i][j], i, j);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                wf3.placeDie(dice[i][j], i, j);
            }
        }
        ArrayList<Player> leaderboard;
        for(int i = 0; i < 60; i++) {
            modelTest.updateTurn();
        }
        if(!modelTest.isOver()) {
            fail();
        }
        leaderboard = modelTest.getLeaderBoard();
        if (!leaderboard.get(2).getUsername().equals("Santa Maria")||!leaderboard.get(1).getUsername().equals("Pinta")
                ||!leaderboard.get(0).getUsername().equals("Niña")){
            fail();
        }
    }
    @After
    public void reset3() {
        Model.instance().reset();
    }

    @Test
    public void testPlayerSetup() {
        Model modelTest = Model.instance();
        modelTest.addPlayer("Niña", new LocalModel());
        modelTest.addPlayer("Pinta", new LocalModel());
        modelTest.addPlayer("Santa Maria", new LocalModel());
        Player p1 = modelTest.getPlayer(0);
        Player p2 = modelTest.getPlayer(1);
        Player p3 = modelTest.getPlayer(2);
        ResourceLoader resourceLoader = new ResourceLoader();
        PatternCard testPC = resourceLoader.loadPC(0);
        WindowFrame wf = new WindowFrame(testPC,true);
        PubObjCard[] pubObjCards = new PubObjCard[3];
        pubObjCards[0] = resourceLoader.loadPubOC(0);
        pubObjCards[1] = resourceLoader.loadPubOC(1);
        pubObjCards[2] = resourceLoader.loadPubOC(2);
        modelTest.setPubOCs(pubObjCards);
        p1.setWinFrame(wf);
        p2.setWinFrame(wf);
        p3.setWinFrame(wf);
        modelTest.playersSetup();
        if((p1.getPrivObjCard() == null) || !modelTest.playerHasChosenPC(0)) {
            fail();
        }
        if((p2.getPrivObjCard() == null) || !modelTest.playerHasChosenPC(1)) {
            fail();
        }
        if((p3.getPrivObjCard() == null) || !modelTest.playerHasChosenPC(2)) {
            fail();
        }
    }
    @After
    public void reset4() {
        Model.instance().reset();
    }

    @Test
    public void testStartMatch() {
        Model modelTest = Model.instance();
        modelTest.addPlayer("Niña", new LocalModel());
        modelTest.addPlayer("Pinta", new LocalModel());
        modelTest.addPlayer("Santa Maria", new LocalModel());
        Player p1 = modelTest.getPlayer(0);
        Player p2 = modelTest.getPlayer(1);
        Player p3 = modelTest.getPlayer(2);
        ResourceLoader resourceLoader = new ResourceLoader();
        PatternCard testPC = resourceLoader.loadPC(0);
        WindowFrame wf = new WindowFrame(testPC,true);
        PrivObjCard privObjCard = new PrivObjCard(Color.BLUE);
        PubObjCard[] pubObjCards = new PubObjCard[3];
        pubObjCards[0] = resourceLoader.loadPubOC(0);
        pubObjCards[1] = resourceLoader.loadPubOC(1);
        pubObjCards[2] = resourceLoader.loadPubOC(2);
        modelTest.setPubOCs(pubObjCards);
        p1.setWinFrame(wf);
        p2.setWinFrame(wf);
        p3.setWinFrame(wf);
        p1.setPrivOC(privObjCard);
        p2.setPrivOC(privObjCard);
        p3.setPrivOC(privObjCard);
        ToolCard[] toolCards = new ToolCard[3];
        toolCards[0] = resourceLoader.loadToolCard(5);
        toolCards[1] = resourceLoader.loadToolCard(1);
        toolCards[2] = resourceLoader.loadToolCard(2);
        modelTest.setToolCards(toolCards);
        modelTest.startMatch();
        for(int i = 0; i < 12; i++) {
            modelTest.updateTurn();
        }
        if(!modelTest.playerHasChosenPC(1)) {
            fail();
        }
        modelTest.checkConnection(0);
        modelTest.removePlayer(0);
        modelTest.reinsertPlayer(0, new LocalModel());
        Die die = new Die(Color.GREEN);
        die.setValue(1);
        modelTest.placeWFDie(0, die, 1, 0);
        PlayerAction pa = new PlayerAction();
        pa.setIdToolCard(1);
        pa.addPosDPDie(0);
        modelTest.performToolCard(1, pa);
    }
    @After
    public void reset5() {
        Model.instance().reset();
    }

    @Test
    public void testRoundTrack() {
        Model modelTest = Model.instance();
        Die die = new Die(Color.YELLOW);
        die.setValue(6);
        modelTest.addRoundTrackDie(die, 1);
        if(!modelTest.getRoundTrackDie(1, 0).equals(die)) {
            fail();
        }
        die = new Die(Color.RED);
        die.setValue(2);
        modelTest.addRoundTrackDie(die, 1);
        if(!modelTest.getRoundTrackDie(1, 1).equals(die)) {
            fail();
        }
        die = new Die(Color.YELLOW);
        die.setValue(5);
        modelTest.addRoundTrackDie(die, 3);
        if(!modelTest.getRoundTrackDie(3, 0).equals(die)) {
            fail();
        }
        die = new Die(Color.BLUE);
        die.setValue(6);
        modelTest.addRoundTrackDie(die, 3);
        if(!modelTest.getRoundTrackDie(3, 1).equals(die)) {
            fail();
        }
        die = new Die(Color.GREEN);
        die.setValue(3);
        modelTest.addRoundTrackDie(die, 5);
        if(!modelTest.getRoundTrackDie(5, 0).equals(die)) {
            fail();
        }
        if(modelTest.getRoundTrackSize(5) != 1) {
            fail();
        }
        Die die1 = modelTest.removeRoundTrackDie(5, 0);
        if(!die1.equals(die)) {
            fail();
        }
        if(modelTest.getRoundTrackSize(3) != 2) {
            fail();
        }
        if(modelTest.getRoundTrackSize(5) != 0) {
            fail();
        }

    }
    @After
    public void reset6() {
        Model.instance().reset();
    }

    @Test
    public void testDraftPool() {
        Model modelTest = Model.instance();
        Die die1 = new Die(Color.YELLOW);
        die1.setValue(6);
        modelTest.addDraftPoolDie(die1);
        if(!modelTest.getDraftPoolDie( 0).equals(die1)) {
            fail();
        }
        Die die = new Die(Color.RED);
        die.setValue(2);
        modelTest.addDraftPoolDie(die);
        if(!modelTest.getDraftPoolDie(1).equals(die)) {
            fail();
        }
        die = new Die(Color.YELLOW);
        die.setValue(5);
        modelTest.addDraftPoolDie(die);
        if(!modelTest.getDraftPoolDie(2).equals(die)) {
            fail();
        }
        Die die2 = modelTest.removeDraftPoolDie(2);
        if(!die2.equals(die)) {
            fail();
        }
        if(modelTest.getDraftPoolSize() != 2) {
            fail();
        }
        modelTest.removeDraftPoolDie(die1);
        if(modelTest.getDraftPoolSize() != 1) {
            fail();
        }
    }
    @After
    public void reset7() {
        Model.instance().reset();
    }
}
