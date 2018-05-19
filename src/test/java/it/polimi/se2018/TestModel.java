package it.polimi.se2018;

import it.polimi.se2018.model.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestModel {
    @Test
    public void TestAddingPlayers() throws MaxNumPlayersException {
        //tests the maximum number of players is respected.
        Model ModelExample = Model.instance();
        Player p1 = new Player("Alessio");
        Player p2 = new Player("Sfidante");
        Player p3 = new Player("Tizio");
        Player p4 = new Player("Caio");
        Player p5 = new Player("Sempronio");

        try {
            ModelExample.addPlayer(p1);
            ModelExample.addPlayer(p2);
            ModelExample.addPlayer(p3);
            ModelExample.addPlayer(p4);
            ModelExample.addPlayer(p5);
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
            System.out.printf("Turn:%2d   Round:",modelExample.getTurn());
            System.out.println(modelExample.getRound());

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

    }
}
