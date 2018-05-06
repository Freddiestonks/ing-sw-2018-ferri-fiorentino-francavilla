package it.polimi.se2018.server.model;

public class DiceBag {
    //Attributes
    final private static DiceBag instance = new DiceBag();
    private int[] content = new int[5];
    //Methods
    private DiceBag(){}
    public static DiceBag instance(){
        return instance;
    }

    public Die extract(){
        //TODO: pick an available color randomly for the die and then roll it
    }

    public void replace(Die die){}
}
