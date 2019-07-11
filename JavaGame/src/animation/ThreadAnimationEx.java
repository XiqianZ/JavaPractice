package animation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ThreadAnimationEx extends JFrame{
	
	public ThreadAnimationEx() {

        initUI();
    }
    
    private void initUI() {
        
        add(new StarShape_Thread());

        setResizable(false);
        pack();
        
        setTitle("Star");    
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
    }

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            JFrame ex = new ThreadAnimationEx();
            ex.setVisible(true);
        });
    }
    
    private class StarShape_Thread extends JPanel implements Runnable{

    	private final int B_WIDTH = 350;
    	private final int B_HEIGHT = 350;
    	private final int INITIAL_X = -40;
    	private final int INITIAL_Y = -40;
    	private final int DELAY = 5;
    	
    	private Image star;
    	private Thread animator;
    	private int x,y;
    	
    	public StarShape_Thread() {
    		init();
    	}
    	
    	private void loadImage() {
    		ImageIcon ii = new ImageIcon("resources/star.png");
    		star = ii.getImage();
    	}
    	
    	private void init() {
    		this.setBackground(Color.black);
    		this.setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
    	
    		loadImage();
    		
    		x=INITIAL_X;
    		y=INITIAL_Y;
    	}
    	
    	@Override
    	public void addNotify() {	//is called after our JPanel has been added to the JFrame component. 
    								//This method is often used for various initialisation tasks.
    		super.addNotify();
    		
    		animator = new Thread(this);
    		animator.start();
    	}
    	
    	@Override
    	public void paintComponent(Graphics g) {
    		super.paintComponent(g);
    		drawStar(g);
    	}
    	
    	private void drawStar(Graphics g) {
    		g.drawImage(star, x, y, this);
    		Toolkit.getDefaultToolkit().sync();
    	}
    	
    	private void cycle() {
    		y += 1;
    		x += 1;
    		if(y>B_HEIGHT) {
    			y=INITIAL_Y;
    			x=INITIAL_X;
    		}
    	}
		@Override
		public void run() {
			long beforeTime, timeDiff, sleep;
			
			beforeTime=System.currentTimeMillis();
			
			while(true) {
				cycle();
				repaint();
				
				timeDiff = System.currentTimeMillis() - beforeTime;
				sleep = DELAY - timeDiff;
				
				if(sleep<0) {
					sleep = 2;
				}
				
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					String msg=String.format("Thread interrupted: %s", e.getMessage());
					
					JOptionPane.showMessageDialog(this, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				beforeTime=System.currentTimeMillis();
			}
			
		}
    	
    }
}
