package latice.view;

import java.net.URL;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import latice.model.Game;
import latice.model.Player;
import latice.model.board.Board;

public class Main extends Application {
    
	@Override
	public void start(Stage primaryStage) {
		//Creating the game
		Game game = new Game(new Player("Player1",null,null,0,1),new Player("Player2",null,null,0,1),new Board(),true);
		
		
		
	    // Temporary Screen
		String startingPlayer = game.getCurrentPlayer().getName(); 

	    Stage splashStage = new Stage();
	    Label splashLabel = new Label(startingPlayer + " starts!");
	    StackPane splashLayout = new StackPane(splashLabel);
	    Scene splashScene = new Scene(splashLayout, 300, 150);
	    splashStage.setScene(splashScene);
	    splashStage.setTitle("Game Start");
	    splashStage.setResizable(false);
	    splashStage.show();

	    // Wait 1 second
	    PauseTransition delay = new PauseTransition(Duration.seconds(1));
	    delay.setOnFinished(event -> {
	    	showMainGameWindow(primaryStage);
	    	splashStage.close();
	        
	    });
	    delay.play();
	}

	private void showMainGameWindow(Stage primaryStage) {
	    try {
	        URL fxmlLocation = getClass().getResource("/GraphicBoard.fxml");
	        Parent root = FXMLLoader.load(fxmlLocation);

	        primaryStage.setTitle("Jeu Latice");
	        primaryStage.setScene(new Scene(root));
	        primaryStage.setResizable(false);
	        primaryStage.sizeToScene();
	        primaryStage.show();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


    public static void main(String[] args) {
        launch(args);
    }
}
