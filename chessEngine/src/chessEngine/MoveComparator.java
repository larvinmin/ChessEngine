package chessEngine;
import java.util.Comparator;


public class MoveComparator implements Comparator<Move> {

	@Override
	public int compare(Move m1, Move m2) {
		return Integer.compare(m2.score,m1.score);
	}
	
	
	
	
}
