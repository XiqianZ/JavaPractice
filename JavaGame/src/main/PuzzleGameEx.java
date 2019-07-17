package main;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class MyButton extends JButton {
	
	private boolean isLastButton;
	
	public MyButton() {
		super();
		initUI();
	}
	
	private void initUI() {
		isLastButton = false;
		BorderFactory.createLineBorder(Color.gray);
		
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				setBorder(BorderFactory.createLineBorder(Color.yellow));
			}
		});
	}
	
	public void setLastButton() {
		isLastButton=true;
	}
	
	public boolean isLastButton() {
		return isLastButton;
	}
}

public class PuzzleGameEx extends JFrame {
	
	private JPanel panel;
	private BufferedImage source;
	private BufferedImage resized;
	private Image image;
	private MyButton lastButton;
	private int width, height;
	
	private List<MyButton> buttons;
	private List<Point> solution;
	
	private final int NUMBER_OF_BUTTONS = 12;
	private final int DESIRED_WIDTH = 300;
	
	public PuzzleGameEx() {
		init();
	}
	
	private void init() {
		solution=new ArrayList<>();
		for(int i=0;i<4;i++) {
			for(int j=0;j<3;j++) {
				solution.add(new Point(i,j));
			}
		}
		
		buttons = new ArrayList<>();
	}
}
