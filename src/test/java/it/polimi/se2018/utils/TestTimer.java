package it.polimi.se2018.utils;

import it.polimi.se2018.utils.Timer;
import org.junit.Test;

import static org.junit.Assert.fail;

public class TestTimer {
    @Test
    public void testTimer(){
        Object o1 = new Object();
        Timer timer = new Timer(4,o1);

        if(timer.isTimeout()){
            fail();
        }

        timer.run();
        if(!timer.isTimeout()){
            fail();
        }
    }

    @Test
    public void testTimer2(){
        Object o1 = new Object();
        try{
            Timer timer = new Timer(-2,o1);
            fail();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testTimer3(){
        Object o1 = null;
        try{
            Timer timer = new Timer(20,o1);
            fail();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
