package ui;
/*

 */
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;

public class ChessBoardDrawer {

    public static void draw(String playerColor){
        if(playerColor.equals("WHITE")){
            ChessBoardDrawer.drawBoardForWhite();
        }
        else{
            ChessBoardDrawer.drawBoardForBlack();
        }
    }

    private static void drawBoardForBlack(){
        ChessBoardDrawer.drawEndBlack();
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print("\u20031 ");
        System.out.print(SET_TEXT_BOLD);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(WHITE_ROOK);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(WHITE_KNIGHT);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(WHITE_BISHOP);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(WHITE_KING);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(WHITE_QUEEN);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(WHITE_BISHOP);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(WHITE_KNIGHT);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(WHITE_ROOK);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(" 1\u2003");
        System.out.print(RESET_BG_COLOR);
        System.out.print("\n");
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print("\u20032 ");
        System.out.print(SET_TEXT_BOLD);
        System.out.print(WHITE_PAWN);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(WHITE_PAWN);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(WHITE_PAWN);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(WHITE_PAWN);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(WHITE_PAWN);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(WHITE_PAWN);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(WHITE_PAWN);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(WHITE_PAWN);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(" 2\u2003");
        System.out.print(RESET_BG_COLOR);
        System.out.print("\n");
        ChessBoardDrawer.drawMiddleBlack();
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print("\u20037 ");
        System.out.print(SET_TEXT_BOLD);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(BLACK_PAWN);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(BLACK_PAWN);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(BLACK_PAWN);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(BLACK_PAWN);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(BLACK_PAWN);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(BLACK_PAWN);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(BLACK_PAWN);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(BLACK_PAWN);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(" 7\u2003");
        System.out.print(RESET_BG_COLOR);
        System.out.print("\n");
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print("\u20038 ");
        System.out.print(SET_TEXT_BOLD);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(BLACK_ROOK);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(BLACK_KNIGHT);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(BLACK_BISHOP);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(BLACK_KING);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(BLACK_QUEEN);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(BLACK_BISHOP);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(BLACK_KNIGHT);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(BLACK_ROOK);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(" 8\u2003");
        System.out.print(RESET_BG_COLOR);
        System.out.print("\n");
        ChessBoardDrawer.drawEndBlack();


    }

    private static void drawBoardForWhite(){
        ChessBoardDrawer.drawEndWhite();
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print("\u20038 ");
        System.out.print(SET_TEXT_BOLD);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(BLACK_ROOK);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(BLACK_KNIGHT);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(BLACK_BISHOP);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(BLACK_QUEEN);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(BLACK_KING);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(BLACK_BISHOP);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(BLACK_KNIGHT);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(BLACK_ROOK);
        System.out.print(" 8\u2003");
        System.out.print(RESET_BG_COLOR);
        System.out.print("\n");
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print("\u20037 ");
        System.out.print(SET_TEXT_BOLD);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(BLACK_PAWN);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(BLACK_PAWN);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(BLACK_PAWN);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(BLACK_PAWN);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(BLACK_PAWN);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(BLACK_PAWN);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(BLACK_PAWN);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(BLACK_PAWN);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(" 7\u2003");
        System.out.print(RESET_BG_COLOR);
        System.out.print("\n");
        ChessBoardDrawer.drawMiddle();
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print("\u20032 ");
        System.out.print(SET_TEXT_BOLD);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(WHITE_PAWN);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(WHITE_PAWN);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(WHITE_PAWN);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(WHITE_PAWN);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(WHITE_PAWN);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(WHITE_PAWN);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(WHITE_PAWN);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(WHITE_PAWN);
        System.out.print(" 2\u2003");
        System.out.print(RESET_BG_COLOR);
        System.out.print("\n");
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print("\u20031 ");
        System.out.print(SET_TEXT_BOLD);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(WHITE_ROOK);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(WHITE_KNIGHT);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(WHITE_BISHOP);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(WHITE_QUEEN);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(WHITE_KING);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(WHITE_BISHOP);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(WHITE_KNIGHT);
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(WHITE_ROOK);
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(" 1\u2003");
        System.out.print(RESET_BG_COLOR);
        System.out.print("\n");
        ChessBoardDrawer.drawEndWhite();

    }

    private static void drawMiddle(){
        for(int i = 6; i>=3; i--){
            if(i%2 == 0){
                System.out.print(SET_BG_COLOR_BLACK);
                System.out.print("\u2003"+i +" ");
                System.out.print(SET_TEXT_BOLD);
                System.out.print(SET_BG_COLOR_WHITE);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_BLACK);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_WHITE);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_BLACK);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_WHITE);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_BLACK);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_WHITE);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_BLACK);
                System.out.print(EMPTY);
                System.out.print(" "+i+"\u2003");
                System.out.print(RESET_BG_COLOR);
                System.out.print("\n");
            }
            if(i%2 !=0){
                System.out.print(SET_BG_COLOR_BLACK);
                System.out.print("\u2003"+i+" ");
                System.out.print(SET_TEXT_BOLD);
                System.out.print(SET_BG_COLOR_BLACK);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_WHITE);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_BLACK);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_WHITE);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_BLACK);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_WHITE);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_BLACK);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_WHITE);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_BLACK);
                System.out.print(" "+i+"\u2003");
                System.out.print(RESET_BG_COLOR);
                System.out.print("\n");
            }
        }
    }

    private static void drawMiddleBlack(){
        for(int i = 3; i<=6; i++){
            if(i%2 ==0){
                System.out.print(SET_BG_COLOR_BLACK);
                System.out.print("\u2003"+i+" ");
                System.out.print(SET_TEXT_BOLD);
                System.out.print(SET_BG_COLOR_BLACK);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_WHITE);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_BLACK);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_WHITE);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_BLACK);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_WHITE);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_BLACK);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_WHITE);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_BLACK);
                System.out.print(" "+i+"\u2003");
                System.out.print(RESET_BG_COLOR);
                System.out.print("\n");
            }
            else{
                System.out.print(SET_BG_COLOR_BLACK);
                System.out.print("\u2003"+i +" ");
                System.out.print(SET_TEXT_BOLD);
                System.out.print(SET_BG_COLOR_WHITE);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_BLACK);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_WHITE);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_BLACK);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_WHITE);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_BLACK);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_WHITE);
                System.out.print(EMPTY);
                System.out.print(SET_BG_COLOR_BLACK);
                System.out.print(EMPTY);
                System.out.print(" "+i+"\u2003");
                System.out.print(RESET_BG_COLOR);
                System.out.print("\n");
            }
        }
    }

    private static void drawEndWhite(){
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(SET_TEXT_COLOR_LIGHT_GREY);
        System.out.print(EMPTY);
        System.out.print("\u2003a \u2003b \u2003c \u2003d \u2003e \u2003f \u2003g \u2003h ");
        System.out.print(EMPTY);
        System.out.print(RESET_BG_COLOR);
        System.out.print("\n");
    }

    private static void drawEndBlack(){
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(SET_TEXT_COLOR_LIGHT_GREY);
        System.out.print(EMPTY);
        System.out.print("\u2003h \u2003g \u2003f \u2003e \u2003d \u2003c \u2003b \u2003a ");
        System.out.print(EMPTY);
        System.out.print(RESET_BG_COLOR);
        System.out.print("\n");
    }
}
