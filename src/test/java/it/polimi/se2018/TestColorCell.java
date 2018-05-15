package it.polimi.se2018;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Die;
import it.polimi.se2018.server.model.ColorCell;
import org.junit.Test;
import static org.junit.Assert.fail;


public class TestColorCell {

    @Test
    public void testPlaceable(){
        ColorCell bcell = new ColorCell(Color.BLUE);
        ColorCell gcell = new ColorCell(Color.GREEN);
        ColorCell ycell = new ColorCell(Color.YELLOW);
        ColorCell rcell = new ColorCell(Color.RED);
        ColorCell pcell = new ColorCell(Color.PURPLE);
        Die blue = new Die(Color.BLUE);
        Die green = new Die(Color.GREEN);
        Die yellow = new Die(Color.YELLOW);
        Die red = new Die(Color.RED);
        Die purple = new Die(Color.PURPLE);
        if(!(bcell.placeable(blue) && gcell.placeable(green) && ycell.placeable(yellow)&& rcell.placeable(red)&& pcell.placeable(purple))){
            fail();
        }
    }
}
