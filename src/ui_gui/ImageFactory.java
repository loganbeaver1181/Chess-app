package ui_gui;
import controllers.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This is a factory class that creates images.
 * @author Laurin Burge(100%)
 */
public class ImageFactory {
    /**
     * This is the factory method that creates the images.
     *
     * @param imv An ImageView an image that can be displayed
     * @param picName A string for the name of the pic you want to insert
     * @param layoutX A double for the x coordinate on the screen
     * @param layoutY A double for the y coordinate on the screen
     * @param fitHeight A double for the height of the image
     * @param fitWidth A double for the width of the image
     *
     * @return An image with the specifications from the params
     */
    public static Image createImage(ImageView imv, String picName, double layoutX, double layoutY,
                                    double fitHeight, double fitWidth){

        //create new image
        Image image = new Image(Main.class.getResourceAsStream(picName));

        //set coordinates
        imv.setLayoutX(layoutX);
        imv.setLayoutY(layoutY);

        //set dimensions
        imv.setFitHeight(fitHeight);
        imv.setFitWidth(fitWidth);

        //place
        imv.setImage(image);

        return image;
    }
}
