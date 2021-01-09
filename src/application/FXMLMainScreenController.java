package application;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import organize_files.MoveFile;
import organize_files.OpenDirectory;
import organize_files.RemoveWhiteSheet;

public class FXMLMainScreenController implements Initializable {

	public static File directory;
	OpenDirectory openDirectory = new OpenDirectory();
	private int indexImage = 0;
	int zoomq = 300;

	@FXML
	private Pane backgroundPrimary;
	@FXML
	private SplitPane background;
	@FXML
	private ImageView imageView;
	@FXML
	private Label folderName;
	@FXML
	private Label imageName;
	@FXML
	private Button previousImage;
	@FXML
	private Button btnOpenFolder;
	@FXML
	private Button btnRemoveWhite;
	@FXML
	private Label removalResponse;
	@FXML
	private Slider zoom;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		background.setVisible(false);
		btnRemoveWhite.setVisible(false);
		removalResponse.setVisible(false);
		imageView.setFitWidth(550);
		slide();
	}

	@FXML
	private void openDirectory(ActionEvent event) {
		try {
			directory = openDirectory.open();
			Main.stage.setTitle("Organizador de documentos digitalizados - " + directory.getAbsolutePath());
			imageView.setImage(openDirectory.openImage(indexImage));
			folderName.setText(directory.getName());
			setImageNameLabel(0);
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
		try {
			rws.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		indexImage++;
		imageView.setImage(openDirectory.openImage(indexImage));
		previousImage.setDisable(false);
		setImageNameLabel(indexImage);
	}
	
	@FXML
	private void previousImage(ActionEvent event) {
		if (indexImage > 0) {
			indexImage--;
			imageView.setImage(openDirectory.openImage(indexImage));
			setImageNameLabel(indexImage);
		} else {
			previousImage.setDisable(true);
		}
	}
	
	private void slide() {
		zoom.setMin(300);
		zoom.setMax(1200);
		zoom.setValue(550);

		zoom.setShowTickLabels(true);
		zoom.setShowTickMarks(true);

		zoom.setBlockIncrement(10);

		zoom.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				imageView.setFitWidth(newValue.doubleValue());
				
			}
		});
	}
	
	private void setImageNameLabel(int indexImage) {
		imageName.setText(directory.listFiles()[indexImage].getName());
	}

	@FXML
	private void link(ActionEvent event) {
		try {
			Desktop.getDesktop().open(directory.listFiles()[indexImage]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
