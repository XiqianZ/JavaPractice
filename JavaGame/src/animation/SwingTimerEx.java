package animation;

import java.awt.EventQueue;

import javax.swing.JFrame;

import shape.StarShape_TimerApp;

public class SwingTimerEx extends JFrame {

	public SwingTimerEx() {
		initUI();
	}
	
	private void initUI() {
		
		//TODO
		this.add(new StarShape_TimerApp());
		
		this.setResizable(false);
		this.pack();
		
		setTitle("Star");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            SwingTimerEx ex = new SwingTimerEx();
            ex.setVisible(true);
        });
    }
}
