package swingAndJFX;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import javafx.application.Platform;

public class InteropFrame extends JFrame {
	
	private JSplitPane centralSplitPane;
	private SwingPanel swingPanel;
	private SwingFXPanel swingFXPanel;
	
	public InteropFrame() {
		init();
	}
	
	private void init() {
		setTitle("Swing <-> JavaFX Interoperatbility");
		setSize(500,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		centralSplitPane = new JSplitPane();
		centralSplitPane.setDividerLocation(0.5);
		centralSplitPane.setResizeWeight(0.3);
		
		swingPanel = new SwingPanel();
        swingFXPanel = new SwingFXPanel();
        
        swingPanel.getTestButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Platform.runLater(() -> {
					swingFXPanel.getTestLabel().setText(swingPanel.getTestTextField().getText());
				});
			}
        });
        
        swingFXPanel.getTestButton().setOnAction(a -> {
        	swingPanel.getTestLabel().setText(swingFXPanel.getTestTextField().getText());
        });
        
        centralSplitPane.setLeftComponent(swingPanel);
        centralSplitPane.setRightComponent(swingFXPanel);
        add(centralSplitPane, BorderLayout.CENTER);
	}
}
