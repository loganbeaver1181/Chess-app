package ui_gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


/**
 * Screen that shows that playing against the computer has yet to be
 * implemented by outputting a simple label.
 *
 * @author Michael Imerman 100%
 */
public class PlayerVsComputerScreen extends Pane {

    /**
     * The main pain of the screen
     */
    final AnchorPane root;

    /**
     * Label to display a message that player vs the computer is not implemented yet.
     */
    final Label message;

    /**
     * A button to return to the main menu
     */
    final Button exit;

    /**
     * Responsible for changing screens
     */
    ScreenChangeHandler sch;


    /**
     * Constructor the create the temporary label for the screen of the
     * unimplemented player vs computer.
     */
    public PlayerVsComputerScreen() {
        root = new AnchorPane();

        message = new Label("Player vs. Computer has yet to be implemented!");
        message.setLayoutX(370.0);
        message.setLayoutY(250.0);
        message.setPrefHeight(20.0);
        message.setPrefWidth(300.0);
        this.getChildren().add(message);

        exit = new Button("Exit");
        exit.setOnAction(buttonHandler);
        exit.setLayoutX(850.0);
        exit.setLayoutY(530.0);
        exit.setPrefHeight(48.0);
        exit.setPrefWidth(115.0);
        this.getChildren().add(exit);
    }

    /**
     * An even handler interface implementation for button handling
     */
    EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            if (sch != null) {
                Object o = event.getSource();

                try {
                    if (o == exit) {
                        sch.switchScreen(ScreenFactory.Screen.SCREEN0);// Should be main menu Screen
                    }
                } catch (NumberFormatException nfe) {
                    throw nfe;
                }
            }
        }//end handle

    };//End buttonHandler------------------------------------------------------

    /**
     * Setter method for the field that is a screen change handler
     * @param sch A screen change handler object
     */
    public void setScreenChangeHandler(ScreenChangeHandler sch) {
        this.sch = sch;
    }
}
