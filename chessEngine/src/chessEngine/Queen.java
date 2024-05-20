package chessEngine;

public class Queen extends Piece {
	public boolean isPromoted = false;
	
	int[] whitePsqt = {
		6,   1,  -8,-104,  69,  24,  88,  26,
        14,  32,  60, -10,  20,  76,  57,  24,
        -2,  43,  32,  60,  72,  63,  43,   2,
         1, -16,  22,  17,  25,  20, -13,  -6,
       -14, -15,  -2,  -5,  -1, -10, -20, -22,
       -30,  -6, -13, -11, -16, -11, -16, -27,
       -36, -18,   0, -19, -15, -15, -21, -38,
       -39, -30, -31, -13, -31, -36, -34, -42
	};
	
	int[] blackPsqt = {
		-39, -30, -31, -13, -31, -36, -34, -42,
		-36, -18,   0, -19, -15, -15, -21, -38,
		-30,  -6, -13, -11, -16, -11, -16, -27,
		-14, -15,  -2,  -5,  -1, -10, -20, -22,
		1, -16,  22,  17,  25,  20, -13,  -6,
		-2,  43,  32,  60,  72,  63,  43,   2,
		14,  32,  60, -10,  20,  76,  57,  24,
		6,   1,  -8,-104,  69,  24,  88,  26
	};
	
	
	
	public Queen(int position, boolean isWhite, Game game) {
		super(position, isWhite, game);
		attackOrder = 1;
		resetPossibleMoves();
	}
	
	public int getValue() {
		if(isWhite) {
			return 929 + whitePsqt[position - 1];
		} else {
			return -1 * (929 + blackPsqt[position - 1]);
		}
	}

	public String toString() {
		if(isWhite) {
			return "♕";
		}
		return "♛";
	}
	
	public String letterToString() {
		if(isWhite) {
			return "Q";
		}
		return "q";
	}
	
	@Override
	public void resetPossibleMoves() {
		possibleMoves.clear();
		possibleAttacks.clear();
		protectingSquares.clear();

		
		// BISHOP MOVES
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
		
		// ROOK MOVES
		
		for(int i = 1; i <= 7; i++) { // left
			int move = i * (-1) + position; 
			if(move % 8 == 0) { // end of board, left
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
		
		for(int i = 1; i <= 7; i++) { // right
			int move = i * (1) + position; 
			if(move % 8 == 1) { // end of board, right
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
		
		for(int i = 1; i <= 7; i++) { // up
			int move = i * (-8) + position; 
			if(move < 1) { // end of board, top
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
		
		for(int i = 1; i <= 7; i++) { // up
			int move = i * (8) + position; 
			if(move > 64) { // end of board, top
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
