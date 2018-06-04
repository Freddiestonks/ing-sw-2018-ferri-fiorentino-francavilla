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
    private String  type = null;
    private boolean correct = false;
    private static CLIView cliView =new CLIView();
    private static final int cardWidth = 25;
    private static final int privOCSize = 1;
    private static final int rowSize = 4;
    private static final int colSize = 5;
    private static final String publicObjString = "Obiettivo Pubblico";


    public CLIView() {
    }


    private void welcomeMSG() throws InterruptedException {
        correct = false;
        out.println("\n desideri giocare in RMI o Socket?\n");
        type = user_input.next().toLowerCase();
        PlayerAction playerAction = new PlayerAction();
        playerAction.setConnection(type);
        while (!correct){
            out.println("Benvenuto in Sagrada \n Perfavore inserisci il IP\n");
            IP = user_input.next();
            out.println(IP + "\nOra inserisci la porta\n");
            port = user_input.next();
            out.println("Server: " + IP + "\nPorta: " + port + "\nè corretto? (y/n)");
            if(Objects.equals(user_input.next(), "y")){
                correct = true;
            }
        }
        if(type.equalsIgnoreCase("socket")){
            out.println("Provo a connettermi in socket\n");
            play("socket");

        }

    }

    public void main() throws InterruptedException {
        cliView.welcomeMSG();

    }
    public void play(String type){
        //TODO SEND CONNECTION TYPE
        //Thread clientGatherer = new ClientGatherer();
        PlayerAction playerAction = new PlayerAction();
        //clientGatherer.run();
        playerAction.setSwitchConnReq(true);
        out.println("Inserisci il tuo username:\n");
        playerAction.setUsernameReq(user_input.next());
        //TODO crate a waiting room


    }
    //TODO REWIEW ABOVE METHODS
    @Override
    public void updateDP(ArrayList<Die> draftPool) {
        out.println("DraftPool:");
        for (int i = 0; i<draftPool.size();i++){
            out.println(i + " : " + draftPool.get(i));
        }
    }
    @Override
    public void updateTokens(int tokens){
        out.println("Hai attualmente " + tokens + " punti favore");
    }
    @Override
    public void updateRound(int round){
        out.println("Round: " + round);
    }
    @Override
    public void updateOrder(boolean backward){
        if (backward == true){
            out.println("Sei attualmente nel giro di andata");
        }
        else {
            out.println("Sei attualmente nel giro di ritorno");
        }
    }
    @Override
    public void updateInfos(int tokens,int round,boolean backward){
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
        string[0] = "Obiettivo Privato";
        layoutFormatter(string,privOCSize);
        string[0] = "Sfumature " + privObjCard;
        layoutFormatter(string,privOCSize);
        string[0] = "Somma del valore su tutti i dadi " + privObjCard;
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
            pOCs[i] = "Punti: " + pubObjCards[i].getPoints();
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
    public void updateMainScreen(Player player,Player[] opponents,int round,boolean backward){
        updateInfos(player.getTokens(),round,backward);
        out.println();
        updateOpponentsWF(opponents);
        updatePlayerWF(player);
    }
    private void layoutFormatter(String[] string,int numCards) {
        int size[] = new int[numCards];
        int big = 0;
        for (int i = 0; i < numCards; i++) {
            size[i] = string[i].length() / cardWidth + 1;
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
    //TODO FINISH LOADING SCREEN, FIRST PAGE AND OTHER SCREENS,CHECK METHODS ABOVE
}



