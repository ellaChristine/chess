package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private  ChessBoard board;
    private  TeamColor currentTurn;

    public ChessGame() {
        this.board = new ChessBoard();
        this.board.resetBoard();
        this.currentTurn = TeamColor.WHITE;

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return currentTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.currentTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        // get the piece from the start position
        ChessPiece piece = board.getPiece(startPosition);
        List<ChessMove> valid = new ArrayList<>();
        //check to see if the piece is null(if it is return null)
        if(piece == null){
            return null;
        }
        //get all the pieceMoves for that piece:
        Collection<ChessMove> possibleMoves = piece.pieceMoves(board, startPosition);
        //for each move:
        for(ChessMove move:possibleMoves){
            ChessBoard newBoard = makeCopy();
            ChessPiece newPiece = this.movePiece(newBoard,move);
            if(!isCopyInCheck(newPiece.getTeamColor(),newBoard)){
                valid.add(move);
            }
        }


        //check if your own king is in check after the move
        //if not in check, keep the move; if in check, discard it
        //return the filtered collection of valid moves.
        return valid;
    }
    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece piece = board.getPiece(move.getStartPosition());
        if(piece == null){
            throw new InvalidMoveException();
        }
        if(piece.getTeamColor() != this.getTeamTurn()){
            throw new InvalidMoveException();
        }
        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());
        if(!validMoves.contains(move)){
            throw new InvalidMoveException();
        }

        ChessPiece movedPiece = this.movePiece(board,move);
            //Switch the turn to the other team


        if(movedPiece.getTeamColor() == TeamColor.WHITE){
            setTeamTurn(TeamColor.BLACK);
        }
        if(movedPiece.getTeamColor() == TeamColor.BLACK){
            setTeamTurn(TeamColor.WHITE);
        }

    }

    private ChessPiece movePiece(ChessBoard board, ChessMove move) {
        ChessPosition start = move.getStartPosition();
        ChessPiece newPiece = board.getPiece(start);
        board.addPiece(start,null);

        if(move.getPromotionPiece() != null){
            ChessPiece.PieceType promotion = move.getPromotionPiece();
            board.addPiece(move.getEndPosition(),new ChessPiece(newPiece.getTeamColor(),promotion));
        }
        else{
            board.addPiece(move.getEndPosition(),newPiece);
        }
        return newPiece;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPosition = findKing(teamColor);

        //loop through the board
        return goThroughBoard(teamColor, kingPosition, board);
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
    //helper methods
    private boolean goThroughBoard(TeamColor teamColor, ChessPosition kingPosition, ChessBoard board) {
        for(int x=1; x<=8; x++){
            for(int y = 1; y<=8; y++){
                ChessPosition position = new ChessPosition(x,y);
                ChessPiece piece = board.getPiece(position);

                //if the position on the board has a piece check if it is an enemy piece
                if(piece != null && piece.getTeamColor() != teamColor){
                    //if that is true check if the end position of the piece moves that it can make are the kings position
                    Collection<ChessMove> possibleMoves = piece.pieceMoves(board, position);
                    for(ChessMove move:possibleMoves){
                        ChessPosition p = move.getEndPosition();
                        if(p.equals(kingPosition)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    private ChessPosition findKing(TeamColor teamColor) {
        for(int x = 1; x<=8; x++){
            for(int y = 1; y<=8; y++){
                ChessPosition position = new ChessPosition(x,y);
                ChessPiece piece = board.getPiece(position);

                if(piece != null && piece.getTeamColor() == teamColor && piece.getPieceType() == ChessPiece.PieceType.KING){
                    return position;

                }

            }
        }
        return null;
    }
    private boolean isCopyInCheck(TeamColor teamColor, ChessBoard board) {
        ChessPosition kingPosition = findNewKing(teamColor, board);

        //loop through the board
        return goThroughBoard(teamColor, kingPosition, board);
    }
    private ChessPosition findNewKing(TeamColor teamColor, ChessBoard board) {
        for(int x = 1; x<=8; x++){
            for(int y = 1; y<=8; y++){
                ChessPosition position = new ChessPosition(x,y);
                ChessPiece piece = board.getPiece(position);

                if(piece != null && piece.getTeamColor() == teamColor && piece.getPieceType() == ChessPiece.PieceType.KING){
                    return position;

                }

            }
        }
        return null;
    }

    private ChessBoard makeCopy() {
        //create a new empty board
        ChessBoard copy = new ChessBoard();
        // for row, col in this.board check to see if there is a piece at each position
        for(int r = 1; r<=8; r++){
            for(int c = 1; c<=8; c++){
                ChessPosition position = new ChessPosition(r,c);
                if(this.board.getPiece(position) != null){
                    // create a new chess piece object that has the same properties as the original
                    ChessPiece old = this.board.getPiece(position);
                    ChessPiece newPiece = new ChessPiece(old.getTeamColor(),old.getPieceType());
                    copy.addPiece(position, newPiece);
                }
            }
        }
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(board, chessGame.board) && currentTurn == chessGame.currentTurn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, currentTurn);
    }
}
