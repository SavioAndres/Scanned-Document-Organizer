package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Main extends Application {

	public static Stage stage;
	public static Scene scene;

	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;

			Pane root = FXMLLoader.load(getClass().getResource("/application/MainScreen.fxml")); // /src/application/MainScreen.fxml
			scene = new Scene(root);

			primaryStage.setTitle("Organizador de documentos digitalizados");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
