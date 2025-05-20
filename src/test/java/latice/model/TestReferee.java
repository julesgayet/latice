package latice.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class TestReferee {

    private Player player1;
    private Player player2;
    private Referee referee;

    @BeforeEach
    public void setUp() {
        player1 = new Player("Jules");
        player2 = new Player("Mostapha");
        referee = new Referee(player1, player2);
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
}