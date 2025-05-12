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
}
