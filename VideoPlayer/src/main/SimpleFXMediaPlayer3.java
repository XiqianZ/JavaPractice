package main;

/**
 * @author Xiqian
 */

import java.io.File;

import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class SimpleFXMediaPlayer3 {

	private final String FILE_URL = "resource/videos/testVideo1.mp4";
	
	public void initFX(JFXPanel panel) {
		Media media = new Media(new File(FILE_URL).toURI().toString());
        final MediaPlayer player = new MediaPlayer(media);
        
        player.setAutoPlay(true);
        
        MediaView mediaView = new MediaView(player);
        mediaView.setFitWidth(600);
        mediaView.setFitHeight(500);        
        mediaView.setSmooth(true);
       
       VBox root = new VBox();
       
	}
}
