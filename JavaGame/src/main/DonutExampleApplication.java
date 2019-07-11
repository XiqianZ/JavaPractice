package main;

import java.awt.EventQueue;
import javax.swing.JFrame;

import shape.Donut;

public class DonutExampleApplication extends JFrame {
	
	public DonutExampleApplication() {
		initUI();
	}
	
	private void initUI() {
		add(new Donut());
		
		setSize(330,330);
		
		setTitle("Donut");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				DonutExampleApplication ex = new DonutExampleApplication();
				ex.setVisible(true);
			}
		});
	}
}
