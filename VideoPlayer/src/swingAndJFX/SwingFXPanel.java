package swingAndJFX;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SwingFXPanel extends JFXPanel {

	private Button testButton;
	private TextField testTextField;
	private Label testLabel;
	private VBox pane;
	
	public SwingFXPanel() {
		init();
	}
	
	private void init() {
		testButton = new Button("I am a JavaFX button");
		testTextField = new TextField();
		testLabel = new Label("empty");
		pane = new VBox();
		pane.setAlignment(Pos.CENTER);
		pane.getChildren().addAll(testTextField, testButton, testLabel);
		Platform.runLater(new Runnable() {
			public void run() {
				createScene();
			}
		});
	}
	
	private void createScene() {
		Scene scene = new Scene(pane);
		setScene(scene);
	}
	
	public Button getTestButton() {
		return testButton;
	}
	
	public TextField getTestTextField() {
		return testTextField;
	}
	
	public Label getTestLabel() {
		return testLabel;
	}
}
