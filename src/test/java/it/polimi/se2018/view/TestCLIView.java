package it.polimi.se2018.view;

import it.polimi.se2018.model.*;
import org.junit.Test;

import java.util.ArrayList;

public class TestCLIView {
    CLIView cliView = new CLIView();
    Model model = Model.instance();
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
        pubObjCards = model.getPubOCs();
        cliView.updatePubOCs(pubObjCards);
    }
    @Test
    public void testUpdatePrivOCs(){
        PrivObjCard privObjCard = new PrivObjCard(Color.BLUE);
        cliView.updatePrivOCs(privObjCard);
    }
    @Test
    public void testRT(){
        ArrayList<ArrayList<Die>> roundTrack = new ArrayList<>();
        Die die = new Die(Color.BLUE);
        ArrayList< Die> dice = new ArrayList<>();
        dice.add(die);
        dice.add(die);
        dice.add(die);
        roundTrack.add(dice);
        roundTrack.add(dice);
        roundTrack.add(dice);
        roundTrack.add(dice);
        cliView.updateRT(roundTrack,4);

    }
    @Test
    public void testMainScreen(){
        MainScreenInfo mainScreenInfo = new MainScreenInfo();
        Player player = new Player("me");
        WindowFrame wf = new WindowFrame(0,true);
        Player[] opponents = new Player[3];
        ArrayList<Die> dp = new ArrayList<>();
        dp.add(new Die(Color.BLUE));
        dp.add(new Die(Color.PURPLE));
        dp.add(new Die(Color.RED));
        dp.add(new Die(Color.YELLOW));
        dp.add(new Die(Color.GREEN));
        int round = 4;
        player.setWinFrame(wf);
        opponents[0] = new Player("Well I guess I should test a long username");
        opponents[1] = new Player("Opp 2");
        opponents[2] = new Player("Opp 3");
        opponents[0].setWinFrame(wf);
        opponents[1].setWinFrame(wf);
        opponents[2].setWinFrame(wf);
        ArrayList<ArrayList<Die>> roundTrack = new ArrayList<>();
        Die die = new Die(Color.BLUE);
        ArrayList<Die> dice = new ArrayList<>();
        dice.add(die);
        dice.add(die);
        dice.add(die);
        roundTrack.add(dice);
        roundTrack.add(dice);
        roundTrack.add(dice);
        roundTrack.add(dice);
        die.setValue(4);
        player.getWF().placeDie(die,0,0);
        mainScreenInfo.setPlayer(player);
        mainScreenInfo.setBackward(false);
        mainScreenInfo.setDraftPool(dp);
        mainScreenInfo.setRound(round);
        mainScreenInfo.setOpponents(opponents);
        mainScreenInfo.setRoundTrack(roundTrack);
        cliView.updateMainScreen(mainScreenInfo);

    }
    @Test
    public void testHelp(){
        cliView.help();
    }
    @Test
    public void testPlayerLobby(){
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("Milano"));
        players.add(new Player("Torino"));
        players.add(new Player("Venezia"));
        cliView.updatePlayerLobby(players);
        players.add(new Player("Roma"));
        cliView.updatePlayerLobby(players);
    }
    @Test
    public void testPatternCard(){
        ArrayList<PatternCard> patternCard = new ArrayList<>();
        WindowFrame windowFrame = new WindowFrame(0,true);
        patternCard.add(windowFrame.loadPC(0));
        patternCard.add(windowFrame.loadPC(1));
        cliView.patternCardGenerator(patternCard);
    }
}
