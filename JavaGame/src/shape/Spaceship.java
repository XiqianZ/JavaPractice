package shape;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Spaceship extends Sprite{
	
	private int dx;
	private int dy;
	private List<Missile> missiles;
	
	
	public Spaceship(int x, int y) {
		super(x,y);
		initSpaceship();
	}
	
	private void initSpaceship() {
		this.missiles = new ArrayList<>();
		this.loadImage("resources/craft.png");
		getImageDimensions();
	}

	public void move() {
		x+=dx;
		y+=dy;
	}
	
	public List<Missile> getMissiles(){
		return missiles;
	}
	
	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
		
		if(key==KeyEvent.VK_LEFT) {
			dx=-2;
		} else if(key==KeyEvent.VK_RIGHT) {
			dx=2;
		}
		
		if(key==KeyEvent.VK_UP) {
			dy=-2;
		} else if(key==KeyEvent.VK_DOWN) {
			dy=2;
		}
		
		if(key==KeyEvent.VK_SPACE) {
			fire();
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key=e.getKeyCode();
		if((key == KeyEvent.VK_LEFT) || (key==KeyEvent.VK_RIGHT)) {
			dx=0;
		}
		if((key==KeyEvent.VK_UP) || (key==KeyEvent.VK_DOWN)) {
			dy=0;
		}
	}
	
	public void fire() {
		missiles.add(new Missile(this.x+this.width, 
				this.y+this.height/2));
	}
}
