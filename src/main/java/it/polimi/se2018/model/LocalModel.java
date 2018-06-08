package it.polimi.se2018.model;

import java.util.ArrayList;

public class LocalModel implements LocalModelInterface {
    private ArrayList<ToolCard> toolCards = new ArrayList<>();
    @Override
    public void checkConnection() {
    }

    public ArrayList<ToolCard> getToolCards() {
        return toolCards;
    }

}
