package latice.model.board;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import latice.model.tiles.Tile;

public class Board {
    private static int size = 9;
    private Cell[][] grid;

    public Board() {
        grid = new Cell[size][size]; 
        initializeBoard();          
    }

    private Set<String> collectSunKeys() {
        Set<String> keys = new HashSet<>();

        // 4 coins
        keys.add("0-0");
        keys.add("0-" + (size - 1));
        keys.add((size - 1) + "-0");
        keys.add((size - 1) + "-" + (size - 1));

        // autres emplacements “soleil”
        keys.add("0-4");
        keys.add("1-7");
        keys.add("4-0");
        keys.add("7-1");
        keys.add("6-2");
        keys.add("7-7");
        keys.add("6-6");
        keys.add("4-8");
        keys.add("8-4");
        keys.add("1-1");
        keys.add("2-2");
        keys.add("2-6");

        return keys;
    }

    
    private void initializeBoard() {
        Set<String> sunKeys = collectSunKeys();
        String centerKey = (size / 2) + "-" + (size / 2);

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                String key = row + "-" + col;

                if (sunKeys.contains(key)) {
                    grid[row][col] = new Cell(CellType.SUN);
                } else if (key.equals(centerKey)) {
                    grid[row][col] = new Cell(CellType.MOON);
                } else {
                    grid[row][col] = new Cell(CellType.NORMAL);
                }
            }
        }
    }

    
    public boolean hasTileIn() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                Cell cell = grid[row][col];
                if (cell == null) continue;

                CellType type = cell.getType(); // suppose que tu as une méthode getType()

                if (type == CellType.TILE_IN) {
                    return true; 
            }
        }
    }
		return false;
    }

	
	public void setGrid(Cell[][] grid) {
		this.grid = grid;
	}

	public int getSize() {
        return size;
    }

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    public Cell[][] getGrid() {
        return grid;
    }
    
	
	public void placeTile(Tile tile, Position pos) {
		
		if (isPlacementValid(tile, pos)) {
	        throw new IllegalArgumentException("Placement invalide.");
	    }

	    Cell cell = getCell(pos.getPosX(), pos.getPosY());
	    cell.setTile(tile);
	    tile.InGame(true);
	}
	
	public boolean isPlacementValid(Tile tile, Position pos) {
	    int row = pos.getPosX();
	    int col = pos.getPosY();

	    if (!isWithinBounds(row, col) || !isCellEmpty(row, col)) {
	        return false;
	    }

	    if (isEmptyBoard()) {
	        return isCenterPosition(row, col);
	    }

	    return hasValidAdjacents(tile, pos);
	}

	private boolean isWithinBounds(int row, int col) {
	    return row >= 0 && row < size && col >= 0 && col < size;
	}

	private boolean isCellEmpty(int row, int col) {
	    return getCell(row, col).isEmpty();
	}

	private boolean isEmptyBoard() {
	    for (int r = 0; r < size; r++) {
	        for (int c = 0; c < size; c++) {
	            if (getCell(r, c).getTile() != null) {
	                return false;
	            }
	        }
	    }
	    return true;
	}

	private boolean isCenterPosition(int row, int col) {
	    int center = size / 2;
	    return row == center && col == center;
	}

	private boolean hasValidAdjacents(Tile tile, Position pos) {
	    List<Tile> adjacents = getAdjacentTiles(pos);
	    if (adjacents == null || adjacents.isEmpty()) {
	        return false;
	    }

	    for (Tile adj : adjacents) {
	        if (adj == null) {
	            continue;
	        }
	        boolean sameColor  = adj.getColor() == tile.getColor();
	        boolean sameSymbol = adj.getSymbol() == tile.getSymbol();
	        if (!(sameColor || sameSymbol)) {
	            return false;
	        }
	    }
	    return true;
	}


	public List<Tile>  getAdjacentTiles(Position pos){
		
		List<Tile> adjacentTiles = new ArrayList<>();
	    int row = pos.getPosX();
	    int col = pos.getPosY();
	    
	    // 4 directions autour d'une case
	    int[][] directions = { {-1,0}, {1,0}, {0,-1}, {0,1} };

	    for (int[] dir : directions) {
	        int newRow = row + dir[0];
	        int newCol = col + dir[1];
	        
	        // On vérifie que la case voisine est dans les limites du plateau
	        if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
	        	// On récupère la tuile située dans la case voisine, si il y en a une 
	            Tile neighborTile = grid[newRow][newCol].getTile();
	            // Ensuite si il y en a une, on l'ajoute a la liste
	            if (neighborTile != null) {
	                adjacentTiles.add(neighborTile);
	            }
	        }
		    }
	
	    return adjacentTiles;
	}

	public Cell getCell(Position pos) {
	    return getCell(pos.getPosX(), pos.getPosY());
	}

}

		