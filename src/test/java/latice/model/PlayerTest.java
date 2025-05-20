package latice.model;

import org.junit.jupiter.api.Test;

import latice.model.tiles.Color;
import latice.model.tiles.Symbol;
import latice.model.tiles.Tile;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class PlayerTest {

    @Test
    void testRack() {
        Player player = new Player("Mostapha");
        List<Tile> rack = new ArrayList<>();
        rack.add(new Tile(1, Color.RED, Symbol.DOLPHIN));
        rack.add(new Tile(2, Color.DARK_BLUE, Symbol.DOLPHIN));

        player.Rack(rack); 
        assertEquals(rack, player.getRack()); 
    }

    @Test
    void testScore() {
        Player player = new Player("Ahmed");
        player.Score(100);
        assertEquals(100, player.getScore());
    }

    @Test
    void testAvailableActions() {
        Player player = new Player("Jules");
        player.AvailableActions(5);
        assertEquals(5, player.getAvailableActions());
    }
    
    @Test
    void testFullConstructor() {
        List<Tile> deck = new ArrayList<>();
        List<Tile> rack = new ArrayList<>();
        Tile tile1 = new Tile(1, Color.RED, Symbol.FEATHER);
        Tile tile2 = new Tile(2, Color.GREEN, Symbol.LIZARD);
        deck.add(tile1);
        rack.add(tile2);

        Player player = new Player("Alice", deck, rack, 10, 2);

        assertEquals("Alice", player.getName());
        assertEquals(deck, player.getDeck());
        assertEquals(rack, player.getRack());
        assertEquals(10, player.getScore());
        assertEquals(2, player.getAvailableActions());
    }

    @Test
    void testConstructorWithDefaultName() {
        List<Tile> deck = new ArrayList<>();
        List<Tile> rack = new ArrayList<>();
        Tile tile1 = new Tile(3, Color.GREEN, Symbol.DOLPHIN);
        Tile tile2 = new Tile(4, Color.YELLOW, Symbol.LIZARD);
        deck.add(tile1);
        rack.add(tile2);

        Player player = new Player(deck, rack, 20, 3);

        assertEquals("player", player.getName()); // nom par dÃ©faut
        assertEquals(deck, player.getDeck());
        assertEquals(rack, player.getRack());
        assertEquals(20, player.getScore());
        assertEquals(3, player.getAvailableActions());
    }
}