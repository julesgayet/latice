package latice.model;

import java.util.ArrayList;
import java.util.List;

import latice.model.board.Position;
import latice.model.tiles.Tile;

public class Player {
	
	private String name;
    private List<Tile> deck = new ArrayList<>();
    private List<Tile> rack = new ArrayList<>();
    private Integer score = 0;
    private Integer availableActions = 1;
    
    public Player(String name, List<Tile> deck, List<Tile> rack, Integer score, Integer availableActions) {
        this.name = name;
        this.deck = deck;
        this.rack = rack;
        this.score = score;
        this.availableActions = availableActions;
    }
    
    public Player(List<Tile> deck, List<Tile> rack, Integer score, Integer availableActions) {
    	this("player",deck,rack,score,availableActions);
    }

    
    public Player(String name) {
        this.name = name;
        this.deck = new ArrayList<>();
        this.rack = new ArrayList<>();
        this.score = 0;
        this.availableActions = 1;
    }

    public void addScore(Integer score) {
    	this.Score(this.getScore()+ score);
    }
    
    public void buyExtraAction() {
        
    	//TODO
    }

    public List<Tile> swapRack() {
        // Exemple d’implémentation à adapter :
        if (deck.size() < rack.size()) {
            return null; // pas assez de tuiles dans le deck pour échanger
        }
        List<Tile> oldRack = new ArrayList<>(rack);
        // Retire toutes les tuiles actuelles et les remet dans le deck
        for (Tile t : oldRack) {
            deck.add(t);
        }
        rack.clear();
        // Pioche de nouvelles tuiles
        for (int i = 0; i < oldRack.size(); i++) {
            rack.add(deck.remove(0));
        }
        return oldRack;
    }
    
    public void passTurn() {
        
    	//TODO
    }
    
    
	public String getName() {
		return name;
	}
	
	public void Name(String name) {
		this.name = name;
	}
	
	public List<Tile> getDeck() {
		return deck;
	}
	
	public void Deck(List<Tile> deck) {
		this.deck = deck;
	}
	
	public List<Tile> getRack() {
		return rack;
	}
	
	public void Rack(List<Tile> rack) {
		this.rack = rack;
	}
	
	public int getScore() {
		return score;
	}
	
	public void Score(int score) {
		this.score = score;
	}
	
	public int getAvailableActions() {
		return availableActions;
	}
	
	public void AvailableActions(int availableActions) {
		this.availableActions = availableActions;
	}
}