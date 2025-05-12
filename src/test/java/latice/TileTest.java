package latice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import latice.model.tiles.Symbol;
import latice.model.tiles.Color;
import latice.model.tiles.Tile;

public class TileTest {

	@Test
	public void testConstructor() {
	    Color color = Color.RED;
	    Symbol symbol = Symbol.TURTLE;
	    Tile tile = new Tile(1, color, symbol, true);
	    
	    assertEquals(1, tile.getId());
	    assertEquals(color, tile.getColor());
	    assertEquals(symbol, tile.getSymbol());
	    assertTrue(tile.getInGame());
	}

	@Test
	public void testGetters() {
	    Tile tile = new Tile(5, Color.PINK, Symbol.DOLPHIN, false);
	    
	    assertEquals(Integer.valueOf(5), tile.getId());
	    assertEquals(Color.PINK, tile.getColor());
	    assertEquals(Symbol.DOLPHIN, tile.getSymbol());
	    assertFalse(tile.getInGame());
	}
	
	@Test
	public void testSetters() {
	    Tile tile = new Tile(0, Color.GREEN, Symbol.FEATHER, true);
	    tile.Id(99);
	    tile.Color(Color.YELLOW);
	    tile.Symbol(Symbol.SEAGULL);
	    tile.InGame(false);

	    assertEquals(Integer.valueOf(99), tile.getId());
	    assertEquals(Color.YELLOW, tile.getColor());
	    assertEquals(Symbol.SEAGULL, tile.getSymbol());
	    assertFalse(tile.getInGame());
	}

}
