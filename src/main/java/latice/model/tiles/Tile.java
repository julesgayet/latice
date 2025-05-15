package latice.model.tiles;

public class Tile {
	
	//attributs
	private Integer id;
	private Color color;
	private Symbol symbol;
	private Boolean inGame;
	
	//Constructeur
	public Tile(Integer id, Color color, Symbol symbol) {
		this.id = id;
		this.color = color;
		this.symbol = symbol;
		this.inGame = false;
	}

	//getteurs et setteurs
	public Integer getId() {
		return id;
	}

	public void Id(Integer id) {
		this.id = id;
	}

	public Color getColor() {
		return color;
	}

	public void Color(Color color) {
		this.color = color;
	}

	public Symbol getSymbol() {
		return symbol;
	}

	public void Symbol(Symbol symbol) {
		this.symbol = symbol;
	}

	public Boolean getInGame() {
		return inGame;
	}

	public void InGame(Boolean inGame) {
		this.inGame = inGame;
	}

}
