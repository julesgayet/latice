package latice.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import latice.model.tiles.Tile;

public class Referee {
    private Player player1;
    private Player player2;
    private final int RACK_SIZE = 5;

    public Referee(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    private void distributeToPlayer(Player player) {
        while (player.getRack().size() < RACK_SIZE && !player.getDeck().isEmpty()) {
            Tile tile = player.getDeck().remove(0);
            player.getRack().add(tile);
        }
    }
    
    public void distributeInitialTiles() {
        player1.fillRack();
        player2.fillRack();
    }
     
    
    private List<Tile> generatePlayerDeck() {
        List<Tile> deck = new ArrayList<>();
        Collections.shuffle(deck);
        return deck;
    }
}
