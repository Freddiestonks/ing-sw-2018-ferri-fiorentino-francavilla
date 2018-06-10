package it.polimi.se2018.controller;

import org.junit.Test;

import static org.junit.Assert.fail;

public class TestPlayerAction {
    @Test
    public void TestInitialization(){
        PlayerAction pa = new PlayerAction();

        pa.setIdToolCard(3);
        pa.addNewDieValue(3);
        pa.addPlaceDPDie(2,2);
        pa.addPlaceDPDie(3,4);
        pa.addPlaceNewWFDie(1,1);
        pa.addPlaceNewWFDie(2,3);
        pa.addPlaceWFDie(1,4);
        pa.addPlaceWFDie(2,2);
        pa.addPosDPDie(2);
        pa.addPosDPDie(17);
        pa.addPosRTDie(5,8);
        pa.setUsernameReq("Tiziello");

        if(!(pa.getIdToolCard()==3))
           fail();
        if(!(pa.getNewDieValue().get(0)==3))
            fail();
        if(!(pa.getPlaceDPDie().get(0)[0]==2))
            fail();
        if(!(pa.getPlaceDPDie().get(0)[1]==2))
            fail();
        if(!(pa.getPlaceDPDie().get(1)[0]==3))
            fail();
        if(!(pa.getPlaceDPDie().get(1)[1]==4))
            fail();
        if(!(pa.getPlaceNewWFDie().get(0)[0]==1))
            fail();
        if(!(pa.getPlaceNewWFDie().get(0)[1]==1))
            fail();
        if(!(pa.getPlaceNewWFDie().get(1)[0]==2))
            fail();
        if(!(pa.getPlaceNewWFDie().get(1)[1]==3))
            fail();
        if(!(pa.getPlaceWFDie().get(0)[0]==1))
            fail();
        if(!(pa.getPlaceWFDie().get(0)[1]==4))
            fail();
        if(!(pa.getPlaceWFDie().get(1)[0]==2))
            fail();
        if(!(pa.getPlaceWFDie().get(1)[1]==2))
            fail();
        if(!(pa.getPosDPDie().get(0)==2))
            fail();
        if(!(pa.getPosDPDie().get(1)==17))
            fail();
        if(!(pa.getPosRTDie().get(0)[0]==5))
            fail();
        if(!(pa.getPosRTDie().get(0)[1]==8))
            fail();
        if(pa.isQuitReq())
            fail();
        if(!(pa.getUsernameReq().equals("Tiziello")))
            fail();

        pa.setQuitReq(true);

        if(!(pa.isQuitReq()))
            fail();

        PlayerAction pa2 = new PlayerAction();
        pa2.setPlayerAction(pa);
        if(!pa2.isUpdated()) {
            fail();
        }
    }
}
