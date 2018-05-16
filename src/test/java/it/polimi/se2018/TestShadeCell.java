package it.polimi.se2018;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Die;
import it.polimi.se2018.server.model.ShadeCell;
import org.junit.Test;
import static org.junit.Assert.fail;



public class TestShadeCell {
    @Test

    public void testPlaceable(){
        Die dice = new Die(Color.BLUE);
        ShadeCell cell;
        for (int i = 1; i<7; i++) {
            dice.setValue(i);
            cell = new ShadeCell(i);
            if(!cell.placeableShade(dice)){
                fail();
            }
        }

        try{
            cell = new ShadeCell(7);
            fail();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

    }
}
