package latice.model.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

}
