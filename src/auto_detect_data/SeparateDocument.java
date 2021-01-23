package auto_detect_data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import structure.ResizeImage;

public class SeparateDocument {
	
	public ArrayList<File> files(File directory) {
		
		File[] files = directory.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		    	System.out.println(dir);
		        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png");
		    }
		});
		
		Arrays.sort(files, new Comparator<File>() {
		    public int compare(File f1, File f2) {
		        try {
		        	String fName1=f1.getName().substring(f1.getName().lastIndexOf("(") + 1, f1.getName().lastIndexOf(")"));
		        	String fName2=f2.getName().substring(f2.getName().lastIndexOf("(") + 1, f2.getName().lastIndexOf(")"));
		        	
		            int i1 = Integer.parseInt(fName1);
		            int i2 = Integer.parseInt(fName2);
		            return i1 - i2;
		        } catch(Exception e) {
		        	Alert alert = new Alert(AlertType.WARNING);
		        	alert.setTitle("Atenção!");
		        	alert.setHeaderText("Não é possível ler alguma imagem.");
		        	alert.setContentText("Verifique se o nome de todas as imagens têm parênteses de abertura e fechamento com número entre. Exemplo: (13)\nPara renomear as imagens nesse padrão basta: Ctrl + A, F2, digitar uma letra qualquer e enter.");

		        	alert.showAndWait();
		            throw new AssertionError(e);
		        }
		    }
		});
		
		ArrayList<File> selectedFiles = new ArrayList<File>();
		BufferedImage img = null;
		
		int size = files.length;
		try {
			for (int i = 0; i < size; i++) {
				if (files[i].isFile()) {
				    img = ResizeImage.resize(files[i], 3);
					
					if (isBlackPage(img) && files[i + 1].exists()) {
						selectedFiles.add(files[i + 1]);
					}
					
				} 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return selectedFiles;
	}
	
	private boolean isBlackPage(BufferedImage img) {
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
		
		return sum < 0.001;
	}
}
