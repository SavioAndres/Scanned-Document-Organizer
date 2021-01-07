package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import organize_files.MoveFile;
import organize_files.OpenDirectory;

public class FXMLMainScreenController implements Initializable {

	public File directory;

	@FXML
	private ImageView imageView;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@FXML
	private void openDirectory(ActionEvent event) {
		OpenDirectory openDirectory = new OpenDirectory();
		directory = openDirectory.open();
	}

	@FXML
	private void removeWhiteSheet(ActionEvent event) {

	}

	@FXML
	private void organize(ActionEvent event) {
		MoveFile moveFile = new MoveFile();
		moveFile.MoveFiles(directory);
	}

}
