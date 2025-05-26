package latice.view;


import java.util.ArrayList;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage; 
import javafx.util.Duration;
import latice.model.Game;
import latice.model.Player;
import latice.model.Referee;
import latice.model.board.Board;

public class Main extends Application {
    
	@Override
	public void start(Stage primaryStage) {
	    Player p1 = new Player("Player 1", new ArrayList<>(), new ArrayList<>(), 0, 1);
	    Player p2 = new Player("Player 2", new ArrayList<>(), new ArrayList<>(), 0, 1);
	    
	    Game game = new Game(p1, p2, new Board(), true);
	    Referee referee = new Referee(p1, p2, game);
	    referee.initializeGame();

	    Player startingPlayer = game.firstPlayer(p1, p2);
	    
	    

	    Stage splashStage = new Stage();

		 // Charger la police personnalisée
		 Font anton = Font.loadFont(
		     getClass().getResourceAsStream("/font/Anton-Regular.ttf"), 36);
		
		 Label splashLabel = new Label(startingPlayer.getName() + " starts!");
		 splashLabel.setFont(anton);
		 splashLabel.setTextFill(Color.web("#FFD700")); 
		
		 StackPane splashLayout = new StackPane(splashLabel);
		 splashLayout.setStyle("-fx-background-color: #2B50AA;"); 
		
		 Scene splashScene = new Scene(splashLayout, 400, 200);
		 splashStage.setScene(splashScene);
		 splashStage.setTitle("Game Start");
		 splashStage.setResizable(false);
		 splashStage.show();


	    // Wait 1 second
	    PauseTransition delay = new PauseTransition(Duration.seconds(1));
	    delay.setOnFinished(event -> {
	    	showMainGameWindow(primaryStage,startingPlayer,game);
	    	splashStage.close();
	        
	    });
	    delay.play();
	}

	private void showMainGameWindow(Stage primaryStage,Player startingPlayer, Game game) {
		try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GraphicBoard.fxml"));
	        Parent root = loader.load();  // charger la vue

	        Controller controller = loader.getController();  // récupérer le contrôleur 
	        if (controller == null) {
	            System.err.println("Erreur: Controller est null après chargement du FXML");
	            return;
	        }

	        controller.updateView(startingPlayer.getRack(),game.getCurrentPlayer());  // mettre à jour la vue

	     
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
