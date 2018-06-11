package it.polimi.se2018.model;

import org.junit.Test;

import static org.junit.Assert.fail;

public class TestPatternCard {
    @Test
    public void TestGetting(){
        Cell[][] m1 = new Cell[4][5], m2 = new Cell[4][5];
        PatternCard pc = new PatternCard(3,4,"caso","casissimo", m1, m2);

        for(int i=0; i<4; i++){
            for(int j=0; j<5; j++){
                if(j%2 == 0){
                    m1[i][j] = new ColorCell(Color.YELLOW);
                }
                else m1[i][j] = new ShadeCell(j+1);
            }
        }

        for(int i=0; i<4; i++){
            for(int j=0; j<5; j++){
                if(j%2 == 0){
                    m2[i][j] = new ColorCell(Color.RED);
                }
                else m2[i][j] = new ShadeCell(j+1);
            }
        }

        for(int i=0; i<4; i++){
            for(int j=0; j<5; j++){
                if(j%2 == 0){
                    if(!(pc.getCell(true,i,j).placeableColor(new Die(Color.YELLOW)))){
                        fail();
                    }
                }

                else {
                    Die d1 = new Die(Color.BLUE);
                    d1.setValue(j+1);
                    if (!(pc.getCell(true, i, j).placeableShade(d1))){
                        fail();
                    }
                }
            }
        }

        for(int i=0; i<4; i++){
            for(int j=0; j<5; j++){
                if(j%2 == 0){
                    if(!(pc.getCell(false,i,j).placeableColor(new Die(Color.RED)))){
                        fail();
                    }
                }

                else {
                    Die d1 = new Die(Color.BLUE);
                    d1.setValue(j+1);
                    if (!(pc.getCell(false, i, j).placeableShade(d1))){
                        fail();
                    }
                }
            }
        }

        if(!(pc.getLevelF() == 3)){
            fail();
        }

        if(!(pc.getLevelB() == 4)){
            fail();
        }

        if(!(pc.getNameF() == "caso")){
            fail();
        }

        if(!(pc.getNameB() == "casissimo")){
            fail();
        }

        if(!(pc.getFront() == m1)){
            fail();
        }

        if(!(pc.getBack() == m2)){
            fail();
        }
    }
}
