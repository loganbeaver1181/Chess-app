package menus;

import interfaces.MenuIF;

/**
 * A class holding a set of CLI screens
 * @author Daniel Aoulou 100%
 */
public class Screen_Set {
    /** The Main menu of the game*/
    public final MenuIF MainMenu = new MainMenu();

    /** The menu for playing the game*/
    public final MenuIF PlayChessMenu = new PlayChessMenu();

    /** The settings menu of the game*/
    public final MenuIF Settings = new SettingsMenu();

    /** the options menu when making a move*/
    public final MenuIF MakeMoveMenu = new MakeMoveMenu();

    /** The Main menu of the game*/
    public final MenuIF PreviewMovesMenu = new PreviewMovesMenu();

    /** The color menu of the game*/
    public final MenuIF ColorCLIMenu =  new ColorCLISettingsMenu();
}