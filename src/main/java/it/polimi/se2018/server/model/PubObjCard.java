package it.polimi.se2018.server.model;

public abstract class PubObjCard {
    String description;
    int points;
    boolean used = false;
    int idRes;

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
}
