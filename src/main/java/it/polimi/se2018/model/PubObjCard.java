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

    /**
     * This method calculate the score with the current Public Objective Card and a selected WindowFrame.
     * @param wf the WindowFrame used to calculate a player final score.
     * @return the calculated score.
     */
    public abstract int calculateScore(WindowFrame wf);

    public String getDesc() {
        return description;
    }

    public String getCardName() {
        return cardName;
    }

    public int getPoints() {
        return points;
    }
}