package it.polimi.se2018.view;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.out;
/**
 * @author Federico Ferri,Alessio Fiorentino,Simone Francavilla
 *
 * */
public class CLIView extends View {

    private Scanner userInput = new Scanner(System.in);
    private String ip;
    private String port;
    private String type = null;
    private String input = null;
    private static final int CARD_WIDTH = 26;
    private static final int PRIV_OC_SIZE = 1;
    private static final int ROW_SIZE = 4;
    private static final int COL_SIZE = 5;
    private static final int PATTERN_CARD_SIZE = 25;
    private static final String PUBLIC_OBJ_STRING = "Public Objective";
    private static final String SPACE = "        ";


    public CLIView() {
    }
    //TODO IMPLEMENT PLAY
    public void play(String type){
        //TODO SEND CONNECTION TYPE
        //NetworkHandler networkHandler = new NetworkHandler();
        PlayerAction playerAction = new PlayerAction();
        out.println("Insert your username:\n");
        playerAction.setUsernameReq(userInput.next());

    }
    @Override
    public void updateWaitingRoom(boolean starting){
        if(starting){
            //TODO add pattern observable
            out.println("The game is about to start,Good Luck");
        }
        else{
            out.println("Please wait while some other players join in");
        }
    }
    @Override
    public void welcomeScreen(){
        boolean correct = false;
        out.println("\n would you like to play on RMI or Socket?\n");
        type = userInput.next().toLowerCase();
        PlayerAction playerAction = new PlayerAction();
        playerAction.setConnectionType(type);
        while (!correct){
            out.println("\n Ok now kindly insert the server's ip address\n");
            ip = userInput.next();
            out.println(ip + "\nPlease insert the port now\n");
            port = userInput.next();
            out.println("Server: " + ip + "\nPort: " + port + "\nis it correct? (y/n)");
            if(Objects.equals(userInput.next(), "y")){
                correct = true;
            }
        }
        if(type.equalsIgnoreCase("socket")){
            out.println("I will now try to establish a socket connection\n");
            play("socket");
        }
        if(type.equalsIgnoreCase("rmi")){
            out.println("I will now try to establish an RMI connection\n");
            play("rmi");
        }

    }
    @Override
    public void updateDP(ArrayList<Die> draftPool) {
        out.println("DraftPool:");
        for (int i = 0; i<draftPool.size();i++){
            out.println(i + " : " + draftPool.get(i));
        }
    }
    @Override
    public void updateTokens(int tokens){
        out.println("You currently have " + tokens + " tokens");
    }
    @Override
    public void updateRound(int round){
        out.println("Round: " + (round+1));
    }
    @Override
    public void updateOrder(boolean backward){
        if (backward){
            out.println("You are in the first turn of this round");
        }
        else {
            out.println("You are in the second turn of the round");
        }
    }
    @Override
    public void updateInfo(int tokens, int round, boolean backward){
        updateTokens(tokens);
        updateRound(round);
        updateOrder(backward);
    }
    @Override
    public void updatePrivOCs(PrivObjCard privObjCard){
        String[] string = new String[PRIV_OC_SIZE];
        for (int i = 0; i < PRIV_OC_SIZE; i++) {
            for (int z = 0; z < CARD_WIDTH +4; z++) {
                out.print("-");
            }
            out.print(SPACE);
        }
        out.println();
        string[0] = "Private Objective";
        layoutFormatter(string, PRIV_OC_SIZE);
        string[0] = "Shades " + privObjCard.getColor().toString().toLowerCase();
        layoutFormatter(string, PRIV_OC_SIZE);
        string[0] = "You score as many points as the values of the shades on all of the " + privObjCard + " dice";
        layoutFormatter(string, PRIV_OC_SIZE);

    }
    @Override
    public void updatePubOCs(PubObjCard[] pubObjCards){
        int pubOCSize = pubObjCards.length;
        for (int i = 0; i < pubOCSize; i++) {
            for (int z = 0; z < CARD_WIDTH +4; z++) {
                out.print("-");
            }
            out.print(SPACE);
        }
        out.println();
        String[] pOCs = new String[pubOCSize];
        for(int i=0;i<pubOCSize;i++) {
            pOCs[i] = PUBLIC_OBJ_STRING;
        }
        layoutFormatter(pOCs,pubOCSize);
        for(int i=0;i<pubOCSize;i++) {
            pOCs[i] = pubObjCards[i].getCardName();
        }
        layoutFormatter(pOCs,pubOCSize);
        for(int i=0;i<pubOCSize;i++) {
            pOCs[i] = pubObjCards[i].getDesc();
        }
        layoutFormatter(pOCs,pubOCSize);
        for(int i=0;i<pubOCSize;i++) {
            pOCs[i] = "Points: " + pubObjCards[i].getPoints();
        }
        layoutFormatter(pOCs,pubOCSize);


    }
    @Override
    public void updateOpponentsWF(Player[] opponents){
        WindowFrame[] wf = new  WindowFrame[opponents.length];
        for(int i = 0;i<opponents.length;i++){
            if(opponents[i].getUsername().length() <= 24){
                out.print(opponents[i].getUsername() + ":");}
            else {
                out.print(opponents[i].getUsername().substring(0,24) + ":");
            }
            for (int j =0;j< (24 - opponents[i].getUsername().length());j++){
                out.print(" ");
            }
            out.print("       ");
        }
        out.println("\n");
        for(int i = 0;i<opponents.length;i++){
            wf[i] = opponents[i].getWF();
        }
        windowFrameGenerator(wf);
    }
    @Override
    public void updatePlayerWF(Player player){
        out.println("You (" + player.getUsername() + "):\n");
        WindowFrame[] wf = new WindowFrame[1];
        wf[0] = player.getWF();
        windowFrameGenerator(wf);
    }
    @Override
    public void updateRT(ArrayList<ArrayList<Die>> roundTrack, int round) {
        int big = 0;
        for (int i=0;i<round;i++){
            if(roundTrack.get(i).size() > big){
                big = roundTrack.get(i).size();
            }
        }
        out.println("Roundtrack:\n");
        for (int i=0;i<big;i++){
            for (int j =0;j<round;j++){
                out.print((j+1) + ": " + roundTrack.get(j).get(i) + " | ");
            }
            out.println();
        }
        out.println();
    }
    @Override
    public void updateMainScreen(MainScreenInfo mainScreenInfo){
        Player player = mainScreenInfo.getPlayer();
        Player[] opponents = mainScreenInfo.getOpponents();
        int round = mainScreenInfo.getRound();
        boolean backward = mainScreenInfo.isBackward();
        ArrayList<Die> draftPool = mainScreenInfo.getDraftPool();
        ArrayList<ArrayList<Die>> roundTrack = mainScreenInfo.getRoundTrack();
        updateInfo(player.getTokens(),round,backward);
        out.println();
        updateDP(draftPool);
        out.println();
        updateRT(roundTrack,round);
        updateOpponentsWF(opponents);
        updatePlayerWF(player);
    }
    @Override
    public void updateConnectionRequest(boolean success){
        if(success){
            out.println("Congratulations you are now connected\n");
        }
        else{
            out.println("Sorry, there was a problem connecting to the server, please check ip and port\n");
        }
    }
    @Override
    public void endGame(Player[] leaderboard,Player player,int[] score){
        out.println("Match is over here is the Leaderboard:\n");
        for(int i = 0; i<leaderboard.length;i++){
            out.println("#"+i+" - " + leaderboard[i].getUsername() + "Points: " + score[i]+ "\n\n");
        }
        if(Objects.equals(player.getUsername(), leaderboard[0].getUsername())){
            out.println("Congratulations you won, good Job!");
        }
        else if(Objects.equals(player.getUsername(), leaderboard[1].getUsername())){
            out.println("You came out second");
        }
        else if(Objects.equals(player.getUsername(), leaderboard[2].getUsername())){
            out.println("You came out third");
        }
        else{
            out.println("You came out fourth");
        }
    }
    //TODO TEST AFTER TOOLCARDS ARE DONE
    @Override
    public void updateToolCards(ToolCard[] toolCards){
        String[] names = new String[toolCards.length];
        String[] description = new String[toolCards.length];
        for (int i = 0; i < toolCards.length; i++) {
            for (int z = 0; z < CARD_WIDTH +4; z++) {
                out.print("-");
            }
            out.print("        ");
        }
        out.println();
        for(int i = 0;i<toolCards.length;i++){
           names[i] = toolCards[i].getName();
           description[i] = toolCards[i].getDescription();
        }

        layoutFormatter(names,toolCards.length);
        layoutFormatter(description,toolCards.length);

    }
    //TODO remove input as soon as observable is implemented
    @Override
    public String input(){
        return userInput.nextLine().toLowerCase();
    }
    @Override
    public void invalidMoveError(){
        out.println("ERROR: Invalid Move");
    }
    @Override
    public void selectionMaker(String[] string){
        out.println("Please select an option:");
        for(int i=0; i<string.length;i++){
            out.print(i + ": ");
            out.println(string[i]);
        }
    }
    @Override
    public void help(){
        out.println("All of the commands need to be on the same line separated by a comma(,), all the lines should either start with toolcard");
        out.println("or with placement, if toolcard is chosen you should only select one toolcard at a time when making selections/placements\n");
        out.println("Here are the possible commands you can use:\n");
        out.println("   board = show the main board");
        out.println("   toolcard");
        out.println("       [element] = select Tool Card #[element]");
        out.println("           select = make a selection");
        out.println("               draftpool [element] = select from draftpool element #[element]");
        out.println("               roundtrack [round][element] = select from roundtrack the die on the [element] position that was on round #[round]");
        out.println("               windowframe [row][col] = select from windowframe die on cell[row][col]");
        out.println("           place = make a placement");
        out.println("               draftpool [row][col] = place die selected from draftpool on cell[row][col]");
        out.println("               windowframe [row][col] = move die selected from windowframe die on cell[row][col]");
        out.println("       all = show all Tool Cards\n");
        out.println("   placement");
        out.println("       select [element] = select from draftpool element #[element] ");
        out.println("       place [row][col] = place die selected from draftpool on the cell[row][col] of the window frame");

    }
    @Override
    public void updatePlayerLobby(ArrayList<Player> players){
        for (Player player : players) {
            out.println(player.getUsername());
        }
        out.println();
    }
    @Override
    public void updatePlayerState(Player player){
        if(player.isConnected()){
        out.println(player.getUsername() + " just disconnected from the game\n");}
        else{
            out.println(player.getUsername() + " just came back into the game\n");}
    }

    public void patternCardGenerator(ArrayList<PatternCard> pc){
        for(int i =0;i<pc.size();i++){
            String prefix= i + ": Front";
            out.print(prefix);
            for(int j=0;j< (PATTERN_CARD_SIZE - prefix.length());j++){
                out.print(" ");
            }
            out.print(SPACE);
            prefix = i + ": Back";
            out.print(prefix);
            for(int j=0;j< (PATTERN_CARD_SIZE - prefix.length());j++){
                out.print(" ");
            }
            out.print(SPACE);

        }
        out.println();
        for(int i = 0; i< ROW_SIZE; i++){
            for(int j=0;j<pc.size();j++){
                for(int k = 0; k< COL_SIZE; k++){
                    out.print("|"  + pc.get(j).getCell(true,i,k) + "|");
                }
                out.print(SPACE);
                for(int k = 0; k< COL_SIZE; k++){
                    out.print("|"  + pc.get(j).getCell(false,i,k) + "|");
                }
                out.print(SPACE);

            }
            out.println();
        }
        out.println();
    }

    private void layoutFormatter(String[] string,int numCards) {
        int[] size = new int[numCards];
        int big = 0;
        for (int i = 0; i < numCards; i++) {
            size[i] = (string[i].length()-1) / CARD_WIDTH + 1;
        }

        for (int i = 0; i < numCards; i++) {
            if(size[i]>big){
                big = size[i];
            }
        }
        big = big% CARD_WIDTH;
        for(int i = 0;i<big;i++){
            for(int j = 0;j<numCards;j++){
                int spaceSize = 0;
                if (string[j].length() % CARD_WIDTH != 0) {
                    spaceSize = CARD_WIDTH - string[j].length() % CARD_WIDTH;
                }
                out.print("- ");
                if(i == string[j].length()/ CARD_WIDTH){
                    out.print(string[j].substring(i * CARD_WIDTH, i * CARD_WIDTH + string[j].length()%(CARD_WIDTH)));
                    for (int z = 0; z < spaceSize; z++) {
                        out.print(" ");
                    }
                }
                else if(i > string[j].length()/ CARD_WIDTH){
                        for (int z = 0; z < CARD_WIDTH; z++) {
                        out.print(" ");
                    }                }
                else {
                    out.print(string[j].substring(i * CARD_WIDTH, i * CARD_WIDTH + CARD_WIDTH));
                }
                out.print(" -        ");
            }
            out.println();

        }
        for (int i = 0; i < numCards; i++) {
            for (int z = 0; z < CARD_WIDTH +4; z++) {
                out.print("-");
            }
            out.print("        ");
        }
        out.println();
    }

    private void windowFrameGenerator(WindowFrame[] wf){
       for(int i = 0; i< ROW_SIZE; i++){
          for(int j=0;j<wf.length;j++){
              for(int k = 0; k< COL_SIZE; k++){
                  Die die = wf[j].getPlacements()[i][k];
                  if(wf[j].getPlacements()[i][k] != null){
                      out.print("|" + die + "|");}
                  else {
                      out.print("|"  + wf[j].getPCCell(i,k) + "|");}
              }
              out.print("       ");
          }
          out.println();
       }
       out.println();
    }
}



