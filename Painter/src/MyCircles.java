import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;


public class MyCircles {
	private Color color;
	Ellipse2D thisEllipse;
	
	
	public MyCircles(int x, int y, int width, int height, Color color) {
		thisEllipse = new Ellipse2D.Double((double)x, (double)y, (double)width, (double)height);
		this.color = color;
	}
	public Color getColor() {
		return color;
	}
	public void paint(Graphics2D g2d) {
		g2d.setColor(getColor());
		g2d.fill(thisEllipse);
		
	}
	
	

	
}
