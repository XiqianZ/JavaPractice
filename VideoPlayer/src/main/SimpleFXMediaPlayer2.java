package main;

import java.io.File;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaErrorEvent;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SimpleFXMediaPlayer2 extends Application {

	private final String FILE_URL = "resource/videos/testVideo1.mp4";
	private Media media; 
	private MediaPlayer player;
	private MediaView mediaView;
	private Scene scene;
	
	private HBox controlBox;
	private VBox root;

	private TextArea messageArea;
	
	
    public static void main(String[] args) 
    {
        Application.launch(args);
    }
	
	@Override
	public void start(Stage stage) throws Exception {
		initialMediaPlayer();
		initialMediaView(stage); 
	}
	
	private void initialMediaPlayer() {
		media = new Media(new File(FILE_URL).toURI().toString());
		media.setOnError(new Runnable() {
			public void run() {
				printMessage(media.getError());
			}
		});
		
		player = new MediaPlayer(media);
		player.setAutoPlay(true);	
		player.setOnError(new Runnable() {
			public void run() {
				printMessage(player.getError());
			}
		});
		
		player.statusProperty().addListener(new ChangeListener<MediaPlayer.Status>() {
			@Override
			public void changed(ObservableValue<? extends Status> ob, final MediaPlayer.Status oldStatus,
					final MediaPlayer.Status newStatus) {
				getMessageArea().appendText("\nStatus change from " + oldStatus + " to " + newStatus);
	
			}
			
		});
	}
	
	private TextArea getMessageArea() {
		if(messageArea == null) {
			messageArea = new TextArea();
		}
		return messageArea;
	}
	
	private void initialMediaView(Stage stage) {
		mediaView = new MediaView();
		
		mediaView.setMediaPlayer(player);
		mediaView.setFitHeight(500);
		mediaView.setFitWidth(400);
		mediaView.setSmooth(true);
		mediaView.setPreserveRatio(true);
		mediaView.setOnError(new EventHandler<MediaErrorEvent>() {
			public void handle(MediaErrorEvent event) {
				printMessage(event.getMediaError());
			}
		});
		
		setupMediaViewEffects();
		
		setupUI(stage);

	}

	private void setupMediaViewEffects() {
		DropShadow dropshadow = new DropShadow();
		dropshadow.setOffsetX(5.0);
		dropshadow.setOffsetY(5.0);
		dropshadow.setColor(Color.WHITE);
		
		mediaView.setEffect(dropshadow);
	}

	private void setupUI(Stage stage) {
		root = getRoot();		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Simple Media Player");
		stage.show();
	}
	
	private VBox getRoot() {
		if(root==null) {
			controlBox = getControlBox();
			root = new VBox(5, mediaView, controlBox, getMessageArea());
			root.setStyle("-fx-padding: 10;" +
	                "-fx-border-style: solid inside;" +
	                "-fx-border-width: 2;" +
	                "-fx-border-insets: 5;" +
	                "-fx-border-radius: 5;" +
	                "-fx-border-color: blue;");
		}
		return root;
	}
	
	private HBox getControlBox() {
		if(controlBox == null) {
			controlBox = new HBox(5, createPlayButton(), createPauseButton());
		}
		return controlBox;
	}
	
	private Button createPlayButton() {
		Button playButton = new Button("Play");
		
		playButton.setOnAction(new EventHandler <ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (player.getStatus() == Status.PLAYING) {
					player.stop();
					player.play();
				} else {
					player.play();
				}
				
			}
		});
		return playButton;
	}
	
	private Button createPauseButton() {
		Button pauseButton = new Button("Pause");
		
		pauseButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if(player.getStatus() == Status.PLAYING) {
					player.pause();
				} else if (player.getStatus() == Status.PAUSED) {
					player.play();
				}
			}
		});
		return pauseButton;
	}

	private Slider createCycleSlider() {
		Slider cycleSlider = new Slider(1,5,1);
		cycleSlider.setMajorTickUnit(1);
		cycleSlider.setShowTickLabels(true);
		cycleSlider.valueProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		return cycleSlider;
	}
	
	private Slider createVolumneSlider() {
		//TODO
		return null;
	}
	
	private Slider createRateSlider() {
		//TODO
		return null;
	}
	
	private Slider createBlanceSlider() {
		//TODO
		return null;
	}
	
	private void printMessage(MediaException error) {
		MediaException.Type errorType = error.getType();
		String errorMessage = error.getMessage();
		getMessageArea().appendText("\n" + "Type: " + errorType 
				+ ", error Message: " + errorMessage);
	}
}
