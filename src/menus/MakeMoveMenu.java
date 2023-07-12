package menus;

import controllers.Chess;
import controllers.Commands;
import interfaces.PieceIF;
import model.Position;
import model.Square;
import ui_cli.Board_CLI;
import ui_cli.Colors;

import java.util.Scanner;

/**
 * This menu allows users to make a move on the chess board.
 * @author Daniel Aoulou 100%
 */
public class MakeMoveMenu extends MenuAbstract {

    /**
     * This menu's options
     */
    private final String options = "";

    /**
     * get the options string
     * @return the options for this menu
     */
    @Override
    public String getOptions() {
        return this.options;
    }

    /**
     * Listens for user input and acts and/or redirects accordingly
     * @param cli the command line interface being used
     */
    @Override
    public void listen(Board_CLI cli) {
        Scanner input = new Scanner(System.in);
        String command;
        Screen_Set screens = cli.screens;
        Chess game = cli.getGame();

        print("What move would you like to make? Describe it using algebraic notation.\n" +
                "For example, "+ Colors.format_command("`a2 a4`") + "\n" +
                "where "+ Colors.format_command("`a2`") + " is the location of the piece you want to move\n" +
                "and "+ Colors.format_command("`a4`") + " is the location of the square you want to move to" +
                "Enter " + Colors.format_command("`cancel`") + " to cancel your move.\n");

        command = input.nextLine();

        while(command.length() != 5) {
            print("Invalid input!\n");
            print("What move would you like to make? Describe it using algebraic notation.\n" +
                    "For example, "+ Colors.format_command("`a2 a4`") + "\n" +
                    "where "+ Colors.format_command("`a2`") + " is the location of the piece you want to move\n" +
                    "and "+ Colors.format_command("`a4`") + " is the location of the square you want to move to.\n" +
                    "Enter " + Colors.format_command("`cancel`") + " to cancel your move.\n");
        }

        String pieceStr = command.substring(0, 2);

        while (!validateAn(pieceStr)) {

            if (checkCancel(pieceStr, cli)) return;

            cli.draw(game.stateEditor.getBoard());
            print("That is not valid algebraic notation!\n" +
                    "What piece would you like to move?\n" +
                    Commands.AN_HELP +
                    "Enter " + Colors.format_command("`cancel`") + " to cancel your move.\n");
            pieceStr = input.nextLine();
        }

        PieceIF piece = game.getPiece(pieceStr);
        boolean validPiece = false;

        while(!validPiece) {
            if (checkCancel(pieceStr, cli)) return;

            if (piece == null) {

                cli.draw(game.stateEditor.getBoard());
                print("There isn't a piece there! Try again.\n" +
                        "What piece would you like to move?\n" +
                        "Enter " + Colors.format_command("`cancel`") + " to cancel your move.\n");

                pieceStr = input.nextLine();
                piece = game.getPiece(pieceStr);
            } else if (!cli.checkCurrentOwner(piece)) {

                cli.draw(game.stateEditor.getBoard());
                print("That isn't your piece! Try again.\n" +
                        "What piece would you like to move?\n" +
                        "Enter " + Colors.format_command("`cancel`") + " to cancel your move.\n");

                pieceStr = input.nextLine();
                piece = game.getPiece(pieceStr);
            } else validPiece = true;
        }

        String sqrStr = command.substring(3);

        while (!validateAn(sqrStr)) {

            if (checkCancel(sqrStr, cli)) return;

            cli.draw(game.stateEditor.getBoard());
            print("That is not valid algebraic notation! Please try again.\n" +
                    "Where would you like to move your piece?\n" +
                    Commands.AN_HELP +
                    "Enter " + Colors.format_command("`cancel`") + " to cancel your move.\n");
            sqrStr = input.nextLine();

        }

        Square square = game.getSquare(sqrStr);
        boolean validSqr = false;

        Position oldPosition = piece.getPosition();

        while(!validSqr) {
            if (checkCancel(pieceStr, cli)) return;

            if (square == null) {

                cli.draw(game.stateEditor.getBoard());
                print("There isn't a square there! Try again.\n" +
                        "Where would you like to move your piece?\n" +
                        "Enter " + Colors.format_command("`cancel`") + " to cancel your move.\n");

                sqrStr = input.nextLine();
                square = game.getSquare(sqrStr);
            } else if (!game.stateEditor.getBoard().move(piece, square.getPosition(), game.stateEditor)) {

                cli.draw(game.stateEditor.getBoard());
                print("That piece cannot make that move! Try again.\n" +
                        "Where would you like to move your piece?\n" +
                        "Enter " + Colors.format_command("`cancel`") + " to cancel your move.\n");

                sqrStr = input.nextLine();
                square = game.getSquare(sqrStr);
            } else {
                game.stateEditor.addMoveToHistory(game.stateEditor.getCurrentPlayer(),
                        oldPosition, square.getPosition());

                if(cli.recordMove(piece, square)) {
                    return;
                }
                validSqr = true;
            }
        }

        goTo(screens.PlayChessMenu, cli);
    }
}
