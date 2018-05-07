package it.polimi.se2018.server.model;

import java.util.Random;

public class DiceBag {
    //Attributes
    final private static DiceBag instance = new DiceBag();
    private int[] content = new int[5];
    //Methods
    private DiceBag(){}
    public static DiceBag instance(){
        //TODO: generation of die sequence.
        return instance;
    }

    public Die extract(){
        Random randomColors = new Random();
        Die newDie = null;
        int num = 1 + randomColors.nextInt(4);

        if(num == 1)
            newDie = new Die(Color.RED);
        else if (num == 2)
            newDie = new Die(Color.YELLOW);
        else if (num == 3)
            newDie = new Die(Color.GREEN);
        else if (num == 4)
            newDie = new Die(Color.BLUE);
        else if (num == 5)
            newDie = new Die(Color.PURPLE);

        try {
            newDie.roll();
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }

        return newDie;
    }

    public void replace(Die die){}
}
