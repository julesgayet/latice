package latice.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import latice.model.tiles.Tile;

public class Main extends Application {
    
	@Override
	public void start(Stage primaryStage) {
		// Create the players first
	    Player p1 = new Player("Player 1", new ArrayList<>(), new ArrayList<>(), 0, 1);
	    Player p2 = new Player("Player 2", new ArrayList<>(), new ArrayList<>(), 0, 1);

	    // Create the game with those players
	    Game game = new Game(p1, p2, new Board(), true);

	    // Choose the starting player from p1 and p2
	    Player startingPlayer = game.firstPlayer(p1, p2);
	    
	    // initialize tiles and racks
	    List<Tile> allTiles = Game.generateAllTiles();
        Collections.shuffle(allTiles);
        
        int mid = allTiles.size() / 2;
        p1.Deck(new ArrayList<>(allTiles.subList(0, mid)));
        p2.Deck(new ArrayList<>(allTiles.subList(mid, allTiles.size())));

        
        Game.fillRack(p1);
        Game.fillRack(p2);

	    Stage splashStage = new Stage();
	    Label splashLabel = new Label(startingPlayer.getName() + " starts!");
	    StackPane splashLayout = new StackPane(splashLabel);
	    Scene splashScene = new Scene(splashLayout, 300, 150);
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

	        RackController rackController = loader.getController();  // récupérer le contrôleur 
	        if (rackController == null) {
	            System.err.println("Erreur: rackController est null après chargement du FXML");
	            return;
	        }

	        rackController.updateView(startingPlayer.getRack(),game.getCurrentPlayer());  // mettre à jour la vue

	     
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
