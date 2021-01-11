package organize_files;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import application.FXMLMainScreenController;

public class SeparateBlackSheet {

	public static ArrayList<File> files() throws IOException {
		File directory = FXMLMainScreenController.directory;
		File[] files = directory.listFiles();
		ArrayList<File> selectedFiles = new ArrayList<File>();
		BufferedImage img = null;
		
		int size = files.length;
		for (int i = 0; i < size; i++) {
			if (files[i].isFile()) {
				img = ImageIO.read(files[i]);
				if (!isBlackPage(img)) {
					selectedFiles.add(files[i]);
				} else {
					size = 0;
				}
				System.out.println(files[i].getName());
			} 
		}
		System.out.println(selectedFiles.size());
		return selectedFiles;
	}
	
	private static boolean isBlackPage(BufferedImage img) {
		double sum = 0;
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				int px = img.getRGB(x, y);

				int r = (px & 0xff) >>> 16;
				int b = (px & 0xff) >>> 8;
				int g = (px & 0xff);

				double l = (r / 255.f + g / 255.f + b / 255.f) / 3.;

				sum += l;
			}
		}

		sum /= img.getWidth() * img.getHeight();
		return sum < 0.001;
	}
	
}
