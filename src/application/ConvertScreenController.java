package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import main.Main;
import organize_files.DocumentType;

public class ConvertScreenController implements Initializable {
	
	private File fileInput;
	
	@FXML
	private ComboBox<String> cb_typeDoc;
	@FXML
	private ComboBox<String> cb_subTypeDoc;
	@FXML
	private Button btn_convert;
	@FXML
	private Label lb_feedback;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cb_typeDoc.getItems().setAll(DocumentType.types());
		cb_typeDoc.setDisable(true);
		cb_subTypeDoc.setDisable(true);
		btn_convert.setDisable(true);
	}
	
	@FXML
	private void openFile(ActionEvent event) {
		lb_feedback.setText("");
		btn_convert.setDisable(true);
		FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.pdf", "*.pdf"));
        fileInput = chooser.showOpenDialog(Main.stage);
        if (fileInput != null)
        	cb_typeDoc.setDisable(false);
	}
	
	@FXML
	private void typeDoc(ActionEvent event) {
		if (!cb_typeDoc.getValue().isEmpty())
			btn_convert.setDisable(false);
		
		cb_subTypeDoc.setValue("");
		DocumentType.subTypes(cb_typeDoc.getValue());
		cb_subTypeDoc.setDisable(DocumentType.subTypeDisable);

		if (!DocumentType.subTypeDisable) {
			cb_subTypeDoc.setValue(DocumentType.valueType);
			cb_subTypeDoc.getItems().setAll(DocumentType.itens);
		}
	}
	
	@FXML
	private void back(ActionEvent event) {
		Main.stage.setScene(Main.scene);
		Main.stage.show();
	}
	
	private String getNameType() {
		return cb_subTypeDoc.getValue().isEmpty() ? cb_typeDoc.getValue() : cb_typeDoc.getValue() + " " + cb_subTypeDoc.getValue();
	}
	
	@FXML
	private void saveFile(ActionEvent event) throws IOException {
		FileChooser chooser = new FileChooser();
		chooser.setInitialFileName(getNameType());
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.jpg", "*.jpg"));
        File fileOutput = chooser.showSaveDialog(Main.stage);

        if (fileOutput != null)
        	convert(fileInput, fileOutput);
	}
	
	
	private void convert(File pdfFilename, File fileOutput) throws IOException {
		lb_feedback.setText("Convertendo...");
		PDDocument document = PDDocument.load(pdfFilename);
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		PDPageTree pages = document.getPages();
		
		for (int i = 0; i < pages.getCount(); i++) {
			BufferedImage bim = pdfRenderer.renderImageWithDPI(i, 300, ImageType.GRAY);
			ImageIOUtil.writeImage(bim, createFileName(fileOutput, i), 300);
		}
		document.close();
		cb_typeDoc.setDisable(true);
		cb_subTypeDoc.setDisable(true);
		btn_convert.setDisable(true);
		lb_feedback.setText("Convertido");
	}
	
	private String createFileName(File fileOutput, int index) {
		String file = fileOutput.getAbsolutePath();
		file = file.substring(0, file.lastIndexOf('.'));
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		return file + " " + timestamp.getTime() + index + ".jpg";
	}

}
