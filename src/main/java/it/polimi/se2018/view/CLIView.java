package it.polimi.se2018.view;

import it.polimi.se2018.model.*;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.out;
/**
 * @author Federico Ferri,Alessio Fiorentino,Simone Francavilla
 *
 * */
public class CLIView extends View {

    private Scanner scanner = new Scanner(System.in);
    private MainScreenInfo mainScreenInfo;
    private static final int CARD_WIDTH = 26;
    private static final int PRIV_OC_SIZE = 1;
    private static final int ROW_SIZE = 4;
    private static final int COL_SIZE = 5;
    private static final int PATTERN_CARD_SIZE = 25;
    private static final String PUBLIC_OBJ_STRING = "Public Objective";
    private static final String SPACE = "        ";
    private static final String CURSOR = ">>";

    public CLIView() {
    }

    /**
     * This method wait the user input from command line and notify the ClientController
     */
    private void readInput(){
        boolean active = true;
        while (active) {
            synchronized (this) {
                out.print(CURSOR);
            }
            userInput = scanner.nextLine().toLowerCase();
            notifyObservers();
        }
    }

    public synchronized void updateWaitingRoom(boolean starting){
        if(starting){
            out.println("The game is about to start, Good Luck");
        }
        else{
            out.println("Please wait while some other players join in");
        }
    }

    protected synchronized void updateDP(ArrayList<Die> draftPool) {
        out.println("DraftPool:");
        for (int i = 0; i < draftPool.size(); i++){
            out.println(i + " : " + draftPool.get(i));
        }
    }

    protected synchronized void updateTokens(int tokens){
        out.println("You currently have " + tokens + " tokens");
    }

    protected synchronized void updateRound(int round){
        out.println("Round: " + round);
    }

    protected synchronized void updateOrder(boolean backward) {
        if (backward){
            out.println("You are in the backward order of this round");
        }
        else {
            out.println("You are in the forward order of the round");
        }
    }

    protected synchronized void updateTurnPlayer(String turnPlayer) {
        out.println("It is " + turnPlayer + "'s turn");
    }

    protected synchronized void updateInfo(int tokens, int round, boolean backward, String turnPlayer) {
        updateTokens(tokens);
        updateRound(round);
        updateOrder(backward);
        updateTurnPlayer(turnPlayer);
    }

    protected synchronized void updatePrivOCs(PrivObjCard privObjCard) {
        clearScreen();
        String[] string = new String[PRIV_OC_SIZE];
        for (int i = 0; i < PRIV_OC_SIZE; i++) {
            for (int z = 0; z < CARD_WIDTH + 4; z++) {
                out.print("-");
            }
            out.print(SPACE);
        }
        out.println();
        string[0] = "Private Objective";
        layoutFormatter(string, PRIV_OC_SIZE);
        string[0] = "Shades " + privObjCard;
        layoutFormatter(string, PRIV_OC_SIZE);
        string[0] = "You score as many points as the values of the shades on all of the " + privObjCard + " dice";
        layoutFormatter(string, PRIV_OC_SIZE);
    }

    public synchronized void showPubOCs(PubObjCard[] pubObjCards) {
        clearScreen();
        int pubOCSize = pubObjCards.length;
        for (int i = 0; i < pubOCSize; i++) {
            for (int z = 0; z < CARD_WIDTH + 4; z++) {
                out.print("-");
            }
            out.print(SPACE);
        }
        out.println();
        String[] pOCs = new String[pubOCSize];
        for(int i = 0; i < pubOCSize; i++) {
            pOCs[i] = PUBLIC_OBJ_STRING;
        }
        layoutFormatter(pOCs,pubOCSize);
        for(int i = 0; i < pubOCSize; i++) {
            pOCs[i] = pubObjCards[i].getCardName();
        }
        layoutFormatter(pOCs,pubOCSize);
        for(int i = 0; i < pubOCSize; i++) {
            pOCs[i] = pubObjCards[i].getDesc();
        }
        layoutFormatter(pOCs,pubOCSize);
        for(int i = 0; i < pubOCSize; i++) {
            pOCs[i] = "Points: " + pubObjCards[i].getPoints();
        }
        layoutFormatter(pOCs,pubOCSize);
        out.print(CURSOR);
    }

    protected void updateOpponentsWF(ArrayList<Player> opponents) {
        out.println();
        String offline = "(OFFLINE)";
        WindowFrame[] windowFrames = new WindowFrame[opponents.size()];
        for (int i = 0; i < opponents.size(); i++) {
            if(!opponents.get(i).isConnected()){
                out.print(offline);
            }
            else {
                for (int j = 0; j < offline.length(); j++) {
                    out.print(" ");
                }
            }
            for (int j = 0; j < (24 - offline.length()); j++){
                out.print(" ");
            }
            out.print("       ");
        }
        out.println();
        for(int i = 0; i < opponents.size(); i++) {
            if(opponents.get(i).getUsername().length() <= 24){
                out.print(opponents.get(i).getUsername() + ":");}
            else {
                out.print(opponents.get(i).getUsername().substring(0, 24) + ":");
            }
            for (int j = 0; j < (24 - opponents.get(i).getUsername().length()); j++){
                out.print(" ");
            }
            out.print("       ");
        }
        out.println("\n");
        for(int i = 0; i < opponents.size(); i++){
            windowFrames[i] = opponents.get(i).getWindowFrame();
        }
        windowFrameGenerator(windowFrames);
    }

    public synchronized void updatePlayerWF(Player player){
        out.println("You (" + player.getUsername() + "):\n");
        WindowFrame[] wf = new WindowFrame[1];
        wf[0] = player.getWindowFrame();
        windowFrameGenerator(wf);
    }

    public synchronized void updateRT(ArrayList<ArrayList<Die>> roundTrack) {
        int max = 0;
        for (int i = 0; i < 10; i++){
            if(roundTrack.get(i).size() > max){
                max = roundTrack.get(i).size();
            }
        }
        out.println("RoundTrack:");
        for(int i = 0; i < 9; i++) {
            out.print("  " + (i + 1) + " | ");
        }
        out.println(" 10 |");
        for(int i = 0; i < 10; i++) {
            out.print("------");
        }
        out.println();
        for(int i = 0; i < max; i++) {
            for(int j = 0; j < 10; j++) {
                if(i < roundTrack.get(j).size()) {
                    out.print(roundTrack.get(j).get(i) + " | ");
                }
                else {
                    out.print(" -  | ");
                }
            }
            out.println();
        }
        out.println();
    }

    public synchronized void showMainScreen() {
        if(this.mainScreenInfo != null) {
            updateMainScreen(this.mainScreenInfo);
        }
    }

    public synchronized void updateMainScreen(MainScreenInfo mainScreenInfo) {
        clearScreen();
        this.mainScreenInfo = mainScreenInfo;
        Player player = mainScreenInfo.getPlayer();
        ArrayList<Player> opponents = mainScreenInfo.getOpponents();
        int round = mainScreenInfo.getRound();
        boolean backward = mainScreenInfo.isBackward();
        String turnPlayer = mainScreenInfo.getTurnPlayer();
        ArrayList<Die> draftPool = mainScreenInfo.getDraftPool();
        ArrayList<ArrayList<Die>> roundTrack = mainScreenInfo.getRoundTrack();
        PrivObjCard privObjCard = mainScreenInfo.getPrivObjCard();
        updatePrivOCs(privObjCard);
        out.println();
        updateInfo(player.getTokens(), round, backward, turnPlayer);
        out.println();
        updateDP(draftPool);
        out.println();
        updateRT(roundTrack);
        updateOpponentsWF(opponents);
        updatePlayerWF(player);
        out.print(CURSOR);
    }

    public void endGame(ArrayList<Player> leaderBoard, Player player) {
        clearScreen();
        out.println("Match is over, here is the Leader Board:\n");
        for(int i = 0; i < leaderBoard.size(); i++){
            Player currentPlayer = leaderBoard.get(i);
            out.println("#" + (i + 1) + " - " + currentPlayer.getUsername() + " Points: " + currentPlayer.getScore() + "\n\n");
        }
        if(player.getUsername().equals(leaderBoard.get(0).getUsername())) {
            out.println("Congratulations you won, good job!");
        }
        else if(player.getUsername().equals(leaderBoard.get(1).getUsername())) {
            out.println("You came out second");
        }
        else if(player.getUsername().equals(leaderBoard.get(2).getUsername())) {
            out.println("You came out third");
        }
        else {
            out.println("You came out fourth");
        }
        out.println();
        out.print(CURSOR);
    }

    public synchronized void showToolCards(ToolCard[] toolCards) {
        clearScreen();
        String[] names = new String[toolCards.length];
        String[] description = new String[toolCards.length];
        for (int i = 0; i < toolCards.length; i++) {
            out.print((i + 1) + ":");
            for (int z = 0; z < CARD_WIDTH + 2; z++) {
                out.print(" ");
            }
            out.print("        ");
        }
        out.println();
        for (int i = 0; i < toolCards.length; i++) {
            for (int z = 0; z < CARD_WIDTH + 4; z++) {
                out.print("-");
            }
            out.print("        ");
        }
        out.println();
        for(int i = 0; i < toolCards.length; i++){
           names[i] = toolCards[i].getName();
           description[i] = toolCards[i].getDescription();
        }
        layoutFormatter(names, toolCards.length);
        layoutFormatter(description, toolCards.length);
    }

    public synchronized void invalidMoveError(boolean parsing){
        out.print("ERROR: Invalid Action");
        if(parsing) {
            out.print(" (parsing)");
        }
        out.println();
    }

    public synchronized void help() {
        clearScreen();
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
        out.println("       show = show all Tool Cards\n");
        out.println("   placement");
        out.println("       select [element] = select from draftpool element #[element] ");
        out.println("       place [row][col] = place die selected from draftpool on the cell[row][col] of the window frame");
        out.println("   skip = skip your turn");
    }

    public synchronized void updatePlayerLobby(ArrayList<String> usernames) {
        clearScreen();
        out.println("Players currently in the lobby:");
        String separator = "-----------------------------------------------------";
        out.println(separator);
        for (String username : usernames) {
            out.println("- " + username);
        }
        out.println(separator);
        out.println();
        out.print(CURSOR);
    }

    /*
    public synchronized void updatePlayerState(Player player) {
        if(player.isConnected()){
            out.println(player.getUsername() + " just disconnected from the game\n");
        }
        else {
            out.println(player.getUsername() + " just came back into the game\n");
        }
    }*/

    public synchronized void connectionError() {
        clearScreen();
        out.println("You have been disconnected, please reconnect using connect,[connection type],[ip]");
        out.print(CURSOR);
    }

    public synchronized void patternCardChooser(ArrayList<PatternCard> pc) {
        clearScreen();
        out.println("Pick a Pattern Card\n");
        for(int i = 0; i < pc.size(); i++){
            String prefix = i * 2 + ":";
            out.print(prefix);
            for(int j = 0; j < (PATTERN_CARD_SIZE - prefix.length()); j++){
                out.print(" ");
            }
            out.print(SPACE);
            prefix = i * 2 + 1 + ":";
            out.print(prefix);
            for(int j = 0; j < (PATTERN_CARD_SIZE - prefix.length()); j++){
                out.print(" ");
            }
            out.print(SPACE);

        }
        out.println();
        for(int i = 0; i < pc.size(); i++){
            String prefix = pc.get(i).getNameF();
            out.print(prefix);
            for(int j = 0; j < (PATTERN_CARD_SIZE - prefix.length()); j++){
                out.print(" ");
            }
            out.print(SPACE);
            prefix = pc.get(i).getNameB();
            out.print(prefix);
            for(int j = 0;j < (PATTERN_CARD_SIZE - prefix.length()); j++){
                out.print(" ");
            }
            out.print(SPACE);
        }
        out.println();
        for(int i = 0; i < pc.size(); i++){
            String prefix= "Level : " + pc.get(i).getLevelF();
            out.print(prefix);
            for(int j = 0; j < (PATTERN_CARD_SIZE - prefix.length()); j++){
                out.print(" ");
            }
            out.print(SPACE);
            prefix = "Level : " + pc.get(i).getLevelB();
            out.print(prefix);
            for(int j = 0; j < (PATTERN_CARD_SIZE - prefix.length()); j++){
                out.print(" ");
            }
            out.print(SPACE);
        }
        out.println();
        for(int i = 0; i < ROW_SIZE; i++){
            for(int j = 0; j < pc.size(); j++){
                for(int k = 0; k < COL_SIZE; k++){
                    out.print("|"  + pc.get(j).getCell(true, i, k) + "|");
                }
                out.print(SPACE);
                for(int k = 0; k < COL_SIZE; k++){
                    out.print("|"  + pc.get(j).getCell(false, i, k) + "|");
                }
                out.print(SPACE);

            }
            out.println();
        }
        out.println();
        out.print(CURSOR);
    }

    public synchronized void enteringError(boolean lobbyGathering) {
        if(lobbyGathering) {
            out.println("\nYour username has already taken, please choose another one");
        }
        else {
            out.println("\nSorry, the match has already started");
        }
        out.print(CURSOR);
    }

    public void startView() {
        readInput();
    }

    private void layoutFormatter(String[] string,int numCards) {
        int[] size = new int[numCards];
        int big = 0;
        for (int i = 0; i < numCards; i++) {
            size[i] = (string[i].length() - 1) / CARD_WIDTH + 1;
        }
        for (int i = 0; i < numCards; i++) {
            if(size[i]>big){
                big = size[i];
            }
        }
        big = big % CARD_WIDTH;
        for(int i = 0; i < big; i++){
            for(int j = 0; j < numCards; j++){
                int spaceSize = 0;
                if (string[j].length() % CARD_WIDTH != 0) {
                    spaceSize = CARD_WIDTH - string[j].length() % CARD_WIDTH;
                }
                out.print("- ");
                if(i == string[j].length() / CARD_WIDTH){
                    out.print(string[j].substring(i * CARD_WIDTH, i * CARD_WIDTH + string[j].length() % (CARD_WIDTH)));
                    for (int z = 0; z < spaceSize; z++) {
                        out.print(" ");
                    }
                }
                else if(i > string[j].length() / CARD_WIDTH){
                    for (int z = 0; z < CARD_WIDTH; z++) {
                        out.print(" ");
                    }
                }
                else {
                    out.print(string[j].substring(i * CARD_WIDTH, i * CARD_WIDTH + CARD_WIDTH));
                }
                out.print(" -        ");
            }
            out.println();

        }
        for (int i = 0; i < numCards; i++) {
            for (int z = 0; z < CARD_WIDTH + 4; z++) {
                out.print("-");
            }
            out.print("        ");
        }
        out.println();
    }

    private void windowFrameGenerator(WindowFrame[] windowFrames){
        for(int i = 0; i< ROW_SIZE; i++){
            for(int j = 0; j < windowFrames.length; j++){
                for(int k = 0; k < COL_SIZE; k++){
                    Die die = windowFrames[j].getPlacements()[i][k];
                    if(windowFrames[j].getPlacements()[i][k] != null){
                        out.print("|" + die + "|");
                    }
                    else {
                        out.print("|"  + windowFrames[j].getPCCell(i, k) + "|");
                    }
                }
                out.print("       ");
            }
            out.println();
        }
        out.println();
    }

    private void clearScreen() {
        for(int i = 0; i < 50; i++) {
            out.println();
        }
    }

}



