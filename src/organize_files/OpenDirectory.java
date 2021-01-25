package organize_files;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import main.Main;

public class OpenDirectory {

	public static File open() {
		DirectoryChooser chooser = new DirectoryChooser();
		File directory = chooser.showDialog(Main.stage);
		return directory;
	}

	public static Image image(File fileImage) throws FileNotFoundException, IOException {
		FileInputStream input = new FileInputStream(fileImage);
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
