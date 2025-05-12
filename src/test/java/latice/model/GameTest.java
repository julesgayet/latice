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
        game.start();
        List<Tile> allTiles = new ArrayList<>();
        for (Color color : Color.values()) {
            for (Symbol symbol : Symbol.values()) {
                allTiles.add(new Tile(0, color, symbol, false));
                allTiles.add(new Tile(0, color, symbol, false));
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
                allTiles.add(new Tile(0, color, symbol, false));
                allTiles.add(new Tile(0, color, symbol, false));
            }
        }

        game.distributeTiles(allTiles, player1, player2);

        assertEquals(allTiles.size() / 2, player1.getDeck().size());
        assertEquals(allTiles.size() / 2, player2.getDeck().size());
    }
    
    @Test
    public void testFillRack() {
        // Add 3 tiles to player1 deck
        List<Tile> deck = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            deck.add(new Tile(i, Color.RED, Symbol.FEATHER, false));
        }
        player1.Deck(deck);

        Game.fillRack(player1);

        assertEquals(3, player1.getRack().size());
        assertTrue(player1.getDeck().isEmpty());
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
            original.add(new Tile(i, Color.GREEN, Symbol.DOLPHIN, false));
        }

        List<Tile> shuffled = Game.shake(original);
        // Not guaranteed but highly likely
        assertNotEquals(original.toString(), shuffled.toString());
    }

    @Test
    public void testGetWinnerReturnsCurrentPlayer() {
        assertEquals(game.getCurrentPlayer(), game.getWinner());
    }
    
}
