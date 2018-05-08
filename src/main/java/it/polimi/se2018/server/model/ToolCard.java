package it.polimi.se2018.server.model;

public abstract class ToolCard {
    //Attributes
    private String description;
    private int price;
    private int tokens;
    private Color color;
    private int idRes;
    //Methods

    public void putTokens(){}

    //TODO: insert player's move information among parameters
    public void performActions(Model model, WindowFrame wf){}
}
