package it.polimi.se2018;

import it.polimi.se2018.model.*;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.fail;

public class TestWindowFrame {
    @Test
    public void testLoadPc(){
        int id = 0;
        //I create the first die, it will be the correct one to place in some parts of the PatternCard
        Die die = new Die(Color.GREEN);
        //The second die should instead ALWAYS be unsuitable for the tested cells
        Die die2 = new Die(Color.BLUE);
        die.setValue(6);
        die2.setValue(3);
        PatternCard testPC = null;
        WindowFrame testWF = new WindowFrame(0,true);
        //We load from the JSON the wanted PatternCard
        testPC = testWF.loadPC(id);
        //We test if the first die can fit in a shade Cell and a Color cell with the right values
        //We test too if the second die cannot fit in the same cells since id does not meet the requirements
        //Last but not least we test if the name of the cards and the difficulty levels are correct
        if (!testPC.getFront()[1][2].placeableShade(die)||
                testPC.getFront()[1][2].placeableShade(die2)||
                !testPC.getFront()[0][4].placeableColor(die)||
                testPC.getFront()[0][4].placeableColor(die2)&&
                (Objects.equals(testPC.getNameF(),"Virtus"))&&
                (Objects.equals(testPC.getNameB(),"Kaleidoscopic Dream"))&&
                (testPC.getLevelF() == 5)&&(testPC.getLevelB()==4)){
            fail();
        }
    }

    @Test
    public void testChecking() throws InvalidPlaceException {
        WindowFrame wf = new WindowFrame(0,false);
        Die die1 = new Die(Color.YELLOW);
        Die die2 = new Die(Color.GREEN);
        Die die3 = new Die(Color.YELLOW);
        die1.roll();
        die2.roll();
        die3.roll();

        try {
            if ((wf.checkRestrictions(die1,0,0))){
            }
            else fail();
        }catch (Exception e){
            e.printStackTrace();
        }


        try{
            if(wf.checkRestrictions(die2,0,1)){
                fail();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            if(wf.checkRestrictions(die3,4,3)){
                fail();
            }
        }catch(Exception e){
            e.printStackTrace();
        }


    }
}
