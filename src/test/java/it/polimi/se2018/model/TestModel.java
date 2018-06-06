package it.polimi.se2018.model;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.fail;

public class TestModel {
    @Test
    public void TestAddingPlayers() throws MaxNumPlayersException {
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
    public void TestUpdatingTurn() throws InvalidTurnException, MaxNumPlayersException {
        //tests if the end of the match is respected and if the order of some casual turn are respected too.
        Model modelExample = Model.instance();
        modelExample.addPlayer("Alessio", null);
        modelExample.addPlayer("Sfidante", null);
        modelExample.addPlayer("Tizio", null);

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
        modelTest.addPlayer("Niña", null);
        modelTest.addPlayer("Pinta", null);
        modelTest.addPlayer("Santa Maria", null);
        Player p1 = modelTest.getPlayer(0);
        Player p2 = modelTest.getPlayer(1);
        Player p3 = modelTest.getPlayer(2);
        //Player[] leaderboard;
        System.out.println(modelTest.getNumPlayers());
        WindowFrame wf = new WindowFrame(0, true);
        PrivObjCard privObjCard = new PrivObjCard(Color.BLUE);
        p1.setWinFrame(wf);
        p2.setWinFrame(wf);
        p3.setWinFrame(wf);
        p1.setPrivOC(privObjCard);
        p2.setPrivOC(privObjCard);
        p3.setPrivOC(privObjCard);
        p1.setTokens(1);
        p2.setTokens(2);
        p3.setTokens(3);

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
                wf.placeDie(dice[i][j], i, j);
            }
        }
        modelTest.calculateScore();/*
        leaderboard = modelTest.getLeaderboard();
        if (!leaderboard[0].getUsername().equals("Santa Maria")||!leaderboard[1].getUsername().equals("Pinta")
                ||!leaderboard[2].getUsername().equals("Niña")){
            fail();
        }*/

    }
    @After
    public void reset3() {
        Model.instance().reset();
    }

}
