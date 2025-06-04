package latice.controller;

import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import latice.model.Game;
import latice.model.Player;
import latice.model.Referee;
import latice.model.board.Position;
import latice.model.tiles.Tile;
import latice.model.tiles.TileUtils;


import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    @FXML private ImageView rack_1;
    @FXML private ImageView rack_2;
    @FXML private ImageView rack_3;
    @FXML private ImageView rack_4;
    @FXML private ImageView rack_5;
    @FXML private Label lbl_deck;
    @FXML private Label lbl_player;
    @FXML private Label lblScoreP2;
    @FXML private Label lblScoreP1;
    @FXML private Label lblRound;
    @FXML private Button btnSwapRack;
    @FXML private AnchorPane rootPane;
    
    
    private static final String POINT_SUFFIX = " points";
    private static final String SCORE_PREFIX = "Score ";
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());
    private ImageView[] rackSlots;

    private Referee referee;
    private Game game;

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    public void setGame(Game game) {
        this.game = game;
        updateViewForCurrentPlayer();
    }

    @FXML
    public void initialize() {
        rackSlots = new ImageView[] { rack_1, rack_2, rack_3, rack_4, rack_5 };
        btnSwapRack.setOnAction(event -> handleSwapRack());
        for (ImageView slot : rackSlots) {
            slot.setOnDragDetected(event -> {
                int index = getSlotIndex(slot.getId());
                if (index >= 0 && index < game.getCurrentPlayer().getRack().size()) {
                    Dragboard db = slot.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(slot.getId());
                    db.setContent(content);
                    db.setDragView(slot.getImage());
                    
                } else {
                	LOGGER.warning("Aucune tuile à déplacer pour " + slot.getId());
                }
                event.consume();
            });
        }

        lbl_deck.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                setupDeckSquares();
            }
        });

    }

    @FXML
    private void handleTurn() {
        // Changer le joueur courant
    	referee.fillRack(game.getCurrentPlayer());
        game.nextPlayer();
        game.setRound(game.getRound()+1);
        // Mettre à jour l'affichage pour le nouveau joueur
        updateViewForCurrentPlayer();
        LOGGER.log(Level.INFO,"Changement de tour effectué, c''est au tour de : {0}",game.getCurrentPlayer().getName());
    }

    private void updateViewForCurrentPlayer() {
        Player player = game.getCurrentPlayer();
        updateView(player.getRack(), player);
        String playerName = game.getPlayer1().getName();
        int score = game.getPlayer1().getScore();
        String playerName2 = game.getPlayer2().getName();
        int score2 = game.getPlayer2().getScore();
        lblScoreP1.setText(SCORE_PREFIX + playerName + " : " + score + POINT_SUFFIX);
        lblScoreP2.setText(SCORE_PREFIX + playerName2 + " : " + score2 + POINT_SUFFIX);
        lblRound.setText("ROUND : "+ (game.getRound()+1)/2);
    }

    public void updateView(List<Tile> tiles, Player player) {
        for (int i = 0; i < rackSlots.length; i++) {
            if (i < tiles.size()) {
                String path = TileUtils.getImagePath(tiles.get(i));
                URL url = getClass().getResource(path);

                if (url == null) {
                	LOGGER.log(Level.SEVERE, "Image non trouvée pour le chemin : {0}", path);
                } else {
                    rackSlots[i].setImage(new Image(url.toExternalForm()));
                }
            } else {
                rackSlots[i].setImage(null);
            }
        }

        lbl_player.setText(player.getName().equals(game.getPlayer1().getName()) ? game.getPlayer1().getName().substring(0, 1) : game.getPlayer2().getName().substring(0, 1));
        lbl_deck.setText(Integer.toString(player.getDeck().size()));
    }

    private int getSlotIndex(String rackId) {
        try {
            return Integer.parseInt(rackId.split("_")[1]) - 1 ; // rack_1 → index 0
        } catch (Exception e) {
        	LOGGER.severe("Erreur pour récupérer l'index à partir de " + rackId);
            return -1;
        }
    }

    private Tile getTileFromRack(String rackId) {
        int index = getSlotIndex(rackId);
        List<Tile> rack = game.getCurrentPlayer().getRack();
        if (index < 0 || index >= rack.size()) {
        	LOGGER.log(Level.WARNING,"Pas de tuile dans le rack à l’index {0} (taille actuelle : {1})",new Object[]{ index, rack.size() });
            return null;
        }
        return rack.get(index);
    }

    private void setupDeckSquares() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String squareId = String.format("square_%d%d", row, col);
                ImageView square = (ImageView) lbl_deck.getScene().lookup("#" + squareId);

                if (square != null) {
                    configureSquareDragHandlers(square, row, col);
                } else {
                	LOGGER.log(Level.WARNING, "Case non trouvée : {0}", squareId);
                }
            }
        }
    }

    private void configureSquareDragHandlers(ImageView square, int row, int col) {
        // OnDragOver : autoriser le drag si c'est un MOUVEMENT valide
        square.setOnDragOver(event -> {
            if (event.getGestureSource() != square && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        // OnDragDropped : déléguer à une méthode dédiée pour alléger le lambda
        square.setOnDragDropped(event -> 
            handleSquareDragDropped(square, row, col, event)
        );
    }

    private void handleSquareDragDropped(ImageView square, int row, int col, DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;

        if (db.hasString()) {
            String rackId = db.getString();
            ImageView draggedSlot = (ImageView) lbl_deck.getScene().lookup("#" + rackId);
            Tile draggedTile = getTileFromRack(rackId);

            if (draggedSlot != null && draggedSlot.getImage() != null && draggedTile != null) {
                if (referee.isValidMove(draggedTile, row, col, game)) {
                    // Placer l'image sur la case
                    square.setImage(draggedSlot.getImage());
                    draggedSlot.setImage(null);

                    // Appliquer le score et mettre à jour le modèle
                    referee.applyScore(game, game.getCurrentPlayer(), draggedTile, new Position(row, col));
                    game.getCurrentPlayer().getRack().remove(draggedTile);
                    game.getBoard().getCell(row, col).setTile(draggedTile);

                    success = true;
                    handleTurn();

                    // Vérifier si la partie est terminée
                    if (referee.isGameOver(game)) {
                        showEndGameDialog();
                    }

                    LOGGER.info("Placement valide !");
                } else {
                    showInvalidPlacementAlert();
                }
            }
        }

        event.setDropCompleted(success);
        event.consume();
    }

    private void showEndGameDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fin de partie");
        alert.setHeaderText(null);

        Player winner = referee.getWinner(game);
        String msg = String.format("The winner is: %s", winner.getName());

        // Fermer la fenêtre principale avant d’afficher le dialogue
        Stage primaryStage = (Stage) rootPane.getScene().getWindow();
        primaryStage.close();

        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showInvalidPlacementAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Placement invalide");
        alert.setHeaderText(null);
        alert.setContentText("Vous ne pouvez pas placer cette tuile ici.");
        alert.showAndWait();
    }

    
    
    private void handleSwapRack() {
        Player current = game.getCurrentPlayer();
        List<Tile> oldRack = current.swapRack();
        handleTurn(); 
        if (referee.isGameOver(game)) {
            // Afficher la boîte de dialogue de fin
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fin de partie");
            alert.setHeaderText(null);
            Player winner = referee.getWinner(game);
            String msg ="";
            if (game.getRound()>= 20) {
            	msg = "Draw between the players";
            }
            else {
            	msg = String.format("The winner is : %s",
                        winner.getName()
                    );
            }
            
            alert.setContentText(msg);
            alert.showAndWait();
            game.setIsOnGoing(false);
            Stage primaryStage = (Stage) rootPane.getScene().getWindow();
            primaryStage.close();
            
            
        }
        if (oldRack == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Swap impossible");
            alert.setHeaderText(null);
            alert.setContentText("Impossible d'échanger le rack en ce moment.");
            alert.showAndWait();
            game.setRound(game.getRound()+1);

            return;
        }
        
        // Les anciennes tuiles sont retournées par swapRack() dans oldRack et réinjectées dans le deck automatiquement
        updateViewForCurrentPlayer();
        String playerName = game.getPlayer1().getName();
        int score = game.getPlayer1().getScore();
        String playerName2 = game.getPlayer2().getName();
        int score2 = game.getPlayer2().getScore();
        lblScoreP1.setText(SCORE_PREFIX+ playerName + " : " + score + POINT_SUFFIX);
        lblScoreP2.setText(SCORE_PREFIX + playerName2 + " : " + score2 + POINT_SUFFIX);
    }}