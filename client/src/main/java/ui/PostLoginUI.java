package ui;

import client.ServerFacade;
import exception.ResponseException;
import model.AuthData;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
                try{
                    facade.logout(authToken);
                    return false;
                } catch (ResponseException e) {
                    System.out.println(e.getMessage());
                }
            } else if (command.equalsIgnoreCase("create")) {
                if(parts.length <2){
                    System.out.println("Expected: createGame <NAME>");
                }else{
                    String name = parts[1];
                    try{
                        facade.createGame(name,authToken);
                    } catch (ResponseException e) {
                        System.out.println(e.getMessage());                    }
                }
            } else if (command.equalsIgnoreCase("list")) {
                try{
                    Collection<GameData> list = facade.listGames(authToken);
                    int x = 1;
                    for(GameData item:list){
                        System.out.println(x + ". " + item.gameName() + " - white: " + item.whiteUsername() +
                                ", black: " + item.blackUsername());
                        games.add(item);
                        x= x+1;

                    }
                }catch (ResponseException e){
                    System.out.println(e.getMessage());
                }

            } else if (command.equalsIgnoreCase("join")) {
                this.join(parts);
            } else if(command.equalsIgnoreCase("observe")){
                this.observe(parts);
            }

        }
    }

    private void join(String[] parts){
        if(parts.length <3){
            System.out.println("Expected: join <ID> [WHITE|BLACK]");
        } else {
            if (!parts[2].equals("WHITE") && !parts[2].equals("BLACK")) {
                System.out.println("Expected: WHITE or BLACK");
            }
            try {
                int gameNumber = Integer.parseInt(parts[1]);
                if(gameNumber <1|| gameNumber > games.size()){
                    System.out.println("Error: invalid game number, please list games and try again");
                }
                else{
                    facade.joinGame(parts[2], gameNumber, authToken);
                    ChessBoardDrawer.draw(parts[2]);
                }

            } catch (ResponseException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Error: game id must be a number");
            }
        }
    }

    private void observe(String[] parts){
        if(parts.length <2){
            System.out.println("Expected: observe <ID>");
        }
        else{
            try{
                Integer gameID = Integer.parseInt(parts[1]);
                if(gameID < 1 || gameID > games.size()){
                    System.out.println("Error: invalid game number, please list games and try again");
                }
                else {
                    ChessBoardDrawer.draw("WHITE");
                }
            } catch (NumberFormatException e){
                System.out.println("Error: game id must be a number");
            }

        }
    }

    private List<GameData> games = new ArrayList<>();

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
