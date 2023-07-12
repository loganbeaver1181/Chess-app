package ui_gui;

import controllers.Chess;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 * A class for making singleton screens and returning them.
 * @author Logan Beaver(80%) Michael Imerman (20%)
 *
 */
public final class ScreenFactory implements ScreenChangeHandler {



    /**The available screens**/
    public enum  Screen {SCREEN0, SCREEN1, SCREEN2, SCREEN3, SCREEN4, SCREEN5, SCREEN6, SCREEN7, SCREEN8};

    /**The menu screen**/
    private static MainMenu screen0;

    /**The Animal Screen**/
    private static PlayerScreen screen1;

    /**The calculator screen**/
    private static GameScreen screen2;

    /**The third music playing screen*/
    private static SettingsMenu screen3;

    /**
     * A screen for the color picker screen
     */
    private static ColorChooser screen4;

    /**
     * A screen for player vs. computer
     */
    private static PlayerVsComputerScreen screen5;

    /**
     * A screen for the rules of chess
     */
    private static RulesScreen screen6;

    /**
     * A screen for online play
     */
    private static OnlinePlayScreen screen7;

    /**
     * A screen for a tutorial in chess
     */
    private static TutorialScreen screen8;


    /**The scene to apply the Screen son**/
    private Scene scene;

    /**The singletone instance of the factory**/
    private static ScreenFactory singleton;

    /** current chess game*/
    Chess game;

    /**
     * A screen factory applies screens to a scene.
     * @param scene The scene to apply the screens to.
     */
    private ScreenFactory(Scene scene){
        this.scene = scene;
        setScreen(Screen.SCREEN0);//The initial Screen.
    }

    /**
     * Get a singleton instance of the scene factory.
     * @param scene The scene to apply the screens to.
     * @return A singleton instance.
     */
    public static ScreenFactory getInstance(Scene scene){
        if(singleton == null)
            singleton = new ScreenFactory(scene);

        return singleton;
    }

    /**Return a singleton instance of the song selected**/
    public Pane setScreen(Screen screenChoice){
        Pane screen;

        switch(screenChoice) {
            case SCREEN1:
                if(screen1 == null) {
                    screen1 = new PlayerScreen(Chess.getInstanceOfGame());
                    screen1.setSCH(this);
                }
                   // screen1.setChess(game);
                screen = screen1;
                break;
            case SCREEN2:
                if(screen2 == null) {
                    screen2 = new GameScreen(Chess.getInstanceOfGame());
                    screen2.setScreenChangeHandler(this);
                }
                screen = screen2;
                break;
            case SCREEN3:
                if(screen3 == null) {
                    screen3 =  SettingsMenu.getInstance(Chess.getInstanceOfGame());
                    screen3.setScreenChangeHandler(this);
                }
                screen = screen3;
                break;
            case SCREEN4:
                if (screen4 == null) {
                    screen4 = new ColorChooser();
                    screen4.setScreenChangeHandler(this);
                }
                screen = screen4;
                break;
            case SCREEN5:
                if (screen5 == null) {
                    screen5 = new PlayerVsComputerScreen();
                    screen5.setScreenChangeHandler(this);
                }
                screen = screen5;
                break;
            case SCREEN6:
                if (screen6 == null) {
                    screen6 = new RulesScreen();
                    screen6.setScreenChangeHandler(this);
                }
                screen = screen6;
                break;
            case SCREEN7:
                if (screen7 == null) {
                    screen7 = new OnlinePlayScreen();
                    screen7.setScreenChangeHandler(this);
                }
                screen = screen7;
                break;
            case SCREEN8:
                if (screen8 == null) {
                    screen8 = new TutorialScreen();
                    screen8.setScreenChangeHandler(this);
                }
                screen = screen8;
                break;
            default:
                if(screen0== null){
                    screen0 = new MainMenu(Chess.getInstanceOfGame());
                    screen0.setScreenChangeHandler(this);
                }

                screen = screen0;
        }///end switch

        //Apply the screen to the root scene
        scene.setRoot(screen);

        return screen;
    }//end getScreen

    /**The implementation of the ScreenChangeHandler interface
     * @param screenChoice The screen chosen.
     */
    @Override
    public void switchScreen(ScreenFactory.Screen screenChoice) {
        Pane root = setScreen(screenChoice);
        scene.setRoot(root);
    }//end

    /**
     * A function to deal with the edge cases found when switching from and to the ColorChooser
     * @param initScreen - The screen we came from
     * @param button - The button clicked that pulled up the ColorChooser
     */
    public void switchToFromColorChooser(Screen initScreen, Button button){
        Pane screen = null;
        // If we're going to the Settings Menu
        if(initScreen == Screen.SCREEN3){
            screen3.setScreenChangeHandler(this);
            screen = screen3;
        // If we're going to the ColorChooser
        } else if(initScreen == Screen.SCREEN4){
            // If the ColorChooser is currently null
            if (screen4 == null) {
                // Creating a new screen from the button pushed
                screen4 = ColorChooser.getInstance(button);
                screen4.setScreenChangeHandler(this);
                screen = screen4;
            }else{
                screen4 = ColorChooser.getInstance(button);
                screen = screen4;
            }
        }
        scene.setRoot(screen);
    }

    /**
     * A function to deal with the edge cases found when switching from and to the Settings screen
     * @param initScreen - The screen we're switching to.
     * @param scene1 - The screen we're switching from.
     */
    public void switchToFromSettings(Screen initScreen, Pane scene1){
        Pane screen;
        // If we're going to the Settings Screen
        if(initScreen == Screen.SCREEN3){
            // If the screen is null, create a new instance
            if (screen3 == null){
                screen3 = SettingsMenu.getInstance(Chess.getInstanceOfGame());
            }
            // Sets the previous screen we've just came from
            screen3.setPreviousScene(scene1);
            screen3.setScreenChangeHandler(this);
            screen = screen3;
            scene.setRoot(screen);
        // Else if we're going from the settings screen to the main screen
        } else if (initScreen == Screen.SCREEN2) {
            switchScreen(Screen.SCREEN2);
        }

    }

    /**
     * get the game screen to update its board drawing
     * @return game screen
     */
    public static GameScreen getGameScreen() {
        return screen2;
    }


}
