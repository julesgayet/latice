package latice.model;

import latice.model.board.*;
import latice.model.tiles.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestReferee {

    private Player player1;
    private Player player2;
    private Referee referee;

    @BeforeEach
    public void setUp() {
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        referee = new Referee(player1, player2, null);
    }

    @Test
    public void testInitializeGame_ShouldDistributeTilesAndFillRacks() {
        referee.initializeGame();

        // Vérifie que les decks ne sont pas vides
        assertNotNull(player1.getDeck(), "Deck du joueur 1 est null");
        assertNotNull(player2.getDeck(), "Deck du joueur 2 est null");

        // Vérifie que les racks sont remplis correctement
        assertTrue(player1.getRack().size() <= 5, "Rack du joueur 1 dépasse la taille max");
        assertTrue(player2.getRack().size() <= 5, "Rack du joueur 2 dépasse la taille max");

        // Vérifie que le total de tuiles reste constant
        int totalDistributed = player1.getDeck().size() + player1.getRack().size()
                             + player2.getDeck().size() + player2.getRack().size();

        assertEquals(Game.generateAllTiles().size(), totalDistributed,
                "Le total de tuiles distribuées n'est pas correct");
    }

    @Test
    void testFillRackFillsUpToRackSize() {
        // Prépare un deck de 10 tuiles
        player1.Deck(new ArrayList<>(Game.generateAllTiles().subList(0, 10)));
        player1.getRack().clear(); // vide le rack

        referee.fillRack(player1);

        assertEquals(5, player1.getRack().size(), "fillRack doit remplir le rack à 5 tuiles");
        assertEquals(5, player1.getDeck().size(), "Le deck doit avoir perdu 5 tuiles");
    }

    @Test
    void testIsValidMove_ReturnsTrueIfPlacementIsValid() {
        // Crée une tuile, une position et un board "ouvert"
        Tile tile = new Tile(1, Color.RED, Symbol.FLOWER);
        Board board = new Board() {
            @Override
            public boolean isPlacementValid(Tile t, Position pos, Game game) {
                return true;
            }
        };
        Game testGame = new Game(player1, player2, board, true);

        boolean result = referee.isValidMove(tile, 2, 2, testGame);
        assertTrue(result, "isValidMove doit retourner true si le placement est accepté par le board");
    }

    @Test
    void testIsGameOver_TrueWhenRackAndDeckEmpty() {
        // Vide rack et deck du joueur 1
        player1.getDeck().clear();
        player1.getRack().clear();

        // Les autres non-vides pour joueur 2
        player2.getDeck().add(new Tile(1, Color.RED, Symbol.SEAGULL));
        player2.getRack().add(new Tile(2, Color.DARK_BLUE, Symbol.FLOWER));

        Game testGame = new Game(player1, player2, new Board(), true);
        testGame.setRound(1);

        assertTrue(referee.isGameOver(testGame), "La partie doit être finie si rack et deck du joueur 1 sont vides");
    }

    @Test
    void testIsGameOver_TrueWhenRoundIs20() {
        Game testGame = new Game(player1, player2, new Board(), true);
        testGame.setRound(20);

        assertTrue(referee.isGameOver(testGame), "La partie doit être finie si round atteint 20");
    }

    @Test
    void testCalculatePointsWithNoAdjacentsAndNoSun() {
        Board board = new Board() {
            @Override
            public List<Tile> getAdjacentTiles(Position pos) { return new ArrayList<>(); }
            @Override
            public Cell getCell(Position pos) { return new Cell(CellType.NORMAL); }
        };
        Tile tile = new Tile(1, Color.RED, Symbol.DOLPHIN);
        Position pos = new Position(1, 1);

        int points = referee.calculatePoints(board, tile, pos);
        assertEquals(0, points, "Pas de voisins, pas de bonus : score doit être 0");
    }

    @Test
    void testCalculatePointsWithAllCombosAndSun() {
        Board board = new Board() {
            @Override
            public List<Tile> getAdjacentTiles(Position pos) {
                Tile t1 = new Tile(2, Color.RED, Symbol.FEATHER);      // match couleur
                Tile t2 = new Tile(3, Color.DARK_BLUE, Symbol.DOLPHIN);     // match symbole
                Tile t3 = new Tile(4, Color.RED, Symbol.DOLPHIN);      // match couleur + symbole
                Tile t4 = new Tile(5, Color.RED, Symbol.FEATHER);         // match couleur
                return Arrays.asList(t1, t2, t3, t4);
            }
            @Override
            public Cell getCell(Position pos) { return new Cell(CellType.SUN); }
        };
        Tile tile = new Tile(1, Color.RED, Symbol.DOLPHIN);
        Position pos = new Position(1, 1);

        int points = referee.calculatePoints(board, tile, pos);
        assertEquals(6, points, "4 matchs + case SUN = 4+2 = 6 points");
        
    }

    @Test
    void testApplyScore_AddsPointsToPlayer() {
        Board board = new Board() {
            @Override
            public List<Tile> getAdjacentTiles(Position pos) {
                Tile t1 = new Tile(2, Color.RED, Symbol.FEATHER);       // match couleur
                Tile t2 = new Tile(3, Color.DARK_BLUE, Symbol.FLOWER);       // match symbole
                return Arrays.asList(t1, t2);
            }
            @Override
            public Cell getCell(Position pos) { return new Cell(CellType.NORMAL); }
        };
        Game testGame = new Game(player1, player2, board, true);

        int initialScore = player1.getScore();
        Tile tile = new Tile(1, Color.RED, Symbol.FLOWER);
        Position pos = new Position(2, 2);

        referee.applyScore(testGame, player1, tile, pos);
        assertEquals(initialScore + 1, player1.getScore(), "applyScore doit ajouter le score calculé au joueur");
    }
    
    @Test
    void testGetWinnerDelegatesToGame() {
        Game testGame = new Game(player1, player2, new Board(), true);
        testGame.setCurrentPlayer(player2);

        assertEquals(player2, referee.getWinner(testGame), "getWinner doit retourner le joueur courant de la partie");
    }
    
    
    @Test
    void testCalculatePointsWith3Matches() {
        Board board = new Board() {
            @Override
            public List<Tile> getAdjacentTiles(Position pos) {
                Tile t1 = new Tile(2, Color.RED, Symbol.FEATHER);   // match couleur
                Tile t2 = new Tile(3, Color.DARK_BLUE, Symbol.DOLPHIN);  // match symbole
                Tile t3 = new Tile(4, Color.RED, Symbol.LIZARD);    // match couleur
                Tile t4 = new Tile(5, Color.GREEN, Symbol.FLOWER);    // aucun match
                return Arrays.asList(t1, t2, t3, t4);
            }
            @Override
            public Cell getCell(Position pos) { return new Cell(CellType.NORMAL); }
        };
        Tile tile = new Tile(1, Color.RED, Symbol.DOLPHIN);
        Position pos = new Position(1, 1);

        int points = referee.calculatePoints(board, tile, pos);
        assertEquals(2, points, "3 matchs (pas de SUN) = 2 points");
    }
}