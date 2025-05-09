package latice.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import latice.model.Board;
import latice.model.tiles.Color;
import latice.model.tiles.Symbol;
import latice.model.tiles.Tile;

public class Game {
	
	private Player player1;
	private Player player2;
	private Board board;
	private Player currentPlayer;
	private Boolean isOnGoing;
	
	public Game(Player p1, Player p2, Board board, Boolean isOnGoing) {
	        this.player1 = p1;
	        this.player2 = p2;
	        this.board = board;
	        this.currentPlayer = p1;
	        this.isOnGoing = isOnGoing;
	    }
	
	public void start() {
		
		List<Tile> allTiles = new ArrayList<>();
		
		int idCounter = 1;
		
		//On parcourt tout les types de couleurs
		for (Color color : Color.values()) {
			//Dans chaque couleur on va parcourir tout les types de symboles
	        for (Symbol symbol : Symbol.values()) {
	        	//Puis via une boucle on a 2 exemplaires de chaque combinaison de couleur et symbole
	            for (int i = 0; i < 2; i++) { 
	                Tile tile = new Tile(idCounter++, color, symbol, false);
	                allTiles.add(tile);
	            }
	        }
	    }	
	}
	
	

	 public Player getWinner() {
		return currentPlayer;
	 }
	
	
	public Player getPlayer1() {
		return player1;
	}
	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}
	public Player getPlayer2() {
		return player2;
	}
	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	public Boolean getIsOnGoing() {
		return isOnGoing;
	}
	public void setIsOnGoing(Boolean isOnGoing) {
		this.isOnGoing = isOnGoing;
	}
	
	
	public void switchTurn(Player player1, Player player2, Player currentPlayer) {
	    if (currentPlayer == player1) {
	        currentPlayer = player2;
	    } else {
	        currentPlayer = player1;
	    }
	}
	
	public void distributeTiles(List<Tile> allTiles, Player player1, Player player2) {
		
		// On crée les deux sous listes
		List<Tile> pile1 = new ArrayList<>();
		List<Tile> pile2 = new ArrayList<>();
		
		// On mélange les tuiles de façon aléatoire
        allTiles = shake(allTiles);

        // On calcule le point de séparation
        int mid = allTiles.size() / 2;


        pile1 = allTiles.subList(0, mid);
        pile2 = allTiles.subList(mid, allTiles.size());

        
        // On donne les piles aux joueurs
        player1.Deck(pile1);
        player2.Deck(pile2);
	}
	
	
	//Methode pour mélanger la pile de tuile 
    public static List<Tile> shake(List<Tile> allTiles) {
        List<Tile> res = new ArrayList<>(allTiles);
        Collections.shuffle(res);
        return res;
    }
	
	 //  Méthode de remplissage sûre (version statique ici pour les essais)
    public static void fillRack(Player player) {
        List<Tile> rack = player.getRack();
        List<Tile> reserve = player.getDeck();

        while (rack.size() < 5 && !reserve.isEmpty()) {
            rack.add(reserve.remove(0));
        }
    }

	
}
