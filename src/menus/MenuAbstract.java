package menus;

import interfaces.MenuIF;
import ui_cli.Board_CLI;

/**
 * Abstract class for menus that includes many commonly used functions
 * @author Daniel Aoulou 90% Logan Beaver 10%
 */
public abstract class MenuAbstract implements MenuIF {

    /**
     * Opens up a new menu, switching the flow of control to that menu.
     * @param menu the menu to switch to
     * @param cli the current CLI being used
     */
    protected void goTo(MenuIF menu, Board_CLI cli) {
        print(menu.getOptions() + "\n");
        menu.listen(cli);
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
     * Validates algebraic notation
     * @param input the String to be validated
     * @return true if the provided input is valid algebraic notation
     */
    protected boolean validateAn(String input) {
        input = input.toLowerCase();

        if(input.length() != 2 ||
                input.charAt(0) < 97 || input.charAt(0) > 104 ||
                input.charAt(1) < 49 || input.charAt(1) > 56
        ) return false;

        return true;
    }

    /**
     * Checks if an action (namely move or preview move) has been cancelled
     * @param str the string to compare
     * @param cli the current CLI
     * @return true if user wants to cancel
     */
    protected boolean checkCancel(String str, Board_CLI cli) {
        if(str.equalsIgnoreCase("cancel")) {
            print("Action cancelled. Returning to Chess menu.\n");
            goTo(cli.screens.PlayChessMenu, cli);
            return true;
        }
        return false;
    }

    /**
     * get the options string
     * @return a Menu's options
     */
    public abstract String getOptions();

    /**
     * Listen for user input
     * @param cli the current CLI
     */
    public abstract void listen(Board_CLI cli);
}
