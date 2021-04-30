package application;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Optional;
import java.util.ResourceBundle;
import auto_detect_data.BackgroundSystem;
import auto_detect_data.DocumentInformation;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import main.Main;
import organize_files.DocumentType;
import organize_files.MoveFile;
import organize_files.OpenDirectory;
import organize_files.SeparateBlackSheet;

public class MainScreenController implements Initializable {

	public static File directory;
	public static ArrayList<File> fileImages;
	public static Hashtable<String, DocumentInformation> dataInfo;
	private int indexImage = 0;
	private double zoomValue = 668;
	private String firstPageName;
	private boolean fullScreen = false;

	@FXML
	private CheckMenuItem menu_autoDetection;
	@FXML
	private ImageView image_view;
	@FXML
	private Hyperlink hl_folderName;
	@FXML
	private Hyperlink hl_imageName;
	@FXML
	private Label lb_numberPage;
	@FXML
	private Label lb_totalPages;
	@FXML
	private ImageView image_penultimate;
	@FXML
	private ImageView image_last;
	@FXML
	private Label lb_last;
	@FXML
	private Label lb_penultimate;
	@FXML
	private DatePicker dp_date;
	@FXML
	private TextField tf_protocoloEdoc;
	@FXML
	private TextField tf_comuInt;
	@FXML
	private TextField tf_portariaPage;
	@FXML
	private Button btn_firstPage;
	@FXML
	private Button btn_lastPage;
	@FXML
	private Button btn_previousImage;
	@FXML
	private Button btn_nextImage;
	@FXML
	private Button btn_organize;
	@FXML
	private Button btn_isWhite;
	@FXML
	private Slider slider_zoom;
	@FXML
	private ComboBox<String> cb_typeDoc;
	@FXML
	private ComboBox<String> cb_subTypeDoc;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		slide();
		maskDate();

		// btn_organize.setDisable(true);
		btn_firstPage.setDisable(true);
		btn_previousImage.setDisable(true);
		btn_lastPage.setDisable(true);
		btn_nextImage.setDisable(true);
		btn_isWhite.setDisable(true);
		tf_protocoloEdoc.setText("");
		cb_typeDoc.getItems().setAll(DocumentType.types());
	}

	@FXML
	private void openDirectory(ActionEvent event) {
		image_view.setFitWidth(668);
		try {
			directory = OpenDirectory.open();
			Main.stage.setTitle("Organizador de documentos digitalizados - " + directory.getAbsolutePath());
			startScreen();
			autocomplete();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@FXML
	private void organize(ActionEvent event) {
		if (dp_date.getValue() == null || cb_typeDoc.getValue().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Atenção");
			alert.setHeaderText("A data e o tipo do documento não podem estar em branco!");
			alert.setContentText("Por favor, preencha para continuar.");
			alert.showAndWait();
		} else {
			MoveFile moveFile = new MoveFile();

			if (!DocumentType.subTypeDisable) {
				moveFile.setData(dp_date.getValue(), tf_protocoloEdoc.getText(), tf_comuInt.getText(),
						cb_typeDoc.getValue() + " " + cb_subTypeDoc.getValue(), tf_portariaPage.getText());
			} else {
				moveFile.setData(dp_date.getValue(), tf_protocoloEdoc.getText(), tf_comuInt.getText(),
						cb_typeDoc.getValue(), tf_portariaPage.getText());
			}
			moveFile.MoveFiles(fileImages);

			startScreen();
			autoFill();
		}
	}

	private void startScreen() {
		try {
			fileImages = SeparateBlackSheet.files();
			if (fileImages != null) {
				indexImage = 0;
				firstPageName = fileImages.get(0).getName();
				image_view.setImage(OpenDirectory.image(fileImages.get(0)));
				image_penultimate.setImage(OpenDirectory.image(fileImages.get(fileImages.size() - 2)));
				image_last.setImage(OpenDirectory.image(fileImages.get(fileImages.size() - 1)));
				lb_penultimate.setText("Penúltima");
				lb_last.setText("Última");
				
				btn_organize.setDisable(false);
				btn_firstPage.setDisable(true);
				btn_previousImage.setDisable(true);
				btn_lastPage.setDisable(false);
				btn_nextImage.setDisable(false);
				btn_isWhite.setDisable(false);

				dp_date.requestFocus();
				lb_totalPages.setText(fileImages.size() + "");
				hl_folderName.setText(directory.getName());
				hl_imageName.setText(fileImages.get(0).getName());
				lb_numberPage.setText("1");
				dp_date.setValue(null);

				tf_protocoloEdoc.setText("");
				tf_comuInt.setText("");
				cb_typeDoc.setValue("");
				cb_subTypeDoc.setValue("");
				tf_portariaPage.setText("");
			} else {
				image_view.setImage(null);
				image_penultimate.setImage(null);
				image_last.setImage(null);
				lb_penultimate.setText("");
				lb_last.setText("");
				btn_organize.setDisable(true);
				btn_firstPage.setDisable(true);
				btn_previousImage.setDisable(true);
				btn_lastPage.setDisable(true);
				btn_nextImage.setDisable(true);
				btn_isWhite.setDisable(true);

				lb_totalPages.setText("...");
				hl_folderName.setText("...");
				hl_imageName.setText("...");
				lb_numberPage.setText("..");
				dp_date.setValue(null);

				tf_protocoloEdoc.setText("");
				tf_comuInt.setText("");
				cb_typeDoc.setValue("");
				cb_subTypeDoc.setValue("");
				tf_portariaPage.setText("");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void showExtractedText(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);

		try {
			alert.setContentText(dataInfo.get(firstPageName).getOriginalText());
			alert.showAndWait();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@FXML
	private void firstPage(ActionEvent event) throws FileNotFoundException, IOException {
		indexImage = 0;
		image_view.setImage(OpenDirectory.image(fileImages.get(indexImage)));
		btn_nextImage.setDisable(false);
		btn_previousImage.setDisable(true);
		hl_imageName.setText(fileImages.get(indexImage).getName());
		lb_numberPage.setText((indexImage + 1) + "");
		btn_firstPage.setDisable(true);
		btn_lastPage.setDisable(false);
	}

	@FXML
	private void lastPage(ActionEvent event) throws FileNotFoundException, IOException {
		indexImage = fileImages.size() - 1;
		image_view.setImage(OpenDirectory.image(fileImages.get(indexImage)));
		btn_nextImage.setDisable(true);
		btn_previousImage.setDisable(false);
		hl_imageName.setText(fileImages.get(indexImage).getName());
		lb_numberPage.setText((indexImage + 1) + "");
		btn_firstPage.setDisable(false);
		btn_lastPage.setDisable(true);
	}

	@FXML
	private void nextImage(ActionEvent event) throws FileNotFoundException, IOException {
		if (indexImage < fileImages.size() - 1) {
			indexImage++;
			image_view.setImage(OpenDirectory.image(fileImages.get(indexImage)));
			btn_previousImage.setDisable(false);
			btn_firstPage.setDisable(false);
			hl_imageName.setText(fileImages.get(indexImage).getName());
			lb_numberPage.setText((indexImage + 1) + "");
			if (indexImage == fileImages.size() - 1) {
				btn_nextImage.setDisable(true);
				btn_lastPage.setDisable(true);
			}
		} else {
			btn_nextImage.setDisable(true);
			btn_lastPage.setDisable(true);
		}
	}

	@FXML
	private void previousImage(ActionEvent event) throws FileNotFoundException, IOException {
		if (indexImage > 0) {
			indexImage--;
			image_view.setImage(OpenDirectory.image(fileImages.get(indexImage)));
			btn_nextImage.setDisable(false);
			btn_lastPage.setDisable(false);
			hl_imageName.setText(fileImages.get(indexImage).getName());
			lb_numberPage.setText((indexImage + 1) + "");
			if (indexImage == 0) {
				btn_previousImage.setDisable(true);
				btn_firstPage.setDisable(true);
			}
		} else {
			btn_previousImage.setDisable(true);
			btn_firstPage.setDisable(true);
		}
	}

	private void slide() {
		slider_zoom.setMin(410);
		slider_zoom.setMax(1200);
		slider_zoom.setValue(zoomValue);
		slider_zoom.setShowTickLabels(true);
		slider_zoom.setShowTickMarks(true);
		slider_zoom.setBlockIncrement(10);

		slider_zoom.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				zoomValue = newValue.doubleValue();
				image_view.setFitWidth(newValue.doubleValue());
			}
		});
	}
	
	@FXML
	private void rotateImage(ActionEvent event){
		image_view.setRotate(image_view.getRotate() + 90); 
	}
	
	@FXML
	private void whitePage(ActionEvent event) throws FileNotFoundException, IOException {
		SeparateBlackSheet.moveWhitePage(fileImages.get(indexImage), 0, new File(directory.getAbsolutePath() + "\\brancas"));
		fileImages.remove(indexImage);
		nextImage(event);
		lb_totalPages.setText(fileImages.size() + "");
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
		cb_subTypeDoc.setValue("");
		DocumentType.subTypes(cb_typeDoc.getValue());
		cb_subTypeDoc.setDisable(DocumentType.subTypeDisable);

		if (!DocumentType.subTypeDisable) {
			cb_subTypeDoc.setValue(DocumentType.valueType);
			cb_subTypeDoc.getItems().setAll(DocumentType.itens);
		}
	}

	/**
	 * Componentes MENU
	 */
	@FXML
	private void oldDocument(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmar");
		alert.setHeaderText("Documento antigo");
		alert.setContentText(
				"Clique em OK somente se esse documento for antigo e fizer parte da pasta arquivo.\nDeseja que esse documento seja movido para pasta arquivo?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			try {
				MoveFile moveFile = new MoveFile();

				moveFile.setDataOldFiles();
				moveFile.MoveFiles(fileImages);

				startScreen();
				autoFill();
			} catch (Exception e) {
				// TODO: handle exception
			}
		} else {
			System.out.println("nonon");
		}
	}

	@FXML
	private void openSourceCode(ActionEvent event) throws MalformedURLException, IOException, URISyntaxException {
		String URL = "https://github.com/SavioAndres/Scanned-Document-Organizer";
		Desktop.getDesktop().browse(new URL(URL).toURI());
	}
	
	@FXML
	private void about(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Sobre");
		alert.setHeaderText("Scanned Document Organizer v0.1.6");
		alert.setContentText("Software desenvolvido para facilitar a separação e "
				+ "organização dos documentos pessoais dos funcionários da "
				+ "Secretaria de Estado da Fazendo de Sergipe");
		alert.showAndWait();
	}

	@FXML
	private void fullScreen(ActionEvent event) {
		fullScreen = !fullScreen;
		Main.stage.setFullScreen(fullScreen);
	}

	@FXML
	private void close(ActionEvent event) {
		Main.stage.close();
	}
	
	@FXML //Zoom Scroll
	private void zoom(ScrollEvent event) {
		if (zoomValue <= 1200) {
			zoomValue = zoomValue + 30;
			slider_zoom.setValue(zoomValue);
		}
	}

	@FXML
	private void zoomIn(ActionEvent event) {
		if (zoomValue <= 1200) {
			zoomValue = zoomValue + 30;
			slider_zoom.setValue(zoomValue);
		}
	}

	@FXML
	private void zoomOut(ActionEvent event) {
		if (zoomValue > 410) {
			zoomValue = zoomValue - 30;
			slider_zoom.setValue(zoomValue);
		}
	}
	
	@FXML
	private void convertPDFtoJPG(ActionEvent event) throws IOException {
		Pane root = FXMLLoader.load(ConvertScreenController.class.getResource("ConvertScreen.fxml"));
		Scene scene = new Scene(root);
		
		Main.stage.setTitle("Organizador de documentos digitalizados");
		Main.stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
		Main.stage.setScene(scene);
		Main.stage.show();
	}

	/**
	 * Fim Componentes MENU
	 */

	private void maskDate() {
		TextField dateEditor = dp_date.getEditor();

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

				if (dateEditor.getText().length() > 9)
					tf_protocoloEdoc.requestFocus();
			});
		});
	}

	private void autocomplete() throws IOException {
		if (menu_autoDetection.isSelected()) {
			dataInfo = new Hashtable<String, DocumentInformation>();
			new Thread(new BackgroundSystem(directory)).start();
		}
	}

	private void autoFill() {
		if (menu_autoDetection.isSelected() && dataInfo.containsKey(firstPageName)) {
			DocumentInformation docInfo = dataInfo.get(firstPageName);

			dp_date.setValue(docInfo.getData());
			tf_protocoloEdoc.setText(docInfo.getProtocolo());
			cb_typeDoc.setValue(docInfo.getTipoDocumento());
		}
	}

}
