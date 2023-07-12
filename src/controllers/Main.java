package controllers;

import interfaces.guiIF;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui_gui.MainMenu;
import ui_gui.ScreenFactory;

/**
 * A class to start the chess application
 */
public class Main extends Application {

    /**
     * Creates a driver and calls runGame.
     */
    private static Chess game;
    public static void main(String[] args) {
        // guiIF GUI = MainMenu.getMenu();
        game = Chess.getInstanceOfGame();
        Driver driver = new Driver(game);
        driver.runGame();
        launch(args);
    }

    /**
     *
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start(Stage primaryStage){
        try{
            MainMenu mainScreen = new MainMenu(game);
            Scene scene = new Scene(mainScreen.getRoot(), 1000, 600);
            ScreenFactory.getInstance(scene);

            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


}