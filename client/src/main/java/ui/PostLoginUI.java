package ui;

import client.ServerFacade;
import exception.ResponseException;
import model.AuthData;

import java.util.Scanner;

public class PostLoginUI {

    private final ServerFacade facade;
    private final Scanner scanner;
    private final String authToken;

    public PostLoginUI(ServerFacade facade, Scanner scanner, String authToken) {
        this.facade = facade;
        this.scanner = scanner;
        this.authToken = authToken;
    }

    public boolean run(){
        while(true){
            System.out.print("[LOGGED_IN] >>> ");
            String line = scanner.nextLine();
            String[] parts = line.trim().split(" ");
            String command = parts[0];
            if(!valid(command)){
                System.out.println("Error: input not recognized, Type help to get started");
            }
            if(command.equalsIgnoreCase("help")){
                System.out.println("create <NAME> - a game\n" +
                        "list -games\n" +
                        "join <ID> [WHITE|BLACK] - a game\n" +
                        "observe <ID> - a game\n" +
                        "logout - when you are done\n" +
                        "quit - playing chess\n" +
                        "help - with possible commands\n");
            } else if (command.equalsIgnoreCase("quit")) {
                System.exit(0);
            } else if (command.equalsIgnoreCase("logout")) {
                return false;
            } else if (command.equalsIgnoreCase("create")) {
                if(parts.length <2){
                    System.out.println("Expected: createGame <NAME>");
                }else{
                    String name = parts[1];
                    try{
                        facade.createGame(name,authToken);
                        return true;
                    } catch (ResponseException e) {
                        System.out.println(e.getMessage());                    }
                }
            } else if (command.equalsIgnoreCase("list")) {

            }
        }
    }

    private enum Options{
        create,
        list,
        join,
        observe,
        logout,
        quit,
        help
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
