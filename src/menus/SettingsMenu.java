package menus;

import controllers.Chess;
import interfaces.BoardIF;
import ui_cli.Board_CLI;
import ui_cli.Board_Color_CLI;
import ui_cli.Board_Mono_CLI;

import java.util.Scanner;

/**
 * This menu allows users to control various settings of
 * how the application is displayed
 * @author Daniel 90% Logan 10%
 */
public class SettingsMenu extends MenuAbstract {

    /**
     * This menu's options
     */
    private final String options = "Settings: \n" +
            "============\n" +
            "1: Set Mono CLI\n" +
            "2: Set Color CLI\n" +
            "3: Color CLI Settings\n" +
            "4: Undo/Redo on\n" +
            "5: Undo/Redo off\n" +
            "9: Main Menu\n" +
            "0: Exit Application\n";

    /**
     * @return the options for this menu
     */
    @Override
    public String getOptions() {
        return this.options;
    }

    /**
     * Listens for user input and acts and/or redirects accordingly
     *
     * @param cli the overarching CLI object
     */
    @Override
    public void listen(Board_CLI cli) {
        String currentCLI = (cli instanceof Board_Mono_CLI) ? "Mono CLI" : "Color CLI";
        System.out.println("Current Command Line Interface style is the " + currentCLI + ".");

        Chess game = cli.getGame();
        BoardIF board = game.stateEditor.getBoard();

        Scanner input = new Scanner(System.in);
        String command = input.nextLine();
        Screen_Set screens = cli.screens;

        switch(command) {
            case "1":
                if (cli instanceof Board_Mono_CLI) {
                    System.out.println("The current CLi is the Mono CLI. No changes made.");
                    goTo(this, cli);
                    break;
                }

                System.out.println("Switching to Mono CLI.");
                game = cli.getGame();
                switchUI(game, Board_Mono_CLI.getCLI());
                break;
            case "2":
                if (cli instanceof Board_Color_CLI) {
                    System.out.println("The current CLi is the Color CLI. No changes made.");
                    goTo(this, cli);
                    break;
                }

                System.out.println("Switching to Color CLI.");
                game = cli.getGame();
                switchUI(game, Board_Color_CLI.getCLI());
                break;
            case "3":
                if(cli instanceof Board_Mono_CLI) {
                    print("The current CLI is the Mono CLI. Switch to the Color CLI to configure these options.\n");
                    goTo(this, cli);
                    break;
                }
                goTo(screens.ColorCLIMenu, cli);
                break;
            case "4":
                if(game.isUndoOn()) {
                    print("Undo and redo are already enabled for this game. No changes made.\n");
                    goTo(this, cli);
                    break;
                }
                game.setUndoOn(true);
                print("Undo and redo have been enabled for this game.\n");
                goTo(this, cli);
                break;
            case "5":
                if(!game.isUndoOn()) {
                    print("Undo and redo are already disabled for this game. No changes made.\n");
                    goTo(this, cli);
                    break;
                }
                game.setUndoOn(false);
                print("Undo and redo have been disabled for this game.\n");
                goTo(this, cli);
                break;
            case "9":
                cli.draw(board);
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
     * helper function for switching the UI of a game
     * @param game the current game
     * @param cli the new CLI to switch to
     */
    private void switchUI(Chess game, Board_CLI cli) {
        game.setUi(cli);
        cli.setGame(game);
        game.getUi().draw(game.stateEditor.getBoard());
        goTo(game.getUi().screens.Settings, (Board_CLI) game.getUi());
    }

}
