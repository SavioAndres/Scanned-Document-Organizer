package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import organize_files.DocumentType;
import organize_files.MoveFile;
import organize_files.OpenDirectory;
import organize_files.RemoveWhiteSheet;
import organize_files.SeparateBlackSheet;

public class FXMLMainScreenController implements Initializable {

	public static File directory;
	public static ArrayList<File> fileImages;
	OpenDirectory openDirectory = new OpenDirectory();
	private int indexImage = 0;
	int zoomq = 300;

	@FXML
	private MenuBar menu;
	@FXML
	private Pane backgroundPrimary;
	@FXML
	private SplitPane background;
	@FXML
	private ImageView imageView;
	@FXML
	private Hyperlink folderName;
	@FXML
	private Hyperlink imageName;
	@FXML
	private DatePicker date;
	@FXML
	private TextField portariaEdoc;
	@FXML
	private Button previousImage;
	@FXML
	private Button btnOpenFolder;
	@FXML
	private Label lbIntro;
	@FXML
	private Slider zoom;
	@FXML
	private ComboBox<String> typeDoc;
	@FXML
	private ComboBox<String> subTypeDoc;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		background.setVisible(false);
		menu.setVisible(false);
		imageView.setFitWidth(550);
		slide();
		typeDoc.getItems().setAll(DocumentType.types());
	}

	@FXML
	private void openDirectory(ActionEvent event) {
		try {
			directory = openDirectory.open();
			
			fileImages = SeparateBlackSheet.files();
			Main.stage.setTitle("Organizador de documentos digitalizados - " + directory.getAbsolutePath());
			imageView.setImage(openDirectory.image(indexImage));
			folderName.setText(directory.getName());
			imageName.setText(fileImages.get(0).getName());
			btnOpenFolder.setVisible(false);
			menu.setVisible(true);
			background.setVisible(true);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@FXML
	private void removeWhiteSheet(ActionEvent event) {
		RemoveWhiteSheet rws = new RemoveWhiteSheet();
		try {
			rws.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void organize(ActionEvent event) {
		MoveFile moveFile = new MoveFile();
		
		if (!DocumentType.subTypeDisable) {
			moveFile.setData(date.getValue().toString(), portariaEdoc.getText(), typeDoc.getValue() + " " + DocumentType.valueType);
		} else {
			moveFile.setData(date.getValue().toString(), portariaEdoc.getText(), typeDoc.getValue());
		}
		moveFile.MoveFiles(fileImages);
	}
	
	@FXML
	private void nextImage(ActionEvent event) throws FileNotFoundException, IOException {
		indexImage++;
		imageView.setImage(openDirectory.image(indexImage));
		previousImage.setDisable(false);
		imageName.setText(fileImages.get(indexImage).getName());
	}
	
	@FXML
	private void previousImage(ActionEvent event) throws FileNotFoundException, IOException {
		if (indexImage > 0) {
			indexImage--;
			imageView.setImage(openDirectory.image(indexImage));
			imageName.setText(fileImages.get(indexImage).getName());
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
	
	@FXML
	private void linkFolderName(ActionEvent event) {
		OpenDirectory.openWindows(directory);
	}
	
	@FXML
	private void linkImageName(ActionEvent event) {
		OpenDirectory.openWindows(directory.listFiles()[indexImage]);
	}
	
	@FXML
	private void typeDoc(ActionEvent event) {
		subTypeDoc.setValue("");
		DocumentType.subTypes(typeDoc.getValue());
		subTypeDoc.setDisable(DocumentType.subTypeDisable);
		
		if (!DocumentType.subTypeDisable) {
			subTypeDoc.setValue(DocumentType.valueType);
			subTypeDoc.getItems().setAll(DocumentType.itens);
		}
	}
	
}
