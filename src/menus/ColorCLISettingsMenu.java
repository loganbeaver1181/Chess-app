package menus;

import controllers.Chess;
import model.GameStateEditor;
import ui_cli.Board_CLI;
import ui_cli.Colors;

import java.util.Scanner;

/**
 * This menu allows users to change the colors and viewing options of the color CLI
 * @author Daniel 90% Logan Beaver 10%
 */
public class ColorCLISettingsMenu extends MenuAbstract{

    /**
     * This menu's options
     */
    private final String options = "Color CLI Settings: \n" +
            "============\n" +
            "1: Change White Color\n" +
            "2: Change Black Color\n" +
            "3: Use Symbols\n" +
            "4: Use Letters\n" +
            "9: Main Menu\n" +
            "0: Exit Application\n";

    /**
     *
     * @return the options for this menu
     */
    @Override
    public String getOptions() {
        return this.options;
    }

    /**
     * Listens for user input and acts and/or redirects accordingly
     * @param cli the overarching CLI object
     */
    @Override
    public void listen(Board_CLI cli) {
        Scanner input = new Scanner(System.in);
        String command = input.nextLine();
        Screen_Set screens = cli.screens;
        Chess game = cli.getGame();
        GameStateEditor stateEditor = game.getStateEditor();

        switch(command) {
            case "1":
                String newWhiteColor = colorChangePrompt();
                boolean validNewWhiteColor = false;
                if (newWhiteColor != null) {
                    while (newWhiteColor.equalsIgnoreCase(cli.getBLACKCOLOR())) {
                        print("White and Black pieces cannot be set to the same color.\n" +
                                "Try again? (y/n)\n");
                        command = input.nextLine();
                        if(command.equalsIgnoreCase("y")) {
                            newWhiteColor = colorChangePrompt();
                        } else {
                            print("WHITE piece color change cancelled.\n");
                            break;
                        }
                    }
                    if(!newWhiteColor.equalsIgnoreCase(cli.getBLACKCOLOR())) {
                        validNewWhiteColor = true;
                    } else {
                        goTo(this, cli);
                        break;
                    }
                }

                if(validNewWhiteColor) {
                    cli.setWHITECOLOR(newWhiteColor);
                    cli.draw(stateEditor.getBoard());
                    print("Changed the color of " + cli.getWHITECOLOR() + "WHITE" + Colors.RESET + " pieces.\n");
                    goTo(this, cli);
                    break;
                }
                break;
            case "2":
                String newBlackColor = colorChangePrompt();
                boolean validNewBlackColor = false;
                if (newBlackColor != null) {
                    while (newBlackColor.equalsIgnoreCase(cli.getWHITECOLOR())) {
                        print("White and Black pieces cannot be set to the same color.\n" +
                                "Try again? (y/n)");
                        command = input.nextLine();
                        if(command.equalsIgnoreCase("y")) {
                            newBlackColor = colorChangePrompt();
                        } else {
                            print("White piece color change cancelled.\n");
                            break;
                        }
                    }
                    if(!newBlackColor.equalsIgnoreCase(cli.getWHITECOLOR())) validNewBlackColor = true;
                }

                if(validNewBlackColor) {
                    cli.setBLACKCOLOR(newBlackColor);
                    cli.draw(stateEditor.getBoard());
                    print("Changed the color of " + cli.getBLACKCOLOR() + "BLACK" + Colors.RESET + " pieces.\n");
                    goTo(this, cli);
                    break;
                }
                break;
            case "3":
                if (cli.isUseSymbols()) {
                    cli.draw(cli.getGame().getBoard());
                    print("Color CLI is already using symbols.\n");
                    goTo(this, cli);
                    break;
                }

                cli.setUseSymbols(true);
                cli.draw(cli.getGame().getBoard());
                print("Color CLI is now using symbols.\n");
                goTo(this, cli);
                break;
            case "4":
                if (!cli.isUseSymbols()) {
                    cli.draw(cli.getGame().getBoard());
                    print("Color CLI is already using letters.\n");
                    goTo(this, cli);
                    break;
                }

                cli.setUseSymbols(false);
                cli.draw(cli.getGame().getBoard());
                print("Color CLI is now using letters.\n");
                goTo(this, cli);
                break;

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
     * Prompts the user for the color they would like to use for
     * white or black pieces.
     * @return a Colors field holding the selected color
     */
    private String colorChangePrompt() {
        Scanner input = new Scanner(System.in);
        String color = "";
        String colors = ("Available Colors:\n" +
                "1: Black\n" +
                "2: Red\n" +
                "3: Green\n" +
                "4: Yellow\n" +
                "5: Blue\n" +
                "6: Purple\n" +
                "7: Cyan\n" +
                "8: White\n" +
                "9: Cancel Color Change\n");
        print(colors);
        String command = input.nextLine();

        boolean active = true;

        while(active) {
            switch (command) {
                case "1":
                    color = Colors.BLACK_BOLD_BRIGHT;
                    active = false;
                    break;
                case "2":
                    color = Colors.RED_BOLD_BRIGHT;
                    active = false;
                    break;
                case "3":
                    color = Colors.GREEN_BOLD_BRIGHT;
                    active = false;
                    break;
                case "4":
                    color = Colors.YELLOW_BOLD_BRIGHT;
                    active = false;
                    break;
                case "5":
                    color = Colors.BLUE_BOLD_BRIGHT;
                    active = false;
                    break;
                case "6":
                    color = Colors.PURPLE_BOLD_BRIGHT;
                    active = false;
                    break;
                case "7":
                    color = Colors.CYAN_BOLD_BRIGHT;
                    active = false;
                    break;
                case "8":
                    color = Colors.WHITE_BOLD_BRIGHT;
                    active = false;
                    break;
                case "9":
                    color = null;
                    active = false;
                    break;
                default:
                    print("Invalid input! Please try again.\n");
                    command = input.nextLine();
                    break;
            }
        }

        input.close();

        return color;
    }
}
