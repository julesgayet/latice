package latice.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import latice.model.board.Board;
import latice.model.board.Cell;
import latice.model.board.CellType;
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

        boolean p1Empty = p1.getRack().isEmpty() && p1.getDeck().isEmpty();
        boolean p2Empty = p2.getRack().isEmpty() && p2.getDeck().isEmpty();

        // Fin de partie si AU MOINS un joueur a à la fois rack vide et pioche vide
        return p1Empty || p2Empty || (game.getRound()==20);
    }
    
    public int calculatePoints(Board board, Tile tile, Position pos) {
        // 1) Recherche des voisins adjacents
        List<Tile> adjacents = board.getAdjacentTiles(pos);
        int matchingCount = 0;
        for (Tile voisin : adjacents) {
            if (voisin.getColor().equals(tile.getColor())
             || voisin.getSymbol().equals(tile.getSymbol())) {
                matchingCount++;
            }
        }

        // 2) Points liés aux combos
        int comboPoints = 0;
        switch (matchingCount) {
            case 2:
                comboPoints = 1;
                break;
            case 3:
                comboPoints = 2;
                break;
            case 4:
                comboPoints = 4;
                break;
            default:
                comboPoints = 0;
        }

        // 3) Bonus case SUN
        Cell cell = board.getCell(pos);
        int sunBonus = (cell.getType() == CellType.SUN) ? 2 : 0;

        return comboPoints + sunBonus;
    }

    public void applyScore(Game game, Player player, Tile tile, Position pos) {
        Board board = game.getBoard();
        int points = calculatePoints(board, tile, pos);
        player.addScore(points);
    }


	public Player getWinner(Game game) {
		return game.getWinner();
	}
    
}