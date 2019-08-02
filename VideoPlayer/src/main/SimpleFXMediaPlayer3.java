package main;

import java.awt.BorderLayout;

/**
 * @author Xiqian
 */

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class SimpleFXMediaPlayer3 {

	private final static String FILE_URL = "resource/videos/testVideo1.mp4";
	static JPanel uiPane;

	private static void initPane() {
		uiPane = new JPanel();
		uiPane.setLayout(new BorderLayout());

		JFXPanel fxpane = new JFXPanel();
		fxpane.setBounds(0, 0, 600, 400);
		initFX(fxpane);

		uiPane.add(fxpane, BorderLayout.CENTER);
	}

	private static void initFX(JFXPanel panel) {
		Media media = new Media(new File(FILE_URL).toURI().toString());
		final MediaPlayer player = new MediaPlayer(media);

		player.setAutoPlay(true);

		MediaView mediaView = new MediaView(player);
		mediaView.setFitWidth(600);
		mediaView.setFitHeight(500);        
		mediaView.setSmooth(true);

		VBox root = new VBox(5, mediaView);
		panel.setScene(new Scene(root));
	}

	public static void main(String[] args) {
		JFrame jfm = new JFrame();
		jfm.setVisible(true);
		jfm.setSize(600,400);
		jfm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SwingUtilities.invokeLater(()->{
			initPane();
			jfm.add(uiPane);
		});
	}
}
