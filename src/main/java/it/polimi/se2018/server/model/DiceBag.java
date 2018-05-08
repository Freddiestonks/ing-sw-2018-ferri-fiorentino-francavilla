package it.polimi.se2018.server.model;

import java.util.Random;

import static java.lang.String.valueOf;

public class DiceBag {
    //Attributes
    final private static DiceBag instance = new DiceBag();
    private int[] content = new int[5];
    //Methods
    private DiceBag(){
        content[0] = 18;
        content[1] = 18;
        content[2] = 18;
        content[3] = 18;
        content[4] = 18;

    }
    public static DiceBag instance(){
        return instance;
    }

    public Die extract(){
        Random randomColors = new Random();
        Die newDie;
        int num = 1 + randomColors.nextInt(5);

        /*Color[] colors = Color.values();
        newDie = new Die(colors[num]);
        content[num]--;*/

        while(content[num]<=0) {
            num = 1 + randomColors.nextInt(5);
        }

        if (num == 1) {
            newDie = new Die(Color.RED);

        } else if (num == 2) {
            newDie = new Die(Color.YELLOW);
        } else if (num == 3) {
            newDie = new Die(Color.GREEN);
        } else if (num == 4) {
            newDie = new Die(Color.BLUE);
        } else if (num == 5) {
            newDie = new Die(Color.PURPLE);
        }
        content[num]--;

        return newDie;
    }

    public void replace(Die die){
        if(die.getColor() == Color.RED){
            content[0]++;
        }
        else if(die.getColor() == Color.YELLOW){
            content[1]++;
        }
        else if(die.getColor() == Color.GREEN){
            content[2]++;
        }
        else if(die.getColor() == Color.BLUE){
            content[3]++;
        }
        else if(die.getColor() == Color.PURPLE){
            content[4]++;
        }
    }
}
