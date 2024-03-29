package shape;

import java.awt.Image;

public class Missile extends Sprite {
	
	private final int BOARD_WIDTH=390;
	private final int MISSILE_SPEED = 4;
	
	public Missile(int x, int y) {
		super(x,y);
		initMissile();
	}
	
	private void initMissile() {
		this.loadImage("resources/missile.png");
		this.getImageDimensions();
	}
	
	public void move() {
		x+=MISSILE_SPEED;
		if(x>BOARD_WIDTH) {
			visible=false;
		}
	}
}
