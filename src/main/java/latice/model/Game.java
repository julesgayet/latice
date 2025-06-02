package latice.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import latice.model.board.Board;
import latice.model.board.Position;
import latice.model.tiles.Color;
import latice.model.tiles.Symbol;
import latice.model.tiles.Tile;


public class Game {
	
	private Player player1;
	private Player player2;
	private Board board;
	private Player currentPlayer;
	private Boolean isOnGoing;
	private int round;
	
	public Game(Player p1, Player p2, Board board, Boolean isOnGoing) {
	        this.player1 = p1;
	        this.player2 = p2;
	        this.board = board;
	        this.currentPlayer = firstPlayer(p1,p2);
	        this.isOnGoing = isOnGoing;
	        this.round = 1;
	    }
	
	
	public static List<Tile> generateAllTiles() {
	    List<Tile> allTiles = new ArrayList<>();
	    int idCounter = 1;
	    for (Color color : Color.values()) {
	        for (Symbol symbol : Symbol.values()) {
	            for (int i = 0; i < 2; i++) {
	                allTiles.add(new Tile(idCounter++, color, symbol));
	            }
	        }
	    }
	    return allTiles;
	}
	
	
	
	public Player firstPlayer(Player p1, Player p2) {
		
	        Random rand = new Random();
	        int nombre = rand.nextInt(2);
	        
	        if (nombre == 1) {
	        	return p1;
	    } else {
	    	return p2;
	    }
	}
	
	public boolean isFirstTurn(Tile tile, Position pos, Player player,Game game) {
        // Vérifie si c'est le premier tour
        if (getRound() == 1) {
            // Seul le joueur courant peut jouer
            if (getCurrentPlayer() != player) {
                return false;
            } 

            // La tuile doit être posée au centre du plateau
            if (pos.getPosX() != 4 || pos.getPosY() != 4) {
                return false;
            }

            //  la position doit être valide selon les règles normales
            return getBoard().isPlacementValid(tile, pos,game);
        }

        // Si ce n'est pas le premier tour, on ne vérifie rien ici
        return true;
    }
	
	public void nextPlayer() {
		Player current = this.getCurrentPlayer();
		if (current.getName()== this.getPlayer1().getName()) {
			this.setCurrentPlayer(getPlayer2());
		}else {
			this.setCurrentPlayer(getPlayer1());
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
	
	public void switchTurn() {
	    this.currentPlayer = (this.currentPlayer == player1) ? player2 : player1;
	    this.round++;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}
	
}
