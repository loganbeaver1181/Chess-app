package ui_gui;

import controllers.Chess;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.beans.value.ChangeListener;
import model.GameStateEditor;
import model.Settings;

/**
 * Screen that shows the settings for the game, which can be changed at any time.
 * @author Josiah Cherbony 100%
 * @version 6/8/23
 */
public class SettingsMenu extends Pane implements EventHandler<ActionEvent> {

    /**The root of the layout**/
    private Pane root;

    /** The button components in the scene */
    Button blackSquareButton, whiteSquareButton, saveButton, exitButton;

    /** The checkbox components in the scene */
    CheckBox undosEnabledCheckbox, unlimitedUndosCheckbox, showMovesCheckbox;

    /** The textfield components in the scene */
    TextField undoTextField;

    /** The screen changed, used for changing from screen to screen*/
    ScreenChangeHandler screenChanger;

    /** The scene that we came from*/
    private Pane previousScene;

    /** The game we're playing */
    public static Chess game;

    /** The instance of the Settings Menu*/
    public static SettingsMenu instance;

    /** The saved state of the game. Used if the player "cancel" to reinitialize settings*/
    public static SettingsMenu savedState;

    /**Set the handler for screen changes**/
    public void setScreenChangeHandler(ScreenChangeHandler sch){
        this.screenChanger = sch;
    }//end

    /**
     * Create a singleton instance of a SettingsMenu
     * @return The instance of this SettingsMenu
     */
    public static SettingsMenu getInstance(Chess game){
        // If the current instance is null, create a new one
        if (instance == null){
            instance = new SettingsMenu(game);
            // Saves the initial state of the game (when the player opened the SettingsMenu)
            instance.saveState();
        }else{
            // Saves the initial state of the game (when the player opened the SettingsMenu)
            instance.saveState();
        }
        return instance;
    }//end getInstance


    /**
     * A constructor for the SettingsMenu
     * @param chessGame - The game whose settings we're changing
     */
    public SettingsMenu(Chess chessGame) {
        super();
        //Creating a root
        root = new Pane();
        // Setting the chess game
        this.game = chessGame;
        this.setId("settingMenuScreen");

        //Initializing our scene's components
        initializeButtons();
        initializeCheckboxes();
        initializeLabel();
    }

    /**
     * Initializes the checkboxes in the scene
     */
    public void initializeCheckboxes(){
        Settings settings = game.getSettings();

        //Creating the "Undos Enabled" label
        Label enabled = new Label("Enabled");
        enabled.setFont(new Font(30));
        enabled.setPrefWidth(200);
        enabled.setPrefHeight(50);

        // Creating the "Undos Enabled" checkbox
        undosEnabledCheckbox = new CheckBox();
        undosEnabledCheckbox.setSelected(true);
        undosEnabledCheckbox.setFont(new Font(30));

        // The HBox for holding the "Undos Enabled" components
        HBox hbox1 = new HBox();
        hbox1.setSpacing(20);
        hbox1.setPrefSize(40.0, 40.0);

        ////Creating the "Unlimited Undos" label
        Label unlimUndosLabel = new Label("Unlimited Undos");
        unlimUndosLabel.setFont(new Font(30));
        unlimUndosLabel.setPrefWidth(400);
        unlimUndosLabel.setPrefHeight(50);

        // Creating the "Unlimited Undos" checkbox
        unlimitedUndosCheckbox = new CheckBox();
        unlimitedUndosCheckbox.setSelected(false);
        unlimitedUndosCheckbox.setFont(new Font(30));

        // The HBox for holding the "Unlimited Undos" components
        HBox hbox2 = new HBox();
        hbox2.setSpacing(20);

        //Creating the "Show Moves" label
        Label showMovesLabel = new Label("Show Moves");
        showMovesLabel.setFont(new Font(30));

        //Creating the "Show Moves" CheckBox
        showMovesCheckbox = new CheckBox();
        showMovesCheckbox.setFont(new Font(30));
        showMovesCheckbox.setSelected(settings.getShowMoves());

        //Creating an HBox for the "Show Moves" components
        HBox hbox3 = new HBox();
        hbox3.setSpacing(20);
        hbox3.setLayoutX(687);
        hbox3.setLayoutY(210);

        // Creating a HBox for the "Max Undos" Components
        HBox hbox4 = new HBox();
        hbox4.setSpacing(20);

        //Creating a "Max Undos" Label
        Label maxUndoLabel = new Label("Max Undos");
        maxUndoLabel.setFont(new Font(30));
        maxUndoLabel.setPrefWidth(400);
        maxUndoLabel.setPrefHeight(50);

        //Creating the "Undos" amount TextField
        undoTextField = new TextField();
        undoTextField.setPrefSize(60, 40);


        // Create a ChangeListener that listens for changes in the "Undos Enabled" checkbox
        ChangeListener<Boolean> listener1 = new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    undoTextField.setDisable(false); // Enable if the checkbox is selected
                    unlimitedUndosCheckbox.setDisable(false);

                } else {
                    unlimitedUndosCheckbox.setDisable(true);
                    unlimitedUndosCheckbox.setSelected(false);
                    undoTextField.clear();
                    if(undoTextField.isDisabled() == false){
                        undoTextField.setDisable(true);
                    }
                }
            }
        };

        // Add the ChangeListener to the "Undos Enabled" selected property
        undosEnabledCheckbox.selectedProperty().addListener(listener1);

        // Setting up listeners for the "Unlimited Undos CheckBox"
        ChangeListener<Boolean> listener2 = new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    undoTextField.setDisable(true); // If checked, disable text field
                    undoTextField.clear();
                } else {
                    undoTextField.setDisable(false);
                }
            }
        };
        // Add the ChangeListener to the "Unlimited Undos" selected property
        unlimitedUndosCheckbox.selectedProperty().addListener(listener2);

        // Adding all the components to their HBoxes
        hbox1.getChildren().addAll(undosEnabledCheckbox, enabled);
        hbox2.getChildren().addAll(unlimitedUndosCheckbox, unlimUndosLabel);
        hbox3.getChildren().addAll(showMovesCheckbox, showMovesLabel);
        hbox4.getChildren().addAll(undoTextField, maxUndoLabel);

        // Setting up a Vbox to hold our "Undos" HBoxes and components
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setLayoutY(375);
        vBox.setLayoutX(110);

        // Adding all the "Undo" components to our VBox
        vBox.getChildren().addAll(hbox1, hbox2, hbox4);
        // Adding the components to the scene
        this.getChildren().add(vBox);
        this.getChildren().add(hbox3);
    }

    /**
     * Initializing the button in the scene
     */
    public void initializeButtons(){
        // Getting the game's options
        Settings settings = game.getSettings();

        // Creating a Hbox for the "Save" and "Exit" components
        HBox hbox1 = new HBox();
        hbox1.setLayoutX(720.0);
        hbox1.setLayoutY(475);
        hbox1.setSpacing(10);

        // Creating a "Save" button
        saveButton = new Button("Save");
        saveButton.setOnAction(this::handle);
        //set dimensions of label
        saveButton.setPrefHeight(45.0);
        saveButton.setPrefWidth(70);

        // Creating an "Exit" button
        exitButton = new Button("Exit");
        exitButton.setOnAction(this::handle);
        //set dimensions of label
        exitButton.setPrefHeight(45.0);
        exitButton.setPrefWidth(70);

        // Adding the "Save" and "Exit" button to the HBox
        hbox1.getChildren().addAll(saveButton, exitButton);

        // Creating a Hbox for the "Black Squares" components
        HBox hbox2 = new HBox();
        hbox2.setSpacing(5);

        // Create a BlackSquare Button
        blackSquareButton = new Button();
        blackSquareButton.setOnAction(this::handle);
        blackSquareButton.setPrefHeight(40.0);
        blackSquareButton.setPrefWidth(40);

        // Initializes the BlackSquare's Button Color
        int[] blkColors = settings.getBlackSquareColor();
        // Setting the initial color of the blackSquareButton (to black)
        Color black = Color.rgb(blkColors[0], blkColors[1], blkColors[2]);
        // Recolor OptionMenu's button with the value chosen in our color picker
        BackgroundFill blackFill = new BackgroundFill(black, CornerRadii.EMPTY, Insets.EMPTY);
        Background backGround = new Background(blackFill);
        blackSquareButton.setBackground(backGround);

        // Creates a "Black Squares" label
        Label blackLabel = new Label();
        blackLabel.setFont(new Font(30));
        blackLabel.setText("Black Squares");
        blackLabel.setPrefWidth(200);
        blackLabel.setPrefHeight(50);

        // Adding the "Black Square" Components to the Hbox
        hbox2.getChildren().addAll(blackLabel, blackSquareButton);

        // Creates a HBox for the "White Square" Components
        HBox hbox3 = new HBox();
        hbox3.setSpacing(5);

        // Creates a "White Square" button
        whiteSquareButton = new Button();
        whiteSquareButton.setOnAction(this::handle);
        whiteSquareButton.setPrefHeight(40.0);
        whiteSquareButton.setPrefWidth(40);

        // Initializes the WhiteSquare's Button Color
        int[] whtColors = settings.getWhiteSquareColor();
        // Setting the initial color of the whiteSquareButton (to white)
        Color white = Color.rgb(whtColors[0], whtColors[1], whtColors[2]);
        // Recolor OptionMenu's button with the value chosen in our color picker
        BackgroundFill whiteFill = new BackgroundFill(white, CornerRadii.EMPTY, Insets.EMPTY);
        Background whiteGround = new Background(whiteFill);
        whiteSquareButton.setBackground(whiteGround);

        // Creates a "White Squares" label
        Label whiteLabel = new Label();
        whiteLabel.setFont(new Font(30));
        whiteLabel.setText("White Squares");
        whiteLabel.setPrefWidth(200);
        whiteLabel.setPrefHeight(50);

        // Adding the "White Square" Components to the Hbox
        hbox3.getChildren().addAll(whiteLabel, whiteSquareButton);

        // Creating a VBox for the Black and White Square components
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setLayoutY(170);
        vBox.setLayoutX(110);

        vBox.getChildren().addAll(hbox2, hbox3);

        this.getChildren().add(hbox1);
        this.getChildren().add(vBox);

    }

    /**
     * Initializes the labels in the scene.
     */
    public void initializeLabel(){
        Label settingsLabel = new Label("Settings");
        settingsLabel.setFont(new Font(50));
        settingsLabel.setLayoutX(440);
        settingsLabel.setLayoutY(30);
        this.getChildren().add(settingsLabel);

        // Creating a label in the scene
        Label colors = new Label("Colors:");
        colors.setFont(new Font(40));
        colors.setLayoutX(110);
        colors.setLayoutY(115);
        this.getChildren().add(colors);

        Label undo = new Label("Undo:");
        undo.setFont(new Font(40));
        undo.setLayoutX(110);
        undo.setLayoutY(320);
        this.getChildren().add(undo);

    }

    /**
     * Saves the state of the scene. Needed for reloading the original state
     * if the player clicks the "Cancel" button
     */
    public void saveState() {
        // Setting the current components of the scene to a new SettingsMenu object
        SettingsMenu newInstance = new SettingsMenu(Chess.getInstanceOfGame());
        newInstance.blackSquareButton.setBackground(instance.blackSquareButton.getBackground());
        newInstance.whiteSquareButton.setBackground(instance.whiteSquareButton.getBackground());
        newInstance.undosEnabledCheckbox.setSelected(instance.undosEnabledCheckbox.isSelected());
        newInstance.unlimitedUndosCheckbox.setSelected(instance.unlimitedUndosCheckbox.isSelected());
        newInstance.undoTextField.setText(instance.undoTextField.getText());
        newInstance.showMovesCheckbox.setSelected(instance.showMovesCheckbox.isSelected());
        // Making the saved state the instance new instance we've created
        savedState = newInstance;
    }

    /**Get the root of the layout in order for another class to display it**/
    public Pane getRoot() {
        return root;
    }

    /**
     * Handles an action event generated by a component in our scene
     * @param actionEvent the event which occurred
     */

    public void handle(ActionEvent actionEvent) {
        if (actionEvent.getSource() == blackSquareButton) {
            openColorChooser(blackSquareButton);
        }else if(actionEvent.getSource() == whiteSquareButton) {
            openColorChooser(whiteSquareButton);
        }else if(actionEvent.getSource() == saveButton){
            saveSettings();
        }else if(actionEvent.getSource() == exitButton){
            exitSettings();
        }
    }

    /**
     * Exits the scene.
     */
    public void exitSettings(){
        // Resetting the settings back to their original state
        instance = savedState;
        if(previousScene != null){ // The previous scene is the game we're coming from
            screenChanger.switchToFromSettings(ScreenFactory.Screen.SCREEN2, previousScene);
            previousScene = null;
        }else{
            screenChanger.switchScreen(ScreenFactory.Screen.SCREEN0);
        }
    }

    /**
     * Saves the Options Menu Screen's settings to the Chess games Options()
     */
    public void saveSettings(){
        // Getting the game's options
        Settings settings = game.getSettings();
        GameStateEditor stateEditor = game.getStateEditor();

        // That means we can undo in this game. If the player doesn't select
        // anything else, we'll assume they'll undo once at a time
        if(undosEnabledCheckbox.isSelected()){
            settings.setUndosEnabled(true);
            settings.setUndoMax(1);
            stateEditor.setMaxUndos(settings.getUndoMax());

            // If the player wants unlimited undos
            if(unlimitedUndosCheckbox.isSelected()){
                settings.setUnlimitedUndos(true);
                settings.setUndoMax(999);
                stateEditor.setMaxUndos(settings.getUndoMax());
                // If the "Max Undos" text field has numbers in it
            }else if(0 < undoTextField.getText().length() && undoTextField.getText().length() < 3){
                settings.setUnlimitedUndos(false);
                int allowed_undos = Integer.parseInt(undoTextField.getText().trim());
                settings.setUndoMax(allowed_undos);
                stateEditor.setMaxUndos(settings.getUndoMax());
            }
        }else{// If the "Undos Enabled" is not selected
            settings.setUndosEnabled(false);
            settings.setUnlimitedUndos(false);
            settings.setUndoMax(0);
            stateEditor.setMaxUndos(settings.getUndoMax());
        }

        if(showMovesCheckbox.isSelected()){
            settings.setShowMoves(true);
        }else{ // If the show moves checkbox isn't checked
            settings.setShowMoves(false);
        }

        int[] blkColors = getButtonColorInt(blackSquareButton);
        int[] whtColors = getButtonColorInt(whiteSquareButton);

        // Setting the options black and white square colors
        settings.setBlackSquareColor(blkColors[0], blkColors[1], blkColors[2]);
        settings.setWhiteSquareColor(whtColors[0], whtColors[1], whtColors[2]);

        if(previousScene != null){ // The previous scene is the game we're coming from
            screenChanger.switchToFromSettings(ScreenFactory.Screen.SCREEN2, previousScene);
            previousScene = null;
        }else{
            screenChanger.switchScreen(ScreenFactory.Screen.SCREEN0);
        }
        ScreenFactory.getGameScreen().draw(game.getBoard());
    }

    /**
     * Opens a ColorChooser triggered by a button click
     * @param button - The button that opened ColorChooser
     */
    public void openColorChooser(Button button){
        screenChanger.switchToFromColorChooser(ScreenFactory.Screen.SCREEN4, button);
    }

    /**
     * Returns the Color of a button in the form of a list of integers representing
     * RBG values.
     * @param button - The button whose color we're getting
     * @return A button's color as a list of integers
     */
    public int[] getButtonColorInt(Button button){
        Color color = (Color) button.getBackground().getFills().get(0).getFill();
        int[] buttonColors =  {(int)(color.getRed() * 255), (int)(color.getGreen() * 255), (int)(color.getBlue() * 255)};
        return buttonColors;
    }

    /**
     * Sets the previous scene.
     * @param scene - The scene we came from.
     */
    public void setPreviousScene(Pane scene){
        previousScene = scene;
    }

}

