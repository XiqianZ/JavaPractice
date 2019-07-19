package shape;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakeGameBoard extends JPanel implements ActionListener {

	private final int B_WIDTH=300;
	private final int B_HEIGHT=300;
	private final int DOT_SIZE=10;
	private final int ALL_DOTS = 900;
	private final int RAND_POS = 29;
	private final int DELAY = 140;
	
	private final int x[] = new int[ALL_DOTS];
	private final int y[] = new int[ALL_DOTS];
	
	private int dots;
	private int apple_x;
	private int apple_y;
	
	private boolean leftDirection = false;
	private boolean rightDirection = true;
	private boolean upDirection = false;
	private boolean downDirection = false;
	private boolean inGame = true;
	
	private Timer timer;
	private Image ball;
	private Image apple;
	private Image head;
	
	public SnakeGameBoard() {
		initBoard();
	}
	
	private void initBoard() {
		this.addKeyListener(new TAdapter());
		this.setBackground(Color.black);
		this.setFocusable(true);
		
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		loadImages();
		initGame();
	}
	
	private void loadImages() {
		ImageIcon iib = new ImageIcon("/resources/snake/dot.png");
		this.ball = iib.getImage();
		
		ImageIcon iia = new ImageIcon("/resources/snake/apple.png");
		this.apple = iia.getImage();
		
		ImageIcon iih = new ImageIcon("/resources/snake/head.png");
		this.head = iih.getImage();
	}
	
	private void initGame() {
		dots = 3;
		for (int z=0; z<dots; z++) {
			x[z] = 50-z*10;
			y[z] = 50;
		}
		locateApple();
		
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}
	
	private void doDrawing(Graphics g) {
		if(inGame) {
			g.drawImage(apple, apple_x, apple_y, this);
			
			for(int z=0; z<dots; z++) {
				if(z==0) {
					g.drawImage(head, x[z], y[z], this);
				} else {
					g.drawImage(ball, x[z], y[z], this);
				}
			}
			Toolkit.getDefaultToolkit().sync();
		} else {
			gameOver(g);
		}
	}
	
	private void gameOver(Graphics g) {
		String msg = "Game Over";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = getFontMetrics(small);
		
		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH-metr.stringWidth(msg))/2, B_HEIGHT/2);
	}
	
	private void checkApple() {
		if((x[0]==apple_x)&&(y[0]==apple_y)) {
			dots++;
			locateApple();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
