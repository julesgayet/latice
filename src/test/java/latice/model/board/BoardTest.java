package latice.model.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import latice.model.tiles.Color;
import latice.model.tiles.Symbol;
import latice.model.tiles.Tile;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void testBoardInitialization() {
        // Soleil aux coins
        assertEquals(CellType.SUN, board.getCell(0, 0).getType());
        assertEquals(CellType.SUN, board.getCell(8, 8).getType());

        // Lune au centre
        assertEquals(CellType.MOON, board.getCell(4, 4).getType());

        // Normal ailleurs
        assertEquals(CellType.NORMAL, board.getCell(3, 3).getType());
    }
    
    @Test
    void testIsPlacementValid_withAdjacentSameColor() {
        Board board = new Board();
        Position pos = new Position(2, 2);
        Tile tile = new Tile(1, Color.RED, Symbol.DOLPHIN);

        // Place adjacent tile to the left
        board.getCell(2, 1).setTile(new Tile(2, Color.RED, Symbol.FLOWER));

        boolean result = board.isPlacementValid(tile, pos);
        assertTrue(result, "Placement devrait être valide avec une tuile adjacente de même couleur");
    }

    @Test
    void testIsPlacementValid_outOfBounds() {
        Board board = new Board();
        Position pos = new Position(-1, 0);
        Tile tile = new Tile(1, Color.PINK, Symbol.LIZARD);

        boolean result = board.isPlacementValid(tile, pos);
        assertFalse(result, "Placement hors des limites du plateau devrait être invalide");
    }

    @Test
    void testIsPlacementValid_onNonEmptyCell() {
        Board board = new Board();
        Position pos = new Position(1, 1);
        board.getCell(1, 1).setTile(new Tile(3, Color.GREEN, Symbol.FEATHER));

        Tile tile = new Tile(4, Color.GREEN, Symbol.FEATHER);
        boolean result = board.isPlacementValid(tile, pos);
        assertFalse(result, "Placement sur une case non vide devrait être invalide");
    }


    @Test
    void testPlaceTile_invalid_doesNotThrow() {
        Board board = new Board();
        Position pos = new Position(1, 1);
        Tile tile = new Tile(3, Color.PINK, Symbol.LIZARD);

        // Aucun voisin : placement invalide
        assertDoesNotThrow(() -> board.placeTile(tile, pos));

        assertEquals(tile, board.getCell(1, 1).getTile());
        assertTrue(tile.getInGame());
    }

    @Test
    void testGetAdjacentTiles() {
        Board board = new Board();
        Position pos = new Position(2, 2);

        Tile t1 = new Tile(1, Color.RED, Symbol.LIZARD);
        Tile t2 = new Tile(2, Color.PINK, Symbol.FLOWER);

        board.getCell(1, 2).setTile(t1); // haut
        board.getCell(2, 3).setTile(t2); // droite

        List<Tile> adj = board.getAdjacentTiles(pos);
        assertEquals(2, adj.size());
        assertTrue(adj.contains(t1));
        assertTrue(adj.contains(t2));
    }

}