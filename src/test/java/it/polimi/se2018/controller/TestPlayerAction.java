package it.polimi.se2018.controller;

import org.junit.Test;

import static org.junit.Assert.fail;

public class TestPlayerAction {
    @Test
    public void TestInitialization(){
        PlayerAction pa = new PlayerAction();

        pa.setIdToolCard(3);
        pa.setNewDieValue(3);
        pa.setPlaceDraftPoolDie(2,2,3,4);
        pa.setPlaceNewWFDie(1,1,2,3);
        pa.setPlaceWFDie(1,4,2,2);
        pa.setPosDraftPoolDie(2,17);
        pa.setPosRoundTrackDie(5,8);
        pa.setUpdated(false);
        pa.setUsernameReq("Tiziello");

        if(!(pa.getIdToolCard()==3))
           fail();
        if(!(pa.getNewDieValue()==3))
            fail();
        if(!(pa.getPlaceDPDie()[0][0]==2))
            fail();
        if(!(pa.getPlaceDPDie()[0][1]==2))
            fail();
        if(!(pa.getPlaceDPDie()[1][0]==3))
            fail();
        if(!(pa.getPlaceDPDie()[1][1]==4))
            fail();
        if(!(pa.getPlaceNewWFDie()[0][0]==1))
            fail();
        if(!(pa.getPlaceNewWFDie()[0][1]==1))
            fail();
        if(!(pa.getPlaceNewWFDie()[1][0]==2))
            fail();
        if(!(pa.getPlaceNewWFDie()[1][1]==3))
            fail();
        if(!(pa.getPlaceWFDie()[0][0]==1))
            fail();
        if(!(pa.getPlaceWFDie()[0][1]==4))
            fail();
        if(!(pa.getPlaceWFDie()[1][0]==2))
            fail();
        if(!(pa.getPlaceWFDie()[1][1]==2))
            fail();
        if(!(pa.getPosDPDie()[0]==2))
            fail();
        if(!(pa.getPosDPDie()[1]==17))
            fail();
        if(!(pa.getPosRTDie()[0]==5))
            fail();
        if(!(pa.getPosRTDie()[1]==8))
            fail();
        if(pa.getUpdated())
            fail();
        if(pa.isQuitReq())
            fail();
        if(pa.isSwitchConnReq())
            fail();
        if(!(pa.getUsernameReq().equals("Tiziello")))
            fail();

        pa.setQuitReq(true);
        pa.setSwitchConnReq(false);

        if(!(pa.isQuitReq()))
            fail();
        if(pa.isSwitchConnReq())
            fail();
    }
}
