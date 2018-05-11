package it.polimi.se2018.server.model;

import java.util.ArrayList;

public class PubOCColorSet extends PubObjCard {
    //attributes
    private ArrayList<Integer> usedColors = new ArrayList<>(5);

    //methods
    public PubOCColorSet(String desc) {
        super(desc);
    }

    public int calculateScore(WindowFrame wf) {
        int score =0;
        return 0;
    }
}
