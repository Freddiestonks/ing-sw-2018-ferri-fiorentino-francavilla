package it.polimi.se2018;

import it.polimi.se2018.view.CLIView;

import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * Hello world!
 *
 */
public class App {


    public static void main( String[] args ) throws InterruptedException {
        Scanner user_input = new Scanner(System.in);
        boolean correct = false;
        out.println("Benvenuto in Sagrada\ndesideri giocare da CLI o da GUI");
        while (!correct){
            String input = user_input.next().toLowerCase();
            switch (input) {
                case "cli":
                    //TODO NO VIEW MA CONTROLLER
                    CLIView cliView = new CLIView();
                    cliView.main();
                    correct = true;
                    break;
                case "gui":
                    correct = true;
                    break;
                default:
                    out.println("Inserisci un parametro valido(GUI o CLI)");
                    break;
            }
        }

    }

}

