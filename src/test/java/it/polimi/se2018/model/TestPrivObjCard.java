package it.polimi.se2018.model;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.PrivObjCard;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestPrivObjCard {
    @Test
    public void TestPrivateCards(){
        PrivObjCard pc = new PrivObjCard(Color.YELLOW);


        if(pc.isUsed()){
            fail();
        }

        pc.use();
        if(!pc.isUsed()){
            fail();
        }

        if(pc.getColor()!=Color.YELLOW){
            fail();
        }
    }
}
