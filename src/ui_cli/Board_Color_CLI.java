package ui_cli;

import enums.ChessPieceType;
import enums.GameColor;
import enums.Rank;
import interfaces.BoardIF;
import interfaces.PieceIF;
import interfaces.UserInterfaceIF;
import model.*;

import java.util.List;

/**
 * Class for a command line interface with color
 * Daniel Aoulou 50% Logan Beaver 50%
 */
public class Board_Color_CLI extends Board_CLI implements UserInterfaceIF {
    private boolean useSymbols = true;

    /**
     * A field for storing a previously created CLI
     */
    static Board_Color_CLI Color_CLI = null;

    /**
     * Draw a chess board in its current state.
     * @param board the board to be drawn
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

        Player check = board.check(game);

        Rank[] ranks = Rank.values();
        for (int i = heightStart; i != heightEnd; i += heightIncrement) {
            System.out.print(ranks[counter].getRank());
            for (int j = widthStart; j != widthEnd; j += widthIncrement) {
                Square square = board.getSquares()[i][j];
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

                String color = "";
                String character = square.toString();

                if (square.isOccupied()) {
                    if (this.useSymbols) {
                        character = getPieceCharacter(square.getPiece());
                    }

                    if (square.getPiece().getColor() == GameColor.WHITE) {
                        color = getWHITECOLOR();
                    } else if (check!=null && square.getPiece().getColor() == currentPlayer.getColor() &&
                            square.getPiece().getChessPieceType() == ChessPieceType.KING) {
                        color = Colors.RED_BOLD_BRIGHT;
                    } else color = getBLACKCOLOR();
                }
                System.out.print(lbracket + bgcolor + color + character + Colors.RESET + rbracket);
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
     * @param board the board to be drawn
     */
    public void previewMoves(BoardIF board, PieceIF piece) {
        List < Position > validMoves = board.validateMoves(piece);
        GameStateEditor gameStateEditor = game.getStateEditor();
        Player currentPlayer = gameStateEditor.getCurrentPlayer();

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
                Square square = board.getSquares()[i][j];
                if (square.getColor() == GameColor.WHITE) {
                    lbracket = WHITE_LEFT_BRACKET;
                    rbracket = WHITE_RIGHT_BRACKET;
                    bgcolor = WHITE_BACKGROUND;
                } else {
                    lbracket = BLACK_LEFT_BRACKET;
                    rbracket = BLACK_RIGHT_BRACKET;
                    bgcolor = BLACK_BACKGROUND;
                }

                String color = "";
                String character = square.toString();

                if (square.isOccupied()) {
                    if (this.useSymbols) {
                        character = getPieceCharacter(square.getPiece());
                    }

                    if (square.getPiece().getColor() == GameColor.WHITE) {
                        color = getWHITECOLOR();
                    } else color = getBLACKCOLOR();
                    if (square.getPiece() == piece) color = Colors.YELLOW_BOLD;
                }
                if (validMoves.contains(square.getPosition())) {
                    if (square.getPiece() == null) {
                        System.out.print(lbracket + bgcolor + Colors.RED_BOLD_BRIGHT + "*" + rbracket + Colors.RESET);
                    } else {
                        System.out.print(lbracket + bgcolor + Colors.RED_BOLD_BRIGHT + character + rbracket +  Colors.RESET);
                    }
                } else {
                    if (square.getPiece() == piece)
                        System.out.print(lbracket + Colors.YELLOW_BOLD + bgcolor + character + Colors.RESET + rbracket);
                    else System.out.print(lbracket + bgcolor + color + character + Colors.RESET + rbracket);
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
     * Method for obtaining a Color CLI. If a CLI has not yet been made, make a new one.
     * Otherwise, return the one that already exists
     * @return a CLI object
     */
    public static Board_CLI getCLI() {
        if (Color_CLI == null) {
            Color_CLI = new Board_Color_CLI();
            return Color_CLI;
        }
        return Color_CLI;
    }

    /**
     * checks if the CLI is set to use symbols
     * @return true/false
     */
    public boolean isUseSymbols() {
        return useSymbols;
    }

    /**
     * toggles symbols usage on or off
     * @param useSymbols true/false
     */
    public void setUseSymbols(boolean useSymbols) {
        this.useSymbols = useSymbols;
    }



}