package latice.model;

import org.junit.jupiter.api.Test;

import latice.model.tiles.Color;
import latice.model.tiles.Symbol;
import latice.model.tiles.Tile;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class PlayerTest {

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
    
    @Test
    void testAddScoreWithInitialScore() {
        Player player = new Player("Test");
        player.Score(10);
        player.addScore(5);
        assertEquals(15, player.getScore());
    }

    @Test
    void testSwapRack() {
        List<Tile> deck = new ArrayList<>();
        List<Tile> initialRack = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            deck.add(new Tile(i + 5, Color.GREEN, Symbol.LIZARD));
            initialRack.add(new Tile(i, Color.RED, Symbol.FEATHER));
        }
        Player player = new Player("SwapTester", deck, initialRack, 0, 1);
        List<Tile> oldRack = player.swapRack();

        assertNotNull(oldRack);
        assertEquals(5, oldRack.size());
        assertEquals(5, player.getRack().size());
    }
    
    @Test
    void testSwapRackWithInsufficientDeck() {
        List<Tile> deck = new ArrayList<>();
        List<Tile> rack = new ArrayList<>();
        rack.add(new Tile(1, Color.RED, Symbol.FEATHER));
        Player player = new Player("Test", deck, rack, 0, 1);
        
        List<Tile> result = player.swapRack();
        assertNull(result);
    }

    @Test
    void testSetDeck() {
        Player player = new Player("Test");
        List<Tile> newDeck = new ArrayList<>();
        newDeck.add(new Tile(1, Color.YELLOW, Symbol.DOLPHIN));
        player.Deck(newDeck);
        assertEquals(newDeck, player.getDeck());
    }

    @Test
    void testSetRack() {
        Player player = new Player("Test");
        List<Tile> newRack = new ArrayList<>();
        newRack.add(new Tile(2, Color.RED, Symbol.FEATHER));
        player.Rack(newRack);
        assertEquals(newRack, player.getRack());
    }
    
    @Test
    void testSetName() {
        Player player = new Player("Initial");
        player.Name("Updated");
        assertEquals("Updated", player.getName());
    }
}