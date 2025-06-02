package latice.model.board;

import java.util.ArrayList;
import java.util.List;

import latice.model.Game;
import latice.model.Player;
import latice.model.tiles.Tile;

public class Board {
    private final int size = 9;
    private Cell[][] grid;
    private Game game;

    public Board() {
        grid = new Cell[size][size]; 
        initializeBoard();          
    }

    private void initializeBoard() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                // Soleil aux 4 coins + centre + autres positions
                if ((row == 0 && col == 0) || 
                		(row == 0 && col == size - 1) ||
                		(row == size - 1 && col == 0) ||
                		(row == size - 1 && col == size - 1) ||
                		
                		(row == 0  && col == 4) ||
                		(row == 1  && col == 7) ||
                		(row == 4  && col == 0)||
                		(row == 7  && col == 1)||
                		(row == 6  && col == 2)||
                		(row == 7  && col == 7)||
                		(row == 6  && col == 6)||
                		(row == 4  && col == 8)||
                		(row == 8  && col == 4)||
                		(row == 1  && col == 1)||
        				(row == 2  && col == 2)||
                		(row == 2  && col == 6))
                {
                    grid[row][col] = new Cell(CellType.SUN);
                } else if((row == 4  && col == 4)) {
                	grid[row][col] = new Cell(CellType.MOON);
                }else {
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
    
	
	public void placeTile(Tile tile, Position pos,Game game) {
		
		if (isPlacementValid(tile, pos,game)) {
	        throw new IllegalArgumentException("Placement invalide.");
	    }

	    Cell cell = getCell(pos.getPosX(), pos.getPosY());
	    cell.setTile(tile);
	    tile.InGame(true);
	}
	
	public boolean isPlacementValid(Tile tile, Position pos, Game game) {
	    int row = pos.getPosX();
	    int col = pos.getPosY();
	    
	    // 1) Vérifier les limites du plateau
	    if (row < 0 || row >= size || col < 0 || col >= size) {
	        return false;
	    }
	    
	    // 2) La case doit être vide
	    Cell cell = getCell(row, col);
	    if (!cell.isEmpty()) {
	        return false;
	    }
	    
	    // Calcul du centre (pour size = 9 → centre en (4,4))
	    int center = size / 2;
	    
	    // 3) Premier tour : placement uniquement au centre
	    //if (game.getRound() == 1) {
	    //    return row == center && col == center;
	    //}
	    boolean emptyBoard = true;
	    for (int r = 0; r < size && emptyBoard; r++) {
	        for (int c = 0; c < size; c++) {
	            if (getCell(r, c).getTile() != null) {
	                emptyBoard = false;
	                break;
	            }
	        }
	    }
	    
	    if (emptyBoard) {
	    	return row == center && col == center;
	    }
	    // 4) Tours suivants : il faut au moins une tuile adjacente de même couleur
	    List<Tile> adjacents = getAdjacentTiles(pos);
	    for (Tile adj : adjacents) {
	        if (adj.getColor() == tile.getColor()) {
	            return true;
	        }else if (adj.getSymbol() == tile.getSymbol()) {
	        	return true;
	        }
	    }
	    
	    // Sinon on refuse
	    return false;
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

		