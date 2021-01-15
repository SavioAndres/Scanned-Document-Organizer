package organize_files;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import application.FXMLMainScreenController;

public class RemoveWhiteSheet {
	
	public static void start() throws IOException {
		long tempoInicio = System.currentTimeMillis();
		
		
		File directory = FXMLMainScreenController.directory;
		
		File[] files = directory.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		    	System.out.println(dir);
		        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png");
		    }
		});
		
		
		File newDirectory = new File(directory.getAbsolutePath() + "\\brancas");
		if (!newDirectory.exists())
			newDirectory.mkdir();
		
		BufferedImage img = null;
		
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				//img = ImageIO.read(files[i]);
				
				
				
			    BufferedImage originalImage = ImageIO.read(files[i]);
			    
			    int dstWidth = originalImage.getWidth() / 3;
			    int dstHeight = originalImage.getHeight() / 3;
			    
			    BufferedImage resizedImage = new BufferedImage(dstWidth, dstHeight, BufferedImage.TYPE_INT_ARGB);
			    Graphics2D g = resizedImage.createGraphics();
			    g.drawImage(originalImage, 0, 0, dstWidth, dstHeight, null);
			    g.dispose();
				
			    img = resizedImage;
			    
				
				
				
				System.out.println(files[i].getName() + " : " + isWhitePage(img));
				if (isWhitePage(img)) {
					try {
						Files.move(Paths.get(directory.getAbsolutePath() + "\\" + files[i].getName()),
								Paths.get(newDirectory.getAbsolutePath() + "\\" + i + "." + files[i].getName().substring(files[i].getName().lastIndexOf(".") + 1)));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} 
		}
		
		long dif = System.currentTimeMillis() - tempoInicio;
		System.out.println("Tempo Total: "+(String.format("%02d segundos  e %02d milisegundos", dif/1000, dif%1000)));
	}
	
	private static boolean isWhitePage(BufferedImage img) {
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
		System.out.println("nivel branca: " + sum);

		return sum > 0.999;
	}

}
