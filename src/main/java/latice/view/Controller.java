package latice.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import latice.model.Player;
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

    private ImageView[] rackSlots;

    @FXML
    public void initialize() {
        // Regrouper les slots dans un tableau pour simplifier l'accès
        rackSlots = new ImageView[] { rack_1, rack_2, rack_3, rack_4, rack_5 };
        for (ImageView slot : rackSlots) {
            slot.setOnDragDetected(event -> {
                if (slot.getImage() != null) {
                    Dragboard db = slot.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(slot.getId()); // par ex. rack_1, rack_2…
                    db.setContent(content);
                    db.setDragView(slot.getImage());
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

    public void updateView(List<Tile> tiles,Player player) {
        for (int i = 0; i < rackSlots.length; i++) {
            if (i < tiles.size()) {
                String path = TileUtils.getImagePath(tiles.get(i));
                URL url = getClass().getResource(path);

                // Test de débogage
                if (url == null) {
                    System.err.println("Image non trouvée pour le chemin : " + path);
                } else {
                    rackSlots[i].setImage(new Image(url.toExternalForm()));
                }

            } else {
                rackSlots[i].setImage(null);
                
            }
        }
        
        
        // Mettre à jour les labels
        if ("Player 1".equals(player.getName())) {
        	lbl_player.setText("1");
        }
        else {
        	lbl_player.setText("2");
        }
        lbl_deck.setText(Integer.toString(player.getDeck().size()));
    
    }
    
    private void setupDeckSquares() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String squareId = String.format("square_%d%d", row, col);
                ImageView square = (ImageView) lbl_deck.getScene().lookup("#" + squareId);

                if (square != null) {
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
                            if (draggedSlot != null && draggedSlot.getImage() != null) {
                                square.setImage(draggedSlot.getImage());
                                draggedSlot.setImage(null); // vide le rack
                                success = true;
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
