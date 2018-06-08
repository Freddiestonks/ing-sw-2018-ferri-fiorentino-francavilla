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


    public static void main( String[] args ){
        Scanner user_input = new Scanner(System.in);
        boolean correct = false;
        out.println("Welcome to Sagrada\nHow would you like to play?  (CLI/GUI)");
        while (!correct){
            String input = user_input.next().toLowerCase();
            switch (input) {
                case "cli":
                    //TODO NO VIEW MA CONTROLLER
                    CLIView cliView = new CLIView();
                    cliView.welcomeScreen();

                    correct = true;
                    break;
                case "gui":
                    correct = true;
                    break;
                default:
                    out.println("Please insert a valid command (GUI or CLI)");
                    break;
            }
        }

    }

}

