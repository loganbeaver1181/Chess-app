package menus;

import controllers.Chess;
import model.GameStateEditor;
import model.Player;
import ui_cli.Board_CLI;
import ui_cli.Colors;

import java.util.Scanner;

/**
 * This menu allows users to play the game
 * and is a driver for the game loop
 * @author Daniel Aoulou 45% Josiah Cherbonay 45% Logan Beaver 10%
 */
public class PlayChessMenu extends MenuAbstract {

    /**
     * This menu's options
     */
    private final String options = "Play Chess: \n" +
            "============\n" +
            "1: Move\n" +
            "2: Preview Moves\n" +
            "3: Undo\n" +
            "4: Redo\n" +
            "5: View Board\n" +
            "6: Draw by agreement\n" +
            "9: Main Menu\n" +
            "0: Exit Application\n";

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
        String command = input.nextLine();
        Chess game = cli.getGame();
        Screen_Set screens = cli.screens;
        GameStateEditor stateEditor = game.getStateEditor();

        switch(command) {
            case "1":
                goTo(screens.MakeMoveMenu, cli);
                break;
            case "2":
                goTo(screens.PreviewMovesMenu, cli);
                break;
            case "3":

                if(!game.isUndoOn()) {
                    print("Undo and redo have been disabled for this game.\n" +
                            "Go to the settings menu to enable this option.\n");
                    goTo(this, cli);
                    break;
                }

                confirmUndo(cli);

                String color = (stateEditor.getCurrentPlayer().isWhite()) ? cli.getWHITECOLOR() : cli.getBLACKCOLOR();
                print("Now " + color +
                        stateEditor.getCurrentPlayer().getName() +
                        Colors.RESET + "'s turn.\n");

                goTo(this, cli);
                break;
            case "4":

                if(!game.isUndoOn()) {
                    print("Undo and redo have been disabled for this game.\n" +
                            "Go to the settings menu to enable this option.\n");
                    goTo(this, cli);
                    break;
                }

                confirmRedo(cli);

                String colorRedo = (stateEditor.getCurrentPlayer().isBlack()) ? cli.getWHITECOLOR() : cli.getBLACKCOLOR();
                print("Now " + colorRedo +
                        stateEditor.getCurrentPlayer().getName() +
                        Colors.RESET + "'s turn.\n");

                goTo(this, cli);
                break;
            case "5":
                cli.draw(game.getBoard());
                goTo(this, cli);
                break;
            case "6":
                String agreed;
                Player currentPlayer = stateEditor.getCurrentPlayer();
                String currentColor = (currentPlayer.isWhite()) ? cli.getWHITECOLOR() : cli.getBLACKCOLOR();
                String oppositeColor = (currentPlayer.isWhite()) ? cli.getBLACKCOLOR() : cli.getWHITECOLOR();
                print("Player " + currentColor + currentPlayer.getName() + Colors.RESET + " has initiated a draw.\n" +
                        "Does Player " + oppositeColor + stateEditor.getOppositePlayer().getName() + Colors.RESET +
                        " agree? (y/n)\n");

                agreed = input.nextLine();

                if (agreed.equalsIgnoreCase("y")) {
                    print("Both players have agreed to a draw.\n" +
                            "Thank you for playing Chess Meister!\n");
                    return;
                } else {
                    print("Draw by agreement cancelled.\n");
                    goTo(this, cli);
                    break;
                }

            case "9":
                cli.draw(stateEditor.getBoard());
                goTo(screens.MainMenu, cli);
                break;
            case "0":
                if(cli.confirmExit()) break;
                else goTo(this, cli);
                break;
            default:
                System.out.println("Invalid input. Try again!");
                goTo(this, cli);
                break;
        }
    }

    /**
     * Gets input from the player about whether the player wants to keep undoing
     * to future states.
     * @param cli - The CLI of the game. Used to print the chess board.
     */
    private void confirmUndo(Board_CLI cli){
        Chess game = cli.getGame();
        GameStateEditor stateEditor = game.getStateEditor();
        Scanner scanner = new Scanner(System.in);
        String input = "y";

        while(input.equals("y")){
            boolean undid = stateEditor.undo_once();
            cli.draw(stateEditor.getBoard());
            if(undid){
                String color = (stateEditor.getCurrentPlayer().isWhite()) ? cli.getWHITECOLOR() : cli.getBLACKCOLOR();
                print("Undid " + color +
                        stateEditor.getCurrentPlayer().getName() + Colors.RESET + "'s move.\n");
            }

            print("Would you like to undo again? (y/n) ");
            input = scanner.nextLine().toLowerCase();
        }
    }

    /**
     * Gets input from the player about whether the player wants to keep redoing
     * to future states.
     * @param cli - The CLI of the game. Used to print the chess board.
     */
    private void confirmRedo(Board_CLI cli){
        Chess game = cli.getGame();
        GameStateEditor stateEditor = game.getStateEditor();
        Scanner scanner = new Scanner(System.in);
        String input = "y";

        while(input.equals("y") || !input.equals("n")){
            // This would be the current player redoing a move.
            Player oldPlayer = stateEditor.getCurrentPlayer();
            boolean redid = stateEditor.redoOnce();
            cli.draw(stateEditor.getBoard());
            if(redid){
                String color = (oldPlayer.isWhite()) ? cli.getWHITECOLOR() : cli.getBLACKCOLOR();
                print("Redid " + color +
                        oldPlayer.getName() + Colors.RESET + "'s move.\n");
            }
            print("Would you like to redo again? (y/n) ");
            input = scanner.nextLine().toLowerCase();
        }
    }

}
