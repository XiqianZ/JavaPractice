package main;

import java.awt.EventQueue;

import javax.swing.JFrame;

import shape.SpaceShootingBoard;

public class CollisionEx extends JFrame {

    public CollisionEx() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new SpaceShootingBoard());
        
        setResizable(false);
        pack();
        
        setTitle("Collision");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            CollisionEx ex = new CollisionEx();
            ex.setVisible(true);
        });
    }
}
