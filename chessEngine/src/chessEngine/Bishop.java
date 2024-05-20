package chessEngine;

public class Bishop extends Piece{
	
	int[] whitePsqt = {
		-59, -78, -82, -76, -23,-107, -37, -50,
        -11,  20,  35, -42, -39,  31,   2, -22,
         -9,  39, -32,  41,  52, -10,  28, -14,
         25,  17,  20,  34,  26,  25,  15,  10,
         13,  10,  17,  23,  17,  16,   0,   7,
         14,  25,  24,  15,   8,  25,  20,  15,
         19,  20,  11,   6,   7,   6,  20,  16,
         -7,   2, -15, -12, -14, -15, -10, -10
	};
	
	int[] blackPsqt = {
		 -7,   2, -15, -12, -14, -15, -10, -10,
		 19,  20,  11,   6,   7,   6,  20,  16,
		 14,  25,  24,  15,   8,  25,  20,  15,
		 13,  10,  17,  23,  17,  16,   0,   7,
		 25,  17,  20,  34,  26,  25,  15,  10,
		 -9,  39, -32,  41,  52, -10,  28, -14,
		-11,  20,  35, -42, -39,  31,   2, -22,
		-59, -78, -82, -76, -23,-107, -37, -50
	};
	
	
	
	public Bishop(int position, boolean isWhite, Game game) {
		super(position, isWhite, game);
		attackOrder = 3;
		resetPossibleMoves();
	}
	
	public int getValue() {
		if(isWhite) {
			return 320 + whitePsqt[position - 1];
		} else {
			return -1 * (320 + blackPsqt[position - 1]);
		}
	}
	
	public String toString() {
		if(isWhite) {
			return "♗";
		}
		return "♝";
	}
	
	public String letterToString() {
		if(isWhite) {
			return "B";
		}
		return "b";
	}

	@Override // check 4 diagonals, 
	public void resetPossibleMoves() {
		possibleMoves.clear();
		possibleAttacks.clear();
		protectingSquares.clear();


		for(int i = 1; i <= 7; i++) { // up left
			int move = i * (-9) + position; 
			if(move < 1 || move % 8 == 0) { // end of board, up or left
				break;
			}
			if(game.pieceMap.get(move) != null) { // meets piece in the way
				if(game.pieceMap.get(move).isWhite == isWhite) { // same color
					protectingSquares.put(move, true); // add piece to protected squares
					break;
				} else { // opposite color
					possibleAttacks.put(move, true);
					possibleMoves.put(move, true);
					break;
				}
			}
			possibleMoves.put(move, true);
		}
		
		for(int i = 1; i <= 7; i++) { // up right
			int move = i * (-7) + position; 
			if(move < 1 || move % 8 == 1) { // end of board, up or right
				break;
			}
			if(game.pieceMap.get(move) != null) { // meets piece in the way
				if(game.pieceMap.get(move).isWhite == isWhite) { // same color
					protectingSquares.put(move, true); // add piece to protected squares
					break;
				} else { // opposite color
					possibleAttacks.put(move, true);
					possibleMoves.put(move, true);
					break;
				}
			}
			possibleMoves.put(move, true);
		}
		
		for(int i = 1; i <= 7; i++) { // down left
			int move = i * (7) + position; 
			if(move > 64 || move % 8 == 0) { // end of board, bottom or left
				break;
			}
			if(game.pieceMap.get(move) != null) { // meets piece in the way
				if(game.pieceMap.get(move).isWhite == isWhite) { // same color
					protectingSquares.put(move, true); // add piece to protected squares
					break;
				} else { // opposite color
					possibleAttacks.put(move, true);
					possibleMoves.put(move, true);
					break;
				}
			}
			possibleMoves.put(move, true);
		}
		
		for(int i = 1; i <= 7; i++) { // down left
			int move = i * (9) + position; 
			if(move > 64 || move % 8 == 1) { // end of board, bottom or left
				break;
			}
			if(game.pieceMap.get(move) != null) { // meets piece in the way
				if(game.pieceMap.get(move).isWhite == isWhite) { // same color
					protectingSquares.put(move, true); // add piece to protected squares
					break;
				} else { // opposite color
					possibleAttacks.put(move, true);
					possibleMoves.put(move, true);
					break;
				}
			}
			possibleMoves.put(move, true);
		}
		seesKing();
	}


	
}
