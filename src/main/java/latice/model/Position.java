package latice.model;

public class Position {
	private Integer posX;
	private Integer posY;
	
	public Position(Integer posX, Integer posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public Integer getPosX() {
		return posX;
	}

	public void posX(Integer posX) {
		this.posX = posX;
	}

	public Integer getPosY() {
		return posY;
	}

	public void posY(Integer posY) {
		this.posY = posY;
	}
}
