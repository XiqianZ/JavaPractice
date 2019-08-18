package erica_empathy_player;

/**
 * @author Xiqian
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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

public class PlayerTemplate extends Application {

	//
	private final String FILE_URL = "resource/videos/testVideo1.mp4";
	private Media media;
	private MediaPlayer player;
	private MediaView mediaView;
	private Scene scene;

	private HBox controlBox;
	private VBox root;

	private Slider timeSlider;
	private Button recordClimaxTimingButton; 
	
	private TextArea messageArea;
	
	private double climaxTiming = 2000.0;

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
			root = new VBox(5, mediaView, controlBox,getMessageArea());
			root.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
					+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
		}
		return root;
	}

	private HBox getControlBox() {
		if (controlBox == null) {
			controlBox = new HBox(5, createPlayButton(), createPauseButton(), 
					getRecordClimaxButton(), getTimeSlider());
			controlBox.setAlignment(Pos.CENTER);
		}
		return controlBox;
	}
	
	private Slider getTimeSlider() {
		if(timeSlider == null) {
			timeSlider = new Slider();
			timeSlider.setMaxWidth(Double.MAX_VALUE);
			HBox.setHgrow(timeSlider, Priority.ALWAYS);
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
		Button playButton = new Button();
		try {
			Image image = new Image(new FileInputStream("resource/icons/play_128.png"));
			ImageView icon = new ImageView(image);
			icon.setFitHeight(30);
			icon.setFitWidth(30);
			playButton.setGraphic(icon);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

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
		Button pauseButton = new Button();
		try {
			Image image = new Image(new FileInputStream("resource/icons/pause.png"));
			ImageView icon = new ImageView(image);
			icon.setFitHeight(30);
			icon.setFitWidth(30);
			pauseButton.setGraphic(icon);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

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
	
	private Button getRecordClimaxButton() {
		if(recordClimaxTimingButton == null) {
			recordClimaxTimingButton = new Button("ERICAが触るのタイミング");
			recordClimaxTimingButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					climaxTiming = player.getCurrentTime().toMillis();
					createMarkers();
					DataManager.writeDataPlus("CurrentTiming(in Millis): " + climaxTiming);
				}
			});
		}
		
		return recordClimaxTimingButton;
	}

	private void createMarkers() {
		ObservableMap<String, Duration> markers = media.getMarkers();
		markers.put("climax", Duration.millis(climaxTiming));

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
