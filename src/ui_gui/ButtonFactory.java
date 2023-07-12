package ui_gui;
import javafx.scene.control.Button;

/**
 * This is a factory class that creates buttons.
 * @author laurinburge (100%)
 */

public class ButtonFactory {
    /**
     * This is the factory method that creates the buttons.
     *
     * @param title A string that represents the text going onto the button
     * @param layoutX A double for the x coordinate on the screen
     * @param layoutY A double for the y coordinate on the screen
     * @param prefHeight A double for the height of the button
     * @param prefWidth A double for the width of the button
     *
     * @return A button with the specifications in the params
     */
    public static Button createButton(String title, double layoutX, double layoutY, double prefHeight,
                                      double prefWidth){
        //create new button
        Button button = new Button(title);

        //set coordinates
        button.setLayoutX(layoutX);
        button.setLayoutY(layoutY);

        //set dimensions
        button.setPrefHeight(prefHeight);
        button.setPrefWidth(prefWidth);

        return button;
    }

}
