package client;

import chess.*;
import ui.UI;

public class ClientMain {
//    public static void main(String[] args) {
//        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
//        System.out.println("♕ 240 Chess Client: " + piece);
//    }
    public static void main(String[] args){
        var facade = new ServerFacade(8080);
        new UI(facade).run();
    }
}
