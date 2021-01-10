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
			"Averbação", 
			"Concessão", 
			"Diárias",
			"Férias", 
			"Gozo", 
			"Indenização", 
			"Licença", 
			"Majoração", 
			"Outros",
			"Portaria", 
			"Progressão"
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
			subTypeDoc.setValue("de Permanência");
			subTypeDoc.getItems().setAll("de Faltas", "de Permanência");
			break;
		case "Afastamento":
			subTypeDoc.setValue("do Cargo");
			subTypeDoc.getItems().setAll("do Cargo", "para Concorrer ao Pleito Eleitoral", "para Mandato Sindical", "sem Justificativa");
			break;
		case "Aposentadoria":
			subTypeDoc.setValue("por Invalidez");
			subTypeDoc.getItems().setAll("por Invalidez");
			break;
		case "Averbação":
			subTypeDoc.setValue("de Tempo de Serviço");
			subTypeDoc.getItems().setAll("de Tempo de Serviço");
			break;
		case "Concessão":
			subTypeDoc.setValue("de Licença-Prêmio");
			subTypeDoc.getItems().setAll("de Finate", "de Licença-Prêmio");
			break;
		case "Diárias":
			subTypeDoc.setDisable(true);
			break;
		case "Férias":
			subTypeDoc.setDisable(true);
			break;
		case "Gozo":
			subTypeDoc.setValue("de Licença-Prêmio");
			subTypeDoc.getItems().setAll("de Licença-Prêmio");
			break;
		case "Indenização":
			subTypeDoc.getItems().setAll("de Licença-Prêmio", "de Férias e 13 Salário", "Outras");
			break;
		case "Licença":
			subTypeDoc.setValue("Médica");
			subTypeDoc.getItems().setAll("Adoção", "para Acompanhamento do Conjuge", "Médica", "para Acompanhar pessoa da famíla", "para Exercício de Mandato Eletivo", "para Trato de Interesse Particular", "Paternidade");
			break;
		case "Majoração":
			subTypeDoc.setValue("de Licença-Prêmio");
			subTypeDoc.getItems().setAll("de Licença-Prêmio");
			break;
		case "Outros":
			subTypeDoc.setDisable(true);
			break;
		case "Portaria":
			subTypeDoc.setValue("Concessão de Licença-Prêmio");
			subTypeDoc.getItems().setAll("Concessão de Licença-Prêmio", "Concessão de Licença Médica", "Cumprimento", "Designação", "Lotar", "Remoção", "Outras", "Dispensa");
			break;
		case "Progressão":
			subTypeDoc.setValue("por Titulação");
			subTypeDoc.getItems().setAll("por Titulação");
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
