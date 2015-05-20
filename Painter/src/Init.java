import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;


public class Init {
	static Color [] [] signature;
	
	public static void main(String [] args) {

		
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("image/pic4.jpg"));//add picture here!!
		   
		} catch (IOException e) {
			System.out.println("Picture not found!");
		}
		signature = ImageComparator.getSignature((RenderedImage)img);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				OuterContainer oc = new OuterContainer();
			}
		});

	}

	

	
	
}
