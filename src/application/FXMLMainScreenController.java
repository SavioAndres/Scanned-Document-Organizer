package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import organize_files.MoveFile;
import organize_files.OpenDirectory;
import organize_files.RemoveWhiteSheet;

public class FXMLMainScreenController implements Initializable {

	public static File directory;
	OpenDirectory openDirectory = new OpenDirectory();
	private int IndexImage = 0;

	@FXML
	private Pane backgroundPrimary;
	@FXML
	private SplitPane background;
	@FXML
	private ImageView imageView;
	@FXML
	private Label folderName;
	@FXML
	private Button previousImage;
	@FXML
	private Button btnOpenFolder;
	@FXML
	private Button btnRemoveWhite;
	@FXML
	private Label removalResponse;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		background.setVisible(false);
		btnRemoveWhite.setVisible(false);
		removalResponse.setVisible(false);
	}

	@FXML
	private void openDirectory(ActionEvent event) {
		try {
			directory = openDirectory.open();
			Main.stage.setTitle("Organizador de documentos digitalizados - " + directory.getAbsolutePath());
			imageView.setImage(openDirectory.openImage(IndexImage));
			folderName.setText(directory.getName());
			btnOpenFolder.setVisible(false);
			btnRemoveWhite.setVisible(true);
			removalResponse.setVisible(true);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@FXML
	private void removeWhiteSheet(ActionEvent event) {
		removalResponse.setText("Removendo...");
		RemoveWhiteSheet rws = new RemoveWhiteSheet();
		rws.start();
		btnRemoveWhite.setVisible(false);
		removalResponse.setVisible(false);
		background.setVisible(true);
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
		previousImage.setDisable(false);
	}
	
	@FXML
	private void previousImage(ActionEvent event) {
		if (IndexImage > 0) {
			IndexImage--;
			imageView.setImage(openDirectory.openImage(IndexImage));
		} else {
			previousImage.setDisable(true);
		}
	}

}
