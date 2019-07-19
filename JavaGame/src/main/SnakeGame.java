package main;

import java.awt.EventQueue;

import javax.swing.JFrame;

import shape.SnakeGameBoard;

/*
 * 1. game animation seems a bit clumsy, make it more smooth
 */
public class SnakeGame extends JFrame {
	public SnakeGame() {
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
            JFrame ex = new SnakeGame();
            ex.setVisible(true);
        });
	}
}
