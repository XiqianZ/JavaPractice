package main;

/**
 * @author Xiqian
 */
import java.io.File;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaErrorEvent;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaMarkerEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

public class FXMediaPlayerTemplate extends Application {

	//
	private final String FILE_URL = "resource/videos/testVideo1.mp4";
	private Media media;
	private MediaPlayer player;
	private MediaView mediaView;
	private Scene scene;

	private HBox controlBox;
	private VBox root;
	private GridPane digiPane;

	private Slider timeSlider;
	
	private TextArea messageArea;

	public static void main(String[] args) {
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
		
		player.currentTimeProperty().addListener((obs, oldTime, newTime) ->{
			if(!getTimeSlider().isValueChanging()) {
				getTimeSlider().setValue(newTime.toSeconds()/player.getMedia().getDuration().toSeconds()*100);
			}
		});

		//Gather metadata stored with the media and display
		player.setOnReady(new Runnable() {
			public void run() {
				ObservableMap<String, Object> metadata = media.getMetadata();
				for(String key : metadata.keySet()) {
					getMessageArea().appendText("\n"+key+"=" + metadata.get(key));
				}
			}
		});
		
        player.setOnEndOfMedia(new Runnable()
        {
            public void run() 
            {
                messageArea.appendText("\nEnd of media !");
            }
        });
 
        player.setOnRepeat(new Runnable()
        {
            public void run() 
            {
                messageArea.appendText("\nRepeating media !");
            }
        });
	
        createMarkers();
	}

	private TextArea getMessageArea() {
		if (messageArea == null) {
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
		dropshadow.setColor(Color.RED);

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
		if (root == null) {
			controlBox = getControlBox();
			root = new VBox(5, mediaView, controlBox, createDigiPane(),getMessageArea());
			root.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
					+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
		}
		return root;
	}

	private HBox getControlBox() {
		if (controlBox == null) {
			controlBox = new HBox(5, createPlayButton(), createPauseButton(), getTimeSlider());
			controlBox.setAlignment(Pos.CENTER);
		}
		return controlBox;
	}
	
	private Slider getTimeSlider() {
		if(timeSlider == null) {
			timeSlider = new Slider();
//		timeSlider.setPrefWidth();
			
			timeSlider.setMaxWidth(Double.MAX_VALUE);
			timeSlider.valueProperty().addListener(new InvalidationListener() { 
				public void invalidated(Observable ov) 
				{ 
					if (timeSlider.isPressed()) {
						player.seek(player.getMedia().getDuration().multiply(timeSlider.getValue()/100)); 
						getMessageArea().appendText("\nTimeslider value is: " + timeSlider.getValue());
					} 
				} 
			}); 			
		}
		
		return timeSlider;
		
	}

	private Button createPlayButton() {
		Button playButton = new Button("Play");

		playButton.setOnAction(new EventHandler<ActionEvent>() {
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
				if (player.getStatus() == Status.PLAYING) {
					player.pause();
				} else if (player.getStatus() == Status.PAUSED) {
					player.play();
				}
			}
		});
		return pauseButton;
	}

	private GridPane createDigiPane() {
		digiPane = new GridPane();
		digiPane.setHgap(5);
		digiPane.setVgap(10);

		digiPane.addRow(0, new Label("Cycle Count:"), createCycleSlider());
		digiPane.addRow(1, new Label("Volume:"), createVolumeSlider());
		digiPane.addRow(2, new Label("Rate:"), createRateSlider());
		digiPane.addRow(3, new Label("Balance:"), createBalanceSlider());
		
		return digiPane;
	}

	private Slider createCycleSlider() {
		Slider cycleSlider = new Slider(1, 5, 1);
		cycleSlider.setMajorTickUnit(1);
		cycleSlider.setShowTickLabels(true);
		cycleSlider.valueProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable ov) {
				if (cycleSlider.isValueChanging()) {
					getMessageArea().appendText("\nCycle Count changed to: " + (int) cycleSlider.getValue());
					player.setCycleCount((int) cycleSlider.getValue());
				}
			}
		});
		return cycleSlider;
	}

	private Slider createVolumeSlider() {
		Slider volumeSlider = new Slider(0.0, 1.0, 0.5);
		volumeSlider.setMajorTickUnit(0.1);
		volumeSlider.setShowTickLabels(true);
		volumeSlider.valueProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable ov) {
				if (volumeSlider.isValueChanging()) {
					getMessageArea().appendText("\nVolume changed to: " + volumeSlider.getValue());
					player.setVolume(volumeSlider.getValue());
				}
			}
		});
		return volumeSlider;
	}

	private Slider createRateSlider() {
		Slider rateSlider = new Slider(0, 8, 4);
		rateSlider.setMajorTickUnit(1);
		rateSlider.setShowTickLabels(true);
		rateSlider.valueProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable ob) {
				if (rateSlider.isValueChanging()) {
					getMessageArea().appendText("\nRate changed to: " + rateSlider.getValue());
					player.setRate(rateSlider.getValue());
				}
			}
		});
		return rateSlider;
	}

	private Slider createBalanceSlider() {
		Slider balanceSlider = new Slider(-1.0, 1.0, 0);
		balanceSlider.setMajorTickUnit(0.2);
		balanceSlider.setShowTickLabels(true);
		balanceSlider.valueProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable ob) {
				if (balanceSlider.isValueChanging()) {
					getMessageArea().appendText("\nBalance changed to: " + balanceSlider.getValue());
					player.setBalance(balanceSlider.getValue());
				}
			}
		});
		return balanceSlider;
	}
	
	private void createMarkers() {
		ObservableMap<String, Duration> markers = media.getMarkers();
		markers.put("1 seconds", Duration.seconds(1));
		markers.put("2 second", Duration.seconds(2.0));
		
		player.setOnMarker(new EventHandler<MediaMarkerEvent>() {
			public void handle(MediaMarkerEvent event) {
				Pair<String, Duration> marker = event.getMarker();
				String markerText = marker.getKey();
				Duration markerTime = marker.getValue();
				getMessageArea().appendText("\nReached the marker " + markerText + " at " + markerTime);
			}
		});
	}

	private void printMessage(MediaException error) {
		MediaException.Type errorType = error.getType();
		String errorMessage = error.getMessage();
		getMessageArea().appendText("\n" + "Type: " + errorType + ", error Message: " + errorMessage);
	}
}
