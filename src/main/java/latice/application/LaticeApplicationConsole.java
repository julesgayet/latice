package latice.application;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import latice.model.Game;
import latice.model.Player;
import latice.model.board.Board;
import latice.model.board.Cell;
import latice.model.board.CellType;
import latice.model.tiles.*;

public class LaticeApplicationConsole {
	
	public static void main(String[] args) {
		System.out.println("-------------------------------------------------------");
		System.out.println("--  Bienvenue dans notre magnifique jeu de latice !  --");
		System.out.println("--  Develloppé par Jules		     	     --");	
		System.out.println("--  et par Mostapha				     --");
		System.out.println("--  et par Ahmed				     --");
		System.out.println("--  et par Milan				     --");
		System.out.println("-------------------------------------------------------");
		
		
		 
        Player player1 = new Player("Player 1", new ArrayList<>(), new ArrayList<>(), 0, 1);
        Player player2 = new Player("Player 2", new ArrayList<>(), new ArrayList<>(), 0, 1);

        // 2. Création de la liste de toutes les tuiles du jeu
        List<Tile> allTiles = new ArrayList<>();
        int idCounter = 1;

        // On crée deux exemplaires de chaque combinaison couleur + symbole
        for (Color color : Color.values()) {
            for (Symbol symbol : Symbol.values()) {
                for (int i = 0; i < 2; i++) {
                    Tile tile = new Tile(idCounter++, color, symbol, false);
                    allTiles.add(tile);
                }
            }
        }

        // 3. Mélanger les tuiles
        Collections.shuffle(allTiles);

        // 4. Création de la partie
        Game game = new Game(player1, player2, new Board(), true);

        // 5. Assigner la réserve aux joueurs
        player1.Deck(allTiles);
        player2.Deck(allTiles); // partage la même réserve

        // 6. Affichage des racks avant remplissage
        System.out.println("Rack de " + player1.getName() + " avant : " + player1.getRack().size());
        System.out.println("Rack de " + player2.getName() + " avant : " + player2.getRack().size());

        
        Game.fillRack(player1);
        Game.fillRack(player2);

        // 8. Affichage des racks après remplissage
        System.out.println("Rack de " + player1.getName() + " après : " + player1.getRack().size());
        System.out.println("Rack de " + player2.getName() + " après : " + player2.getRack().size());

        // 9. Affichage final des tuiles dans les racks
        System.out.println(player1.getName() + " tuiles dans le rack : " + player1.getRack());
        System.out.println(player2.getName() + " tuiles dans le rack : " + player2.getRack());
        
	     // 10. Affichage du plateau de jeu
		    Board board = game.getBoard();  // Récupère le plateau utilisé dans le jeu
		    System.out.println("Plateau de jeu :");

		    for (int row = 0; row < board.getSize(); row++) {
		        for (int col = 0; col < board.getSize(); col++) {
		            Cell cell = board.getCell(row, col);
		            if (cell.getType() == CellType.SUN) {
	                    System.out.print(" ☀️ ");
	                }  else if (cell.getType() == CellType.MOON) {
	                    System.out.print(" 🌙 ");
	                } else {
	                    System.out.print(" ⚫ ");
	                }
		       
		        }
		        System.out.println();
		}	

		
	} 
}
	

