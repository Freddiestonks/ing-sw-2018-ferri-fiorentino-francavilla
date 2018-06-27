package it.polimi.se2018.model;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.fail;

public class TestDie {
    @Test
    public void simpleRolling(){
        Die die1 = new Die(Color.YELLOW);

        die1.roll();

        if(die1.getValue()<=0 && die1.getValue()>=7){
              fail();
        }

        if(die1.getColor()!=Color.YELLOW){
            fail();
        }

        die1.setValue(4);

        if (die1.getValue()!=4){
            fail();
        }
    }
    @After
    public void reset() {
        Model.instance().reset();
    }
}
