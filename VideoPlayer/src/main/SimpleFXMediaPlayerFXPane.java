package main;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class SimpleFXMediaPlayerFXPane {

	private static final String FILE_URL = "resource/videos/testVideo1.mp4";

	private static void initFxLater(JFXPanel panel) {
		Media media = new Media(new File(FILE_URL).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

	    MediaView mediaView = new MediaView(mediaPlayer);
	    mediaView.setFitWidth(450);
	    mediaView.setFitHeight(200);
	    mediaPlayer.setAutoPlay(true);
	    
	    VBox root = new VBox(5,mediaView);

	    panel.setScene(new Scene(root));
	}

	private static void initSwingLater() {
		JFrame jFrame = new JFrame("- JFrame -");
		jFrame.setSize(545, 426);
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.getContentPane().setLayout(null);

		final JFXPanel jFXPanel = new JFXPanel();
		jFXPanel.setBounds(0, 0, 540, 188);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 528, 392);
		jFrame.getContentPane().add(tabbedPane);

		JPanel panel = new JPanel();
		tabbedPane.addTab("New tab", null, panel, null);
		JPanel playerpanel = new JPanel();
		tabbedPane.addTab("Media Player", null, playerpanel, null);

		JPanel panel_1 = new JPanel();
		playerpanel.add(jFXPanel);

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				initFxLater(jFXPanel);
			}
		});

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				initSwingLater();
			}

		});
	}
}