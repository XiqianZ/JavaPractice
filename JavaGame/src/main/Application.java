package main;

import java.awt.EventQueue;
import javax.swing.JFrame;

import shape.DonutShape;

public class Application extends JFrame {
	
	public Application() {
		initUI();
	}
	
	private void initUI() {
		add(new DonutShape());
		setSize(250,200);
		
		setTitle("Application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater( () -> {
			Application ex=new Application();
			ex.setVisible(true);
		});
	}
}
