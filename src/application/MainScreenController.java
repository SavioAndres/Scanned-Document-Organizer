package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ResourceBundle;
import auto_detect_data.BackgroundSystem;
import auto_detect_data.DocumentInformation;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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
	private String firstPageName;

	@FXML
	private CheckMenuItem menu_autoDetection;
	@FXML
	private ImageView imageView;
	@FXML
	private Hyperlink hl_folderName;
	@FXML
	private Hyperlink hl_imageName;
	@FXML
	private Label lb_numberPage;
	@FXML
	private Label lb_totalPages;
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
	private Slider slider_zoom;
	@FXML
	private ComboBox<String> cb_typeDoc;
	@FXML
	private ComboBox<String> cb_subTypeDoc;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		slide();
		maskDate();

		btn_organize.setDisable(true);
		btn_firstPage.setDisable(true);
		btn_previousImage.setDisable(true);
		btn_lastPage.setDisable(true);
		btn_nextImage.setDisable(true);
		tf_protocoloEdoc.setText("");
		cb_typeDoc.getItems().setAll(DocumentType.types());
	}

	@FXML
	private void openDirectory(ActionEvent event) {
		imageView.setFitWidth(668);
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
			alert.setHeaderText("A data e o tipo do documento não podem está em branco!");
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
			indexImage = 0;
			firstPageName = fileImages.get(0).getName();
			imageView.setImage(OpenDirectory.image(fileImages.get(0)));

			btn_organize.setDisable(false);
			btn_firstPage.setDisable(true);
			btn_previousImage.setDisable(true);
			btn_lastPage.setDisable(false);
			btn_nextImage.setDisable(false);

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

		// alert.setContentText("Não há texto extraído!");

		alert.setContentText(dataInfo.get(firstPageName).getOriginalText());

		alert.showAndWait();
	}

	@FXML
	private void firstPage(ActionEvent event) throws FileNotFoundException, IOException {
		indexImage = 0;
		imageView.setImage(OpenDirectory.image(fileImages.get(indexImage)));
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
		imageView.setImage(OpenDirectory.image(fileImages.get(indexImage)));
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
			imageView.setImage(OpenDirectory.image(fileImages.get(indexImage)));
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
			imageView.setImage(OpenDirectory.image(fileImages.get(indexImage)));
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
		slider_zoom.setValue(668);
		slider_zoom.setShowTickLabels(true);
		slider_zoom.setShowTickMarks(true);
		slider_zoom.setBlockIncrement(10);

		slider_zoom.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
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
		cb_subTypeDoc.setValue("");
		DocumentType.subTypes(cb_typeDoc.getValue());
		cb_subTypeDoc.setDisable(DocumentType.subTypeDisable);

		if (!DocumentType.subTypeDisable) {
			cb_subTypeDoc.setValue(DocumentType.valueType);
			cb_subTypeDoc.getItems().setAll(DocumentType.itens);
		}
	}

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
		if (menu_autoDetection.isSelected()) {
			DocumentInformation docInfo = dataInfo.get(firstPageName);

			dp_date.setValue(docInfo.getData());
			tf_protocoloEdoc.setText(docInfo.getProtocolo());
			cb_typeDoc.setValue(docInfo.getTipoDocumento());
		}
	}

}
