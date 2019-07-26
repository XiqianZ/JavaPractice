package effects;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
 
public class FxEffectsExample1 extends Application
{
    public static void main(String[] args) 
    {
        Application.launch(args);
    }
     
    @Override
    public void start(Stage stage)
    {
        // Create the Effect
        DropShadow shadow = new DropShadow();
         
        // Create the Rectangle
        Rectangle rect = new Rectangle(100, 50, Color.GRAY);
        // Add the Effect to the Rectangle
        rect.setEffect(shadow);
         
        // Create the Sliders
        Slider offsetXSlider = new Slider(-200, 200, 0);
        Slider offsetYSlider = new Slider(-200, 200, 0);
        Slider radiusSlider = new Slider(0, 127, 10);
        Slider spreadSlider = new Slider(0.0, 1.0, 0);
         
        // Create the ColorPicker
        ColorPicker colorPicker = new ColorPicker(Color.BLACK);
 
        // Create the Box for the Blur-Types
        ComboBox<BlurType> blurTypeList = new ComboBox<>();
        blurTypeList.setValue(shadow.getBlurType());
        blurTypeList.getItems().addAll(BlurType.ONE_PASS_BOX,BlurType.TWO_PASS_BOX,
                BlurType.THREE_PASS_BOX,BlurType.GAUSSIAN);
 
        // Bind the Properties to the Effect
        shadow.offsetXProperty().bind(offsetXSlider.valueProperty());
        shadow.offsetYProperty().bind(offsetYSlider.valueProperty());
        shadow.radiusProperty().bind(radiusSlider.valueProperty());
        shadow.spreadProperty().bind(spreadSlider.valueProperty());
        shadow.colorProperty().bind(colorPicker.valueProperty());
        shadow.blurTypeProperty().bind(blurTypeList.valueProperty());
         
        // Create the GridPane
        GridPane pane = new GridPane();
        // Set horizontal and vertical Spaving
        pane.setHgap(5);
        pane.setVgap(10);
         
        // Add the details to the GridPane
        pane.addRow(0, new Label("OffsetX:"), offsetXSlider, new Label("OffsetY:"), offsetYSlider);
        pane.addRow(1, new Label("Radius:"), radiusSlider,new Label("Spread:"), spreadSlider);
        pane.addRow(2, new Label("Color:"), colorPicker,new Label("Blur Type:"), blurTypeList);
                 
        // Create the BorderPane
        BorderPane root = new BorderPane();
        // Add the children to the BorderPane
        root.setCenter(rect);
        root.setBottom(pane);
         
        // Set the Padding and Border for the BorderPane
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
         
        // Create the Scene
        Scene scene = new Scene(root,500,300);
        // Add the Scene to the Stage
        stage.setScene(scene);
        // Set the Title of the Stage
        stage.setTitle("An Example of the DropShadow Effect");
        // Display the Stage
        stage.show();       
    }
 
}
