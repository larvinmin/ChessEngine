package chessEngine;

public class King extends Piece {	

	int[] whitePsqt = {
	   	 4,  54,  47, -99, -99,  60,  83, -62,
	   -32,  10,  55,  56,  56,  55,  10,   3,
	   -62,  12, -57,  44, -67,  28,  37, -31,
	   -55,  50,  11,  -4, -19,  13,   0, -49,
	   -55, -43, -52, -28, -51, -47,  -8, -50,
	   -47, -42, -43, -79, -64, -32, -29, -32,
	    -4,   3, -14, -50, -57, -18,  13,   4,
	    17,  30,  -3, -14,   6,  -1,  40,  18
	};
	
	int[] blackPsqt = {
		 17,  30,  -3, -14,   6,  -1,  40,  18,
		 -4,   3, -14, -50, -57, -18,  13,   4,
		-47, -42, -43, -79, -64, -32, -29, -32,
		-55, -43, -52, -28, -51, -47,  -8, -50,
		-55,  50,  11,  -4, -19,  13,   0, -49,
		-62,  12, -57,  44, -67,  28,  37, -31,
		-32,  10,  55,  56,  56,  55,  10,   3,
	  	  4,  54,  47, -99, -99,  60,  83, -62
	};

	
	public boolean prevHasMoved = false;
	public boolean hasMoved = false;
	
	
	public King(int position, boolean isWhite, Game game) {
		super(position, isWhite, game);
		attackOrder = 0;
		
		if(isWhite) {
			game.whiteKing = this;
		}
		if(!isWhite) {
			game.blackKing = this;
		}
		resetPossibleMoves();
	}
	
	public int getValue() {
		if(isWhite) {
			return 60000 + whitePsqt[position - 1];
		} else {
			return -1 * (60000 + blackPsqt[position - 1]);
		}
	}
	
	public String toString() {
		if(isWhite) {
			return "♔";
		}
		return "♚";
	}
	
	public String letterToString() {
		if(isWhite) {
			return "K";
		}
		return "k";
	}
	
	public void checkGameOver() {
		possibleAttacks.clear();
		possibleMoves.clear();
		protectingSquares.clear();

		
		if(possibleMoves.isEmpty()) {
			if(isWhite) { // check for checkmate
				if(game.howManyBlackPiecesSee.get(position) != null) {
					game.gameOver = true;
					game.blackCheckMate = true;
				}
			} else { // check for checkmate
				if(game.howManyWhitePiecesSee.get(position) != null) {
					game.gameOver = true;
					game.whiteCheckMate = true;
				}
			}
			
			if(game.moveNumber >= 10) {
				if(isWhite) { // check for stalemate
					if(!game.checkIfWhiteHasPossibleMoves()) {
						game.gameOver = true;
					}
				} else { // check for stalemate, black 
					if(!game.checkIfBlackHasPossibleMoves()) {
						game.gameOver = true;
					}

				}
			}
		}
	}
	
	public void changePosition(int newPosition) {
		if(position == 61) { // white castle check: move rook
			if(newPosition == 59) {
				int[] array = {57, 60};
				Move m = new Move(array, 0);
				game.movePiece(m);
				
				
//				Rook r = (Rook) game.pieceMap.get(57);
//				r.changePosition(60);
			}
			if(newPosition == 63) {
				int[] array = {64, 62};
				Move m = new Move(array, 0);
				game.movePiece(m);
				
//				Rook r = (Rook) game.pieceMap.get(64);
//				r.changePosition(62);
			}
		}
		
		if(position == 5) { // black castle check: move rook
			if(newPosition == 3) {
				int[] array = {1, 4};
				Move m = new Move(array, 0);
				game.movePiece(m);
				
//				Rook r = (Rook) game.pieceMap.get(1);
//				r.changePosition(4);
			}
			if(newPosition == 7) {
				int[] array = {8, 6};
				Move m = new Move(array, 0);
				game.movePiece(m);
				
//				Rook r = (Rook) game.pieceMap.get(8);
//				r.changePosition(6);
			}
		}
		
		super.changePosition(newPosition);
		hasMoved = true;
	}
	
	
	public void resetPossibleMoves() {
		possibleMoves.clear();
		possibleAttacks.clear();
		game.reloadcheckHowManyPiecesSee();
		
		checkGameOver();
		
		resetPossibleMovesHelper(-9);
		resetPossibleMovesHelper(-8);
		resetPossibleMovesHelper(-7);
		resetPossibleMovesHelper(-1);
		resetPossibleMovesHelper(1);
		resetPossibleMovesHelper(7);
		resetPossibleMovesHelper(8);
		resetPossibleMovesHelper(9);
		checkCastle();
		

	}

	
	public void resetPossibleMovesHelper(int direction) {
			int move = position + direction;
			if((move < 1) || move > 64) { // up out of bounds up down
				return;
			}
			if(direction == -9 || direction == -1 || direction == 7) { // out of bounds left
				if(move % 8 == 0) {
					return;
				}
			}
			if(direction == 9 || direction == 1 || direction == -7) { // out of bounds right
				if(move % 8 == 1) {
					return;
				}
			}
			if(isWhite) {
				if(game.checkHowManyBlackPiecesSee(move) != 0) {
					return;
				}
			} else {
				if(game.checkHowManyWhitePiecesSee(move) != 0) {
					return;
				}
			}
			
			if(game.pieceMap.get(move) != null) { // meets piece in the way
				if(game.pieceMap.get(move).isWhite == isWhite) { // same color
					protectingSquares.put(move, true); // add piece to protected squares
					return;
				} else { // opposite color: can take
					if(game.protectedPieceSquares.get(move) == null) {
						possibleAttacks.put(move, true);
					} else {
						return;
					}
				}
			}
			possibleMoves.put(move, true);
		
	}

	
	public void checkCastle() {
		boolean leftCastle = true;
		boolean rightCastle = true;
		
		if(this.hasMoved) { // instant quit if king has moved 
			leftCastle = false;
			rightCastle = false;
			return;
		}
		// check if any enemy pieces attack in between castle
		if(this.isWhite) {
			if(game.howManyBlackPiecesSee.get(58) != null || game.howManyBlackPiecesSee.get(59) != null
					|| game.howManyBlackPiecesSee.get(60) != null) {
				leftCastle = false;
			}
			if(game.howManyBlackPiecesSee.get(62) != null || game.howManyBlackPiecesSee.get(63) != null) {
				rightCastle = false;
			}
		}
		// check if any enemy pieces attack in between castle
		if(!this.isWhite) {
			if(game.howManyWhitePiecesSee.get(2) != null || game.howManyWhitePiecesSee.get(3) != null
					|| game.howManyWhitePiecesSee.get(4) != null) {
				leftCastle = false;
			}
			if(game.howManyWhitePiecesSee.get(6) != null || game.howManyWhitePiecesSee.get(7) != null) {
				rightCastle = false;
			}
		}
		
		
	    if (this.isWhite) {
	    	if(leftCastle == true) {
		        leftCastle = isRookValidForCastle(57, true, true);
	    	}
	    	if(rightCastle == true) {
		        rightCastle = isRookValidForCastle(64, true, false);
	    	}
	    } else {
	    	if(leftCastle == true) {
	        leftCastle = isRookValidForCastle(1, false, true);
	    	}
	    	if(rightCastle == true) {
	        rightCastle = isRookValidForCastle(8, false, false);
	    	}
	    }
	    
	    if(this.isWhite) {
	    	if(leftCastle) {
	    		possibleMoves.put(59, true);
	    	}
	    	if(rightCastle) {
	    		possibleMoves.put(63, true);
	    	}
	    } else {
	    	if(leftCastle) {
	    		possibleMoves.put(3, true);
	    	}
	    	if(rightCastle) {
	    		possibleMoves.put(7, true);
	    	}
	    }
	}
	
	private boolean isRookValidForCastle(int rookPosition, boolean isWhite, boolean isLeftCastle) {
	    if (!(game.pieceMap.get(rookPosition) instanceof Rook)) {
	        return false;
	    } else {
	        Rook rook = (Rook) game.pieceMap.get(rookPosition);
	        if (rook.hasMoved) {
	            return false;
	        } else if (isWhite != rook.isWhite) {
	            return false; // Ensure rook color matches the player's color
	        } else if (!rook.seesKing) {
	            return false;
	        } else if (isLeftCastle && rookPosition % 8 != 1) {
	            return false; // Ensure left rook is in the correct initial position
	        } else if (!isLeftCastle && rookPosition % 8 != 0) {
	            return false; // Ensure right rook is in the correct initial position
	        }
	    }
	    return true; // Rook is valid for castling
	}



	
	

}
