package ui_gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * A simple color chooser using three SliderPanes.
 * @author Josiah Cherbony 100%
 * @version 6/8/23
 */
public class ColorChooser extends GridPane implements  EventHandler<ActionEvent> /*Any more needed??*/{
	
	/**The selected color as a hex value (RGB)**/
	String selectedColor;

	/**Red color slider panel**/
	private SliderPane red;
	
	/**Green color slider panel**/
	private SliderPane green;
	
	/**Blue color slider panel**/
	private SliderPane blue;
	
	/**Confirms a color choice**/
	private Button ok;
	
	/**Cancels a color choice**/
	private Button cancel;
	
	/**The area on to which the color is shown**/
	StackPane color;

	/** The scene that came before opening the colorChooser */
	private Scene previousScene;

	/** The button that opened the colorChooser */
	private Button previousButton;
	
	/**The color text label**/
	Label hexColor;

	/** The screen changed, used for changing from screen to screen*/
	ScreenChangeHandler screenChanger;
	
	/**Maximum color intensity**/
	private final int MAX_INTEN = 255;
	
	/**Minimum color intensity**/
	private final int MIN_INTEN = 0;

	/**The singleton instance of this class**/
	private static ColorChooser instance;
	
	//TODO: Extra fields here

	/**
	 * Create a singleton instance of a ColorChooser
	 * @return The instance of this ColorChooser
	 */
	public static ColorChooser getInstance(Button button){
		if (instance == null) instance = new ColorChooser();
		instance.setPreviousButton(button);
		String color = instance.getButtonColor(button);
		instance.setColor(color);
		return instance;
	}//end getInstance

	/**Set the handler for screen changes**/
	public void setScreenChangeHandler(ScreenChangeHandler sch){
		this.screenChanger = sch;
	}//end

	/**Construct a color chooser. */
	public ColorChooser(){
		
		this.setVgap(20);
		//Grid contstraints for row
		RowConstraints row0 = new RowConstraints();
		row0.setPercentHeight(40);
		RowConstraints row1 = new RowConstraints();
		row1.setPercentHeight(50);
		RowConstraints row2 = new RowConstraints();
		row2.setPercentHeight(10);
		
		//grid constraints for columns
		ColumnConstraints col0 = new ColumnConstraints();
		col0.setPercentWidth(50);
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(50);
		
		//Appy constraints
		this.getRowConstraints().addAll(row0, row1,row2);
		this.getColumnConstraints().addAll(col0,col1);

		//Top panel for color
		color = new StackPane();
		color.getStyleClass().add("color");
		hexColor = new Label();
		hexColor.getStyleClass().add("color_text");
		color.getChildren().add(hexColor);

		//Create Panel for sliders with centered layout
		HBox  sliders = new HBox();
		sliders.setSpacing(20.0);
		sliders.setAlignment(Pos.TOP_CENTER);

		//Construct 3 sliders for RGB
		red = new SliderPane("Red",MIN_INTEN, MIN_INTEN, MAX_INTEN);
		green = new SliderPane("Green",MIN_INTEN, MIN_INTEN, MAX_INTEN);
		blue = new SliderPane("Blue",MIN_INTEN, MIN_INTEN, MAX_INTEN);

		// Setting up listeners for the color sliders
		red.getSlider().valueProperty().addListener((obs, oldVal, newVal) -> {
			setBackround(newVal.intValue(), green.getValue(), blue.getValue());
		});

		green.getSlider().valueProperty().addListener((obs, oldVal, newVal) -> {
			setBackround(red.getValue(), newVal.intValue(), blue.getValue());
		});

		blue.getSlider().valueProperty().addListener((obs, oldVal, newVal) -> {
			setBackround(red.getValue(), green.getValue(),  newVal.intValue());
		});

		//Add the sliders
		sliders.getChildren().add(red);
		sliders.getChildren().add(green);
		sliders.getChildren().add(blue);

		ok = new Button("OK");
		cancel = new Button("Cancel");
		ok.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
		cancel.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
		
		sliders.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
		color.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
		
		//col, row, colspan, rowspan
		this.add(color, 0,0,2,1);
		this.add(sliders, 0, 1,2,1);
		this.add(cancel, 0,2, 1,1);
		this.add(ok, 1,2,1,1);
		
		ok.setOnAction(this);
		cancel.setOnAction(this);
		
		//TODO: Set up Listeners here

		// Listen for Slider value changes
		
	}//end constructor
	
	/**
	 * Set the background color of the  chooser
	 * @param r The red intensity 0..255
	 * @param g The blue intensity 0..255
	 * @param b The green intensity 0..255
	 */
	public void setBackround(int r, int g, int b){
		//Avoid invalid values
		if(r > 255 || g > 255 || b >255) return;
		
		String hr = Integer.toHexString(r);
		String hg = Integer.toHexString(g);
		String hb = Integer.toHexString(b);
		
		if(r+g+b/3 > 127)
			hexColor.setTextFill(Color.BLACK);
		else
			hexColor.setTextFill(Color.WHITE);
		
		//Add preceeding 0 if only 1 char
		if(hr.length() == 1)
			hr = 0 + hr;	
		if(hg.length() == 1)
			hg = 0 + hg;	
		if(hb.length() == 1)
			hb = 0 + hb;
		
		selectedColor = hr+hg+hb;
		
		color.setStyle("-fx-background-color: #" + selectedColor);
		
		hexColor.setText("#" + selectedColor);
		//System.out.println(col + " : " + hr + ", " + hg + ", " + hb);
	}//end setBackground

	/**
	 * Sets the color of the color chooser with a Hexadecimal value.
	 * @param selectedColor - The Hexadecimal value of the color we're settng.
	 */
	public void setColor(String selectedColor){
		color.setStyle("-fx-background-color: #" + selectedColor);
		hexColor.setText(selectedColor);

		red.set(Integer.parseInt(selectedColor.substring(0, 2), 16));
		green.set(Integer.parseInt(selectedColor.substring(2, 4), 16));
		blue.set(Integer.parseInt(selectedColor.substring(4, 6), 16));
	}

	/**The method of the button EventHandler interface implementation that this class has**/
	@Override
	public void handle(ActionEvent event) {
		// If we clicked the "Ok" button
		if(event.getSource() == ok){
			// Get the RGB color value
			Color color = Color.rgb(red.getValue(), green.getValue(), blue.getValue());
			// Recolor OptionMenu's button with the value chosen in our color picker
			BackgroundFill backgroundFill = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
			Background background = new Background(backgroundFill);
			previousButton.setBackground(background);
			//saveState();
			// Return to previous stage
			screenChanger.switchToFromColorChooser(ScreenFactory.Screen.SCREEN3, ok);

		}
		// If we clicked the "Cancel" button
		else if(event.getSource() == cancel){
			screenChanger.switchToFromColorChooser(ScreenFactory.Screen.SCREEN3, cancel);
		}
	}//end handle


	/** Sets the last button pressed that opened the ColorChooser*/
	public void setPreviousButton(Button previousButton){
		this.previousButton = previousButton;
	}

	/**
	 * Returns the Color of a button in the form of a Hexadecimal string.
	 * @param button - The button whose color we're getting
	 * @return A button's color as a Hexadecimal string
	 */
	public String getButtonColor(Button button){
		Color color = (Color) button.getBackground().getFills().get(0).getFill();
		String hex = String.format("%02X%02X%02X", (int)(color.getRed()*255),
				(int)(color.getGreen()*255), (int)(color.getBlue()*255));
		return hex;
	}
}//end class

