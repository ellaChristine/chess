package client;

import chess.*;
import ui.UI;

public class ClientMain {
    static void main(String[] args){
        var facade = new ServerFacade(8080);
        new UI(facade).run();
    }
}
