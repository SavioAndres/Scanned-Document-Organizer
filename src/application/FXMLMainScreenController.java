package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import organize_files.DocumentType;
import organize_files.ExtractText;
import organize_files.MoveFile;
import organize_files.OpenDirectory;
import organize_files.RemoveWhiteSheet;
import organize_files.SeparateBlackSheet;

public class FXMLMainScreenController implements Initializable {

	public static File directory;
	public static ArrayList<File> fileImages;
	OpenDirectory openDirectory = new OpenDirectory();
	private int indexImage = 0;

	@FXML
	private MenuBar menu;
	@FXML
	private SplitPane background;
	@FXML
	private ImageView imageView;
	@FXML
	private Hyperlink folderName;
	@FXML
	private Hyperlink imageName;
	@FXML
	private Label numberPage;
	@FXML
	private Label totalPages;
	@FXML
	private DatePicker date;
	@FXML
	private TextField portariaEdoc;
	@FXML
	private TextField comuInt;
	@FXML
	private Button firstPage;
	@FXML
	private Button lastPage;
	@FXML
	private Button previousImage;
	@FXML
	private Button nextImage;
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
	@FXML
	private TextField portariaPage;
	@FXML
	private CheckMenuItem autoDetection;
	@FXML
	private ImageView imageLoad;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		// background.setVisible(false);
		// menu.setVisible(false);
		slide();
		maskDate();
		// disableComponents(true);
		background.setDisable(true);
		// maskProtocolo();
		portariaEdoc.setText("");
		typeDoc.getItems().setAll(DocumentType.types());
		// firstPage.setDisable(true);
		// previousImage.setDisable(true);
		try {
		FileInputStream input = new FileInputStream(new File("C:\\Users\\savio\\Desktop\\pikachu.gif"));
		Image image = new Image(input);
		
			input.close();
			imageLoad.setImage(image);
			imageLoad.setVisible(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@FXML
	private void openDirectory(ActionEvent event) {
		
		try {
			directory = openDirectory.open();
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		// RemoveWhiteSheet.start();
		imageView.setFitWidth(668);
		imageLoad.setVisible(true);
		new Thread(load).start();
		imageLoad.setVisible(false);
	}
	
	private Runnable load = new Runnable() {
		public void run() {
			try {
				fileImages = SeparateBlackSheet.files();
				totalPages.setText(fileImages.size() + "");
				autocomplete();
				background.setDisable(false);
				date.requestFocus();
				Main.stage.setTitle("Organizador de documentos digitalizados - " + directory.getAbsolutePath());
				imageView.setImage(openDirectory.image(indexImage));
				folderName.setText(directory.getName());
				imageName.setText(fileImages.get(0).getName());
				numberPage.setText((indexImage + 1) + "");
				btnOpenFolder.setVisible(false);
				menu.setVisible(true);
				background.setVisible(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	@FXML
	private void organize(ActionEvent event) {
		if (date.getValue() == null || typeDoc.getValue().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Atenção");
			alert.setHeaderText("A data e o tipo do documento não podem está em branco!");
			alert.setContentText("Por favor, preencha para continuar.");
			alert.showAndWait();
		} else {
		
			MoveFile moveFile = new MoveFile();
	
			if (!DocumentType.subTypeDisable) {
				moveFile.setData(date.getValue(), portariaEdoc.getText(), comuInt.getText(), typeDoc.getValue() + " " + subTypeDoc.getValue(),
						portariaPage.getText());
			} else {
				moveFile.setData(date.getValue(), portariaEdoc.getText(), comuInt.getText(), typeDoc.getValue(), portariaPage.getText());
			}
			moveFile.MoveFiles(fileImages);
			try {
				fileImages = SeparateBlackSheet.files();
				indexImage = 0;
				imageView.setImage(openDirectory.image(indexImage));
				imageName.setText(fileImages.get(0).getName());
				date.setValue(null);
				portariaEdoc.setText("");
				typeDoc.setValue("");
				subTypeDoc.setValue("");
				portariaPage.setText("");
				autocomplete();
	
				firstPage.setDisable(true);
				previousImage.setDisable(true);
				lastPage.setDisable(false);
				nextImage.setDisable(false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void showExtractedText(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		if (ExtractText.getOriginalText().isEmpty())
			alert.setContentText("Não há texto extraído!");
		else
			alert.setContentText(ExtractText.getOriginalText());

		alert.showAndWait();
	}

	@FXML
	private void firstPage(ActionEvent event) throws FileNotFoundException, IOException {
		indexImage = 0;
		imageView.setImage(openDirectory.image(indexImage));
		nextImage.setDisable(false);
		previousImage.setDisable(true);
		imageName.setText(fileImages.get(indexImage).getName());
		numberPage.setText((indexImage + 1) + "");
		firstPage.setDisable(true);
		lastPage.setDisable(false);
	}

	@FXML
	private void lastPage(ActionEvent event) throws FileNotFoundException, IOException {
		indexImage = fileImages.size() - 1;
		imageView.setImage(openDirectory.image(indexImage));
		nextImage.setDisable(true);
		previousImage.setDisable(false);
		imageName.setText(fileImages.get(indexImage).getName());
		numberPage.setText((indexImage + 1) + "");
		firstPage.setDisable(false);
		lastPage.setDisable(true);
	}

	@FXML
	private void nextImage(ActionEvent event) throws FileNotFoundException, IOException {
		if (indexImage < fileImages.size() - 1) {
			indexImage++;
			imageView.setImage(openDirectory.image(indexImage));
			previousImage.setDisable(false);
			firstPage.setDisable(false);
			imageName.setText(fileImages.get(indexImage).getName());
			numberPage.setText((indexImage + 1) + "");
			if (indexImage == fileImages.size() - 1) {
				nextImage.setDisable(true);
				lastPage.setDisable(true);
			}
		} else {
			nextImage.setDisable(true);
			lastPage.setDisable(true);
		}
	}

	@FXML
	private void previousImage(ActionEvent event) throws FileNotFoundException, IOException {
		if (indexImage > 0) {
			indexImage--;
			imageView.setImage(openDirectory.image(indexImage));
			nextImage.setDisable(false);
			lastPage.setDisable(false);
			imageName.setText(fileImages.get(indexImage).getName());
			numberPage.setText((indexImage + 1) + "");
			if (indexImage == 0) {
				previousImage.setDisable(true);
				firstPage.setDisable(true);
			}
		} else {
			previousImage.setDisable(true);
			firstPage.setDisable(true);
		}
	}

	private void slide() {
		zoom.setMin(410);
		zoom.setMax(1200);
		zoom.setValue(668);
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
		OpenDirectory.openWindows(fileImages.get(indexImage));
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

	private void maskDate() {
		TextField dateEditor = date.getEditor();

		dateEditor.addEventHandler(KeyEvent.KEY_TYPED, event -> {
			Platform.runLater(() -> {
				String textUntilHere = dateEditor.getText(0, dateEditor.getCaretPosition());
				if (textUntilHere.matches("\\d\\d") || textUntilHere.matches("\\d\\d/\\d\\d")) {
					String textAfterHere = "";
					try {
						textAfterHere = dateEditor.getText(dateEditor.getCaretPosition() + 1,
								dateEditor.getText().length());
					} catch (Exception ignored) {
					}
					int caretPosition = dateEditor.getCaretPosition();
					dateEditor.setText(textUntilHere + "/" + textAfterHere);
					dateEditor.positionCaret(caretPosition + 1);
				}
			});
		});
	}

	private void maskProtocolo() {
		// TextField dateEditor = portariaEdoc.getEditor();

		portariaEdoc.addEventHandler(KeyEvent.KEY_TYPED, event -> {
			Platform.runLater(() -> {
				String textUntilHere = portariaEdoc.getText(0, portariaEdoc.getCaretPosition());
				if (textUntilHere.matches("\\d{3}\\.\\d{3}\\.\\d{5}/\\d{4}")) {
					String textAfterHere = "";
					try {
						textAfterHere = portariaEdoc.getText(portariaEdoc.getCaretPosition() + 1,
								portariaEdoc.getText().length());
					} catch (Exception ignored) {
					}
					int caretPosition = portariaEdoc.getCaretPosition();
					portariaEdoc.setText(textUntilHere + "-" + textAfterHere);
					portariaEdoc.positionCaret(caretPosition + 1);
				}
			});
		});
	}

	private void autocomplete() throws IOException {
		if (autoDetection.isSelected()) {
			ExtractText.readImage(fileImages.get(0));
			System.out.println("tamanho: " + fileImages.size() + " - List: " + fileImages.toString());
			System.out.println(ExtractText.getText());
			portariaEdoc.setText(ExtractText.getPortaria());
			System.out.println(">>> " + ExtractText.getPortaria());
			typeDoc.setValue(ExtractText.getTypeDoc());
			System.out.println(">>>> " + ExtractText.getTypeDoc());
			date.setValue(ExtractText.getDate());
			System.out.println(">>>>> " + ExtractText.getDate());
		}
	}

}
