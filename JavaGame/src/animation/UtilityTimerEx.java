package animation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UtilityTimerEx extends JFrame{
    public UtilityTimerEx() {

        initUI();
    }
    
    private void initUI() {
        
        add(new StarShape_UtilityTimer());
                        
        setResizable(false);
        pack();
        
        setTitle("Star");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
    }

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            JFrame ex = new UtilityTimerEx();
            ex.setVisible(true);
        });
    }
    
    private class StarShape_UtilityTimer extends JPanel {
    	private final int B_WIDTH = 350;
        private final int B_HEIGHT = 350;
        private final int INITIAL_X = -40;
        private final int INITIAL_Y = -40;    
        private final int INITIAL_DELAY = 100;
        private final int PERIOD_INTERVAL = 15;
        
        private Image star;
        private Timer timer;
        private int x, y;
        
        public StarShape_UtilityTimer() {
        	
        	this.setBackground(Color.black);
        	this.setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        	
        	loadImage();
        	
        	x=INITIAL_X;
        	y=INITIAL_Y;
        	
        	timer = new Timer();
        	timer.scheduleAtFixedRate(new ScheduleTask(), INITIAL_DELAY, PERIOD_INTERVAL);
        }
        
        private void loadImage() {
        	ImageIcon ii = new ImageIcon("resources/star.png");
        	star = ii.getImage();
        }
        
        private class ScheduleTask extends TimerTask {
			@Override
			public void run() {
				x += 1;
				y += 1;
				
				if(y>B_HEIGHT) {
					y=INITIAL_Y;
					x=INITIAL_X;
				}		
				repaint();
			}
        	
        }
        
        @Override
        public void paintComponent(Graphics g) {
        	super.paintComponent(g);
        	drawStar(g);
        }
        
        private void drawStar(Graphics g) {
        	g.drawImage(star, x, y, this);
        	Toolkit.getDefaultToolkit().sync(); //synchronises the painting on systems that buffer graphics events. 
        }
    }
}
