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
                int new_row = myPosition.getRow() + i;
                int new_col = myPosition.getColumn() + i;
                if (new_row > 8 || new_row < 1 || new_col > 8 || new_col < 1) {
                    break;
                }
                ChessPiece destination = board.getPiece(new ChessPosition(new_row, new_col));
                if (destination != null) {
                    if (destination.getTeamColor() != piece.getTeamColor()) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(new_row, new_col), null));
                    }
                    break;
                } else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(new_row, new_col), null));
                }
            }
            for (int i = 1; i <= 7; i++) {
                int new_row = myPosition.getRow() - i;
                int new_col = myPosition.getColumn() - i;
                if (new_row > 8 || new_row < 1 || new_col > 8 || new_col < 1) {
                    break;
                }
                ChessPiece destination = board.getPiece(new ChessPosition(new_row, new_col));
                if (destination != null) {
                    if (destination.getTeamColor() != piece.getTeamColor()) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(new_row, new_col), null));
                    }
                    break;
                } else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(new_row, new_col), null));
                }
            }
            for (int i = 1; i <= 7; i++) {
                int new_row = myPosition.getRow() + i;
                int new_col = myPosition.getColumn() - i;
                if (new_row > 8 || new_row < 1 || new_col > 8 || new_col < 1) {
                    break;
                }
                ChessPiece destination = board.getPiece(new ChessPosition(new_row, new_col));
                if (destination != null) {
                    if (destination.getTeamColor() != piece.getTeamColor()) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(new_row, new_col), null));
                    }
                    break;
                } else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(new_row, new_col), null));
                }
            }
            for (int i = 1; i <= 7; i++) {
                int new_row = myPosition.getRow() - i;
                int new_col = myPosition.getColumn() + i;
                if (new_row > 8 || new_row < 1 || new_col > 8 || new_col < 1) {
                    break;
                }
                ChessPiece destination = board.getPiece(new ChessPosition(new_row, new_col));
                if (destination != null) {
                    if (destination.getTeamColor() != piece.getTeamColor()) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(new_row, new_col), null));
                    }
                    break;
                } else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(new_row, new_col), null));
                }
            }
        }

        if (piece.getPieceType() == PieceType.KING) {
            int[] row = new int[]{1, 1, 0, -1, -1, -1, 0, 1};
            int[] col = new int[]{0, 1, 1, 1, 0, -1, -1, -1};
            for (int i = 0; i <= 7; i++) {
                int new_row = myPosition.getRow() + row[i];
                int new_col = myPosition.getColumn() + col[i];
                if (new_row > 8 || new_row < 1 || new_col > 8 || new_col < 1) {
                    break;
                }
                checkMoves(board, myPosition, row[i], col[i], moves);
            }
            return moves;
        }

        if (piece.getPieceType() == PieceType.KNIGHT) {
            int[] row = new int[]{2, 2, 1, -1, -2, -2, -1, 1};
            int[] col = new int[]{-1, 1, 2, 2, 1, -1, -2, -2};
            for (int i = 0; i <= 7; i++) {
                int new_row = myPosition.getRow() + row[i];
                int new_col = myPosition.getColumn() + col[i];
                if (new_row > 8 || new_row < 1 || new_col > 8 || new_col < 1) {
                    continue;
                }
                checkMoves(board, myPosition, row[i], col[i], moves);
            }
            return moves;
        }

        if (piece.getPieceType() == PieceType.PAWN) {
            if (piece.pieceColor == ChessGame.TeamColor.WHITE) {
                // start position

                if (myPosition.getRow() == 2) {
                    ChessPiece destination1 = board.getPiece(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()));
                    ChessPiece destination2 = board.getPiece(new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn()));

                    if (destination1 == null && destination2 == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), null));
                        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn()), null));
                    } else if (destination1 == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), null));
                    }

                } else if (myPosition.getRow() == 7) {
                    ChessPiece promotionDestination = board.getPiece(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()));
                    if (promotionDestination == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), PieceType.BISHOP));
                        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), PieceType.ROOK));
                        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), PieceType.KNIGHT));
                    }

                } else {
                    ChessPiece destination = board.getPiece(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()));
                    if (destination == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), null));
                    }
                }
                ChessPosition capture1 = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1);
                ChessPosition capture2 = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1);


                if (isInBounds(capture1)) {
                    ChessPiece captureDestination1 = board.getPiece(capture1);
                    if (captureDestination1 != null && captureDestination1.getTeamColor() != piece.getTeamColor()) {
                        if (myPosition.getRow() == 7) {
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
                        if (myPosition.getRow() == 7) {
                            moves.add(new ChessMove(myPosition, capture2, PieceType.QUEEN));
                            moves.add(new ChessMove(myPosition, capture2, PieceType.BISHOP));
                            moves.add(new ChessMove(myPosition, capture2, PieceType.KNIGHT));
                            moves.add(new ChessMove(myPosition, capture2, PieceType.ROOK));
                        } else {
                            moves.add(new ChessMove(myPosition, capture2, null));
                        }
                    }
                }


            }
            if (piece.pieceColor == ChessGame.TeamColor.BLACK) {
                if (myPosition.getRow() == 7) {
                    ChessPiece destination1 = board.getPiece(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()));
                    ChessPiece destination2 = board.getPiece(new ChessPosition(myPosition.getRow() - 2, myPosition.getColumn()));
                    if (destination1 == null & destination2 == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()), null));
                        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 2, myPosition.getColumn()), null));
                    } else if (destination1 == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()), null));
                    }
                } else if (myPosition.getRow() == 2) {
                    ChessPiece promotionDestination = board.getPiece(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()));
                    if (promotionDestination == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()), PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()), PieceType.BISHOP));
                        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()), PieceType.ROOK));
                        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()), PieceType.KNIGHT));
                    }
                } else {
                    ChessPiece destination = board.getPiece(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()));
                    if (destination == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()), null));
                    }
                }
                ChessPosition capture1 = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1);
                ChessPosition capture2 = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1);


                if (isInBounds(capture1)) {
                    ChessPiece captureDestination1 = board.getPiece(capture1);
                    if (captureDestination1 != null && captureDestination1.getTeamColor() != piece.getTeamColor()) {
                        if (myPosition.getRow() == 2) {
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
                        if (myPosition.getRow() == 2) {
                            moves.add(new ChessMove(myPosition, capture2, PieceType.QUEEN));
                            moves.add(new ChessMove(myPosition, capture2, PieceType.BISHOP));
                            moves.add(new ChessMove(myPosition, capture2, PieceType.KNIGHT));
                            moves.add(new ChessMove(myPosition, capture2, PieceType.ROOK));
                        } else {
                            moves.add(new ChessMove(myPosition, capture2, null));
                        }
                    }
                }
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


            return moves;
        }

        int size = board.place.length;
        List<ChessMove> valid_moves = new ArrayList<>();
        for (ChessMove move : moves) {
            ChessPosition end = move.getEndPosition();
            int row = end.getRow();
            int col = end.getColumn();
            if (row > size || row < 1) {
                continue;
            }
            if (col > size || col < 1) {
                continue;
            }
            valid_moves.add(move);
        }
        return valid_moves;
        //I now need to add in the broad functionality of what each chess piece can and can not do
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

    private boolean isInBounds(ChessPosition position) {
        return position.getRow() <= 8 && position.getRow() >= 1 && position.getColumn() <= 8 && position.getColumn() >= 1;

    }
}
