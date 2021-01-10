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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
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
	private Hyperlink folderName;
	@FXML
	private Hyperlink imageName;
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
	@FXML
	private ComboBox<String> typeDoc;
	@FXML
	private ComboBox<String> subTypeDoc;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		background.setVisible(false);
		background.setPrefWidth(Region.USE_COMPUTED_SIZE);
		background.setMaxWidth(Region.USE_COMPUTED_SIZE);
		btnRemoveWhite.setVisible(false);
		removalResponse.setVisible(false);
		imageView.setFitWidth(550);
		slide();
		subTypeDoc.setOnAction(e -> {
			System.out.println("kkkkkkk" + e);
		});
		typeDoc.getItems().setAll(
			"Abono", 
			"Afastamento", 
			"Aposentadoria", 
			"Averba��o", 
			"Concess�o", 
			"Di�rias",
			"F�rias", 
			"Gozo", 
			"Indeniza��o", 
			"Licen�a", 
			"Majora��o", 
			"Outros",
			"Portaria", 
			"Progress�o"
		);
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
	private void linkFolderName(ActionEvent event) {
		openWindows(directory);
	}
	
	@FXML
	private void linkImageName(ActionEvent event) {
		openWindows(directory.listFiles()[indexImage]);
	}
	
	@FXML
	private void typeDoc(ActionEvent event) {
		subTypeDoc.setDisable(false);
		subTypeDoc.setValue("");
		switch (typeDoc.getValue()) {
		case "Abono":
			subTypeDoc.setValue("de Perman�ncia");
			subTypeDoc.getItems().setAll("de Faltas", "de Perman�ncia");
			break;
		case "Afastamento":
			subTypeDoc.setValue("do Cargo");
			subTypeDoc.getItems().setAll("do Cargo", "para Concorrer ao Pleito Eleitoral", "para Mandato Sindical", "sem Justificativa");
			break;
		case "Aposentadoria":
			subTypeDoc.setValue("por Invalidez");
			subTypeDoc.getItems().setAll("por Invalidez");
			break;
		case "Averba��o":
			subTypeDoc.setValue("de Tempo de Servi�o");
			subTypeDoc.getItems().setAll("de Tempo de Servi�o");
			break;
		case "Concess�o":
			subTypeDoc.setValue("de Licen�a-Pr�mio");
			subTypeDoc.getItems().setAll("de Finate", "de Licen�a-Pr�mio");
			break;
		case "Di�rias":
			subTypeDoc.setDisable(true);
			break;
		case "F�rias":
			subTypeDoc.setDisable(true);
			break;
		case "Gozo":
			subTypeDoc.setValue("de Licen�a-Pr�mio");
			subTypeDoc.getItems().setAll("de Licen�a-Pr�mio");
			break;
		case "Indeniza��o":
			subTypeDoc.getItems().setAll("de Licen�a-Pr�mio", "de F�rias e 13 Sal�rio", "Outras");
			break;
		case "Licen�a":
			subTypeDoc.setValue("M�dica");
			subTypeDoc.getItems().setAll("Ado��o", "para Acompanhamento do Conjuge", "M�dica", "para Acompanhar pessoa da fam�la", "para Exerc�cio de Mandato Eletivo", "para Trato de Interesse Particular", "Paternidade");
			break;
		case "Majora��o":
			subTypeDoc.setValue("de Licen�a-Pr�mio");
			subTypeDoc.getItems().setAll("de Licen�a-Pr�mio");
			break;
		case "Outros":
			subTypeDoc.setDisable(true);
			break;
		case "Portaria":
			subTypeDoc.setValue("Concess�o de Licen�a-Pr�mio");
			subTypeDoc.getItems().setAll("Concess�o de Licen�a-Pr�mio", "Concess�o de Licen�a M�dica", "Cumprimento", "Designa��o", "Lotar", "Remo��o", "Outras", "Dispensa");
			break;
		case "Progress�o":
			subTypeDoc.setValue("por Titula��o");
			subTypeDoc.getItems().setAll("por Titula��o");
			break;
		default:
			break;
		}
	}
	
	private void openWindows(File path) {
		try {
			Desktop.getDesktop().open(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
