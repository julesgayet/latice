package latice.view;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage; 
import latice.controller.Controller;
import latice.model.Game;
import latice.model.Player;
import latice.model.Referee;
import latice.model.board.Board;

public class Main extends Application {

    private Font antonFont;

    @Override
    public void start(Stage primaryStage) {
        // Charger la police custom une fois pour tout le programme
        antonFont = Font.loadFont(getClass().getResourceAsStream("/font/Anton-Regular.ttf"), 36);

        // Afficher le login form
        showPlayerNameForm(primaryStage);
    }

    // Affiche le formulaire d'entrée des noms, puis lance le jeu
    private void showPlayerNameForm(Stage primaryStage) {
        Stage loginStage = new Stage();

        // Label titre
        Label titleLabel = new Label("Noms des joueurs");
        titleLabel.setFont(antonFont);
        titleLabel.setTextFill(Color.web("gold"));

        // Champs texte
        TextField player1Field = new TextField();
        player1Field.setPromptText("Nom du Joueur 1");
        player1Field.setPrefWidth(250);

        TextField player2Field = new TextField();
        player2Field.setPromptText("Nom du Joueur 2");
        player2Field.setPrefWidth(250);

        // Bouton
        Button startButton = new Button("Commencer la partie");
        startButton.setDefaultButton(true);
        startButton.setStyle("-fx-background-color: gold; -fx-text-fill: #ffffff; -fx-font-size: 16px; -fx-background-radius: 16;");

        VBox form = new VBox(18, titleLabel, player1Field, player2Field, startButton);
        form.setPadding(new Insets(32));
        form.setAlignment(javafx.geometry.Pos.CENTER);
        form.setStyle(
            "-fx-background-color: #de8f5e; " +
            "-fx-background-radius: 18; " +
            "-fx-border-radius: 18; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 4);"
        );

        Scene loginScene = new Scene(form, 420, 340);
        loginStage.setScene(loginScene);
        loginStage.setTitle("Noms des joueurs");
        loginStage.setResizable(false);

        // ENTER pour passer au champ suivant/valider
        player1Field.setOnAction(e -> player2Field.requestFocus());
        player2Field.setOnAction(e -> startButton.fire());

        // Quand on clique sur "Commencer la partie"
        startButton.setOnAction(e -> {
            String nom1 = player1Field.getText().trim();
            String nom2 = player2Field.getText().trim();

            if (nom1.isEmpty()) {
                player1Field.requestFocus();
                showAlert(loginStage, "Veuillez entrer le nom du Joueur 1.");
                return;
            }
            if (nom2.isEmpty()) {
                player2Field.requestFocus();
                showAlert(loginStage, "Veuillez entrer le nom du Joueur 2.");
                return;
            }
            if (nom1.equalsIgnoreCase(nom2)) {
                showAlert(loginStage, "Les deux joueurs doivent avoir un nom différent.");
                player2Field.requestFocus();
                return;
            }

            // Fermer la fenêtre de login et lancer le jeu !
            loginStage.close();
            startGame(primaryStage, nom1, nom2);
        });

        loginStage.show();
    }

    // Affiche une alerte simple
    private void showAlert(Stage owner, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.initOwner(owner);
        alert.setHeaderText(null);
        alert.setTitle("Attention");
        alert.showAndWait();
    }

    // Initialise la partie et lance la scène principale
    private void startGame(Stage primaryStage, String joueur1, String joueur2) {
        // Création des joueurs, game, referee
        Player p1 = new Player(joueur1, new ArrayList<>(), new ArrayList<>(), 0, 1);
        Player p2 = new Player(joueur2, new ArrayList<>(), new ArrayList<>(), 0, 2);
        Game game = new Game(p1, p2, new Board(), true);
        Referee referee = new Referee(p1, p2, game);
        referee.initializeGame();
        Player startingPlayer = game.getCurrentPlayer();

        // Affiche la fenêtre de jeu principale (FXML)
        showMainGameWindow(primaryStage, startingPlayer, game, referee);
    }

    // Charge le FXML et ouvre la fenêtre du jeu
    private void showMainGameWindow(Stage primaryStage, Player startingPlayer, Game game, Referee referee) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GraphicBoard.fxml"));
            Parent root = loader.load();  // charger la vue

            Controller controller = loader.getController();  // récupérer le contrôleur

            if (controller == null) {
                System.err.println("Erreur: Controller est null après chargement du FXML");
                return;
            }
            controller.setReferee(referee);
            controller.setGame(game);
            controller.updateView(startingPlayer.getRack(), game.getCurrentPlayer());  // mettre à jour la vue

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