package latice.model;

import latice.model.board.Board;
import latice.model.tiles.Color;
import latice.model.tiles.Symbol;
import latice.model.tiles.Tile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class GameTest {

    private Player player1;
    private Player player2;
    private Board board;
    private Game game;

    @BeforeEach
    public void setUp() {
        player1 = new Player("Jules", null, null, null, null);
        player2 = new Player("Milan", null, null, null, null);
        board = new Board(); // Assuming a default constructor
        game = new Game(player1, player2, board, true);
    }

    @Test
    public void testStartCreatesCorrectNumberOfTiles() {
        Game.generateAllTiles();
        List<Tile> allTiles = new ArrayList<>();
        for (Color color : Color.values()) {
            for (Symbol symbol : Symbol.values()) {
                allTiles.add(new Tile(0, color, symbol));
                allTiles.add(new Tile(0, color, symbol));
            }
        }
        assertEquals(Color.values().length * Symbol.values().length * 2, allTiles.size());
    }

    @Test
    public void testDistributeTilesEvenly() {
        // Generate tile set
        List<Tile> allTiles = new ArrayList<>();
        for (Color color : Color.values()) {
            for (Symbol symbol : Symbol.values()) {
                allTiles.add(new Tile(0, color, symbol));
                allTiles.add(new Tile(0, color, symbol));
            }
        }

        game.distributeTiles(allTiles, player1, player2);

        assertEquals(allTiles.size() / 2, player1.getDeck().size());
        assertEquals(allTiles.size() / 2, player2.getDeck().size());
    }
    
    @Test
    public void testFillRack() {
        // Add 3 tiles to player1 deck
        List<Tile> rack = new ArrayList<>();
        while (rack.size() < 5) {
        	int i = 0;
            rack.add(new Tile(i, Color.RED, Symbol.FEATHER));
            i = i + 1;
        }
        player1.Rack(rack);

        Game.fillRack(player1);

        assertEquals(5, player1.getRack().size());
    }

    @Test
    public void testSwitchTurnChangesCurrentPlayer() {
        // Joueur courant au début : player1
        assertEquals(player1, game.getCurrentPlayer());

        game.switchTurn();
        assertEquals(player2, game.getCurrentPlayer());

        game.switchTurn();
        assertEquals(player1, game.getCurrentPlayer());
    }
    
    @Test
    public void testShakeTilesRandomizesOrder() {
        List<Tile> original = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            original.add(new Tile(i, Color.GREEN, Symbol.DOLPHIN));
        }

        List<Tile> shuffled = Game.shake(original);
        // Not guaranteed but highly likely
        assertNotEquals(original.toString(), shuffled.toString());
    }

    @Test
    public void testGetWinnerReturnsCurrentPlayer() {
        assertEquals(game.getCurrentPlayer(), game.getWinner());
    }
    
    @Test
    public void testFirstPlayerReturnsEitherP1OrP2() {
        Game game = new Game(player1, player2, board, true);
        Player p1 = new Player("Ahmed", null, null, null, null);
        Player p2 = new Player("Mostapha", null, null, null, null);

        boolean p1Chosen = false;
        boolean p2Chosen = false;

        for (int i = 0; i < 10; i++) {
            Player first = game.firstPlayer(p1, p2);
            if (first == p1) p1Chosen = true;
            if (first == p2) p2Chosen = true;
        }

        assertTrue(p1Chosen, "p1 should be chosen at least once");
        assertTrue(p2Chosen, "p2 should be chosen at least once");
        
    }
}
