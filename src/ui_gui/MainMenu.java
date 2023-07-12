package ui_gui;
import controllers.Chess;
import interfaces.guiIF;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;

/**
 * This class is responsible for the main screen of the chess game.
 * @author Logan Beaver(10%) Laurin Burge (45%) Michael Imerman(45%)
 */
public class MainMenu extends Pane implements guiIF {

    /**
     * the main pane of the screen
     */
    AnchorPane root;

    /**
     * responsible for changing screen
     */
    ScreenChangeHandler screenChanger;

    /**
     * all the buttons on the screen
     */
    Button pvpB, pvcB, onlineB, rulesB, tutB, setB, exitB;

    /** the main menu screen ~ for singleton **/

    static MainMenu mainMenu;

    public static Chess game;

    public MainMenu(Chess chess) {
        root = new AnchorPane();
        game = Chess.getInstanceOfGame();

        //setting the id for the css file
        this.setId("mainMenuScreen");

        initializeLabel();
        initializeButtons();
        initializeImages();

    }

    /**
     * This function initializes and puts a label on the screen.
     */

    public void initializeLabel(){
        Label title = new Label("Chess Meister");
        title.setId("mainMenuTitle");

        //set coordinates
        title.setLayoutX(370.0);
        title.setLayoutY(70);

        //set dimensions of label
        title.setPrefHeight(17.0);
        title.setPrefWidth(250);

        //add to screen
        this.getChildren().add(title);
    }

    /**
     * This function uses the factory method to create buttons and then places them on the screen.
     */

    public void initializeButtons(){
        //initialize and place player vs player button
        pvpB = ButtonFactory.createButton("Player vs. Player", 432.0, 150.0,
                                    46.0,115.0 );

        //set an event handler on the button
        pvpB.setOnAction(buttonHandler);

        //set id of button for css file
        pvpB.setId("pvpB");

        //place button on screen
        this.getChildren().add(pvpB);

        //initialize and place player vs cpu button
        pvcB = ButtonFactory.createButton("Player vs. CPU", 432, 220,
                                    48, 115);
        pvcB.setOnAction(buttonHandler);
        pvcB.setId("pvcB");
        this.getChildren().add(pvcB);

        //initialize and place online play button
        onlineB = ButtonFactory.createButton("Online Play", 432, 290,
                                    48, 115);
        onlineB.setOnAction(buttonHandler);
        onlineB.setId("onlineB");
        this.getChildren().add(onlineB);

        //initialize and place rules of chess button
        rulesB = ButtonFactory.createButton("Rules of Chess", 432, 360,
                                    48, 115);
        rulesB.setOnAction(buttonHandler);
        rulesB.setId("rulesB");
        this.getChildren().add(rulesB);

        //initialize and place tutorial button
        tutB = ButtonFactory.createButton("Tutorial", 432, 430,
                                48, 115);
        tutB.setOnAction(buttonHandler);
        tutB.setId("tutB");
        this.getChildren().add(tutB);

        //initialize and place settings button
        setB = ButtonFactory.createButton("Settings", 30, 530,
                                        48, 115 );
        setB.setOnAction(buttonHandler);
        setB.setId("setB");
        this.getChildren().add(setB);

        //initialize and place exit button
        exitB = ButtonFactory.createButton("Exit", 850, 530,
                                48, 115);
        exitB.setOnAction(buttonHandler);
        exitB.setId("exitB");
        this.getChildren().add(exitB);

    }

    /**
     * This function uses the factory method to create images and then places them on the screen.
     */

    public void initializeImages(){

        //initialize and add black king
        ImageView imv = new ImageView();
        this.getChildren().add(imv);

        ImageFactory.createImage(imv, "BK.png", 310, 390, 70, 70);

        //initialize and add black queen
        ImageView imv2 = new ImageView();
        this.getChildren().add(imv2);

        ImageFactory.createImage(imv2, "BQ.png", 310, 300, 70, 70);

        //initialize and add black rook
        ImageView imv3 = new ImageView();
        this.getChildren().add(imv3);

        ImageFactory.createImage(imv3, "BR.png", 310, 210, 70, 70);

        //initialize and add white rook
        ImageView imv4 = new ImageView();
        this.getChildren().add(imv4);

        ImageFactory.createImage(imv4, "WR.png", 604, 210, 70, 70);

        //initialize and add white queen
        ImageView imv5 = new ImageView();
        this.getChildren().add(imv5);

        ImageFactory.createImage(imv5, "WQ.png", 604, 300, 70, 70);

        //initialize and add white king
        ImageView imv6 = new ImageView();
        this.getChildren().add(imv6);

        ImageFactory.createImage(imv6, "WK.png", 604, 390, 70, 70);

        //initialize and add black knight
        ImageView imv7 = new ImageView();
        this.getChildren().add(imv7);
        ImageFactory.createImage(imv7, "BN.png", 310, 120, 70, 70);

        //initialize and add white knight
        ImageView imv8 = new ImageView();
        this.getChildren().add(imv8);
        ImageFactory.createImage(imv8, "WN.png", 604, 120, 70, 70);

    }

    /**
     * Set the handler for screen changes
     **/
    public void setScreenChangeHandler(ScreenChangeHandler sch) {
        this.screenChanger = sch;
    }//end

    /**
     * A getter method for the root of this pane.
     * @return the root
     */

    public AnchorPane getRoot(){
        return root;
    }

    /**
     * The action event handler.  The action was clicking a button on the screen. This function
     * will determine and carry out what will happen when a specific button is clicked.
     */

    EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event){
            if(screenChanger != null) {
                //getting the button that was clicked
                Object o = event.getSource();

                if (o == pvpB){
                    //change to player screen
                    screenChanger.switchScreen(ScreenFactory.Screen.SCREEN1);
                }

                else if(o == pvcB){
                    // changes to the temporary player vs. computer screen
                    screenChanger.switchScreen(ScreenFactory.Screen.SCREEN5);

                }

                else if(o == onlineB){
                    screenChanger.switchScreen(ScreenFactory.Screen.SCREEN7);
                }

                else if(o == rulesB){
                    screenChanger.switchScreen(ScreenFactory.Screen.SCREEN6);
                }

                else if(o == tutB){
                    screenChanger.switchScreen(ScreenFactory.Screen.SCREEN8);
                }

                else if(o == setB){

                    //change to the settings screen
                    screenChanger.switchScreen(ScreenFactory.Screen.SCREEN3);
                }

                else if(o == exitB){
                    //end the program
                    System.exit(1);
                }

            }//end of the != null if statement

        } //end event
    };

    /**
     * Sets the chess game
     * @param chess the chess game
     */
    @Override
    public void setGame(Chess chess) {
        game = chess;
    }

    /**
     * Singleton implementation of the main menu.
     * If there is no main menu, then create one, if there is already a main menu, return it.
     * @return the main menu
     */

    public static MainMenu getMenu() {
        if(mainMenu ==null){
            mainMenu = new MainMenu(game);
        }
        return mainMenu;
    }

}

