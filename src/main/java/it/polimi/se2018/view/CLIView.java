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
public class CLIView extends View{

    private ArrayList<Die> draftPool = new ArrayList<>();
    private Scanner user_input = new Scanner(System.in);
    private String IP;
    private String port;
    private String type = null;
    private boolean correct = false;
    private static final int cardWidth = 26;
    private static final int privOCSize = 1;
    private static final int rowSize = 4;
    private static final int colSize = 5;
    private static final String publicObjString = "Public Objective";


    public CLIView() {
    }
    public void play(String type){
        //TODO SEND CONNECTION TYPE
        //Thread clientGatherer = new ClientGatherer();
        PlayerAction playerAction = new PlayerAction();
        //clientGatherer.run();
        out.println("Insert your username:\n");
        playerAction.setUsernameReq(user_input.next());

    }
    //TODO REWIEW ABOVE METHODS
    @Override
    public void updateWaitingRoom(boolean starting){
        if(starting){
            out.println("The game is about to start,Good Luck");
        }
        else{
            out.println("Please wait while some other players join in");
        }
    }
    @Override
    public void welcomeScreen(){
        correct = false;
        out.println("\n would you like to play on RMI or Socket?\n");
        type = user_input.next().toLowerCase();
        PlayerAction playerAction = new PlayerAction();
        playerAction.setConnection(type);
        while (!correct){
            out.println("\n Ok now kindly insert the server's IP address\n");
            IP = user_input.next();
            out.println(IP + "\nPlease insert the port now\n");
            port = user_input.next();
            out.println("Server: " + IP + "\nPort: " + port + "\nis it correct? (y/n)");
            if(Objects.equals(user_input.next(), "y")){
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
        if (backward == true){
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
        String[] string = new String[privOCSize];
        for (int i = 0; i < privOCSize; i++) {
            for (int z = 0; z < cardWidth+4; z++) {
                out.print("-");
            }
            out.print("        ");
        }
        out.println();
        string[0] = "Private Objective";
        layoutFormatter(string,privOCSize);
        string[0] = "Shades " + privObjCard.getColor().toString().toLowerCase();
        layoutFormatter(string,privOCSize);
        string[0] = "You score as many points as the values of the shades on all of the " + privObjCard + " dice";
        layoutFormatter(string,privOCSize);

    }
    @Override
    public void updatePubOCs(PubObjCard pubObjCards[]){
        int pubOCSize = pubObjCards.length;
        for (int i = 0; i < pubOCSize; i++) {
            for (int z = 0; z < cardWidth+4; z++) {
                out.print("-");
            }
            out.print("        ");
        }
        out.println();
        String[] pOCs = new String[pubOCSize];
        for(int i=0;i<pubOCSize;i++) {
            pOCs[i] = publicObjString;
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
        out.println("Tu (" + player.getUsername() + "):\n");
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
            out.println("Congratulations you are now connected");
        }
        else{
            out.println("Sorry, there was a problem connecting to the server, please check IP and port");
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
    public void updateToolCards(ArrayList<ToolCard> toolCard){
        String[] names = new String[toolCard.size()];
        String[] description = new String[toolCard.size()];
        for (int i = 0; i < toolCard.size(); i++) {
            for (int z = 0; z < cardWidth+4; z++) {
                out.print("-");
            }
            out.print("        ");
        }
        out.println();
        for(int i = 0;i<toolCard.size();i++){
           names[i] = toolCard.get(i).getName();
           description[i] = toolCard.get(i).getDescription();
        }

        layoutFormatter(names,toolCard.size());
        layoutFormatter(description,toolCard.size());

    }
    @Override
    public String input(){
        return user_input.next().toLowerCase();
    }
    @Override
    public void errorMessage(String string){
    }
    private void layoutFormatter(String[] string,int numCards) {
        int size[] = new int[numCards];
        int big = 0;
        for (int i = 0; i < numCards; i++) {
            size[i] = (string[i].length()-1) / cardWidth + 1;
        }

        for (int i = 0; i < numCards; i++) {
            if(size[i]>big){
                big = size[i];
            }
        }
        big = big%cardWidth;
        for(int i = 0;i<big;i++){
            for(int j = 0;j<numCards;j++){
                int spaceSize = 0;
                if (string[j].length() % cardWidth != 0) {
                    spaceSize = cardWidth - string[j].length() % cardWidth;
                }
                out.print("- ");
                if(i == string[j].length()/cardWidth){
                    out.print(string[j].substring(i * cardWidth, i * cardWidth + string[j].length()%(cardWidth)));
                    for (int z = 0; z < spaceSize; z++) {
                        out.print(" ");
                    }
                }
                else if(i > string[j].length()/cardWidth){
                        for (int z = 0; z < cardWidth; z++) {
                        out.print(" ");
                    }                }
                else {
                    out.print(string[j].substring(i * cardWidth, i * cardWidth + cardWidth));
                }
                out.print(" -        ");
            }
            out.println();

        }
        for (int i = 0; i < numCards; i++) {
            for (int z = 0; z < cardWidth+4; z++) {
                out.print("-");
            }
            out.print("        ");
        }
        out.println();
    }

    private void windowFrameGenerator(WindowFrame[] wf){
       for(int i = 0;i<rowSize;i++){
          for(int j=0;j<wf.length;j++){
              for(int k=0;k<colSize;k++){
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


    //TODO FINISH OTHER SCREENS,CHECK METHODS ABOVE
}



