package latice.model.tiles;

public class TileUtils {
	
	public static String getImagePath(Tile tile) {
		String path = "/img/" + tile.getColor() + "_" + tile.getSymbol() + ".png";
        return path.toLowerCase();
}
}
