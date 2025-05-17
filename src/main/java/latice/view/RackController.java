package latice.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import latice.model.Player;
import latice.model.tiles.Tile;
import latice.model.tiles.TileUtils;

import java.net.URL;
import java.util.List;

public class RackController {

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
    
    

}
