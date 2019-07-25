package main;

import java.awt.EventQueue;

import javax.swing.JFrame;

import shape.SpaceShootingBoard;

public class ShootingMissilesEx extends JFrame {
	
	public ShootingMissilesEx() {
		initUI();
	}
	
	private void initUI() {
		this.add(new SpaceShootingBoard());
		
		this.setTitle("Shooting missiles");
		this.setSize(400,300);
		
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            ShootingMissilesEx ex = new ShootingMissilesEx();
            ex.setVisible(true);
        });
    }
}
