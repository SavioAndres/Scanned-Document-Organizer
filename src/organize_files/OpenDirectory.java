package organize_files;

import java.io.File;

import application.Main;
import javafx.stage.DirectoryChooser;

public class OpenDirectory {
	
	public File open() {
		DirectoryChooser chooser = new DirectoryChooser();
		//chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png"));
		File directory = chooser.showDialog(Main.stage);
		
		return directory;
	}
	
	
}
