package it.polimi.se2018.model;

public abstract class PubObjCard {
    private String description;
    private int points;
    private boolean used = false;
    private int idRes;

    protected PubObjCard(String desc) {
        description = desc;
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
}
