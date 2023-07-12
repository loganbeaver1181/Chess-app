package interfaces;

import ui_cli.Board_CLI;

/**
 * An interface to represent different 'screens' or menus of the CLI
 * Daniel Aoulou 100%
 */
public interface MenuIF {

    /**
     * A method for listening for user input
     * @param cli the overarching CLI object
     */
    void listen(Board_CLI cli);

    /**
     *
     * @return the options a menu screen can have
     */
    String getOptions();
}
