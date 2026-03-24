package ui;

import client.ServerFacade;

public class UI {
    private final ServerFacade facade;
    private boolean loggedIn = false;

    public UI(ServerFacade facade) {
        this.facade = facade;
    }

    public void run(){
        //main loop
    }
}
