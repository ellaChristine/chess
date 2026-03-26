package ui;

import client.ServerFacade;
import model.AuthData;
import ui.PreLoginUI;

import java.util.Scanner;

public class UI {
    private final ServerFacade facade;
    private boolean loggedIn = false;
    private final Scanner scanner = new Scanner(System.in);

    public UI(ServerFacade facade) {
        this.facade = facade;
    }

    public void run(){
        while(true){
            if(!loggedIn){
                var authData =  new PreLoginUI(facade, scanner).run();
                //if it returns an authToken, switch to loggedIn
                if(authData != null){
                    loggedIn = true;
                    System.out.println("Logged in as " + authData.username());
                    boolean stayLoggedIn =new PostLoginUI(facade,scanner,authData.authToken()).run();
                    System.out.println("I am running PostLoginUI");
                    if(!stayLoggedIn){
                        loggedIn = false;
                    }
                }

            }
        }
    }
}
