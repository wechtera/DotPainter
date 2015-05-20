import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.iterator.RandomIter;
import javax.media.jai.iterator.RandomIterFactory;
import javax.media.jai.InterpolationNearest;

/**
 * No image comparing tools exist in java I could find so used Java image
 * Processing Cookbook solutiono presented by Rafael Santos.  Tweeked as need be
 * Website: http://www.lac.inpe.br/JIPCookbook/6050-howto-compareimages.jsp#howtohowdoicomparetwoimagestoseeiftheyareequalaverysimplealgorithmtocompareimagesforipossibleisimilaritywithoutconsideringthehighlevelcontents
 *
 */
public class ImageComparator {
	
	private static final int baseSize = 600;  //TODO: add options for this
	
	
	public static double calcDistance(RenderedImage other) {
		Color [] [] signatureOther = getSignature(other);
		
		
		
		
		
		double dist = 0;
		for(int i = 0; i<5; i++)
			for(int j = 0; j<5; j++) {
				int r1 = Init.signature[i][j].getRed();
				int g1 = Init.signature[i][j].getGreen();
				int b1 = Init.signature[i][j].getBlue();
				int r2 = signatureOther[i][j].getRed();
				int g2 = signatureOther[i][j].getGreen();
				int b2 = signatureOther[i][j].getBlue();
				
				double tempDist = Math.sqrt((r1-r2) * (r1-r2) + (g1-g2) * (g1-g2) + (b1-b2) * (b1-b2));
				dist+= tempDist;
			}
		return dist;
		
	}
	
	public static Color[][] getSignature(RenderedImage pic) {
		//rescale
		pic = rescale(pic);
		
		Color[][] sig = new Color[5][5];
		float[] propr = new float[] {1f / 10f, 3f / 10f, 5f / 10f, 7f / 10f, 9f / 10f};
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				sig[i][j] = averageAround(pic, propr[i], propr[j]);
			}
		}
		return sig;
	}
	
	public static Color averageAround(RenderedImage pic, double px, double py) {
		//Itrator for pic
		RandomIter iterator = RandomIterFactory.create(pic,null);
		//decl
		double[] pixel = new double[3];
		double[] accum = new double[3];
		
		int sampleSize = 15;
		int numPixels = 0;
		//sample 
		for(double i =px*baseSize-sampleSize; i < px * baseSize + sampleSize; i++) {
			for (double j = py * baseSize - sampleSize; j < py * baseSize + sampleSize; j++) {
		         iterator.getPixel((int) i, (int) j, pixel);
		         accum[0] += pixel[0];
		         accum[1] += pixel[1];
		         accum[2] += pixel[2];
		         numPixels++;
			}
		}
		//average
		accum[0] /= numPixels;
		accum[1] /= numPixels;
		accum[2] /= numPixels;
		
		return new Color((int) accum[0], (int) accum[1], (int) accum[2]);
	}
	
	public static RenderedImage rescale(RenderedImage pic) {
		float scaleW = ((float) baseSize) / pic.getWidth();
		float scaleH = ((float) baseSize) / pic.getHeight();
		
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(pic);
		pb.add(scaleW);
		pb.add(scaleH);
		pb.add(0.0F);
		pb.add(0.0F);
		pb.add(new InterpolationNearest());
		
		return JAI.create("scale", pb);
	}
	
	
	public static Long difference2(BufferedImage biO, BufferedImage bi) {
		bi = rescaleBI(bi);
		long diff = 0;
	    for (int y = 0; y < baseSize; y++) {
	      for (int x = 0; x < baseSize; x++) {
	        int rgb1 = biO.getRGB(x, y);
	        int rgb2 = bi.getRGB(x, y);
	        int r1 = (rgb1 >> 16) & 0xff;
	        int g1 = (rgb1 >>  8) & 0xff;
	        int b1 = (rgb1      ) & 0xff;
	        int r2 = (rgb2 >> 16) & 0xff;
	        int g2 = (rgb2 >>  8) & 0xff;
	        int b2 = (rgb2      ) & 0xff;
	        diff += Math.abs(r1 - r2);
	        diff += Math.abs(g1 - g2);
	        diff += Math.abs(b1 - b2);
	      }
	    }
	    return diff;
	}
	
	public static BufferedImage rescaleBI(BufferedImage pic) {
		float scaleW = ((float) baseSize) / pic.getWidth();
		float scaleH = ((float) baseSize) / pic.getHeight();
		
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(pic);
		pb.add(scaleW);
		pb.add(scaleH);
		pb.add(0.0F);
		pb.add(0.0F);
		pb.add(new InterpolationNearest());
		
		return JAI.create("scale", pb).getAsBufferedImage();
	}
	

}
