package playBot;
import java.util.Scanner;

import chessEngine.*;


public class PlayChess {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Game g = new Game();
		g.addBlack();
		g.addWhite();
		g.reloadPieceMap();
		g.reloadAllPossibleMoves();
		g.resetSortedPossibleMoves(true);
		
		// load a position
//		g.fenReader("any fen");
//		g.reloadPieceMap();
//		g.reloadAllPossibleMoves();
//		g.resetSortedPossibleMoves(true);
		
		while(true){
			g.chessBot();
			System.out.println();

			g.printGame();
			g.fenPrinter();
			System.out.println(g.staticEval());

			System.out.println("Enter your move (source position, target position):");
			int source = scanner.nextInt();
			int target = scanner.nextInt();

			int[] array = {source, target};
			
			Move m = new Move(array, 0);
			
		    boolean moveSuccessful = g.movePiece(m);
		    while (moveSuccessful == false) {
		        
		    	source = scanner.nextInt();
				target = scanner.nextInt();

				array[0] = source;
				array[1] = target;
				
				m = new Move(array, 0);
			    moveSuccessful = g.movePiece(m);
		    	
		    }
		    
			g.printGame();
			g.fenPrinter();
			System.out.println(g.staticEval());
		}
	}
}
