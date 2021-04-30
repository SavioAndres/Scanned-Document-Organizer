package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import main.Main;

public class ConvertScreenController implements Initializable {
	
	private File directory;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	private void openFile(ActionEvent event) {
		DirectoryChooser chooser = new DirectoryChooser();
		File directory = chooser.showDialog(Main.stage);
		this.directory = directory;
		System.out.println(this.directory);
	}
	
	@FXML
	private void back(ActionEvent event) throws IOException {
		Pane root = FXMLLoader.load(ConvertScreenController.class.getResource("MainScreen.fxml"));
		Scene scene = new Scene(root);
		
		Main.stage.setTitle("Organizador de documentos digitalizados");
		Main.stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
		Main.stage.setScene(scene);
		Main.stage.show();
	}
	
	

}
