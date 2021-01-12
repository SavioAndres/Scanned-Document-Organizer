package organize_files;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import application.FXMLMainScreenController;

public class SeparateBlackSheet {

	public static ArrayList<File> files() throws IOException {
		File directory = FXMLMainScreenController.directory;
		File[] files = directory.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		    	System.out.println(dir);
		        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png");
		    }
		});
		
		// Sort files by name
		Arrays.sort(files, new Comparator<File>() {
		    public int compare(File f1, File f2) {
		        try {
		        	String fName1=f1.getName().substring(0,f1.getName().lastIndexOf("."));
		        	String fName2=f2.getName().substring(0,f2.getName().lastIndexOf("."));
		        	
		            int i1 = Integer.parseInt(fName1);
		            int i2 = Integer.parseInt(fName2);
		            return i1 - i2;
		        } catch(NumberFormatException e) {
		            throw new AssertionError(e);
		        }
		    }
		});
		
		//Arrays.sort(files, Comparator.comparingInt());
		//Arrays.sort(files, Comparator.comparingLong(File::lastModified));
		//Arrays.sort(files, (f1, f2) -> f1.compareTo(f2));
		//Arrays.sort(files);
		//Arrays.sort(files, new AlphanumComparator());
		
		int gg = 0;
		for (int i = 0; i < files.length; i++) {
			System.out.print(files[i].getName() + ", ");
			gg++;
		}
		System.out.println(gg + "");
		
		ArrayList<File> selectedFiles = new ArrayList<File>();
		BufferedImage img = null;
		
		int size = files.length;
		for (int i = 0; i < size; i++) {
			if (files[i].isFile()) {
				img = ImageIO.read(files[i]);
				if (!isBlackPage(img)) {
					
					selectedFiles.add(files[i]);
				} else {
					selectedFiles.add(files[i]);
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
		System.out.println("Nível: " + sum);
		return sum < 0.001;
	}
	
}
