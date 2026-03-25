package ui;

import client.ServerFacade;
import exception.ResponseException;
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
        System.out.println("♕ Welcome to 240 chess. Type Help to get started.♕");
        while(true){
            String line = scanner.nextLine();
            String[] parts = line.trim().split(" ");
            String command = parts[0];
            if(!valid(command)){
                System.out.println("Error: input not recognized, Type help to get started");
            }
            if(command.equalsIgnoreCase("help")){
                System.out.print("register <USERNAME> <PASSWORD> <EMAIL> - to create an account\n" +
                        "login <USERNAME> <PASSWORD> - to play chess\n quit - playing chess\n" +
                        "help - with possible commands\n");
            } else if (command.equalsIgnoreCase("quit")) {
                return null;
            } else if (command.equalsIgnoreCase("login")) {
                if(parts.length <3){
                    System.out.println("Expected: login <USERNAME> <PASSWORD>");
                }
                else{
                    String username = parts[1];
                    String password = parts[2];
                    try{
                        AuthData data = facade.login(username,password);
                        return data;
                    } catch (ResponseException e) {
                        System.out.println("Error:" + e.getMessage());
                    }
                }

            } else if (command.equalsIgnoreCase("register")) {
                if(parts.length <4){
                    System.out.println("Expected: register <USERNAME> <PASSWORD> <EMAIL>");
                }
                else{
                    String username = parts[1];
                    String password = parts[2];
                    String email = parts[3];
                    try{
                        AuthData registerData = facade.register(username,password, email);
                        return registerData;
                    } catch (ResponseException e) {
                        System.out.println("Error:" + e.getMessage());
                    }
                }
            }
        }

    }

    private boolean valid(String value){
        try{
            String toLower = value.toLowerCase();
            Options.valueOf(toLower);
            return true;
        } catch(IllegalArgumentException | NullPointerException e){
            return false;
        }
    }
}
