package latice;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;

import latice.model.tiles.Color;
import latice.model.tiles.Symbol;
import latice.model.tiles.Tile;
import latice.model.tiles.TileUtils;

public class TileUtilsTest {

    @Test
    public void testGetImagePath() {
        Tile tile = new Tile(1,Color.GREEN,Symbol.DOLPHIN);  // Valeurs en dur
        String expectedPath = "/img/green_dolphin.png";
        String actualPath = TileUtils.getImagePath(tile); // Classe contenant la méthode

        assertEquals(expectedPath, actualPath);
    }

}
