package ui_cli;

import controllers.Chess;
import controllers.Commands;
import interfaces.BoardIF;
import interfaces.PieceIF;
import interfaces.UserInterfaceIF;
import menus.Screen_Set;
import model.*;

import java.io.File;
import java.util.Objects;
import java.util.Scanner;

/**
 * Class for a command line interface with color
 * @author Daniel Aoulou 20% Josiah Cherbony 20% Logan Beaver 20% Laurin Burge 20% Michael Imerman 20%
 */
public abstract class Board_CLI implements UserInterfaceIF {

    /**
     * field for the color of "white" pieces
     */
    protected String WHITE_COLOR = Colors.PURPLE_BOLD_BRIGHT;

    /**
     * field for the color of "black" pieces
     */
    protected String BLACK_COLOR = Colors.BLUE_BOLD_BRIGHT;

    /**
     * field for the background color of white squares
     */
    protected String WHITE_BACKGROUND = Colors.WHITE_BACKGROUND;

    /**
     * field for the background color of black squares
     */
    protected String BLACK_BACKGROUND = Colors.BLACK_BACKGROUND;

    /**
     * fields for bracket formatting
     */
    protected String BLACK_LEFT_BRACKET = BLACK_BACKGROUND + " " + Colors.RESET;
    protected String BLACK_RIGHT_BRACKET = BLACK_BACKGROUND + " " + Colors.RESET;
    protected String WHITE_LEFT_BRACKET = WHITE_BACKGROUND + " " + Colors.RESET;
    protected String WHITE_RIGHT_BRACKET = WHITE_BACKGROUND + " " + Colors.RESET;

    /**
     * The game that the CLI is displaying
     */
    protected Chess game;

    /**
     * A set of menu screens for the CLI to interact with the user
     */
    public Screen_Set screens = new Screen_Set();

    /**
     * Draw a chess board in its current state.
     *
     * @param board board
     */
    public abstract void draw(BoardIF board);

    /**
     * Draw a chess board in its current state
     * with a piece's possible moves highlighted
     *
     * @param board board
     */
    public abstract void previewMoves(BoardIF board, PieceIF piece);

    /**
     * At the start of a new game, print a friendly user
     * welcome text and print the board to the screen
     */
    public boolean intro() {
        Scanner scanner = new Scanner(System.in);
        String command;
        print(Commands.INTRO);
        command = scanner.nextLine().toLowerCase();
        while (!command.equals(Commands.START) && !command.equals(Commands.EXIT)) {
            print("Invalid input. To start a new demo game, type `start` and press enter!\n" +
                    "You can also type `exit` to close the application.\n\n");
            command = scanner.next().toLowerCase();
            if (command.equals(Commands.EXIT)) {
                if (confirmExit()) {
                    return false;
                } else {
                    print("Exit cancelled. To start a new demo game, type `start` and press enter!\n" +
                            "You can also type `exit` to close the application.\n\n");
                    command = scanner.next().toLowerCase();
                }
            }
        }

        return true;
    }

    /**
     * Print the board to the screen and enter the Main Menu
     * to listen for user input.
     * @param game the game we are starting
     */

    public void startGame(Chess game) {
        GameStateEditor stateEditor = game.stateEditor;

        this.draw(stateEditor.getBoard()); //print the board to the screen
        this.game = game;

        print(screens.MainMenu.getOptions());
        String color = (stateEditor.getCurrentPlayer().isWhite()) ? getWHITECOLOR() : getBLACKCOLOR();
        print("Player " + color + stateEditor.getCurrentPlayer().getName() + Colors.RESET + "'s turn.\n");
        screens.MainMenu.listen(this);

    }


    /**
     * Helper function to get file to load game from
     */
    private void confirmLoad() {
        Scanner input = new Scanner(System.in);
        print("Provide the path of the game you would like to load: ");
        String path = input.nextLine();
        File file = new File(path);
        if (file.canRead()) {
            print("We are now playing on the game loaded in from the file. The undo feature will\n allow you to revisit"
                    + "moves from this game. Or you can continue playing from this state of the board.\nIf you undo" +
                    " a few moves and then continue playing, all moves that have been undone are lost.\n");
            game.stateEditor.load(path);

        } else {
            print("Provided path does not exist or does not have the correct permissions. please try again.\n");
        }
    }


    /**
     * Helper function for confirming board reset
     *
     * @return true/false
     */
    private boolean confirmReset() {
        Scanner input = new Scanner(System.in);
        print("Are you sure you want to reset the board? (y/n)\n");
        if (Objects.equals(input.nextLine(), "y")) {
            return true;
        } else {
            print("Board reset cancelled.\n");
            return false;
        }
    }

    /**
     * Function for recording a move made on the board
     * CLI updates the stateEditor and displays
     * move information to the user
     *
     * @param piece the piece moved
     * @param sqr the square moved to
     *
     * @return true if checkmate has occured
     */
    public boolean recordMove(PieceIF piece, Square sqr) {
        // Retrieve the gameState editor
        GameStateEditor stateEditor = game.stateEditor;


        // If the player "undo"s to a previous state
        if(stateEditor.haveShifted()){
            // Sort the state history correctly
            stateEditor.sortStateHistory();
        }


        this.draw(stateEditor.getBoard());

        String endPos = sqr.getPosition().getFile() + "" +
                sqr.getPosition().getRank().getRank();

        String color = (stateEditor.getCurrentPlayer().isWhite()) ? BLACK_COLOR : WHITE_COLOR;

        print(color + piece.getColor().toString().toUpperCase() +
                " " + piece.getChessPieceType() + Colors.RESET +
                " moves to " + endPos + ".\n");

        color = (stateEditor.getCurrentPlayer().isWhite()) ? WHITE_COLOR : BLACK_COLOR;

        if(game.getBoard().check(this.game) != null) {
            print("Player " + color + stateEditor.getCurrentPlayer().getName() + Colors.RESET + " is in check.\n");
        }

        if(game.getBoard().checkmate(this.game) != null) {
            print("Player " + color + stateEditor.getCurrentPlayer().getName() + Colors.RESET + " is in checkmate.\n");
            color = (stateEditor.getCurrentPlayer().isWhite()) ? BLACK_COLOR : WHITE_COLOR;
            print("Player " + color + stateEditor.getOppositePlayer().getName() + Colors.RESET + " wins!\n");
            this.promptSaveGame();
            this.promptNewGame();
            return true;
        } else {
            print("Player " + color + stateEditor.getCurrentPlayer().getName() + Colors.RESET + "'s turn.\n");
            return false;
        }

    }

    /**
     * Helper function for prompting to save the game
     */
    private void promptSaveGame() {
        Scanner input = new Scanner(System.in);
        String command;
        print("Save this game? (y/n)\n");

        command = input.nextLine();
        if (command.equalsIgnoreCase("y")) {
            game.stateEditor.saveGame();
        } else {
            print("Game not saved.\n");
        }

    }

    /**
     * Helper function for prompting a new game
     */
    private void promptNewGame() {
        Scanner input = new Scanner(System.in);
        String command;
        print("Start a new game? (y/n)\n");

        command = input.nextLine();
        if (command.equalsIgnoreCase("y")) {
            print("Starting a new game!\n");
            //this.game = new Chess(this);
            this.startGame(this.game);
        } else {
            print("Thank you for playing Chess Meister!\n");
        }


    }

    /**
     * Helper function for confirming the exit of the application.
     * @return whether the user confirmed exit or not
     */
    public boolean confirmExit() {
        Scanner input = new Scanner(System.in);
        print("Are you sure you want to exit the application? (y/n)\n");
        if (Objects.equals(input.nextLine(), "y")) {
            print("Thank you for playing Chess Meister!\n");
            return true;
        } else {
            print("Application exit cancelled.\n");
            return false;
        }
    }

    /**
     * Check if a piece is owned by the current player.
     * @param piece the piece we want to check
     * @return true if current player owns piece
     */
    public boolean checkCurrentOwner(PieceIF piece) {
        GameStateEditor stateEditor = game.getStateEditor();
        return stateEditor.getCurrentPlayer().getColor() == piece.getColor();
    }

    /**
     * helper function for printing to the console
     * rather than typing System.out.println all the time
     *
     * @param m the string to be printed
     */
    protected void print(String m) {
        System.out.print(m);
    }

    /**
     * Setter for the chess field
     * @param chess game of chess to be set
     */
    public void setGame(Chess chess) {
        this.game = chess;
    }

    /**
     *
     * @return the chess game being represented by the CLI
     */
    public Chess getGame() {
        return game;
    }

    /**
     * @return the color of "white" pieces
     */
    public String getWHITECOLOR() {
        return WHITE_COLOR;
    }

    /**
     * Set the represented color of "white" pieces
     * @param WHITE_COLOR the new color to be set
     */
    public void setWHITECOLOR(String WHITE_COLOR) {
        this.WHITE_COLOR = WHITE_COLOR;
    }

    /**
     * @return the color of "black" pieces
     */
    public String getBLACKCOLOR() {
        return BLACK_COLOR;
    }

    /**
     * Set the represented color of "black" pieces
     * @param BLACK_COLOR the new color to be set
     */
    public void setBLACKCOLOR(String BLACK_COLOR) {
        this.BLACK_COLOR = BLACK_COLOR;
    }

    /**
     * If the CLI uses symbols, get the appropriate symbol
     * according to the piece type
     * @param piece the piece to identify
     * @return the corresponding symbol
     */
    protected String getPieceCharacter(PieceIF piece) {
        String character = "";
        if(piece.isBlack() && this instanceof Board_Mono_CLI) {
            switch(piece.getChessPieceType()) {
                case PAWN:
                    character = Colors.WHITE_PAWN;
                    break;
                case BISHOP:
                    character = Colors.WHITE_BISHOP;
                    break;
                case ROOK:
                    character = Colors.WHITE_ROOK;
                    break;
                case KNIGHT:
                    character = Colors.WHITE_KNIGHT;
                    break;
                case KING:
                    character = Colors.WHITE_KING;
                    break;
                case QUEEN:
                    character = Colors.WHITE_QUEEN;
                    break;
            }
        } else {
            switch(piece.getChessPieceType()) {
                case PAWN:
                    character = Colors.BLACK_PAWN;
                    break;
                case BISHOP:
                    character = Colors.BLACK_BISHOP;
                    break;
                case ROOK:
                    character = Colors.BLACK_ROOK;
                    break;
                case KNIGHT:
                    character = Colors.BLACK_KNIGHT;
                    break;
                case KING:
                    character = Colors.BLACK_KING;
                    break;
                case QUEEN:
                    character = Colors.BLACK_QUEEN;
                    break;
            }
        }
        return character;
    }

    /**
     * Checks if the cli is set to use symbols
     * @return true/false
     */
    public boolean isUseSymbols() {
        return false;
    }

    /**
     * Toggles symbols usage on or off
     * @param b true/false
     */
    public void setUseSymbols(boolean b) {
    }


}