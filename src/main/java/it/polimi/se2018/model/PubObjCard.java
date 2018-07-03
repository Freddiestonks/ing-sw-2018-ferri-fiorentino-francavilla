package it.polimi.se2018.model;

import java.io.Serializable;

public abstract class PubObjCard implements Serializable {
    private String description;
    private String cardName;
    protected int points;

    public PubObjCard(String desc, String name, int points) {
        this.description = desc;
        this.cardName = name;
        this.points = points;
    }

    public String getDesc() {
        return description;
    }

    public abstract int calculateScore(WindowFrame wf);

    public String getCardName() {
        return cardName;
    }

    public int getPoints() {
        return points;
    }
}