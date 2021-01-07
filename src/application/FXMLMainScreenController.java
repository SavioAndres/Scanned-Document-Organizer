package application;

import java.awt.Button;
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
	OpenDirectory openDirectory = new OpenDirectory();
	private int IndexImage = 0;

	@FXML
	private ImageView imageView;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@FXML
	private void openDirectory(ActionEvent event) {
		
		directory = openDirectory.open();
		imageView.setImage(openDirectory.openImage(IndexImage));
	}

	@FXML
	private void removeWhiteSheet(ActionEvent event) {

	}

	@FXML
	private void organize(ActionEvent event) {
		MoveFile moveFile = new MoveFile();
		moveFile.MoveFiles(directory);
	}
	
	@FXML
	private void nextImage(ActionEvent event) {
		IndexImage++;
		imageView.setImage(openDirectory.openImage(IndexImage));
	}
	
	@FXML
	private void previousImage(ActionEvent event) {
		if (IndexImage > 0) {
			IndexImage--;
			imageView.setImage(openDirectory.openImage(IndexImage));
		}
	}

}
