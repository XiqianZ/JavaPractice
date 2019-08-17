package breakoutgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class BreakoutBoard extends JPanel implements Commons, Runnable{

	private String message = "Game over";
	private Ball ball;
	private Paddle paddle;
	private Brick bricks[];
	private boolean inGame = true;
	
	public BreakoutBoard() {
		initBoard();
	}
	
	private void initBoard() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		
		bricks = new Brick[N_OF_BRICKS];
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		gameInit();
	}
	
	private void gameInit() {
		ball = new Ball();
		paddle = new Paddle();
		initBricks();
	}
	
	private void initBricks() {
		int k = 0;
		for(int i=0; i<5; i++) {
			for (int j=0; j<6; j++) {
				bricks[k] = new Brick(j*40+30, i*10+50);
				k++;
			}
		}
	}
	
	@Override 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		if(inGame) {
			drawObjects(g2d);
		} else {
			drawGameOver(g2d);
		}
		Toolkit.getDefaultToolkit().sync();
	}
	
	private void drawObjects(Graphics2D g2d) {
        g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(),
                ball.getImageWidth(), ball.getImageHeight(), this);
        g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),
                paddle.getImageWidth(), paddle.getImageHeight(), this);

        for (int i = 0; i < N_OF_BRICKS; i++) {
        	if(!bricks[i].isDestroyed()) {
        		g2d.drawImage(bricks[i].getImage(), bricks[i].getX(), bricks[i].getY(),
        				bricks[i].getImageWidth(), bricks[i].getImageHeight(), this);
        	}
        }
	}
	
	private void drawGameOver(Graphics2D g2d) {
		Font font = new Font("Verdana", Font.BOLD, 18);
		FontMetrics metr = this.getFontMetrics(font);
		
		g2d.setColor(Color.black);
		g2d.setFont(font);
		g2d.drawString(message, (Commons.WIDTH - metr.stringWidth(message))/2, Commons.HEIGHT/2);
	}
	
	private class TAdapter extends KeyAdapter{
		@Override
		public void keyReleased(KeyEvent e) {
			paddle.keyReleased(e);
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			paddle.keyPressed(e);
		}
	}
	
	private void stopGame() {
		inGame = false;
	}
	
	
	
	@Override
	public void run() {
		long beforeTime, timeDiff, cycle;
		beforeTime = System.currentTimeMillis();
		while(inGame) {
			ball.move();
			paddle.move();
			checkCollision();
			this.repaint();
			
			timeDiff = System.currentTimeMillis() - beforeTime;
			cycle = Commons.PERIOD - timeDiff;
			
			if(cycle<0) {
				cycle = 0;
			} 
			
			try {
				Thread.sleep(cycle);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			beforeTime = System.currentTimeMillis();
		}
		//drawGameOver();
	}
	
	private void checkCollision() {
		
	}

	
}
