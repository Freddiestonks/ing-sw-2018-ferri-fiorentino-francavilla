package it.polimi.se2018.view;

import it.polimi.se2018.controller.ResourceLoader;
import it.polimi.se2018.model.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

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
        ResourceLoader resourceLoader = new ResourceLoader();
        PubObjCard[] pubObjCards = new PubObjCard[3];
        /*
        pubObjCards[0] = resourceLoader.loadPubOC(0);
        pubObjCards[1] = resourceLoader.loadPubOC(1);
        pubObjCards[2] = resourceLoader.loadPubOC(2);
        */
        int numPubOcs = 10; // TODO: ?? from file
        ArrayList<Integer> pubOCIds = new ArrayList<>();
        for(int i = 0; i < numPubOcs; i++) {
            pubOCIds.add(i);
        }
        Random random = new Random();
        for(int i = 0; i < 3; i++) {
            int num = random.nextInt(pubOCIds.size());
            int id = pubOCIds.remove(num);
            pubObjCards[i] = resourceLoader.loadPubOC(id);
        }
        cliView.updatePubOCs(pubObjCards);
    }
    @Test
    public void testRT(){
        ArrayList<ArrayList<Die>> roundTrack = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            roundTrack.add(new ArrayList<>());
        }
        Die die = new Die(Color.BLUE);
        ArrayList<Die> dice = new ArrayList<>();
        dice.add(die);
        dice.add(die);
        dice.add(die);
        roundTrack.set(0, dice);
        ArrayList<Die> dice2 = new ArrayList<>(dice);
        dice2.remove(0);
        roundTrack.set(1, dice2);
        ArrayList<Die> dice3 = new ArrayList<>(dice);
        dice3.add(new Die(Color.GREEN));
        dice3.add(new Die(Color.RED));
        roundTrack.set(2, dice3);
        ArrayList<Die> dice4 = new ArrayList<>(dice);
        dice4.add(new Die(Color.RED));
        roundTrack.set(3, dice4);
        cliView.updateRT(roundTrack);

    }
    @Test
    public void testMainScreen(){
        MainScreenInfo mainScreenInfo = new MainScreenInfo();
        Player player = new Player("me");
        ResourceLoader resourceLoader = new ResourceLoader();
        PatternCard testPC = resourceLoader.loadPC(0);
        WindowFrame wf = new WindowFrame(testPC,true);
        ArrayList<Player> opponents = new ArrayList<>();
        ArrayList<Die> dp = new ArrayList<>();
        dp.add(new Die(Color.BLUE));
        dp.add(new Die(Color.PURPLE));
        dp.add(new Die(Color.RED));
        dp.add(new Die(Color.YELLOW));
        dp.add(new Die(Color.GREEN));
        int round = 4;
        player.setWinFrame(wf);
        opponents.add(new Player("Well I guess I should test a long username"));
        opponents.add(new Player("Opp 2"));
        opponents.add(new Player("Opp 3"));
        opponents.get(0).setWinFrame(wf);
        opponents.get(1).setWinFrame(wf);
        opponents.get(2).setWinFrame(wf);
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
        roundTrack.add(new ArrayList<>());
        roundTrack.add(new ArrayList<>());
        roundTrack.add(new ArrayList<>());
        roundTrack.add(new ArrayList<>());
        roundTrack.add(new ArrayList<>());
        roundTrack.add(new ArrayList<>());
        die.setValue(4);
        player.getWindowFrame().placeDie(die,0,0);
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
        ArrayList<String> usernames = new ArrayList<>();
        usernames.add("Milano");
        usernames.add("Torino");
        usernames.add("Venezia");
        cliView.updatePlayerLobby(usernames);
        usernames.add("Roma");
        cliView.updatePlayerLobby(usernames);
    }
    @Test
    public void testPatternCard(){
        ArrayList<PatternCard> patternCard = new ArrayList<>();
        ResourceLoader resourceLoader = new ResourceLoader();
        PatternCard testPC = resourceLoader.loadPC(0);
        patternCard.add(resourceLoader.loadPC(0));
        patternCard.add(resourceLoader.loadPC(1));
        cliView.patternCardGenerator(patternCard);
    }
}
