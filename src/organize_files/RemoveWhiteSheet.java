package organize_files;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import application.FXMLMainScreenController;

public class RemoveWhiteSheet {
	
	public void start() throws IOException {
		File directory = FXMLMainScreenController.directory;
		File[] files = directory.listFiles();
		BufferedImage img = null;
		
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				img = ImageIO.read(files[i]);
				System.out.println(files[i].getName() + " : " + isWhitePage(img, 0.999));
				
			} 
		}
	}
	
	private boolean isWhitePage(BufferedImage img, double value) {
		double sum = 0;
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				int px = img.getRGB(x, y);

				int r = (px & 0x00ff0000) >>> 16;
				int b = (px & 0x0000ff00) >>> 8;
				int g = (px & 0x000000ff);

				double l = (r / 255.f + g / 255.f + b / 255.f) / 3.;

				sum += l;
			}
		}

		sum /= img.getWidth() * img.getHeight();

		return sum > value;
	}

}
