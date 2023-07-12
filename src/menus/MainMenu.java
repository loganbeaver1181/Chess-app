package menus;

import controllers.Chess;
import model.GameStateEditor;
import model.Player;
import ui_cli.Board_CLI;
import ui_cli.Colors;

import java.util.Scanner;

/**
 * This class represents the main menu
 * @author Daniel Aoulou 90% Logan Beaver 10%
 */
public class MainMenu extends MenuAbstract {

    /**
     * This menu's options
     */
    private final String options = "Main Menu: \n" +
            "============\n" +
            "1: Play Chess\n" +
//            "2: View Rules\n" +
            "2: Settings\n" +
            "3: Set WHITE player's name\n" +
            "4: Set BLACK player's name\n" +
            "5: Save game\n" +
            "6: Load game\n" +
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
     * @param cli the command line interface being used
     */
    @Override
    public void listen(Board_CLI cli) {
        Scanner input = new Scanner(System.in);
        String command = input.nextLine();
        Chess game = cli.getGame();
        GameStateEditor stateEditor = game.getStateEditor();
        Screen_Set screens = cli.screens;

        switch (command) {
            case "1":
                goTo(screens.PlayChessMenu, cli);
                break;
            case "2":
                goTo(screens.Settings, cli);
                break;
            case "3":
                promptNewName(stateEditor.getPlayers().get(0), cli);
                goTo(this, cli);
                break;
            case "4":
                promptNewName(stateEditor.getPlayers().get(1), cli);
                goTo(this, cli);
                break;
            case"5":
                if(!stateEditor.saveGame()) {
                    print("An error occurred in saving a new game. Please try again.\n");
                    goTo(this, cli);
                    break;
                }
                print("Game saved.\n");
                goTo(this, cli);
                break;
            case "6":
                print("What is the name of the file you want to load?\n" +
                        "Please type a file name with a .txt extension.\n" +
                        "Files are saved and loaded from the saved_files directory.\n");
                String fileName = input.nextLine();
                if(!stateEditor.load(fileName)) {
                    print("An error occurred in loading this file. Please try again.\n");
                    goTo(this, cli);
                    break;
                }
                print(fileName + " loaded.\n");

                cli.draw(game.getBoard());

                String color = (stateEditor.getCurrentPlayer().isWhite()) ?
                        cli.getWHITECOLOR() : cli.getBLACKCOLOR();
                print("Player " + color + stateEditor.getCurrentPlayer().getName() +
                        Colors.RESET + "'s turn.\n");
                goTo(this, cli);
                break;
            case "0":
                if (cli.confirmExit()) break;
                else goTo(this, cli);
                break;
            default:
                System.out.println("Invalid input. Try again!\n");
                goTo(this, cli);
                break;
        }
    }

    private void promptNewName(Player player, Board_CLI cli) {
        Scanner input = new Scanner(System.in);
        String color = (player.isWhite()) ?
                cli.getWHITECOLOR() : cli.getBLACKCOLOR();
        print("Enter a new name for player " + color + player.getName() + Colors.RESET + ":\n");
        player.setName(input.nextLine());
        print("Player " + color + player.getName() + Colors.RESET + "'s name updated.\n");
    }

}
