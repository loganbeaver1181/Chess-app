package ui_gui;
//*************************************************************************

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 * A class implementing this interface must be able to switch the application
 * screens using the switch screen method call.
 * @author Logan(100%)
 */
//*************************************************************************
public interface ScreenChangeHandler{


    /**Sub screens must call this to switch screen.
     * @param choice The screen to show**/
    public void switchScreen(ScreenFactory.Screen choice);



    void switchToFromColorChooser(ScreenFactory.Screen screen, Button ok);

    void switchToFromSettings(ScreenFactory.Screen initScreen, Pane scene);
}//end *********************************************