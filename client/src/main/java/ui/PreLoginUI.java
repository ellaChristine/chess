package ui;

import client.ServerFacade;
import model.AuthData;

import java.util.Scanner;

public class PreLoginUI {

    private final ServerFacade facade;
    private final Scanner scanner;

    public PreLoginUI(ServerFacade facade, Scanner scanner) {
        this.facade = facade;
        this.scanner = scanner;
    }

    private enum Options{
        help,
        quit,
        login,
        register
    }

    public AuthData run(){
        while(true){
            System.out.println("Welcome to 240 chess. Type Help to get started.");
            String line = scanner.nextLine();
            String[] parts = line.trim().split(" ");
            String command = parts[0];
            if(!valid(command)){
                System.out.println("Error: input not recognized, Type help to get started");
            }
            if(command == "help"){
                System.out.print("register <USERNAME> <PASSWORD> <EMAIL> - to create an account\n" +
                        "login <USERNAME> <PASSWORD> - to play chess\n quit - playing chess\n" +
                        "help - with possible commands\n");
            }
        }
        //print out starting statement
        //if they type help print out options for them for prelogin
        //if they register, log them in and return auth data
        // if they log in return the authdata
        //if they quit return null
    }

    private boolean valid(String value){
        try{
            Options.valueOf(value);
            return true;
        } catch(IllegalArgumentException | NullPointerException e){
            return false;
        }
    }
}
