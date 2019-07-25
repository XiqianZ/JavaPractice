package shape;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class SpaceShootingBoard extends JPanel implements Runnable {

	private Spaceship spaceship;
	private List<Alien> aliens;
	private boolean ingame;
	private Thread animator;
	
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
	
	public SpaceShootingBoard() {
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
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		animator = new Thread(this);
		animator.start();
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
				g.drawImage(missile.getImage(), missile.getX(), missile.getY(),this);
			}
		}
		
		for (Alien alien : aliens) {
			if(alien.isVisible()) {
				g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
			}
		}
		
		g.setColor(Color.WHITE);
		g.drawString("Aliens left: " + aliens.size(), 5, 15);
		
	}
	
	private void drawGameOver(Graphics g) {
		String msg = "Game Over";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics fm=getFontMetrics(small);
		
		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH-fm.stringWidth(msg))/2, B_HEIGHT/2);
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
		if(spaceship.isVisible()) {
			spaceship.move();			
		}
	}
	
	private void updateAliens() {
		if(aliens.isEmpty()) {
			ingame=false;
			return;
		}
		
		for(int i=0;i<aliens.size();i++) {
			Alien a=aliens.get(i);
			if(a.isVisible()) {
				a.move();
			} else {
				aliens.remove(i);
			}
		}
	}
	
	private void checkCollisions() {
		Rectangle r3=spaceship.getBounds();
		
		for (Alien alien : aliens) {
			Rectangle r2=alien.getBounds();
			
			if(r3.intersects(r2)) {
				spaceship.setVisible(false);
				alien.setVisible(false);
				ingame=false;
			}
		}
		
		List<Missile> ms=spaceship.getMissiles();
		for(Missile m : ms) {
			Rectangle r1=m.getBounds();
			for(Alien alien : aliens) {
				Rectangle r2 = alien.getBounds();
				//TODO This is not very efficient, must have better ways
				if(r1.intersects(r2)) {
					m.setVisible(false);
					alien.setVisible(false);
				}
			}
		}
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

	@Override
	public void run() {
		long beforeTime, timeDiff, cycle;
		beforeTime=System.currentTimeMillis();
		
		while(ingame) {
			checkCollisions();
			updateSpaceship();
			updateAliens();
			updateMIssiles();
			repaint();
			
			timeDiff = System.currentTimeMillis() - beforeTime;
			cycle = DELAY - timeDiff;
			if(cycle < 0) {
				cycle=2;
			}
			try {
				Thread.sleep(cycle);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			beforeTime = System.currentTimeMillis();
		}
		repaint();
	}
	
}
