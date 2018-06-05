package it.polimi.se2018.model;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.toolcards.*;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.fail;

public class TestToolcards {
    @Test
    public void TestT1(){
        ToolCard1 t1 = new ToolCard1();
        WindowFrame wf = new WindowFrame(1,true);
        Model model = Model.instance();
        PlayerAction pa = new PlayerAction();
        pa.setPosDraftPoolDie(0,2);
        pa.setNewDieValue(2);
        pa.setPlaceWFDie(0,0,1,2);
        Die d1 = new Die(Color.YELLOW);
        d1.setValue(2);
        d1.setValue(3);
        model.addDraftPoolDie(d1);
        if(t1.validAction(model,wf,pa))
            t1.performAction(model,wf,pa);
        else fail();
    }
    @After
    public void reset1(){
        Model.instance().reset();
    }

    @Test
    public void TestT2(){
        ToolCard2 t2 = new ToolCard2();
        WindowFrame wf = new WindowFrame(1,true);
        Model model = Model.instance();
        PlayerAction pa = new PlayerAction();
        pa.setPlaceWFDie(0,0,1,1);
        pa.setPlaceNewWFDie(1,2,3,3);
        Die d1 = new Die(Color.YELLOW);
        Die d2 = new Die(Color.RED);
        d1.setValue(2);
        d2.setValue(3);
        wf.placeDie(d1,0,0);
        wf.placeDie(d2,1,1);
        if(t2.validAction(model,wf,pa)){
            t2.performAction(model,wf,pa);
        }
        else fail();
    }
    @After
    public void reset2(){
        Model.instance().reset();
    }

    @Test
    public void TestT3(){
        ToolCard3 t3 = new ToolCard3();
        WindowFrame wf = new WindowFrame(1,true);
        Model model = Model.instance();
        PlayerAction pa = new PlayerAction();
        pa.setPlaceWFDie(0,0,1,1);
        pa.setPlaceNewWFDie(2,4,2,2);
        Die d1 = new Die(Color.YELLOW);
        d1.setValue(5);
        wf.placeDie(d1,0,0);
        if(t3.validAction(model,wf,pa)){
            t3.performAction(model,wf,pa);
        }
        else fail();
    }
    @After
    public void reset3(){
        Model.instance().reset();
    }

    @Test
    public void TestT4(){
        ToolCard4 t4 = new ToolCard4();
        WindowFrame wf = new WindowFrame(1,true);
        Model model = Model.instance();
        PlayerAction pa = new PlayerAction();
        pa.setPlaceWFDie(0,0,0,1);
        pa.setPlaceNewWFDie(2,4,3,4);
        Die d1 = new Die(Color.YELLOW);
        Die d2 = new Die(Color.RED);
        d1.setValue(2);
        d2.setValue(3);
        wf.placeDie(d1,0,0);
        wf.placeDie(d2,0,1);
        if(t4.validAction(model,wf,pa)){
            t4.performAction(model,wf,pa);
        }
        else fail();
    }
    @After
    public void reset4(){
        Model.instance().reset();
    }

    @Test
    public void TestT5(){
        ToolCard5 t5 = new ToolCard5();
        WindowFrame wf = new WindowFrame(1,true);
        Model model = Model.instance();
        PlayerAction pa = new PlayerAction();
        pa.setPosDraftPoolDie(0,1);
        pa.setPosRoundTrackDie(0,0);
        model.addDraftPoolDie(new Die(Color.GREEN));
        model.addRoundTrackDie(new Die(Color.GREEN),0);
        if(t5.validAction(model,wf,pa)){
            t5.performAction(model,wf,pa);
        }
        else fail();
    }
    @After
    public void reset5(){
        Model.instance().reset();
    }

    @Test
    public void TestT6(){
        ToolCard6 t6 = new ToolCard6();
        WindowFrame wf = new WindowFrame(1,true);
        Model model = Model.instance();
        PlayerAction pa = new PlayerAction();
        Die d1 = new Die(Color.YELLOW);
        Die d2 = new Die(Color.RED);
        d1.setValue(2);
        d2.setValue(3);
        model.addDraftPoolDie(d2);
        wf.placeDie(d1,0,0);
        pa.setPosDraftPoolDie(0,1);
        pa.setPlaceDraftPoolDie(0,1,0,1);
        if(t6.validAction(model,wf,pa)){
            t6.performAction(model,wf,pa);
        }
        else fail();
    }
    @After
    public void reset6(){
        Model.instance().reset();
    }

    @Test
    public void TestT7(){
        ToolCard7 t7 = new ToolCard7();
        WindowFrame wf = new WindowFrame(1,true);
        Model model = Model.instance();
        model.addPlayer("Carlo");
        if(model.getTurn()==1 && model.getRound()==1)
            model.updateTurn();
        PlayerAction pa = new PlayerAction();
        if(t7.validAction(model,wf,pa)){
            t7.performAction(model,wf,pa);
        }
        else fail();
    }
    @After
    public void reset7(){
        Model.instance().reset();
    }

    //TODO: waiting for the clone implementation.
    /*@Test
    public void TestT8() throws CloneNotSupportedException {
        ToolCard8 t8 = new ToolCard8();
        WindowFrame wf = new WindowFrame(1,true);
        Model model = Model.instance();
        PlayerAction pa = new PlayerAction();
        pa.setPosDraftPoolDie(0,1);
        pa.setPlaceDraftPoolDie(0,0,0,1);
        Die d1 = new Die(Color.YELLOW);
        Die d2 = new Die(Color.RED);
        d1.setValue(2);
        d2.setValue(3);
        model.addDraftPoolDie(d1);
        model.addDraftPoolDie(d2);
        if(t8.validAction(model,wf,pa)){
            t8.performAction(model,wf,pa);
        }
        else fail();
    }
    @After
    public void reset8(){
        Model.instance().reset();
    }*/

    @Test
    public void TestT9(){
        ToolCard9 t9 = new ToolCard9();
        WindowFrame wf = new WindowFrame(1,true);
        Model model = Model.instance();
        PlayerAction pa = new PlayerAction();
        pa.setPlaceDraftPoolDie(0,1,3,0);
        pa.setPosDraftPoolDie(0,1);
        model.addDraftPoolDie(new Die(Color.RED));
        Die d1 = new Die(Color.YELLOW);
        d1.setValue(2);
        wf.placeDie(d1,0,0);
        if(t9.validAction(model,wf,pa)){
            t9.performAction(model,wf,pa);
        }
        else fail();
    }
    @After
    public void reset9(){
        Model.instance().reset();
    }

    @Test
    public void TestT10() {
        ToolCard10 t10 = new ToolCard10();
        WindowFrame wf = new WindowFrame(1,true);
        Model model = Model.instance();
        PlayerAction pa = new PlayerAction();
        Die d2 = new Die(Color.RED);
        d2.setValue(3);
        Die d1 = new Die(Color.YELLOW);
        d1.setValue(2);
        model.addDraftPoolDie(d2);
        pa.setPlaceDraftPoolDie(0,1,1,1);
        if(t10.validAction(model,wf,pa)){
            t10.performAction(model,wf,pa);
        }
        else fail();
    }
    @After
    public void reset10(){
        Model.instance().reset();
    }

    @Test
    public void TestT11(){
        ToolCard11 t11 = new ToolCard11();
        WindowFrame wf = new WindowFrame(1,true);
        Model model = Model.instance();
        PlayerAction pa = new PlayerAction();
        Die d2 = model.getDiceBag().extract();
        model.addDraftPoolDie(d2);
        pa.setPosDraftPoolDie(0,1);
        pa.setPlaceDraftPoolDie(0,1,0,1);
        if(t11.validAction(model,wf,pa)){
            t11.performAction(model,wf,pa);
        }
        else fail();
    }
    @After
    public void reset11(){
        Model.instance().reset();
    }

    @Test
    public void TestT12(){
        ToolCard12 t12 = new ToolCard12();
        WindowFrame wf = new WindowFrame(1,true);
        Model model = Model.instance();
        PlayerAction pa = new PlayerAction();
        Die d1 = new Die(Color.YELLOW);
        Die d2 = new Die(Color.RED);
        Die d3 = new Die(Color.YELLOW);
        d2.setValue(5);
        d3.setValue(3);
        d1.setValue(1);
        pa.setPlaceWFDie(0,0,2,1);
        pa.setPlaceNewWFDie(2,4,3,3);
        wf.placeDie(d1,0,0);
        wf.placeDie(d2,3,4);
        wf.placeDie(d3,2,1);
        model.addRoundTrackDie(new Die(Color.YELLOW),0);
        if(t12.validAction(model,wf,pa)){
            t12.performAction(model,wf,pa);
        }
        else fail();
    }
    @After
    public void reset12(){
        Model.instance().reset();
    }
}
