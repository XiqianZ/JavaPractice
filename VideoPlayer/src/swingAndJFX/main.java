package swingAndJFX;

import javax.swing.SwingUtilities;

public class main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(main::showGUI);
	}
	
	private static void showGUI() {
		InteropFrame frame = new InteropFrame();
		frame.setVisible(true);
	}
}
