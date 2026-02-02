package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        List<ChessMove> moves = new ArrayList<>();
        if (piece.getPieceType() == PieceType.BISHOP || piece.getPieceType() == PieceType.QUEEN) {
            for (int i = 1; i <= 7; i++) {
                if(!checkMoves(board, myPosition, i, i, moves)){
                    break;
                }
            }
            for (int i = 1; i <= 7; i++) {
                if(!checkMoves(board, myPosition, -i, -i, moves)){
                    break;
                }
            }
            for (int i = 1; i <= 7; i++) {
                if(!checkMoves(board, myPosition, -i, i, moves)){
                    break;
                }
            }
            for (int i = 1; i <= 7; i++) {
                if(!checkMoves(board, myPosition, i, -i, moves)){
                    break;
                }
            }
        }

        if (piece.getPieceType() == PieceType.KING) {
            int[] row = new int[]{1, 1, 0, -1, -1, -1, 0, 1};int[] col = new int[]{0, 1, 1, 1, 0, -1, -1, -1};
            for (int i = 0; i <= 7; i++) {
                if(isInBounds(new ChessPosition(myPosition.getRow()+ row[i], myPosition.getColumn()+col[i]))){
                    checkMoves(board, myPosition, row[i], col[i], moves);
                }
            }
            return moves;
        }

        if (piece.getPieceType() == PieceType.KNIGHT) {
            int[] row = new int[]{2, 2, 1, -1, -2, -2, -1, 1};int[] col = new int[]{-1, 1, 2, 2, 1, -1, -2, -2};
            for (int i = 0; i <= 7; i++) {
                if(isInBounds(new ChessPosition(myPosition.getRow()+ row[i], myPosition.getColumn()+col[i]))){
                    checkMoves(board, myPosition, row[i], col[i], moves);
                }
            }
            return moves;
        }

        if (piece.getPieceType() == PieceType.PAWN) {
            if (piece.pieceColor == ChessGame.TeamColor.WHITE) {
                ChessPosition move_1 = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn());
                ChessPosition move_2 = new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn());
                ChessPosition capture1 = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1);
                ChessPosition capture2 = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1);

                return pawnMoves(board,myPosition,move_1,move_2,2,7, capture1,capture2);
            }

            if (piece.pieceColor == ChessGame.TeamColor.BLACK) {
                ChessPosition move_1 = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn());
                ChessPosition move_2 = new ChessPosition(myPosition.getRow() - 2, myPosition.getColumn());
                ChessPosition capture1 = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1);
                ChessPosition capture2 = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1);

                return pawnMoves(board, myPosition,move_1,move_2,7,2,capture1,capture2);
            }
        }

        if (piece.getPieceType() == PieceType.ROOK|| piece.getPieceType() == PieceType.QUEEN) {
            for (int i = 1; i <= 7; i++) {
                if(!checkMoves(board, myPosition, i, 0, moves)){
                    break;
                }
            }
            for (int i = 1; i <= 7; i++) {
                if(!checkMoves(board, myPosition, 0, i, moves)){
                    break;
                }
            }
            for (int i = 1; i <= 7; i++) {
                if(!checkMoves(board, myPosition, -i, 0, moves)){
                    break;
                }
            }
            for (int i = 1; i <= 7; i++) {
                if(!checkMoves(board, myPosition, 0, -i, moves)){
                    break;
                }
            }
        }

        return moves;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    private boolean checkMoves(ChessBoard board, ChessPosition myPosition, Integer rowInt, Integer colInt, Collection<ChessMove> moves) {
        ChessPiece piece = board.getPiece(myPosition);
        ChessPosition move = new ChessPosition(myPosition.getRow() + rowInt, myPosition.getColumn() + colInt);

        if (isInBounds(move)) {
            ChessPiece destination = board.getPiece(move);
            if (destination != null) {
                if (destination.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + rowInt, myPosition.getColumn() + colInt), null));
                    return false;
                }

            }
            else {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + rowInt, myPosition.getColumn() + colInt), null));
                return true;
            }
        }
        return false;
    }
    private Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition, ChessPosition move_1, ChessPosition move_2,Integer startRow,Integer promotionRow, ChessPosition capture1, ChessPosition capture2){
        ChessPiece piece = board.getPiece(myPosition);
        List<ChessMove> moves = new ArrayList<>();
        if (myPosition.getRow() == startRow) {
            ChessPiece destination1 = board.getPiece(move_1);
            ChessPiece destination2 = board.getPiece(move_2);

            if (destination1 == null && destination2 == null) {
                moves.add(new ChessMove(myPosition, move_1, null));
                moves.add(new ChessMove(myPosition, move_2, null));
            } else if (destination1 == null) {
                moves.add(new ChessMove(myPosition, move_1, null));
            }

        } else if (myPosition.getRow() == promotionRow) {
            ChessPiece promotionDestination = board.getPiece(move_1);
            if (promotionDestination == null) {
                moves.add(new ChessMove(myPosition, move_1, PieceType.QUEEN));
                moves.add(new ChessMove(myPosition, move_1, PieceType.BISHOP));
                moves.add(new ChessMove(myPosition, move_1, PieceType.ROOK));
                moves.add(new ChessMove(myPosition, move_1, PieceType.KNIGHT));
            }

        } else {
            ChessPiece destination = board.getPiece(move_1);
            if (destination == null) {
                moves.add(new ChessMove(myPosition, move_1, null));
            }
        }
        if (isInBounds(capture1)) {
            ChessPiece captureDestination1 = board.getPiece(capture1);
            if (captureDestination1 != null && captureDestination1.getTeamColor() != piece.getTeamColor()) {
                if (myPosition.getRow() == promotionRow) {
                    moves.add(new ChessMove(myPosition, capture1, PieceType.QUEEN));
                    moves.add(new ChessMove(myPosition, capture1, PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, capture1, PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, capture1, PieceType.ROOK));
                } else {
                    moves.add(new ChessMove(myPosition, capture1, null));
                }
            }
        }
        if (isInBounds(capture2)) {
            ChessPiece captureDestination2 = board.getPiece(capture2);
            if (captureDestination2 != null && captureDestination2.getTeamColor() != piece.getTeamColor()) {
                if (myPosition.getRow() == promotionRow) {
                    moves.add(new ChessMove(myPosition, capture2, PieceType.QUEEN));
                    moves.add(new ChessMove(myPosition, capture2, PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, capture2, PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, capture2, PieceType.ROOK));
                } else {
                    moves.add(new ChessMove(myPosition, capture2, null));
                }
            }
        }
        return moves;
    }

    private boolean isInBounds(ChessPosition position) {
        return position.getRow() <= 8 && position.getRow() >= 1 && position.getColumn() <= 8 && position.getColumn() >= 1;
    }
}