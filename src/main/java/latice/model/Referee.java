package latice.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import latice.model.board.Position;
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
    
    
    public void fillRack(Player player) {
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
    
    public boolean isValidMove(Tile tile, int row, int col,Game game) {
        Position pos = new Position(row, col);
        return game.getBoard().isPlacementValid(tile, pos,game);
    }

    public boolean isGameOver(Game game) {
        Player p1 = game.getPlayer1();
        Player p2 = game.getPlayer2();

        boolean rack1Empty = p1.getRack().isEmpty();
        boolean rack2Empty = p2.getRack().isEmpty();
        // On suppose que TileUtils.drawTile() retourne null quand plus de tuiles dans la pioche.
        boolean deckEmpty = (p1.getDeck().isEmpty() && p2.getDeck().isEmpty());
        // Variante stricte : la partie est finie dès que les deux racks sont vides ET la pioche est vide.
        return rack1Empty && rack2Empty && deckEmpty;
    }
    
}