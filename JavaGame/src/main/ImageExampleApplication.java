package main;

import java.awt.EventQueue;

import javax.swing.JFrame;

import shape.ImageShape;


public class ImageExampleApplication extends JFrame{

	 public ImageExampleApplication() {

	        initUI();
	    }

	    private void initUI() {

	        add(new ImageShape());

	        pack();

	        setTitle("Bardejov");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	    }

	    public static void main(String[] args) {

	        EventQueue.invokeLater(() -> {
	            ImageExampleApplication ex = new ImageExampleApplication();
	            ex.setVisible(true);
	        });
	    }
}
