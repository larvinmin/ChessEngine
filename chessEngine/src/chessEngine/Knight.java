package chessEngine;

public class Knight extends Piece {
	
	int[] whitePsqt = {
		-66, -53, -75, -75, -10, -55, -58, -70,
        -3,  -6, 100, -36,   4,  62,  -4, -14,
        10,  67,   1,  74,  73,  27,  62,  -2,
        24,  24,  45,  37,  33,  41,  25,  17,
        -1,   5,  31,  21,  22,  35,   2,   0,
       -18,  10,  13,  22,  18,  15,  11, -14,
       -23, -15,   2,   0,   2,   0, -23, -20,
       -74, -23, -26, -24, -19, -35, -22, -69
	};
	
	int[] blackPsqt = {
		-74, -23, -26, -24, -19, -35, -22, -69,
		-23, -15,   2,   0,   2,   0, -23, -20,
		-18,  10,  13,  22,  18,  15,  11, -14,
		 -1,   5,  31,  21,  22,  35,   2,   0,
		 24,  24,  45,  37,  33,  41,  25,  17,
		 10,  67,   1,  74,  73,  27,  62,  -2,
		 -3,  -6, 100, -36,   4,  62,  -4, -14,
		-66, -53, -75, -75, -10, -55, -58, -70
	};
	
	
	public Knight(int position, boolean isWhite, Game game) {
		super(position, isWhite, game);
		attackOrder = 4;
		resetPossibleMoves();
	}
	
	public int getValue() {
		if(isWhite) {
			return 280 + whitePsqt[position - 1];
		} else {
			return -1 * (280 + blackPsqt[position - 1]);
		}
	}
	
	public String toString() {
		if(isWhite) {
			return "♘";
		}
		return "♞";
	}
	
	public String letterToString() {
		if(isWhite) {
			return "N";
		}
		return "n";
	}

	@Override
	public void resetPossibleMoves() {	
		possibleMoves.clear();
		possibleAttacks.clear();
		protectingSquares.clear();

		outer: {
			int move = position - 17;
			if(!(position - 17 < 1) && !(position % 8 == 1)) { // up 2 left 1, check in bounds
				if(game.pieceMap.get(position - 17) != null) { // check if null
					if(game.pieceMap.get(position - 17).isWhite == isWhite) { // same color piece in the way
						protectingSquares.put(move, true); // add piece to protected squares
						break outer;
					} else {
						possibleAttacks.put(position - 17, true); // attacking opponent
					}
				}		
				possibleMoves.put(position - 17, true); // unobstructed
			}
		}
		outer: {
			int move = position - 15;
			if(!(position - 15 < 1) && !(position % 8 == 0)) { // up 2 right 1, check in bounds
				if(game.pieceMap.get(position - 15) != null) { // check if null
					if(game.pieceMap.get(position - 15).isWhite == isWhite) { // same color piece in the way
						protectingSquares.put(move, true); // add piece to protected squares
						break outer;
					} else {
						possibleAttacks.put(position - 15, true);
					}
				}
				possibleMoves.put(position - 15, true);
			}
		}
		outer: {
			int move = position - 10;
			if(!(position - 10 < 1) && !(position % 8 == 1) && !(position % 8 == 2)) { // up 1 left 2
				if(game.pieceMap.get(position - 10) != null) { // check if null
					if(game.pieceMap.get(position - 10).isWhite == isWhite) { // same color piece in the way
						protectingSquares.put(move, true); // add piece to protected squares
						break outer;
					} else {
						possibleAttacks.put(position - 10, true);
					}
				}
				possibleMoves.put(position - 10, true);
			}
		}
		outer: {
			int move = position - 6;
			if(!(position - 6 < 1) && !(position % 8 == 7) && !(position % 8 == 0)) { // up 1 right 2
				if(game.pieceMap.get(position - 6) != null) { // check if null
					if(game.pieceMap.get(position - 6).isWhite == isWhite) { // same color piece in the way
						protectingSquares.put(move, true); // add piece to protected squares
						break outer;
					} else {
						possibleAttacks.put(position - 6, true);
					}
				}
				possibleMoves.put(position - 6, true);
			}
		}
		outer: {
			int move = position + 6;
			if(!(position + 6 > 64) && !(position % 8 == 1) && !(position % 8 == 2)) { // down 1 left 2
				if(game.pieceMap.get(position + 6) != null) { // check if null
					if(game.pieceMap.get(position + 6).isWhite == isWhite) { // same color piece in the way
						protectingSquares.put(move, true); // add piece to protected squares
						break outer;
					} else {
						possibleAttacks.put(position + 6, true);
					}
				}
				possibleMoves.put(position + 6, true);
			}
		}
		outer: {
			int move = position - 10;
			if(!(position + 10 > 64) && !(position % 8 == 7) && !(position % 8 == 0)) { // down 1 right 2
				if(game.pieceMap.get(position + 10) != null) { // check if null
					if(game.pieceMap.get(position + 10).isWhite == isWhite) { // same color piece in the way
						protectingSquares.put(move, true); // add piece to protected squares
						break outer;
					} else {
						possibleAttacks.put(position + 10, true);
					}
				}
				possibleMoves.put(position + 10, true);
			}
		}
		outer: {
			int move = position + 15;
			if(!(position + 15 > 64) && !(position % 8 == 1)) { // down 2 left 1
				if(game.pieceMap.get(position + 15) != null) { // check if null
					if(game.pieceMap.get(position + 15).isWhite == isWhite) { // same color piece in the way
						protectingSquares.put(move, true); // add piece to protected squares
						break outer;
					} else {
						possibleAttacks.put(position + 15, true);
					}
				}
				possibleMoves.put(position + 15, true);
			}
		}
		outer: {
			int move = position + 17;
			if(!(position + 17 > 64) && !(position % 8 == 0)) { // down 2 left 1
				if(game.pieceMap.get(position + 17) != null) { // check if null
					if(game.pieceMap.get(position + 17).isWhite == isWhite) { // same color piece in the way
						protectingSquares.put(move, true); // add piece to protected squares
						break outer;
					} else {
						possibleAttacks.put(position + 17, true);

					}
				}
				possibleMoves.put(position + 17, true);
			}
		}
		seesKing();
	}




}
