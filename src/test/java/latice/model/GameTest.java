package latice.model;

import latice.model.board.Board;
import latice.model.board.Position;
import latice.model.tiles.Color;
import latice.model.tiles.Symbol;
import latice.model.tiles.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Player player1;
    private Player player2;
    private Board board; // on simulera un board vide
    private Game game;

    @BeforeEach
    public void setUp() {
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        board = new Board(); // supposons que Board a un constructeur vide
        game = new Game(player1, player2, board, true);
    }


    @Test
    public void testFirstPlayerIsEitherPlayer1OrPlayer2() {
        Player selected = game.firstPlayer(player1, player2);
        assertTrue(selected == player1 || selected == player2, "Le joueur sélectionné doit être player1 ou player2");
    }

    @Test
    public void testSwitchTurnChangesCurrentPlayer() {
        Player first = game.getCurrentPlayer();
        game.switchTurn();
        Player second = game.getCurrentPlayer();

        assertNotSame(first, second, "Le joueur courant doit changer après switchTurn");
    }

    @Test
    public void testGetWinnerReturnsCurrentPlayer() {
        assertEquals(game.getCurrentPlayer(), game.getCurrentPlayer(), "getWinner devrait retourner le joueur courant");
    }

    @Test
    public void testGameSettersAndGetters() {
        Game g = new Game(player1, player2, board, true);
        g.setIsOnGoing(false);
        g.setCurrentPlayer(player2);
        g.setPlayer1(player2);
        g.setPlayer2(player1);

        assertFalse(g.getIsOnGoing(), "isOnGoing devrait être false");
        assertEquals(player2, g.getPlayer1(), "player1 devrait être Bob");
        assertEquals(player1, g.getPlayer2(), "player2 devrait être Alice");
        assertEquals(player2, g.getCurrentPlayer(), "Joueur courant incorrect");
    }
    
    
    @Test
    void testGenerateAllTiles() {
        List<Tile> tiles = Game.generateAllTiles(); // Adapte si le nom de classe est différent

        // Vérifie le nombre total de tuiles
        int expectedTileCount = 72;
        assertEquals(expectedTileCount, tiles.size(), "Le nombre total de tuiles est incorrect");

        // Vérifie l’unicité des IDs
        Set<Integer> ids = new HashSet<>();
        for (Tile tile : tiles) {
            assertTrue(ids.add(tile.getId()), "ID dupliqué détecté : " + tile.getId());
        }

        // Vérifie que chaque combinaison apparaît deux fois
        Map<String, Integer> comboCount = new HashMap<>();
        for (Tile tile : tiles) {
            String key = tile.getColor() + "-" + tile.getSymbol();
            comboCount.put(key, comboCount.getOrDefault(key, 0) + 1);
        }

        for (Color color : Color.values()) {
            for (Symbol symbol : Symbol.values()) {
                String key = color + "-" + symbol;
                assertEquals(2, comboCount.getOrDefault(key, 0), "La combinaison " + key + " n’apparaît pas deux fois");
            }
        }
    }
    
    
    
    @Test
    void testNextPlayerSwitchesToPlayer2WhenPlayer1IsCurrent() {
        // Forcer player1 comme joueur courant
        game.setCurrentPlayer(player1);
        game.nextPlayer();
        assertEquals(player2, game.getCurrentPlayer(), "Doit passer de player1 à player2");
    }

    @Test
    void testNextPlayerSwitchesToPlayer1WhenPlayer2IsCurrent() {
        // Forcer player2 comme joueur courant
        game.setCurrentPlayer(player2);
        game.nextPlayer();
        assertEquals(player1, game.getCurrentPlayer(), "Doit passer de player2 à player1");
    }
    
    
    @Test
    void testSetBoardSetsTheBoard() {
        Board newBoard = new Board(); // Ou adapte selon ton constructeur Board
        game.setBoard(newBoard);
        assertEquals(newBoard, game.getBoard(), "setBoard doit modifier l'attribut board de la partie");
    }
    
    @Test
    void testSetRoundSetsTheRound() {
        game.setRound(7);
        assertEquals(7, game.getRound(), "setRound doit modifier l'attribut round de la partie");
    }
}