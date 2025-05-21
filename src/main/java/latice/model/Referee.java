package latice.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import latice.model.tiles.Tile;

public class Referee {
    private Player player1;
    private Player player2;
    private final int RACK_SIZE = 5;
	private Game game;

    public Referee(Player player1, Player player2, Game game) {
        this.player1 = player1;
        this.player2 = player2;
        this.game = game;
    }
    
    
    private void fillRack(Player player) {
        while (player.getRack().size() < RACK_SIZE && !player.getDeck().isEmpty()) {
            Tile tile = player.getDeck().remove(0);
            player.getRack().add(tile);
        }
    }

    public void initializeGame() {
        List<Tile> allTiles = Game.generateAllTiles();
        Collections.shuffle(allTiles); 

        int mid = allTiles.size() / 2;
        player1.Deck(new ArrayList<>(allTiles.subList(0, mid)));
        player2.Deck(new ArrayList<>(allTiles.subList(mid, allTiles.size())));

        fillRack(player1);
        fillRack(player2);
    }
    
    

    

}