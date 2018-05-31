package it.polimi.se2018.view;

import it.polimi.se2018.controller.PlayerAction;
import it.polimi.se2018.model.Model;
import it.polimi.se2018.network.ClientGatherer;
import it.polimi.se2018.network.SocketView;

import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.out;

public class CLIView {

    private Scanner user_input = new Scanner(System.in);
    private String IP;
    private String port;
    private String  type = null;
    private boolean correct = false;
    private static CLIView cliView =new CLIView();

    public CLIView() {
    }


    private void welcomeMSG() throws InterruptedException {
        correct = false;
        out.println("\n desideri giocare in RMI o Socket?\n");
        while (!Objects.equals(type, "rmi") && !Objects.equals(type, "socket")) {
            type = user_input.next().toLowerCase();
            if(!(Objects.equals(type, "rmi") || Objects.equals(type, "socket"))){
                out.println("inserisci un tipo valido\n");
            }
        }
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
            playSocket();
        }

    }

    public void main() throws InterruptedException {
        cliView.welcomeMSG();

    }
    public void playSocket(){
        //TODO initiate a socket game
        //Thread clientGatherer = new ClientGatherer();
        PlayerAction playerAction = new PlayerAction();
        //clientGatherer.run();
        playerAction.setSwitchConnReq(true);
        out.println("Inserisci il tuo username:\n");
        playerAction.setUsernameReq(user_input.next());
        //TODO crate a waiting room
        //TODO

    }
}

