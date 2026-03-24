package ui;

import client.ServerFacade;
import model.AuthData;

public class UI {
    private final ServerFacade facade;
    private boolean loggedIn = false;

    public UI(ServerFacade facade) {
        this.facade = facade;
    }

    public void run(){
        while(true){
            if(!loggedIn){
                //hand off to preLoginUI
                //if it returns an authToken, switch to loggedIn

            } else {
                //hand off to PostLoginUI
                //if it logs out, switch back to !loggedIn
            }
        }
    }
}
