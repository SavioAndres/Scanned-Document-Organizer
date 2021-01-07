package organize_files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
	
	public Image openImage(int index) {
		FileInputStream input = null;
		try {
			input = new FileInputStream(directory.listFiles()[index]);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image image = new Image(input);
		return image;
	}
	
}
