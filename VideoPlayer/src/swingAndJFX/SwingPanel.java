package swingAndJFX;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Xiqian
 */
public class SwingPanel extends JPanel{

    private JButton testButton;
    private JTextField testTextField;
    private JLabel testLabel;
    
    public SwingPanel() {
        init();
    }
    
    private void init(){
        setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        testButton = new JButton("I am a Swing Button");
        testTextField = new JTextField();
        testLabel = new JLabel("empty");
        testButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        testTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        testLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        Box.Filler filler1 = new Box.Filler(new Dimension(0, 0), new Dimension(0, 1000), new Dimension(0, 32767));
        Box.Filler filler2 = new Box.Filler(new Dimension(0, 0), new Dimension(0, 1000), new Dimension(0, 32767));
        panel.add(filler1);
        panel.add(testTextField);
        panel.add(testButton);
        panel.add(testLabel);
        panel.add(filler2);
        add(panel, BorderLayout.CENTER);
    }

    public JButton getTestButton() {
        return testButton;
    }

    public JLabel getTestLabel() {
        return testLabel;
    }

    public JTextField getTestTextField() {
        return testTextField;
    }
    
}
