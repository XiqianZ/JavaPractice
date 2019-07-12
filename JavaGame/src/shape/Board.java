package shape;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
	
	private Timer timer;
	private Spaceship spaceship;
	private List<Alien> aliens;
	private boolean ingame;
	
	private final int DELAY = 15;
	private final int ICRAFT_X=40;
	private final int ICRAFT_Y=60;
	private final int B_WIDTH=400;
	private final int B_HEIGHT=300;
	//Initial alien position
	private final int[][] pos= {
	        {2380, 29}, {2500, 59}, {1380, 89},
	        {780, 109}, {580, 139}, {680, 239},
	        {790, 259}, {760, 50}, {790, 150},
	        {980, 209}, {560, 45}, {510, 70},
	        {930, 159}, {590, 80}, {530, 60},
	        {940, 59}, {990, 30}, {920, 200},
	        {900, 259}, {660, 50}, {540, 90},
	        {810, 220}, {860, 20}, {740, 180},
	        {820, 128}, {490, 170}, {700, 30}
	};
	
	public Board() {
		initBoard();
	}
	
	private void initBoard() {
		this.addKeyListener(new TAdapter());
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(B_WIDTH,B_HEIGHT));
		ingame=true;
		
		spaceship = new Spaceship(ICRAFT_X,ICRAFT_Y);
		initAliens();
		
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	private void initAliens() {
		aliens = new ArrayList<>();
		
		for(int[] p : pos) {
			aliens.add(new Alien(p[0],p[1]));
		}
	}
	
	@Override 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(ingame) {
			drawObjects(g);			
		} else {
			drawGameOver(g);
		}
		Toolkit.getDefaultToolkit().sync();
	}
	
	private void drawObjects(Graphics g) {
		//draw spaceship, missiles and aliens
		if(spaceship.isVisible()) {
			g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(), this);
		}
		
		List<Missile> ms = spaceship.getMissiles();
		for (Missile missile : ms) {
			if(missile.isVisible()) {
				
			}
		}
	}
	
	private void drawGameOver(Graphics g) {
		//TODO
	}
	
	private void doDrawing(Graphics g) {
		Graphics2D g2d=(Graphics2D) g;
		g2d.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(), this);
		
		List<Missile> missiles=spaceship.getMissiles();
		for(Missile missile : missiles) {
			g2d.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		updateMIssiles();
		updateSpaceship();
		
		repaint();
	}
	
	private void updateMIssiles() {
		List<Missile> missiles=spaceship.getMissiles();
		for(int i=0;i<missiles.size();i++) {
			Missile missile=missiles.get(i);
			if(missile.isVisible()) {
				missile.move();
			} else {
				missiles.remove(i);
			}
		}
	}
	
	private void updateSpaceship() {
		spaceship.move();
	}
	
	private class TAdapter extends KeyAdapter{
		@Override
		public void keyReleased(KeyEvent e) {
			spaceship.keyReleased(e);
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			spaceship.keyPressed(e);
		}
	}
	
}
