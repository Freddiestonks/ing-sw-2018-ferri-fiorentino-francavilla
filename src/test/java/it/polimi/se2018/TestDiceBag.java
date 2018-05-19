package it.polimi.se2018;

import it.polimi.se2018.model.DiceBag;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.EmptyDiceBagException;
import org.junit.Test;

import static it.polimi.se2018.model.DiceBag.*;
import static org.junit.Assert.*;

import static org.junit.Assert.fail;

public class TestDiceBag {
    @Test
    public void testBag() throws EmptyDiceBagException {
        int n1=0,n2=0,n3=0,n4=0,n5=0;
        Die val = null;
        DiceBag diceBag = DiceBag.instance();

        for(int i=0; i<90; i++) {
            val = diceBag.extract();

            switch(val.getColor()){
                case YELLOW:
                    n1++;
                    break;
                case GREEN:
                    n2++;
                    break;
                case RED:
                    n3++;
                    break;
                case BLUE:
                    n4++;
                    break;
                case PURPLE:
                    n5++;
                    break;
            }
        }

        if(!(n1 == n2 && n2 == n3 && n3 == n4 && n4 == n5 && n5 == 18)){
            fail();
        }

    }
}
