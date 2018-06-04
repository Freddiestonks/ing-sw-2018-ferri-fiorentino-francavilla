package it.polimi.se2018.model;

public abstract class PubObjCard {
    private String description;
    private String cardName;
    protected int points;
    private boolean used = false;

    protected PubObjCard(String desc, String name) {
        description = desc;
        cardName = name;

    }

    public String getDesc() {
        return description;
    }

    public void use() {
        used = true;
    }

    public boolean isUsed() {
        return used;
    }

    public abstract int calculateScore(WindowFrame wf);

    public String getCardName() {
        return cardName;
    }

    public int getPoints() {
        return points;
    }
}