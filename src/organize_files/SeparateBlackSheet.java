package organize_files;

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
import application.FXMLMainScreenController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import structure.ResizeImage;

public class SeparateBlackSheet {

	public static ArrayList<File> files() throws IOException {
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
		
		// Sort files by name
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
		        	alert.setHeaderText("Não é possível ler os arquivos.");
		        	alert.setContentText("Verifique se o nome de todas as imagens têm parênteses de abertura e fechamento com número entre. Exemplo: (12)\nPara renomear as imagens nesse padrão basta: Ctrl + A, F2, digitar uma letra qualquer e enter.");

		        	alert.showAndWait();
		            throw new AssertionError(e);
		        }
		    }
		});
		
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
			    img = ResizeImage.resize(files[i], 3);

				if (isBlackPage(img) == 0) {
					selectedFiles.add(files[i]);
				} else if (isBlackPage(img) == 1) {
					try {
						Timestamp timestamp = new Timestamp(System.currentTimeMillis());
						
						Files.move(Paths.get(directory.getAbsolutePath() + "\\" + files[i].getName()),
								Paths.get(newDirectory.getAbsolutePath() + "\\" + timestamp.getTime() + "." + files[i].getName().substring(files[i].getName().lastIndexOf(".") + 1)));
						System.out.println("Passou branca por aqui: " + files[i].getName());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					selectedFiles.add(files[i]);
					size = 0;
				}
				
				
				System.out.println(files[i].getName());
			} 
		}

		System.out.println(selectedFiles.size());
		
		long dif = System.currentTimeMillis() - tempoInicio;
		System.out.println("Tempo Total: "+(String.format("%02d segundos  e %02d milisegundos", dif/1000, dif%1000)));
		
		return selectedFiles;
	}
	
	private static int isBlackPage(BufferedImage img) {
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
		System.out.println("Nível: " + sum);
		
		if (sum > 0.999) {
			return 1;
		} else if (sum < 0.001) {
			return -1;
		} else {
			return 0;
		}
	}
	
}
