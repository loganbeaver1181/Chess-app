package ui_gui;


import controllers.Chess;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

/**
 * A Screen to allow players to enter their name's for the chess game.
 * @author Logan Beaver(95%) Michael Imerman (5%)
 */
public class PlayerScreen extends Pane{
    /** The main pane of the screen */
    Pane root;

    /** responsible for changing screens */
    ScreenChangeHandler sch;

    /**The buttons on the screen**/
    Button b1,b2;

    /** player 1's name */
    TextField Player1;

    /** player 2's name */
    TextField Player2;

    /** a reference to the current field*/
    TextField current;

    /** the chess game being played */
    static Chess game;

    /**
     * Build player screen
     * @param game - the chess game
     */
    public PlayerScreen(Chess game){
        super();
        setChess(game);
        root = new Pane();
        this.setId("PlayerScreen");

        //Play button
        b1 = new Button("PLAY");
        b1.setStyle("-fx-color: gold");
        b1.setOnAction(buttonHandler);
        b1.setLayoutX(405);
        b1.setLayoutY(525);
        b1.setPrefHeight(48.0);
        b1.setPrefWidth(200);
        this.getChildren().add(b1);

        //Exit button
        b2 = new Button("Exit");
        b2.setStyle("-fx-color: gold");
        b2.setOnAction(buttonHandler);
        b2.setLayoutX(850);
        b2.setLayoutY(530);
        b2.setPrefHeight(48);
        b2.setPrefWidth(115);
        this.getChildren().add(b2);

        //label above player one text box
        Label lab2 = new Label("Player One Name");
        lab2.setFont(new Font(25));
        lab2.setStyle("-fx-text-fill: purple");
        lab2.setLayoutX(400);
        lab2.setLayoutY(124.0);
        lab2.setPrefHeight(17.0);
        lab2.setPrefWidth(250);
        this.getChildren().add(lab2);

        //label above player two text box
        Label lab3 = new Label("Player Two Name");
        lab3.setFont(new Font(25));
        lab3.setStyle("-fx-text-fill: purple");
        lab3.setLayoutX(400);
        lab3.setLayoutY(250.0);
        lab3.setPrefHeight(17.0);
        lab3.setPrefWidth(250);
        this.getChildren().add(lab3);

        //Label for top of screen
        Label lab4 = new Label("ENTER PLAYER NAMES");
        lab4.setFont(new Font(50));
        lab4.setStyle("-fx-text-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, white 20%, black 100%);");
        lab4.setLayoutX(250);
        lab4.setLayoutY(34.0);
        lab4.setPrefWidth(600.0);
        lab4.setPrefHeight(39.0);
        this.getChildren().add(lab4);

        //text field for white players name
        TextField tf1 = new TextField();
        tf1.setPrefWidth(200);
        tf1.setPrefHeight(25);
        tf1.setLayoutX(400);
        tf1.setLayoutY(170);
        Player1 = tf1;
        this.getChildren().add(Player1);

        //text field for black players name
        TextField tf2 = new TextField();
        tf2.setPrefWidth(200);
        tf2.setPrefHeight(25);
        tf2.setLayoutX(400);
        tf2.setLayoutY(290);
        Player2 = tf2;
        this.getChildren().add(Player2);


        /** changing focused property to be in correct text field */
        Player1.focusedProperty().addListener(new ChangeListener<Boolean>() {

            public void changed(ObservableValue<? extends Boolean> observable,
                                Boolean wasFocused, Boolean isFocused) {
                if(isFocused){
                    current = Player1;
                }

            }
        });


        /** changing focused property to be in correct text field */
        Player2.focusedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> observable,
                                Boolean wasFocused, Boolean isFocused) {
                if(isFocused){
                    current = Player2;
                }
            }
        });
    }


    //-------------------------------------------------------------------------
    /**An event handler interface implementation for button handling*/
    //-------------------------------------------------------------------------
    EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {

            Object o = event.getSource();
            try{
                    String name1 = Player1.getText();
                    if(name1.isEmpty()) name1 = "WHITE";
                    game.getSettings().setWhiteName(name1);
                    String name2 = Player2.getText();
                    if(name2.isEmpty()) name2 = "BLACK";
                    game.getSettings().setBlackName(name2);
                    if (o == b1) {
                        //set players names to chess game
                        setNames(name1,name2);
                        sch.switchScreen(ScreenFactory.Screen.SCREEN2);
                    } else if (o == b2) {
                        sch.switchScreen(ScreenFactory.Screen.SCREEN0);
                    }
            } catch (NumberFormatException nfe){

            }
        }//end handle

    };


    /**
     * register screen with possible screens from main menu
     * @param sch - the class responsible for handling changing screens
     */
    public void setSCH(ScreenChangeHandler sch){
        this.sch = sch;
    }


    /**
     * get the root of the screen
     * @return the root pane
     */
    public Pane getRoot(){
        return this.root;
    }


    /**
     * Call the state editors setPLayers method with the input from the two text fields
     * @param name1 white players name
     * @param name2 black players name
     */
    private void setNames(String name1, String name2){
        game.stateEditor.setPlayers(name1,name2);
    }

    /**
     * Set the current chess game
     * @param game - the chess object to set
     */
    public void setChess(Chess game){this.game = game;}
}
