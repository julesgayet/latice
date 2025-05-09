package latice.model;
import latice.model.tiles.Tile;

public class Cell {
	
    private CellType type;
    private Tile tile; // peut rester null pour l’instant

    public Cell(CellType type) {
        this.type = type;
        this.tile = null;
    }

    public CellType getType() {
        return type;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public boolean isEmpty() {
        return tile == null;
    }
}
