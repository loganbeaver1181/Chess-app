package ui_cli;

import enums.GameColor;
import enums.Rank;
import interfaces.BoardIF;
import interfaces.PieceIF;
import interfaces.UserInterfaceIF;
import model.*;
import java.util.List;

/**
 * Class for a monochromatic command line interface
 * @author Daniel Aoulou 100%
 */
public class Board_Mono_CLI extends Board_CLI implements UserInterfaceIF  {

    /**
     * A field for storing a previously created CLI
     */
    static Board_Mono_CLI Mono_CLI = null;

    /**
     * Draw a chess board in its current state.
     * @param board the board to draw
     */
    public void draw(BoardIF board) {
        GameStateEditor stateEditor = game.getStateEditor();
        Player currentPlayer = stateEditor.getCurrentPlayer();


        int widthStart = (currentPlayer.isWhite()) ? 0 : board.getWidth()-1;
        int widthEnd = (currentPlayer.isWhite()) ? board.getWidth() : -1;
        int widthIncrement = (currentPlayer.isWhite()) ? 1 : -1;

        int heightStart = (currentPlayer.isWhite()) ? board.getHeight() - 1 : 0;
        int heightEnd = (currentPlayer.isWhite()) ? -1 : board.getHeight();
        int heightIncrement = (currentPlayer.isWhite()) ? -1 : 1;

        int counter = (currentPlayer.isWhite()) ? 7 : 0;

        Rank[] ranks = Rank.values();
        for (int i = heightStart; i != heightEnd; i += heightIncrement) {
            System.out.print(ranks[counter].getRank());
            for (int j = widthStart; j != widthEnd; j += widthIncrement) {
                Square square = board.getBoard()[i][j];
                String lbracket;
                String rbracket;
                String bgcolor;
                if (square.getColor() == GameColor.WHITE) {
                    lbracket = WHITE_LEFT_BRACKET;
                    rbracket = WHITE_RIGHT_BRACKET;
                    bgcolor = WHITE_BACKGROUND;
                } else {
                    lbracket = BLACK_LEFT_BRACKET;
                    rbracket = BLACK_RIGHT_BRACKET;
                    bgcolor = BLACK_BACKGROUND;
                }

                String character = square.toString();
                if (square.getPiece() != null) character = this.getPieceCharacter(square.getPiece());
                System.out.print(lbracket + bgcolor + character + Colors.RESET + rbracket);
            }
            System.out.println("");
            if(currentPlayer.isWhite()) counter--;
            else counter++;
        }
        String files = (currentPlayer.isWhite()) ? ("  A  B  C  D  E  F  G  H") :
                ("  H  G  F  E  D  C  B  A");
        System.out.println(files);

        //printing the captured black pieces after every move
        System.out.println("Captured Black Pieces:");
        for(int i = 0; i < board.capturedBlack.size(); i++){
            System.out.println(board.capturedBlack.get(i).getChessPieceType().getName());
        }
        //printing the captured white pieces after every move
        System.out.println("Captured White Pieces:");
        for(int i = 0; i < board.capturedWhite.size(); i++){
            System.out.println(board.capturedWhite.get(i).getChessPieceType().getName());
        }
    }

    /**
     * Draw a chess board in its current state
     * with a piece's possible moves highlighted
     * @param board the board to draw
     */
    public void previewMoves(BoardIF board, PieceIF piece) {
        List < Position > validMoves = board.validateMoves(piece);
        GameStateEditor stateEditor = game.getStateEditor();
        Player currentPlayer = stateEditor.getCurrentPlayer();

        int widthStart = (currentPlayer.isWhite()) ? 0 : board.getWidth()-1;
        int widthEnd = (currentPlayer.isWhite()) ? board.getWidth() : -1;
        int widthIncrement = (currentPlayer.isWhite()) ? 1 : -1;

        int heightStart = (currentPlayer.isWhite()) ? board.getHeight() - 1 : 0;
        int heightEnd = (currentPlayer.isWhite()) ? -1 : board.getHeight();
        int heightIncrement = (currentPlayer.isWhite()) ? -1 : 1;

        int counter = (currentPlayer.isWhite()) ? 7 : 0;

        Rank[] ranks = Rank.values();
        for (int i = heightStart; i != heightEnd; i += heightIncrement) {
            System.out.print(ranks[counter].getRank());
            for (int j = widthStart; j != widthEnd; j += widthIncrement) {
                String lbracket;
                String rbracket;
                String bgcolor;
                Square square = board.getBoard()[i][j];
                if (square.getColor() == GameColor.WHITE) {
                    lbracket = WHITE_LEFT_BRACKET;
                    rbracket = WHITE_RIGHT_BRACKET;
                    bgcolor = WHITE_BACKGROUND;
                } else {
                    lbracket = BLACK_LEFT_BRACKET;
                    rbracket = BLACK_RIGHT_BRACKET;
                    bgcolor = BLACK_BACKGROUND;
                }

                String thisPieceColor = piece.isBlack() ? Colors.BLACK_BOLD_BRIGHT : Colors.WHITE_BOLD_BRIGHT;

                String character = square.toString();
                if (square.getPiece() != null) character = this.getPieceCharacter(square.getPiece());

                if (validMoves.contains(square.getPosition())) {
                    if (square.getPiece() == null) {
                        System.out.print(lbracket + bgcolor + "*" + rbracket);
                    } else {
                        System.out.print(lbracket + bgcolor + character + rbracket);
                    }
                } else {
                    if (square.getPiece() == piece)
                        System.out.print(lbracket + bgcolor + thisPieceColor + character + rbracket);
                    else System.out.print(lbracket + bgcolor + character + rbracket);
                }
            }
            System.out.println("");
            if(currentPlayer.isWhite()) counter--;
            else counter++;
        }
        String files = (currentPlayer.isWhite()) ? ("  A  B  C  D  E  F  G  H") :
                ("  H  G  F  E  D  C  B  A");
        System.out.println(files);
    }

    /**
     * Method for obtaining a Mono CLI. If a CLI has not yet been made, make a new one.
     * Otherwise, return the one that already exists
     * @return a CLI object
     */
    public static Board_CLI getCLI() {
        if (Mono_CLI == null) {
            Mono_CLI = new Board_Mono_CLI();
            return Mono_CLI;
        }
        return Mono_CLI;
    }
}