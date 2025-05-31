package latice.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.event.ActionEvent;
import latice.model.Game;
import latice.model.Player;
import latice.model.Referee;
import latice.model.board.Position;
import latice.model.tiles.Tile;
import latice.model.tiles.TileUtils;

import java.net.URL;
import java.util.List;

public class Controller {

    @FXML private ImageView rack_1;
    @FXML private ImageView rack_2;
    @FXML private ImageView rack_3;
    @FXML private ImageView rack_4;
    @FXML private ImageView rack_5;
    @FXML private Label lbl_deck;
    @FXML private Label lbl_player;
    @FXML private Button btnTurn;

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

        for (ImageView slot : rackSlots) {
            System.out.println("Slot enregistré : " + slot + " → id=" + slot.getId());

            slot.setOnDragDetected(event -> {
                int index = getSlotIndex(slot.getId());
                if (index >= 0 && index < game.getCurrentPlayer().getRack().size()) {
                    Dragboard db = slot.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(slot.getId());
                    db.setContent(content);
                    db.setDragView(slot.getImage());
                } else {
                    System.out.println("Aucune tuile à déplacer pour " + slot.getId());
                }
                event.consume();
            });
        }

        lbl_deck.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                setupDeckSquares();
            }
        });

        // Configurer l'action du bouton pour changer le tour
        btnTurn.setOnAction(this::handleTurnButton);
    }

    @FXML
    private void handleTurnButton(ActionEvent event) {
        // Changer le joueur courant
    	referee.fillRack(game.getCurrentPlayer());
        game.nextPlayer();
        game.setRound(game.getRound()+1);
        // Mettre à jour l'affichage pour le nouveau joueur
        updateViewForCurrentPlayer();
        System.out.println("Changement de tour effectué, c'est au tour de : " + game.getCurrentPlayer().getName());
    }

    private void updateViewForCurrentPlayer() {
        Player player = game.getCurrentPlayer();
        updateView(player.getRack(), player);
    }

    public void updateView(List<Tile> tiles, Player player) {
        for (int i = 0; i < rackSlots.length; i++) {
            if (i < tiles.size()) {
                String path = TileUtils.getImagePath(tiles.get(i));
                URL url = getClass().getResource(path);

                if (url == null) {
                    System.err.println("Image non trouvée pour le chemin : " + path);
                } else {
                    rackSlots[i].setImage(new Image(url.toExternalForm()));
                }
            } else {
                rackSlots[i].setImage(null);
            }
        }

        lbl_player.setText(player.getName().equals("Player 1") ? "1" : "2");
        lbl_deck.setText(Integer.toString(player.getDeck().size()));
    }

    private int getSlotIndex(String rackId) {
        try {
            return Integer.parseInt(rackId.split("_")[1]) - 1 ; // rack_1 → index 0
        } catch (Exception e) {
            System.err.println("Erreur pour récupérer l'index à partir de " + rackId);
            return -1;
        }
    }

    private Tile getTileFromRack(String rackId) {
        int index = getSlotIndex(rackId);
        List<Tile> rack = game.getCurrentPlayer().getRack();
        if (index < 0 || index >= rack.size()) {
            System.err.println("Pas de tuile dans le rack à l’index " + index + " (taille actuelle : " + rack.size() + ")");
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
                    int finalRow = row;
                    int finalCol = col;

                    square.setOnDragOver(event -> {
                        if (event.getGestureSource() != square && event.getDragboard().hasString()) {
                            event.acceptTransferModes(TransferMode.MOVE);
                        }
                        event.consume();
                    });

                    square.setOnDragDropped(event -> {
                        Dragboard db = event.getDragboard();
                        boolean success = false;
                        if (db.hasString()) {
                            String rackId = db.getString();
                            ImageView draggedSlot = (ImageView) lbl_deck.getScene().lookup("#" + rackId);
                            Tile draggedTile = getTileFromRack(rackId);

                            if (draggedSlot != null && draggedSlot.getImage() != null && draggedTile != null) {
                                if (referee.isValidMove(draggedTile, finalRow, finalCol, game)) {
                                    square.setImage(draggedSlot.getImage());
                                    draggedSlot.setImage(null);

                                    game.getCurrentPlayer().getRack().remove(draggedTile);
                                    game.getBoard().getCell(finalRow, finalCol).setTile(draggedTile);

                                    success = true;
                                    System.out.println("Placement valide !");
                                } else {
                                    Alert alert = new Alert(AlertType.WARNING);
                                    alert.setTitle("Placement invalide");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Vous ne pouvez pas placer cette tuile ici.");
                                    alert.showAndWait();
                                }
                            }
                        }
                        event.setDropCompleted(success);
                        event.consume();
                    });
                } else {
                    System.err.println("Case non trouvée : " + squareId);
                }
            }
        }
    }
}
