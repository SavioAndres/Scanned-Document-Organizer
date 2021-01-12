package organize_files;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import application.FXMLMainScreenController;
import application.Main;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;

public class OpenDirectory {
	
	private File directory;
	
	public File open() {
		DirectoryChooser chooser = new DirectoryChooser();
		//chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png"));
		directory = chooser.showDialog(Main.stage);
		return directory;
	}
	
	public Image image(int index) throws FileNotFoundException, IOException {
		FileInputStream input = new FileInputStream(FXMLMainScreenController.fileImages.get(index));
		Image image = new Image(input);
		input.close();
		
		return image;
	}
	
	public static void openWindows(File path) {
		try {
			Desktop.getDesktop().open(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
