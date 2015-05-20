import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;


public class ContPane extends JPanel {
	Timer timer;
	Random r;
	ArrayList<MyCircles> ovals;
	double previous;
	long previousL;
	static BufferedImage shrunk;
	
	public ContPane() {
		super();
		previous = 9999999;
		previousL = 922337203;
		shrunk = null;
		ovals = new ArrayList<MyCircles>();
		setPreferredSize(new Dimension(600,600));
		this.setBackground(Color.white);
		timer = new Timer(10, new ActionListener() {
			public void  actionPerformed(ActionEvent e) {
				
				
				repaint();
			
//		    	double current = ImageComparator.calcDistance((RenderedImage)createImage());
//
//		    	//debugging script
//		    	System.out.println("Current: " + current);
//		    	System.out.println("Previous" + previous);
//
//
//		    	if(current > previous) {
//		    		ovals.remove(ovals.size()-1);
//		    		ovals.remove(ovals.size()-2);
//		    	}
//		    	else {
//		    		previous = current;
//		    	}
				
				long currentL = 922337203;
				try {
					currentL = ImageComparator.difference2(ImageIO.read(new File("image/pic4.jpg")), createImage());
				} catch (IOException e1) {
				      e1.printStackTrace();
				}
				if(currentL > previousL) {
		    		ovals.remove(ovals.size()-1);
		    		ovals.remove(ovals.size()-2);
		    	}
		    	else {
		    		previousL = currentL;
		    	}
				System.out.println(currentL);
			
			}	
		});
		
		timer.start();
		
	}
	

	@Override
	protected void paintComponent(Graphics g) {

	    
	    r = new Random(System.nanoTime());
	    int maxSize = 100;
	    int randX = r.nextInt(getWidth());
	    int randY = r.nextInt(getHeight());
	    int randWidth = r.nextInt(maxSize);
	    int randHeight = r.nextInt(maxSize);
	    Color color = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
	    Graphics2D g2d = (Graphics2D) g;
	    ovals.add(new MyCircles(randX, randY, randWidth, randHeight, color));   
	    for (MyCircles c : ovals) {
	            c.paint(g2d);
	    }
	    
	}
	
	public BufferedImage createImage() {
	    int w = getWidth();
	    int h = getHeight();
	    BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    paint(bi.getGraphics());



	    return bi;
	}

}
