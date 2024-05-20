package chessEngine;


public class Move{
	int[] array;
	int score;
	Piece lastCaptured;
	boolean isCastle;
	
	
	public Move(int[] array, int score) {
		this.array = array;
		this.score = score;
		lastCaptured = null;
		isCastle = false;
		moveCheckCastle();
	}
	
	public void moveCheckCastle() {
		int position1 = array[0];
		int position2 = array[1];
		
		if(position1 == 61 && position2 == 63) {
			isCastle = true;
		}
		if(position1 == 61 && position2 == 59) {
			isCastle = true;
		}
		if(position1 == 5 && position2 == 7) {
			isCastle = true;
		}
		if(position1 == 5 && position2 == 3) {
			isCastle = true;
		}
	}
	
	public int compare(Move m) {
		return Integer.compare(score, m.score);
	}
	
	public String toString() {
		return array[0] + " to " + array[1];
	}


	
}
