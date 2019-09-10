package breakoutgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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
	
	private int destroiedBrickNum = 0;
	
	private Thread animator;
	
	public BreakoutBoard() {
		initBoard();
	}
	
	private void initBoard() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		bricks = new Brick[N_OF_BRICKS];
		gameInit();
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		animator = new Thread(this);
		animator.run();
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
			
			doGameCycle();
			
			timeDiff = System.currentTimeMillis() - beforeTime;
			cycle = Commons.PERIOD - timeDiff;
			
			if(cycle<0) {
				cycle = 2;
			} 
			
			try {
				Thread.sleep(cycle);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			beforeTime = System.currentTimeMillis();
		}
//		drawGameOver();
	}
	
	private void doGameCycle() {
		ball.move();
		paddle.move();
		checkCollision();
		this.repaint();
	}
	
	private void checkCollision() {
		System.out.println("ball position: [Max X: " + ball.getRect().getMaxX()+"] "
				+ "[Max Y:" + ball.getRect().getMaxY() + "] " +
				"[Min X: " + ball.getRect().getMinX() + "] " + 
				"[Min Y: " + ball.getRect().getMinY() + "];");
		
		if(destroiedBrickNum == bricks.length) {
			message = "You win!";
			stopGame();
		}
		
		if(ball.getRect().getMaxY()>Commons.BOTTOM_EDGE) {
			stopGame();
		}
		
		if(ball.getRect().intersects(paddle.getRect())) {
			
			int paddleLPos = (int) paddle.getRect().getMinX();
			int ballLPos = (int) ball.getRect().getMinX();
			
			int first = paddleLPos + 8;
			int second = paddleLPos + 16;
			int third = paddleLPos + 24;
			int fourth = paddleLPos + 32;
			

			if(ballLPos < first) {
				ball.setXDir(-1);
				ball.setYDir(-1);	//TODO really?
				
			} else if(ballLPos < second) {
				ball.setXDir(-1);
				ball.setYDir(-1*ball.getYDir());
				
			} else if(ballLPos < third) {
				ball.setXDir(0);
				ball.setYDir(-1);
				
			} else if(ballLPos < fourth){
				ball.setXDir(1);
				ball.setYDir(-1*ball.getYDir());
			} else {
				ball.setXDir(1);
				ball.setYDir(-1);	//TODO really?
			}	
		}
		
		for (int i=0; i<Commons.N_OF_BRICKS; i++) {
			if((ball.getRect().intersects(bricks[i].getRect()))) {
				
				int ballLeft = (int) ball.getRect().getMinX();
				int ballHeight = (int) ball.getRect().getHeight();
				int ballWidth = (int) ball.getRect().getWidth();
                int ballTop = (int) ball.getRect().getMinY();

                Point pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
                Point pointLeft = new Point(ballLeft - 1, ballTop);
                Point pointTop = new Point(ballLeft, ballTop - 1);
                Point pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);
                
                if (!bricks[i].isDestroyed()) {

                    if (bricks[i].getRect().contains(pointRight)) {

                        ball.setXDir(-1);
                    } else if (bricks[i].getRect().contains(pointLeft)) {

                        ball.setXDir(1);
                    }

                    if (bricks[i].getRect().contains(pointTop)) {

                        ball.setYDir(1);
                    } else if (bricks[i].getRect().contains(pointBottom)) {

                        ball.setYDir(-1);
                    }

                    bricks[i].setDestroyed(true);
                }
			}
					
		}
	}
	
}
