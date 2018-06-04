package it.polimi.se2018.model;

import it.polimi.se2018.view.CLIView;
import org.junit.Test;

import java.util.ArrayList;

public class TestCLIView {
    @Test
    public void testUpdateDP(){
        ArrayList<Die> die = new ArrayList<>();
        die.add(new Die(Color.BLUE));
        die.add(new Die(Color.GREEN));
        die.add(new Die(Color.RED));
        die.add(new Die(Color.PURPLE));
        die.add(new Die(Color.YELLOW));
        CLIView cliView = new CLIView();
        cliView.updateDP(die);

    }
    @Test
    public void testUpdatePubOCs(){
        PubObjCard[] pubObjCards = new PubObjCard[3];
        pubObjCards[0] = new PubOCShadeDet("descrizione di test","nome",false,false,2);
        pubObjCards[1] = new PubOCShadeDet("descrizione di test","nome",false,false,2);
        pubObjCards[2] = new PubOCShadeDet("descrizione di test","nome",false,false,2);
        CLIView cliView = new CLIView();
        cliView.updatePubOCs(pubObjCards);
    }
    @Test
    public void testUpdatePrivOCs(){
        PrivObjCard privObjCard = new PrivObjCard(Color.BLUE);
        CLIView cliView = new CLIView();
        cliView.updatePrivOCs(privObjCard);
    }
    @Test
    public void testMainScreen(){
        Player player = new Player("me");
        WindowFrame wf = new WindowFrame(0,true);
        Player[] opponents = new Player[3];
        CLIView cliView = new CLIView();
        int round = 0;
        boolean backward = false;
        player.setWinFrame(wf);
        opponents[0] = new Player("Well I guess I should test a long username");
        opponents[1] = new Player("Opp 2");
        opponents[2] = new Player("Opp 3");
        opponents[0].setWinFrame(wf);
        opponents[1].setWinFrame(wf);
        opponents[2].setWinFrame(wf);
        cliView.updateMainScreen(player,opponents,round,backward);

    }
}
