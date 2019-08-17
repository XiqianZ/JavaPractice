package breakoutgame;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Paddle extends Sprite implements BreakoutCommonValues {

	private int dx;
	
	public Paddle() {
		initPaddle();
	}
	
	private void initPaddle() {
		loadImage();
		loadImageDimensions();
		
		resetState();
	}
	
	private void loadImage() {
		ImageIcon ii = new ImageIcon("resources/breakout/paddle.png");
		image = ii.getImage();
	}
	
	public void move() {
		x += dx;
		
		if(x<=0) {
			x=0;
		}
		
		if(x>= WIDTH - imageWidth) {
			x=WIDTH - imageWidth;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key==KeyEvent.VK_LEFT || key==KeyEvent.VK_RIGHT) {
			dx = 0;
		}
	}
	
	private void resetState() {
		x = INIT_PADDLE_X;
		y = INIT_PADDLE_Y;
	}
}

