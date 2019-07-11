package shape;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.BasicStroke;


public class Donut extends JPanel {
	
	public Donut() {}

	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		
		drawDonut(g);
	}
	
	private void drawDonut(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		RenderingHints rh 
			= new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setRenderingHints(rh);
		
		Dimension size = this.getSize();	//THIS is to get the this check
		double w = size.getHeight();
		double h = size.getHeight();
		
		Ellipse2D e = new Ellipse2D.Double(0,0,80,130);
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.gray);
		
		for (double deg=0; deg<360; deg+=3) {
			AffineTransform at 
				= AffineTransform.getTranslateInstance(w/2, h/2);
			at.rotate(Math.toRadians(deg));
			g2d.draw(at.createTransformedShape(e));
		}
		
	}
}
