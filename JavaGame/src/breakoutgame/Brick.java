package breakoutgame;

import javax.swing.ImageIcon;

public class Brick extends Sprite {

	private boolean destroyed;
	
	public Brick(int x, int y) {
		initBrick(x,y);
	}
	
	private void initBrick(int x, int y) {
        this.x = x;
        this.y = y;
        
        destroyed = false;

        loadImage();
        loadImageDimensions();
	}
	
	private void loadImage() {
		ImageIcon ii = new ImageIcon("resources/breakout/brickie.png");
		image = ii.getImage();
	}
	
	public boolean isDestoryed() {
		return this.destroyed;
	}
	
	public void setDestoyed(boolean val) {
		destroyed = val;
	}
}
