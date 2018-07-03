package it.polimi.se2018.model;

import it.polimi.se2018.controller.ResourceLoader;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.fail;

public class TestPlayer {
    @Test
    public void testCalculateScore() throws InvalidPlaceException {
        Player player = new Player("Peppino");
        PrivObjCard privObjCard = new PrivObjCard(Color.BLUE);
        player.setPrivOC(privObjCard);
        PubObjCard[] pubObjCards = new PubObjCard[3];
        ResourceLoader resourceLoader = new ResourceLoader();
        Random rand = new Random();
        PatternCard testPC;
        testPC = resourceLoader.loadPC(0);
        WindowFrame wf = new WindowFrame(testPC,true);
        final int privPoints = 4;
        final int emptySpaces = 0;
        final int tokens = 5;
        int[] result = new int[10];
        player.setWinFrame(wf);

        result[0] = 12;
        result[1] = 10;
        result[2] = 10;
        result[3] = 4;
        result[4] = 6;
        result[5] = 6;
        result[6] = 4;
        result[7] = 10;
        result[8] = 15;
        result[9] = 8;

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

        for (int a = 0; a < 3; a++) {
            int id = rand.nextInt(9);
            pubObjCards[a] = resourceLoader.loadPubOC(id);
            if ((pubObjCards[a].calculateScore(wf) != result[id])) {

                fail();
            }
        }
        if(player.calculateScore(pubObjCards) != (pubObjCards[0].calculateScore(wf) + pubObjCards[1].calculateScore(wf)+
                pubObjCards[2].calculateScore(wf) + privPoints - emptySpaces + tokens)){
            fail();
        }
    }
}
