package latice.view;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		try {
			URL fxmlLocation = getClass().getResource("/GraphicBoard.fxml");
			System.out.println("FXML Location: " + fxmlLocation); // doit être non null
			Parent root = FXMLLoader.load(fxmlLocation);

			primaryStage.setTitle("Jeu Latice");
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		} catch (Exception e) {
	        e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
