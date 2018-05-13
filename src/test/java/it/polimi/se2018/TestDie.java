package it.polimi.se2018;
import static org.junit.Assert.*;

import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Die;
import org.junit.Test;

public class TestDie {
    @Test
    public void simpleRolling(){
        Die die1 = new Die(Color.YELLOW);

        die1.roll();

        if(die1.getNumber()<=0 && die1.getNumber()>=7){
              fail();
        }
        
        if(die1.getColor()!=Color.YELLOW){
            fail();
        }

        die1.setNumber(4);

        if (die1.getNumber()!=4){
            fail();
        }
    }
}
