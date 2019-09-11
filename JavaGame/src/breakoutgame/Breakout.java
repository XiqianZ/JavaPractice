package breakoutgame;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Breakout {
	
	JFrame jf;
	
	public Breakout() {
		initUI();
	}
	
	private void initUI() {
		jf = new JFrame();
		jf.setTitle("breakout");
//		jf.add(new BreakoutBoard());
		jf.add(new BreakoutBoardTimer());
		
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(Commons.WIDTH, Commons.HEIGHT);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		jf.setVisible(true);
		System.out.println("Where is my JFrame");
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() ->{
			Breakout game = new Breakout();
			
		});
	}
}
