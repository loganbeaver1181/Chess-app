package menus;

import controllers.Chess;
import controllers.Commands;
import interfaces.PieceIF;
import model.GameStateEditor;
import ui_cli.Board_CLI;

import java.util.Scanner;

/**
 * This menu allows users to preview moves
 * @author Daniel Aoulou 100%
 */
public class PreviewMovesMenu extends MenuAbstract{

    /**
     * This menu's options
     */
    private String options = " ";

    /**
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
        Screen_Set screens = cli.screens;
        Chess game = cli.getGame();
        GameStateEditor stateEditor = game.getStateEditor();

        print("""
                Which piece's moves would you like to preview? Use algebraic notation to identify the piece.
                i.e. "a2"
                Enter "cancel" to cancel.
                """);

        String pieceStr = input.nextLine();
        while (!validateAn(pieceStr)) {

            if (checkCancel(pieceStr, cli)) return;

            cli.draw(game.stateEditor.getBoard());
            print("That is not valid algebraic notation! Please try again.\n" +
                    Commands.AN_HELP +
                    "Enter \"cancel\" to cancel your move.\n");
            pieceStr = input.nextLine();
        }

        PieceIF piece = game.getPiece(pieceStr);
        boolean validPiece = false;

        while(!validPiece) {
            if (checkCancel(pieceStr, cli)) return;

            if (piece == null) {

                cli.draw(stateEditor.getBoard());
                print("There isn't a piece there! Try again.\n" +
                        "Enter \"cancel\" to cancel your move.\n");

                pieceStr = input.nextLine();
                piece = game.getPiece(pieceStr);
            } else if (!cli.checkCurrentOwner(piece)) {

                cli.draw(stateEditor.getBoard());
                print("That isn't your piece! Try again.\n" +
                        "Enter \"cancel\" to cancel your move.\n");

                pieceStr = input.nextLine();
                piece = game.getPiece(pieceStr);
            } else {
                cli.previewMoves(stateEditor.getBoard(), piece);
                validPiece = true;
            }
        }

        goTo(screens.PlayChessMenu, cli);
    }
}
