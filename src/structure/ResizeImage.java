package structure;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ResizeImage {
	
	public static BufferedImage resize(File file, int size) throws IOException {
		BufferedImage originalImage = ImageIO.read(file);
	    
	    int dstWidth = originalImage.getWidth() / size;
	    int dstHeight = originalImage.getHeight() / size;
	    
	    BufferedImage resizedImage = new BufferedImage(dstWidth, dstHeight, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = resizedImage.createGraphics();
	    g.drawImage(originalImage, 0, 0, dstWidth, dstHeight, null);
	    g.dispose();
		
	    return resizedImage;
	}
	
}
