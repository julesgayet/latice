package latice.model;

import latice.model.tiles.Tile;
import latice.model.tiles.Color;
import latice.model.tiles.Symbol;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestReferee{

    private Player player1;
    private Player player2;
    private Referee referee;

    @BeforeEach
    void setUp() {
        // On utilise la méthode Game.generateAllTiles()
        List<Tile> allTiles = Game.generateAllTiles(); 

        // Mélanger pour rendre la distribution aléatoire
        Collections.shuffle(allTiles);

        // Diviser en deux paquets égaux pour les deux joueurs
        List<Tile> deck1 = new ArrayList<>(allTiles.subList(0, allTiles.size() / 2));
        List<Tile> deck2 = new ArrayList<>(allTiles.subList(allTiles.size() / 2, allTiles.size()));

        player1 = new Player("Alice", deck1, new ArrayList<>(), 0, 1);
        player2 = new Player("Bob", deck2, new ArrayList<>(), 0, 1);
        referee = new Referee(player1, player2);
    }

    @Test
    void testDistributeToPlayerFillsRackUpToFive() {
        // S'assure que le rack est vide
        assertEquals(0, player1.getRack().size());

        // Appelle la méthode de remplissage
    //    referee.distributeToPlayer(player1);  // méthode rendue package-private ou protected

        // Doit remplir le rack jusqu'à 5
        assertEquals(5, player1.getRack().size());

        // Vérifie que le deck a été réduit
        assertEquals(31, player1.getDeck().size());
    }
    
    @Test
    void testDistributeInitialTiles() {
        // Vérifie que les racks sont vides avant
        assertEquals(0, player1.getRack().size());
        assertEquals(0, player2.getRack().size());

        // Lance la distribution
     //   referee.distributeInitialTiles();

        // Chaque joueur doit avoir 5 tuiles (taille max du rack)
        assertEquals(5, player1.getRack().size());
        assertEquals(5, player2.getRack().size());

        // Vérifie que le deck a été réduit en conséquence
        assertEquals(31, player1.getDeck().size()); 
        assertEquals(31, player2.getDeck().size());
    }

    @Test
    void testGeneratePlayerDeckReturnsShuffledDeck() {
     //   List<Tile> generatedDeck = referee.generatePlayerDeck(); // méthode rendue accessible

     //   assertNotNull(generatedDeck);
      //  assertTrue(generatedDeck.isEmpty(), "Deck généré doit être vide (actuellement mal implémenté)");
    }

    // Méthode utilitaire
    private List<Tile> createTiles(int count, Color color, Symbol symbol) {
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            tiles.add(new Tile(i, color, symbol));
        }
        return tiles;
    }
}