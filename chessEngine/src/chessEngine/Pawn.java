package chessEngine;


public class Pawn extends Piece {
		
	int[] whitePsqt = {
		0,   0,   0,   0,   0,   0,   0,   0,
        78,  83,  86,  73, 102,  82,  85,  90,
         7,  29,  21,  44,  40,  31,  44,   7,
       -17,  16,  -2,  15,  14,   0,  15, -13,
       -26,   3,  10,   9,   6,   1,   0, -23,
       -22,   9,   5, -11, -10,  -2,   3, -19,
       -31,   8,  -7, -37, -36, -14,   3, -31,
         0,   0,   0,   0,   0,   0,   0,   0
	};
	
	int[] blackPsqt = {
		 0,   0,   0,   0,   0,   0,   0,   0,
		 -31,   8,  -7, -37, -36, -14,   3, -31,
		 -22,   9,   5, -11, -10,  -2,   3, -19,
		 -26,   3,  10,   9,   6,   1,   0, -23,
		 -17,  16,  -2,  15,  14,   0,  15, -13,
		  7,  29,  21,  44,  40,  31,  44,   7,
		 78,  83,  86,  73, 102,  82,  85,  90,
		  0,   0,   0,   0,   0,   0,   0,   0
	};
	
	
	public int getValue() {
		if(isWhite) {
			return 100 + whitePsqt[position - 1];
		} else {
			return -1 * (100 + blackPsqt[position - 1]);
		}
	}

	
	public Pawn(int position, boolean isWhite, Game game) {
		super(position, isWhite, game);
		attackOrder = 5;
		resetPossibleMoves();
	}
	
	public void changePosition(int newPosition) {
		super.changePosition(newPosition);
		
		if(isWhite && newPosition <= 8) {
			promotion(newPosition);
		}
		
		if(!isWhite && newPosition >= 57) {
			promotion(newPosition);
		}
		
	}
	
	public void promotion(int newPosition) {
		
		game.pieceList.remove(this);
		game.pieceMap.remove(position);
		
		game.promoteQueen(position, isWhite);
				
		game.reloadPieceMap();
		game.reloadAllPossibleMoves();	
		
	}
	
	public String toString() {
		if(isWhite) {
			return "♙";
		}
		return "♟";
	}
	
	public String letterToString() {
		if(isWhite) {
			return "P";
		}
		return "p";
	}
	
	public void resetPossibleMoves() {
		possibleMoves.clear();
		possibleAttacks.clear();
		protectingSquares.clear();
		
		if(isWhite) { // case for white pawns		
			if(game.checkSpaceTaken(position - 9) && position % 8 != 1) { // there is a piece in the diagonal
				if(game.pieceMap.get(position - 9).isWhite != isWhite) { // piece is opposite color
					possibleMoves.put(position - 9, true);
					possibleAttacks.put(position - 9, true);
				} else { // same color
					protectingSquares.put(position - 9, true); // add piece to protected squares
				}
			}
			if(game.checkSpaceTaken(position - 7) ) { // there is a piece in the diagonal
				if(game.pieceMap.get(position - 7).isWhite != isWhite) { // piece is opposite color
					possibleMoves.put(position - 7, true);
					possibleAttacks.put(position - 7, true);
				} else {
					protectingSquares.put(position - 7, true); // add piece to protected squares
				}
			}
			if(game.checkSpaceTaken(position - 8)) { // blocked by something right in front
				return;
			}
			if(position >= 49 && position <= 58) { // case for starting row
				if(game.checkSpaceTaken(position - 16)) { // blocked by something 2 in front
					possibleMoves.put(position - 8, true);
					return;
				}
				possibleMoves.put(position - 8, true);
				possibleMoves.put(position - 16, true);
				return;
			}
			possibleMoves.put(position - 8, true); // nothing in front, not in first row
		}
		
		if(!isWhite) { // case for black pawns
			if(game.checkSpaceTaken(position + 9) && position % 8 != 0) {  // there is a piece in the diagonal
				if(game.pieceMap.get(position + 9).isWhite != isWhite) { // piece is opposite color
					possibleMoves.put(position + 9, true);
					possibleAttacks.put(position + 9, true);
				} else {
					protectingSquares.put(position + 9, true); // add piece to protected squares
				}
			}
			if(game.checkSpaceTaken(position + 7)) {  // there is a piece in the diagonal
				if(game.pieceMap.get(position + 7).isWhite != isWhite) { // piece is opposite color
					possibleMoves.put(position + 7, true);
					possibleAttacks.put(position + 7, true);
				} else {
					protectingSquares.put(position + 7, true); // add piece to protected squares
				}
			}
			if(game.checkSpaceTaken(position + 8)) { // blocked by something right in front
				return;
			}
			if(position >= 9 && position <= 16) { // case for starting row
				if(game.checkSpaceTaken(position + 16)) { // blocked by something 2 in front
					possibleMoves.put(position + 8, true);
					return;
				}
				possibleMoves.put(position + 8, true);
				possibleMoves.put(position + 16, true);
				return;
			}
			possibleMoves.put(position + 8, true); // nothing in front, not in first row
			
			seesKing();
		}
	}


	
	

}
