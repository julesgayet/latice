package latice.model.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import latice.model.Game;
import latice.model.tiles.Color;
import latice.model.tiles.Symbol;
import latice.model.tiles.Tile;

class BoardTest {
    private Board board;
    private Game game;                  // 2. déclarer game

    @BeforeEach
    void setUp() {
        board = new Board();
        game = new Game(null, null, board, null) {            // 3. initialiser game
            @Override
            public int getRound() {
                return 2;              // simule un tour > 1
            }
        };
    }

    @Test
    void testBoardInitialization_cellsNotNull() {
        assertEquals(9, board.getSize());
        for (int r = 0; r < board.getSize(); r++) {
            for (int c = 0; c < board.getSize(); c++) {
                assertNotNull(board.getCell(r, c), "Cell at " + r + "," + c + " should not be null");
            }
        }
    }

    @Test
    void testBoardInitialization_cornerAndCenterTypes() {
        // SUN positions
        int[][] sunPos = {
            {0,0}, {0,8}, {8,0}, {8,8},
            {0,4},{1,7},{4,0},{7,1},{6,2},{7,7},{6,6},{4,8},{8,4},{1,1},{2,2},{2,6}
        };
        for (int[] p : sunPos) {
            assertEquals(CellType.SUN, board.getCell(p[0], p[1]).getType(),
                         "SUN at " + p[0] + "," + p[1]);
        }
        // Center is MOON
        assertEquals(CellType.MOON, board.getCell(4,4).getType());
        // Some NORMAL
        assertEquals(CellType.NORMAL, board.getCell(3,3).getType());
    }

    @Test
    void testGetSizeAndGridAccessors() {
        assertEquals(9, board.getSize());
        Cell[][] newGrid = new Cell[1][1];
        newGrid[0][0] = new Cell(CellType.NORMAL);
        board.setGrid(newGrid);
        assertSame(newGrid, board.getGrid());
    }

    @Test
    void testIsPlacementValid_noAdjacentReturnsFalse() {
        Position p = new Position(2,2);
        Tile t = new Tile(1, Color.DARK_BLUE, Symbol.SEAGULL);
        assertFalse(board.isPlacementValid(t, p, game));
    }

    @Test
    void testIsPlacementValid_withAdjacentSameColor() {
        Position p = new Position(2,2);
        Tile neighbor = new Tile(2, Color.RED, Symbol.FLOWER);
        board.getCell(2,1).setTile(neighbor);
        Tile t = new Tile(3, Color.RED, Symbol.DOLPHIN);
        assertTrue(board.isPlacementValid(t, p, game));
    }

    @Test
    void testIsPlacementValid_outOfBounds() {
        Position p = new Position(-1, 0);
        Tile t = new Tile(4, Color.PINK, Symbol.LIZARD);
        assertFalse(board.isPlacementValid(t, p, game));
    }

    @Test
    void testIsPlacementValid_onNonEmptyCell() {
        Position p = new Position(1,1);
        Tile existing = new Tile(5, Color.GREEN, Symbol.FEATHER);
        board.getCell(1,1).setTile(existing);
        Tile t = new Tile(6, Color.GREEN, Symbol.FEATHER);
        assertFalse(board.isPlacementValid(t, p, game));
    }

    @Test
    void testGetAdjacentTiles_emptyAtCorner() {
        List<Tile> adj = board.getAdjacentTiles(new Position(0,0));
        assertTrue(adj.isEmpty());
    }

    @Test
    void testGetAdjacentTiles_someNeighbors() {
        Position p = new Position(2,2);
        Tile t1 = new Tile(1, Color.RED, Symbol.LIZARD);
        Tile t2 = new Tile(2, Color.PINK, Symbol.FLOWER);
        board.getCell(1,2).setTile(t1);
        board.getCell(2,3).setTile(t2);
        List<Tile> adj = board.getAdjacentTiles(p);
        assertEquals(2, adj.size());
        assertTrue(adj.contains(t1));
        assertTrue(adj.contains(t2));
    }

    @Test
    void testPlaceTile_alwaysPlacesWithoutValidation() {
        Position p = new Position(1,1);
        Tile t = new Tile(3, Color.PINK, Symbol.LIZARD);
        assertDoesNotThrow(() -> board.placeTile(t, p, game));
        assertEquals(t, board.getCell(1,1).getTile());
        assertTrue(t.getInGame());
    }

    @Test
    void testHasTileIn_noTileIn() {
        assertFalse(board.hasTileIn());
    }

    @Test
    void testHasTileIn_withTileInCell() {
        Cell[][] custom = new Cell[2][2];
        custom[0][0] = new Cell(CellType.NORMAL);
        custom[0][1] = new Cell(CellType.TILE_IN);
        custom[1][0] = null;
        custom[1][1] = new Cell(CellType.MOON);
        board.setGrid(custom);
        assertTrue(board.hasTileIn());
    }
    

}
