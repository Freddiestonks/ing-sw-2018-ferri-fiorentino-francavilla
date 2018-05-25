package it.polimi.se2018;

import it.polimi.se2018.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class TestModel {
    @Test
    public void TestAddingPlayers() throws MaxNumPlayersException {
        //tests the maximum number of players is respected.
        Model modelExample = Model.instance();
        Player p1 = new Player("Alessio");
        Player p2 = new Player("Sfidante");
        Player p3 = new Player("Tizio");
        Player p4 = new Player("Caio");
        Player p5 = new Player("Sempronio");

        try {
            modelExample.addPlayer(p1);
            modelExample.addPlayer(p2);
            modelExample.addPlayer(p3);
            modelExample.addPlayer(p4);
            modelExample.addPlayer(p5);
            fail();
        }catch (MaxNumPlayersException e){
            e.printStackTrace();
        }
    }
    @Test
    public void TestUpdatingTurn() throws InvalidTurnException, MaxNumPlayersException {
        //tests if the end of the match is respected and if the order of some casual turn are respected too.
        Model modelExample = Model.instance();
        Player p1 = new Player("Alessio");
        Player p2 = new Player("Sfidante");
        Player p3 = new Player("Tizio");
        modelExample.addPlayer(p1);
        modelExample.addPlayer(p2);
        modelExample.addPlayer(p3);

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

        try{
            modelExample.updateTurn();
            fail();
        }catch (InvalidTurnException e){
            e.printStackTrace();
        }
        modelExample = null;
    }
    @Test
    public void testCalculateScore() throws MaxNumPlayersException, InvalidPlaceException {
        Model modelTest = Model.instance();
        Player p1 = new Player("Niña");
        Player p2 = new Player("Pinta");
        Player p3 = new Player("Santa Maria");
        Player[] leaderboard;
        System.out.println(modelTest.getNumPlayers());
        WindowFrame wf = new WindowFrame(0, true);
        PrivObjCard privObjCard = new PrivObjCard(Color.BLUE);
        modelTest.addPlayer(p1);
        modelTest.addPlayer(p2);
        modelTest.addPlayer(p3);
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
        modelTest.calculateScore();
        leaderboard = modelTest.getLeaderboard();
        if (!leaderboard[0].getUsername().equals("Santa Maria")||!leaderboard[1].getUsername().equals("Pinta")
                ||!leaderboard[2].getUsername().equals("Niña")){
            fail();
        }

    }

}
