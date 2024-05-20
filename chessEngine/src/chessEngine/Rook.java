package chessEngine;

public class Rook extends Piece {

	int[] whitePsqt = {
		35,  29,  33,   4,  37,  33,  56,  50,
        55,  29,  56,  67,  55,  62,  34,  60,
        19,  35,  28,  33,  45,  27,  25,  15,
         0,   5,  16,  13,  18,  -4,  -9,  -6,
       -28, -35, -16, -21, -13, -29, -46, -30,
       -42, -28, -42, -25, -25, -35, -26, -46,
       -53, -38, -31, -26, -29, -43, -44, -53,
       -30, -24, -18,   5,  -2, -18, -31, -32
	};
	
	int[] blackPsqt = {
		-30, -24, -18,   5,  -2, -18, -31, -32,
		-53, -38, -31, -26, -29, -43, -44, -53,
		-42, -28, -42, -25, -25, -35, -26, -46,
		-28, -35, -16, -21, -13, -29, -46, -30,
		0,   5,  16,  13,  18,  -4,  -9,  -6,
		19,  35,  28,  33,  45,  27,  25,  15,
		55,  29,  56,  67,  55,  62,  34,  60,
		35,  29,  33,   4,  37,  33,  56,  50
	};
	
	
	public boolean prevHasMoved = false;
	public boolean hasMoved = false;
	public boolean seesKing = false;
	
	public Rook(int position, boolean isWhite, Game game) {
		super(position, isWhite, game);
		attackOrder = 2;
		resetPossibleMoves();
	}
	
	public int getValue() {
		if(isWhite) {
			return 479 + whitePsqt[position - 1];
		} else {
			return -1 * (479 + blackPsqt[position - 1]);
		}
	}
	
	public String toString() {
		if(isWhite) {
			return "♖";
		}
		return "♜";
	}
	
	public String letterToString() {
		if(isWhite) {
			return "R";
		}
		return "r";
	}
	
	public void changePosition(int newPosition) {
		super.changePosition(newPosition);
		
		hasMoved = true;
	}

	@Override
	public void resetPossibleMoves() {
		possibleMoves.clear();
		possibleAttacks.clear();
		protectingSquares.clear();

		seesKing = false;
		
		for(int i = 1; i <= 7; i++) { // left
			int move = i * (-1) + position; 
			if(move % 8 == 0) { // end of board, left
				break;
			}
			if(game.pieceMap.get(move) != null) { // meets piece in the way
				if(game.pieceMap.get(move).isWhite == isWhite) { // same color
					if(isWhite && move == 61) { // check if rook sees king, for castle
						seesKing = true;
					}
					if(!isWhite && move == 5) {
						seesKing = true;
					}
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
					if(isWhite && move == 61) { // check if rook sees king, for castle
						seesKing = true;
					}
					if(!isWhite && move == 5) {
						seesKing = true;
					}
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
		
		for(int i = 1; i <= 7; i++) { // down
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
