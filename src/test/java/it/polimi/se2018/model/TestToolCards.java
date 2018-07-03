package it.polimi.se2018.model;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.controller.ResourceLoader;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.fail;

public class TestToolCards {
    @Test
    public void TestT1(){
        Model model = Model.instance();
        model.addPlayer("A", new LocalModel());
        model.addPlayer("B", new LocalModel());
        ResourceLoader resourceLoader = new ResourceLoader();
        ToolCard t1 = resourceLoader.loadToolCard(0);
        PatternCard testPC = resourceLoader.loadPC(1);
        WindowFrame wf = new WindowFrame(testPC,true);
        PlayerAction pa = new PlayerAction();
        pa.addPosDPDie(0);
        pa.addPosDPDie(2);
        pa.addNewDieValue(5);
        pa.addPlaceWFDie(0, 0);
        pa.addPlaceDPDie(0, 1);
        Die d1 = new Die(Color.YELLOW);
        d1.setValue(4);
        model.addDraftPoolDie(d1);
        if(t1.validAction(model,wf,pa))
            t1.performAction(model,wf,pa);
        else fail();
    }
    @After
    public void reset1() {
        Model.instance().reset();
    }

    @Test
    public void TestT2(){
        Model model = Model.instance();
        model.addPlayer("A", new LocalModel());
        model.addPlayer("B", new LocalModel());
        ResourceLoader resourceLoader = new ResourceLoader();
        ToolCard t2 = resourceLoader.loadToolCard(1);
        PatternCard testPC = resourceLoader.loadPC(1);
        WindowFrame wf = new WindowFrame(testPC,true);
        PlayerAction pa = new PlayerAction();
        pa.addPlaceWFDie(0,0);
        pa.addPlaceWFDie(1, 1);
        pa.addPlaceNewWFDie(1,2);
        pa.addPlaceNewWFDie(3,3);
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
    public void reset2() {
        Model.instance().reset();
    }

    @Test
    public void TestT3(){
        Model model = Model.instance();
        model.addPlayer("A", new LocalModel());
        model.addPlayer("B", new LocalModel());
        ResourceLoader resourceLoader = new ResourceLoader();
        ToolCard t3 = resourceLoader.loadToolCard(2);
        PatternCard testPC = resourceLoader.loadPC(1);
        WindowFrame wf = new WindowFrame(testPC,true);
        PlayerAction pa = new PlayerAction();
        pa.addPlaceWFDie(0,0);
        pa.addPlaceWFDie(1,1);
        pa.addPlaceNewWFDie(2,4);
        pa.addPlaceNewWFDie(2,2);
        Die d1 = new Die(Color.YELLOW);
        d1.setValue(5);
        wf.placeDie(d1,0,0);
        if(t3.validAction(model,wf,pa)){
            t3.performAction(model,wf,pa);
        }
        else fail();
    }
    @After
    public void reset3() {
        Model.instance().reset();
    }

    @Test
    public void TestT4(){
        Model model = Model.instance();
        model.addPlayer("A", new LocalModel());
        model.addPlayer("B", new LocalModel());
        ResourceLoader resourceLoader = new ResourceLoader();
        ToolCard t4 = resourceLoader.loadToolCard(3);
        PatternCard testPC = resourceLoader.loadPC(1);
        WindowFrame wf = new WindowFrame(testPC,true);
        PlayerAction pa = new PlayerAction();
        pa.addPlaceWFDie(0,0);
        pa.addPlaceWFDie(0,1);
        pa.addPlaceNewWFDie(0,2);
        pa.addPlaceNewWFDie(0,3);
        Die d1 = new Die(Color.YELLOW);
        Die d2 = new Die(Color.RED);
        d1.setValue(6);
        d2.setValue(3);
        wf.placeDie(d1,0,0);
        wf.placeDie(d2,0,1);
        if(t4.validAction(model,wf,pa)){
            t4.performAction(model,wf,pa);
        }
        else fail();
    }
    @After
    public void reset4() {
        Model.instance().reset();
    }

    @Test
    public void TestT5(){
        Model model = Model.instance();
        model.addPlayer("A", new LocalModel());
        model.addPlayer("B", new LocalModel());
        ResourceLoader resourceLoader = new ResourceLoader();
        ToolCard t5 = resourceLoader.loadToolCard(4);
        PatternCard testPC = resourceLoader.loadPC(1);
        WindowFrame wf = new WindowFrame(testPC,true);
        PlayerAction pa = new PlayerAction();
        pa.addPosDPDie(0);
        pa.addPosDPDie(1);
        pa.addPosRTDie(1,0);
        model.addDraftPoolDie(new Die(Color.GREEN));
        model.addRoundTrackDie(new Die(Color.GREEN),1);
        if(t5.validAction(model,wf,pa)){
            t5.performAction(model,wf,pa);
        }
        else fail();
    }
    @After
    public void reset5() {
        Model.instance().reset();
    }

    @Test
    public void TestT6(){
        Model model = Model.instance();
        model.addPlayer("A", new LocalModel());
        model.addPlayer("B", new LocalModel());
        ResourceLoader resourceLoader = new ResourceLoader();
        ToolCard t6 = resourceLoader.loadToolCard(5);
        PatternCard testPC = resourceLoader.loadPC(1);
        WindowFrame wf = new WindowFrame(testPC,true);
        PlayerAction pa = new PlayerAction();
        Die d1 = new Die(Color.YELLOW);
        Die d2 = new Die(Color.RED);
        d1.setValue(2);
        d2.setValue(3);
        model.addDraftPoolDie(d2);
        wf.placeDie(d1,0,0);
        pa.addPosDPDie(0);
        pa.addPosDPDie(1);
        pa.addPlaceDPDie(0,1);
        pa.addPlaceDPDie(0,1);
        if(t6.validAction(model,wf,pa)){
            t6.performAction(model,wf,pa);
        }
        else fail();
        pa.clear();
        Die die = ToolCard.getPendingDie();
        die.setValue(6);
        pa.addPlaceDPDie(0, 1);
        if(t6.validAction(model,wf,pa)){
            t6.performAction(model,wf,pa);
        }
        else fail();
    }
    @After
    public void reset6() {
        Model.instance().reset();
    }

    @Test
    public void TestT7(){
        Model model = Model.instance();
        model.addPlayer("A", new LocalModel());
        model.addPlayer("B", new LocalModel());
        ResourceLoader resourceLoader = new ResourceLoader();
        ToolCard t7 = resourceLoader.loadToolCard(6);
        PatternCard testPC = resourceLoader.loadPC(1);
        WindowFrame wf = new WindowFrame(testPC,true);
        model.updateTurn();
        model.updateTurn();
        PlayerAction pa = new PlayerAction();
        if(t7.validAction(model,wf,pa)){
            t7.performAction(model,wf,pa);
        }
        else fail();
    }
    @After
    public void reset7() {
        Model.instance().reset();
    }

    @Test
    public void TestT8() {
        Model model = Model.instance();
        model.addPlayer("A", new LocalModel());
        model.addPlayer("B", new LocalModel());
        ResourceLoader resourceLoader = new ResourceLoader();
        ToolCard t8 = resourceLoader.loadToolCard(7);
        PatternCard testPC = resourceLoader.loadPC(1);
        WindowFrame wf = new WindowFrame(testPC,true);
        PlayerAction pa = new PlayerAction();
        pa.addPosDPDie(0);
        pa.addPosDPDie(1);
        pa.addPlaceDPDie(0,0);
        pa.addPlaceDPDie(0,1);
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
    }

    @Test
    public void TestT9(){
        Model model = Model.instance();
        model.addPlayer("A", new LocalModel());
        model.addPlayer("B", new LocalModel());
        ResourceLoader resourceLoader = new ResourceLoader();
        ToolCard t9 = resourceLoader.loadToolCard(8);
        PatternCard testPC = resourceLoader.loadPC(1);
        WindowFrame wf = new WindowFrame(testPC,true);
        PlayerAction pa = new PlayerAction();
        pa.addPlaceDPDie(0,1);
        pa.addPlaceDPDie(3,0);
        pa.addPosDPDie(0);
        pa.addPosDPDie(1);
        Die dpDie = new Die(Color.RED);
        dpDie.setValue(5);
        model.addDraftPoolDie(dpDie);
        Die d1 = new Die(Color.YELLOW);
        d1.setValue(2);
        wf.placeDie(d1,0,0);
        if(t9.validAction(model,wf,pa)){
            t9.performAction(model,wf,pa);
        }
        else fail();
    }
    @After
    public void reset9() {
        Model.instance().reset();
    }

    @Test
    public void TestT10() {
        Model model = Model.instance();
        model.addPlayer("A", new LocalModel());
        model.addPlayer("B", new LocalModel());
        ResourceLoader resourceLoader = new ResourceLoader();
        ToolCard t10 = resourceLoader.loadToolCard(9);
        PatternCard testPC = resourceLoader.loadPC(1);
        WindowFrame wf = new WindowFrame(testPC,true);
        PlayerAction pa = new PlayerAction();
        Die d2 = new Die(Color.RED);
        d2.setValue(3);
        Die d1 = new Die(Color.YELLOW);
        d1.setValue(2);
        model.addDraftPoolDie(d1);
        model.addDraftPoolDie(d2);
        pa.addPosDPDie(0);
        pa.addPosDPDie(1);
        pa.addPlaceDPDie(0,1);
        pa.addPlaceDPDie(1,1);
        if(t10.validAction(model,wf,pa)){
            t10.performAction(model,wf,pa);
        }
        else fail();
    }
    @After
    public void reset10() {
        Model.instance().reset();
    }

    @Test
    public void TestT11(){
        Model model = Model.instance();
        model.addPlayer("A", new LocalModel());
        model.addPlayer("B", new LocalModel());
        ResourceLoader resourceLoader = new ResourceLoader();
        ToolCard t11 = resourceLoader.loadToolCard(10);
        PatternCard testPC = resourceLoader.loadPC(1);
        WindowFrame wf = new WindowFrame(testPC,true);
        PlayerAction pa = new PlayerAction();
        Die d2 = model.getDiceBag().extract();
        model.addDraftPoolDie(d2);
        pa.addPosDPDie(0);
        pa.addPosDPDie(1);
        pa.addPlaceDPDie(0,1);
        pa.addPlaceDPDie(0,1);
        if(t11.validAction(model,wf,pa)){
            t11.performAction(model,wf,pa);
        }
        else fail();
        pa.clear();
        pa.addNewDieValue(1);
        pa.addPlaceDPDie(0, 1);
        if(t11.validAction(model,wf,pa)){
            t11.performAction(model,wf,pa);
        }
        else fail();
    }
    @After
    public void reset11() {
        Model.instance().reset();
    }

    @Test
    public void TestT12(){
        Model model = Model.instance();
        model.addPlayer("A", new LocalModel());
        model.addPlayer("B", new LocalModel());
        ResourceLoader resourceLoader = new ResourceLoader();
        ToolCard t12 = resourceLoader.loadToolCard(11);
        PatternCard testPC = resourceLoader.loadPC(1);
        WindowFrame wf = new WindowFrame(testPC,true);
        PlayerAction pa = new PlayerAction();
        Die d1 = new Die(Color.YELLOW);
        Die d2 = new Die(Color.RED);
        Die d3 = new Die(Color.YELLOW);
        d2.setValue(5);
        d3.setValue(3);
        d1.setValue(1);
        pa.addPlaceWFDie(0,0);
        pa.addPlaceWFDie(2,1);
        pa.addPlaceNewWFDie(2,4);
        pa.addPlaceNewWFDie(3,3);
        pa.addPosRTDie(1, 0);
        wf.placeDie(d1,0,0);
        wf.placeDie(d2,3,4);
        wf.placeDie(d3,2,1);
        model.addRoundTrackDie(new Die(Color.YELLOW),1);
        if(t12.validAction(model,wf,pa)){
            t12.performAction(model,wf,pa);
        }
        else fail();
    }
    @After
    public void reset12() {
        Model.instance().reset();
    }
}
