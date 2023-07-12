package moves;

import interfaces.MoveIF;
import interfaces.PieceIF;
import model.Board;
import model.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Authors: Laurin(50%), Josiah(50%)
 */
public class Castling implements MoveIF {

    /**
     * A method for getting a list of legal Positions our
     * piece can move to. If no valid moves, return null.
     *
     * @param piece - The piece we're getting valid positions for.
     * @param board - The board we're analyzing for valid positions.
     */
    @Override
    public List<Position> getValidPositions(PieceIF piece, Board board) {
        List<Position> validPositions = new ArrayList<Position>();
//        if ((piece.getChessPieceType() != ChessPieceType.KING) ||
//                (piece.getMoveCount() != 0)) return validPositions;
//
//        int rank = piece.getPosition().getRank().getRow();
//        int file = piece.getPosition().getFile().getColumn();
//
//        int kingside = file + 2;
//        int queenside = file - 2;
//
//        for (int i = 1; i < kingside; i++) {
//            if(board.getSquare(rank, file + i).isOccupied()) {
//                kingside = 0;
//                break;
//            }
//        }
//        for (int i = 1; i > queenside; i--) {
//            if(board.getSquare(rank, file + i).isOccupied()) {
//                queenside = 0;
//                break;
//            }
//        }
//
//        Square square;
//        Piece rook;
//        Rank rookRank = (piece.isWhite()) ? Rank.R1 : Rank.R8;
//        if(kingside != 0) {
//            rook = board.getPiece(rookRank, File.H);
//            if(rook == null ||
//                    rook.getChessPieceType() != ChessPieceType.ROOK ||
//                    rook.getMoveCount() >= 1) return validPositions;
//            square = board.getSquare(rank, kingside);
//            validPositions.add(square.getPosition());
//        }
//        if(queenside != 0) {
//            rook = board.getPiece(rookRank, File.A);
//            if(rook == null ||
//                    rook.getChessPieceType() != ChessPieceType.ROOK ||
//                    rook.getMoveCount() >= 1) return validPositions;
//            square = board.getSquare(rank, queenside);
//            validPositions.add(square.getPosition());
//        }

        return validPositions;
    }


}
