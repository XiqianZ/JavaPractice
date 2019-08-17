package snakegame;

import java.awt.EventQueue;

import javax.swing.JFrame;

/*
 * 1. game animation seems a bit clumsy, make it more smooth
 */
public class SnakeGameMain extends JFrame {
	public SnakeGameMain() {
		init();
	}
	
	private void init() {
		this.add(new SnakeGameBoard());
        
        setResizable(false);
        pack();
        
        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
            JFrame ex = new SnakeGameMain();
            ex.setVisible(true);
        });
	}
}
