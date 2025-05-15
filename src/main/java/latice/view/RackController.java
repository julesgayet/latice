package latice.view;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import latice.model.tiles.Tile;
import latice.model.tiles.TileUtils;

import java.util.List;

public class RackController {

    @FXML private ImageView rack_1;
    @FXML private ImageView rack_2;
    @FXML private ImageView rack_3;
    @FXML private ImageView rack_4;
    @FXML private ImageView rack_5;

    private ImageView[] rackSlots;

    @FXML
    public void initialize() {
        // Regrouper les slots dans un tableau pour simplifier l'accès
        rackSlots = new ImageView[] { rack_1, rack_2, rack_3, rack_4, rack_5 };
    }

    public void updateRack(List<Tile> tiles) {
        for (int i = 0; i < rackSlots.length; i++) {
            if (i < tiles.size()) {
                String imagePath = getClass().getResource(TileUtils.getImagePath(tiles.get(i))).toExternalForm();
                rackSlots[i].setImage(new Image(imagePath));
            } else {
                // Vider le slot si aucun tile
                rackSlots[i].setImage(null);
            }
        }
    }
}
