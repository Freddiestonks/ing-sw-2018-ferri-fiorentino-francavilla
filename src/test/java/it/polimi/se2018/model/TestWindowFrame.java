package it.polimi.se2018.model;

import it.polimi.se2018.controller.ResourceLoader;
import org.junit.Test;

import static org.junit.Assert.fail;

public class TestWindowFrame {
    @Test
    public void testChecking() throws InvalidPlaceException {
        int id = 0;
        ResourceLoader resourceLoader = new ResourceLoader();
        PatternCard testPC = resourceLoader.loadPC(id);
        WindowFrame wf = new WindowFrame(testPC,false);
        Die die1 = new Die(Color.YELLOW);
        Die die2 = new Die(Color.GREEN);
        die2.setValue(2);
        Die die3 = new Die(Color.YELLOW);

        if (!wf.checkRestrictions(die2,3,0)){
            fail();
        }
        if (!wf.checkRestrictions(die1,0,2)){
            fail();
        }
        if (!wf.checkRestrictions(die1,3,4)){
            fail();
        }
        if (!wf.checkRestrictions(die1,0,0)){
            fail();
        }
        if(wf.checkRestrictions(die2,0,1)){
            fail();
        }
        if(wf.checkRestrictions(die3,3,3)){
            fail();
        }
        if(wf.checkRestrictions(die3,2,3)){
            fail();
        }
        if(!wf.checkRestrictions(die3,3,1)){
            fail();
        }
        wf.placeDie(die1, 0, 0);
        if (wf.checkRestrictions(die1,0,2)){
            fail();
        }
        if (!wf.checkRestrictions(die2,1,1)){
            fail();
        }
    }
}
