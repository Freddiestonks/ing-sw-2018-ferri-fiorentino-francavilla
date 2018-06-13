package it.polimi.se2018.model;

import org.junit.Test;

import static org.junit.Assert.fail;

public class TestPrivObjCard {
    @Test
    public void TestPrivateCards(){
        PrivObjCard pc = new PrivObjCard(Color.YELLOW);

        if(pc.getColor()!=Color.YELLOW){
            fail();
        }
    }
}
