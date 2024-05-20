package chessEngine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;


public class Game {
	public HashMap<Integer, Piece> pieceMap;
	public ArrayList<Piece> pieceList;
	public HashMap<Integer, Integer> howManyPiecesSee;
	public HashMap<Integer, Integer> howManyWhitePiecesSee;
	public HashMap<Integer, Integer> howManyBlackPiecesSee;
	public HashMap<Integer, Boolean> protectedPieceSquares;
	public  ArrayList<Move> sortedPossibleMoves = new ArrayList<>();
	public boolean whiteMove;
	public boolean gameOver;
	public boolean whiteCheckMate;
	public boolean blackCheckMate;
	public int moveNumber;
	public int halfMove;
	public boolean kingBeingSeen;
	public Piece whiteKing = null;
	public Piece blackKing = null;

	
	public Game() {
		pieceList = new ArrayList<Piece>();
		pieceMap = new HashMap<>();
		howManyPiecesSee = new HashMap<>();
		howManyWhitePiecesSee = new HashMap<>();
		howManyBlackPiecesSee = new HashMap<>();
		protectedPieceSquares = new HashMap<>();
		sortedPossibleMoves = new ArrayList<Move>();
		whiteCheckMate = false;
		blackCheckMate = false;
		gameOver = false;
		whiteMove = true;
		kingBeingSeen = false;
		halfMove = 0;
		moveNumber = 0;
		
	}
	

	
	// make sure king needs to move out of check
	// write minimax with alpha beta pruning
	// write qsearch --> first write calmness checker
	// write calmness checker --> check how many pieces on each side see attacking square
	
	public Move chessBot() {
		Move bestMove = null;
		int bestScore = Integer.MIN_VALUE;

		for(int i = 0; i < sortedPossibleMoves.size(); i++) {
			Move move = sortedPossibleMoves.get(i);
			

            movePiece(move);

			int score = minimax(2, Integer.MIN_VALUE, Integer.MAX_VALUE, whiteMove);
			undoMove(move);

		    if (score > bestScore) {
		        bestScore = score;
		        bestMove = move;			
		    }
		}
		movePiece(bestMove);
		System.out.println(bestMove);
		return bestMove;
		
	}
	
	public int minimax(int depth, int alpha, int beta, boolean maximizingPlayer) {
		if(depth == 0) {
			return staticEval();
		}
//		System.out.println("" + maximizingPlayer + depth );
//		System.out.println(sortedPossibleMoves);
		if(maximizingPlayer) {
			int maxEval = Integer.MIN_VALUE;
			for(int i = 0; i < sortedPossibleMoves.size(); i++) {
				Move move = sortedPossibleMoves.get(i);
				movePiece(move);
//				System.out.println(move + " out");
				
				int eval = minimax(depth - 1, alpha, beta, false);
				
//				System.out.println(move + " in");

//				System.out.println("eval" + eval);

				resetSortedPossibleMoves(!this.whiteMove);
				maxEval = Math.max(maxEval, eval);
	            alpha = Math.max(alpha, eval);
	            undoMove(move);
				
	            if (beta <= alpha) {
	                break; // Alpha-beta pruning
	            } 
			}
            return maxEval;			
		} else {
			int minEval = Integer.MAX_VALUE;
			for(int i = 0; i < sortedPossibleMoves.size(); i++) {
				Move move = sortedPossibleMoves.get(i);
								
				movePiece(move);
				
//				System.out.println(move + " before");
				
				int eval = minimax(depth - 1, alpha, beta, true);
				
//				System.out.println(move + " after " + depth );
				
				minEval = Math.min(minEval, eval);
	            beta = Math.max(beta, eval);
	            undoMove(move);
	            
	            
	            if (beta <= alpha) {
	                break; // Alpha-beta pruning
	            } 
			}
	        return minEval;
		}
	}
	
	

	
	public void undoMove(Move move) {
		int oldPosition = move.array[0];
		int newPosition = move.array[1];
		
		
		Piece piece = pieceMap.get(newPosition);

		

		
		
		if(move.isCastle) { // if the move was a castle
			if (piece instanceof King) { // if the piece that castled is a king
				((King) piece).hasMoved = false; // undo hasMoved
				if(newPosition == 59) { // white castle left
					Rook r = (Rook) pieceMap.get(60); 
					r.changePosition(57);
					r.hasMoved = false;
				}
				if(newPosition == 63) { // white castle right
					Rook r = (Rook) pieceMap.get(62); 
					r.changePosition(64);
					r.hasMoved = false;
				}
				if(newPosition == 7) { // black castle left
					Rook r = (Rook) pieceMap.get(6); 
					r.changePosition(8);
					r.hasMoved = false;
				}
				if(newPosition == 3) { // black castle right
					Rook r = (Rook) pieceMap.get(4); 
					r.changePosition(1);
					r.hasMoved = false;
				}
			}
			

		} 
		
		if(piece instanceof Queen) {
			if(((Queen) piece).isPromoted == true) {
				pieceList.remove(piece);
				pieceMap.remove(piece.position);
				
				Pawn p = new Pawn(piece.position, piece.isWhite, this);
				piece = p;
				
				pieceList.add(piece);
				pieceMap.put(piece.position, null);
			}
		}
		
		piece.changePosition(oldPosition);
			
		
		
		if (piece instanceof King) {
			((King) piece).hasMoved = false; // undo hasMoved
	    }
		
		if (piece instanceof Rook) {
			((Rook) piece).hasMoved = false; // undo hasMoved
	    }
		
		
		if(move.score != 0) { // move was a capture, need to re-add captured piece
			pieceList.add(move.lastCaptured);
			pieceMap.put(move.lastCaptured.position, move.lastCaptured);
		}
		
		
		
		this.whiteMove = !this.whiteMove; // switch turn
		reloadPieceMap();
		
		reloadAllPossibleMoves();
		whiteKing.resetPossibleMoves();
		blackKing.resetPossibleMoves();
		resetSortedPossibleMoves(this.whiteMove);
		
	}
	
	
	public boolean movePiece(Move m) {
		int oldPosition = m.array[0];
		int newPosition = m.array[1];
//		if(gameOver) {
//			if(whiteCheckMate) {
//				System.out.println("White wins");
//			} else if(blackCheckMate) {
//				System.out.println("Black wins");
//			} else {
//				System.out.println("Stalemate");
//			}
//			return false;
//		} else {
			
		
		
			moveNumber++;
			Piece piece = pieceMap.get(oldPosition);
			if(piece == null) { // no piece to move
				System.out.println("no piece there");
				

				return false;
			}
			
			if(piece.possibleMoves.get(newPosition) == null) { // invalid move
				System.out.println("not possible move");
				return false;
			} else {
				
				
			if(whiteMove && piece.isWhite) {
				
				if(piece.possibleAttacks.get(newPosition) != null) { // move must be an attack
					m.lastCaptured = pieceMap.get(newPosition);
					pieceList.remove(pieceMap.get(newPosition));
					pieceMap.remove(newPosition);
					
				}
				
				piece.changePosition(newPosition); // moves piece
				whiteMove = false;
				reloadPieceMap();
				reloadcheckHowManyPiecesSee();
				reloadAllPossibleMoves(); // refreshes all piece's move vision
				blackKing.resetPossibleMoves();
				whiteKing.resetPossibleMoves();
				resetSortedPossibleMoves(whiteMove);

				return true;
			} else if (!whiteMove && !piece.isWhite) {
				
				if(piece.possibleAttacks.get(newPosition) != null) { // move must be an attack
					m.lastCaptured = pieceMap.get(newPosition);
					pieceList.remove(pieceMap.get(newPosition));
					pieceMap.remove(newPosition);
				}
				
				piece.changePosition(newPosition); // moves piece
				whiteMove = true;
				reloadPieceMap();
				reloadcheckHowManyPiecesSee();
				reloadAllPossibleMoves(); // refreshes all piece's move vision
				whiteKing.resetPossibleMoves();
				blackKing.resetPossibleMoves();
				resetSortedPossibleMoves(whiteMove);

				return true;
			} else {
				System.out.println("Other player's turn");
				return false;
			}
			
			}
		}
		
		
//	}
	

	

	
	public ArrayList<Move> resetSortedPossibleMoves(boolean whiteMove){
		sortedPossibleMoves.clear();
		for(int i = 0; i < pieceList.size(); i++) { // go through all pieces
			Piece piece = pieceList.get(i);
			if(whiteMove && piece.isWhite) { // put all moves for white into move list
				addMovesToMoveList(piece);
			}
			if(!whiteMove && !piece.isWhite) { // put all moves for black into move list
				addMovesToMoveList(piece);
			}
		}
		
		Collections.sort(sortedPossibleMoves, new MoveComparator());
		
		return sortedPossibleMoves;
	}
	
	
    private static final int[][] orderTable = {
            {0, 56, 57, 58, 59, 60, 61},       // victim K, attacker K, Q, R, B, N, P
            {50, 51, 52, 53, 54, 55, 0}, // victim Q, attacker K, Q, R, B, N, P
            {40, 41, 42, 43, 44, 45, 0}, // victim R, attacker K, Q, R, B, N, P
            {30, 31, 32, 33, 34, 35, 0}, // victim B, attacker K, Q, R, B, N, P
            {20, 21, 22, 23, 24, 25, 0}, // victim N, attacker K, Q, R, B, N, P
            {10, 11, 12, 13, 14, 15, 0}, // victim P, attacker K, Q, R, B, N, P
            {0, 0, 0, 0, 0, 0, 0}        // victim None, attacker K, Q, R, B, N, P
        };
	
	private void addMovesToMoveList(Piece piece){
		piece.possibleMoves.forEach((key, value) -> { // go through all moves of each piece
			if(piece.possibleAttacks.get(key) != null) { // also an attack, assigns value
				Piece victim = pieceMap.get(key); // victim and attacker
				Piece attacker = piece;
				
//				System.out.println(attacker.possibleAttacks);
//				
//				System.out.println(key);
//				System.out.println("" + piece + piece.position);
				
				int moveScore = orderTable[victim.attackOrder][attacker.attackOrder]; // 

				int[] moveCoordinates = {attacker.position, victim.position};
				Move move = new Move(moveCoordinates, moveScore);
				
				sortedPossibleMoves.add(move);
				
			} else { // not an attack
				int[] moveCoordinates = {piece.position, key};
				Move move = new Move(moveCoordinates, 0);

				sortedPossibleMoves.add(move);
			}
		});	
	}
	
	public int staticEval() {
		int gameEval = 0;
		for(int i = 0; i < pieceList.size(); i++) {
			gameEval += pieceList.get(i).getValue();
		}
		
		return gameEval;
	}
	
	public boolean movePiece(int oldPosition, int newPosition) {
		int[] array = {oldPosition, newPosition};
		Move m = new Move(array, 0);
//		if(gameOver) {
//			if(whiteCheckMate) {
//				System.out.println("White wins");
//			} else if(blackCheckMate) {
//				System.out.println("Black wins");
//			} else {
//				System.out.println("Stalemate");
//			}
//			return false;
//		} else {
			moveNumber++;
			Piece piece = pieceMap.get(oldPosition);
			if(piece == null) { // no piece to move
				System.out.println("no piece there");
				

				return false;
			}
			
			if(piece.possibleMoves.get(newPosition) == null) { // invalid move
				System.out.println("not possible move");
				return false;
			} else {
				
				
			if(whiteMove && piece.isWhite) {
				
				if(piece.possibleAttacks.get(newPosition) != null) { // move must be an attack
					m.lastCaptured = pieceMap.get(newPosition);
					pieceList.remove(pieceMap.get(newPosition));
					pieceMap.remove(newPosition);
					
				}
				
				piece.changePosition(newPosition); // moves piece
				reloadAllPossibleMoves(); // refreshes all piece's move vision
				whiteMove = false;
				reloadPieceMap();
				
				resetSortedPossibleMoves(whiteMove);
				return true;
			} else if (!whiteMove && !piece.isWhite) {
				
				if(piece.possibleAttacks.get(newPosition) != null) { // move must be an attack
					m.lastCaptured = pieceMap.get(newPosition);
					pieceList.remove(pieceMap.get(newPosition));
					pieceMap.remove(newPosition);
				}
				
				piece.changePosition(newPosition); // moves piece
				reloadAllPossibleMoves(); // refreshes all piece's move vision
				whiteMove = true;
				reloadPieceMap();
				resetSortedPossibleMoves(whiteMove);
				return true;
			} else {
				System.out.println("Other player's turn");
				return false;
			}
			
			}
		}
		
		
//	}
	
	
	
	
		
	
	public void reloadAllPossibleMoves() { // reloads piece vision for each piece
		protectedPieceSquares.clear(); // clear protected Piece Squares
		kingBeingSeen = false;
		for(int i = 0; i < pieceList.size(); i++) {
			Piece piece = pieceList.get(i);
			pieceList.get(i).resetPossibleMoves();
			boolean b = pieceList.get(i).seesOppositeKing;
			
			for (Integer key : piece.protectingSquares.keySet()) {	
				protectedPieceSquares.put(key, true); // add them in one by one
			}
			
			if(b) {
				kingBeingSeen = true;
			}
		}
	}
	
	public int checkHowManyPiecesSee(int position) { // check how many pieces can move to a square
		reloadcheckHowManyPiecesSee();
		if(howManyPiecesSee.get(position) == null) {
			return 0;
		}
		return howManyPiecesSee.get(position);
	}
	
	public int checkHowManyWhitePiecesSee(int position) { // check how many white can move to a square
		if(howManyWhitePiecesSee.get(position) == null) {
			return 0;
		}
		return howManyWhitePiecesSee.get(position);
	}
	
	public int checkHowManyBlackPiecesSee(int position) { // check how many black can move to a square
		if(howManyBlackPiecesSee.get(position) == null) {
			return 0;
		}
		return howManyBlackPiecesSee.get(position);
	}
	
	public boolean checkIfWhiteHasPossibleMoves() {
		if(howManyWhitePiecesSee.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public boolean checkIfBlackHasPossibleMoves() {
		if(howManyBlackPiecesSee.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public void reloadcheckHowManyPiecesSee() { // essentially helper method to refresh
		howManyPiecesSee.clear();
		howManyBlackPiecesSee.clear();
		howManyWhitePiecesSee.clear();
		for(int i = 0; i < pieceList.size(); i++) { // examine each individual piece
			Piece current = pieceList.get(i);				
			pieceList.get(i).possibleMoves.forEach((key, value) -> { // examine each square seen by each piece
				int position = key; // position being examined
				int currentSeen = 0;
				if(howManyPiecesSee.get(position) != null) {
					currentSeen = howManyPiecesSee.get(position);
				}
				howManyPiecesSee.put(position, ++currentSeen);
				
				if(current.isWhite) {
					howManyWhitePiecesSee.put(position, currentSeen);
				} else {
					howManyBlackPiecesSee.put(position, currentSeen);
				}

			});

		}
	}
	
	public boolean checkSpaceTaken(int position) { // checks if spot is taken on square
		if(pieceMap.get(position) == null) {
			return false;
		} else {
			return true;
		}
	}
	
	
	public void reloadPieceMap() { // reloads map that assigns a number to piece
		pieceMap.clear();
		for(int i = 0; i < pieceList.size(); i++) {
			int position = pieceList.get(i).position;
			pieceMap.put(position, pieceList.get(i));
		}
	}
	
	
	
	public void addPawn(int position, boolean isWhite) {
		Pawn p = new Pawn(position, isWhite, this);
		pieceList.add(p);
	}
	
	public void addKnight(int position, boolean isWhite) {
		Knight p = new Knight(position, isWhite, this);
		pieceList.add(p);
	}
	
	public void addBishop(int position, boolean isWhite) {
		Bishop p = new Bishop(position, isWhite, this);
		pieceList.add(p);
	}
	
	public void addKing(int position, boolean isWhite) {
		King p = new King(position, isWhite, this);
		pieceList.add(p);
	}
	
	public void addRook(int position, boolean isWhite) {
		Rook p = new Rook(position, isWhite, this);
		pieceList.add(p);
	}
	
	public void addQueen(int position, boolean isWhite) {
		Queen p = new Queen(position, isWhite, this);
		pieceList.add(p);
	}
	
	public void promoteQueen(int position, boolean isWhite) {
		Queen p = new Queen(position, isWhite, this);
		p.isPromoted = true;
		pieceList.add(p);
	}
	
	
	
	public void addWhite() {
		for(int i = 49; i <= 56; i++) {
			addPawn(i, true);
		}
		addRook(57, true);
		addRook(64, true);
		
		addKnight(58, true);
		addKnight(63, true);
		
		addBishop(59, true);
		addBishop(62, true);
		
		addQueen(60, true);
		addKing(61, true);
	}
	
	public void addBlack() {
		for(int i = 9; i <= 16; i++) {
			addPawn(i, false);
		}
		addRook(1, false);
		addRook(8, false);
		
		addKnight(2, false);
		addKnight(7, false);
		
		addBishop(3, false);
		addBishop(6, false);
		
		addQueen(4, false);
		addKing(5, false);
	}
	
	public void printGame() {
		for(int i = 1; i < 65; i++) {
			if(pieceMap.get(i) != null) {
				System.out.print(pieceMap.get(i).toString() + "  ");
			} else {
				if(i < 10) {
					System.out.print("0" + i + " ");
				} else {
					System.out.print(i + " ");
				}
			}
			if(i % 8 == 0) {
				System.out.println();
			}
		}
	}
	
	
	
	
	public String fenPrinter() {
		String fenPosition = "";
		String fenWhosMove = "";
		String fenCastlePermission = "";
		String enPassantPermission = "-";
		String halfMoveClock = "0";
		String fullMoveCounter = "" + moveNumber / 2;
		int currentGap = 0;
		
		// position
		for(int i = 1; i <= 64; i++) {
			if(pieceMap.get(i) != null) {
				if(currentGap != 0) {
					fenPosition = fenPosition + currentGap;
				}
				fenPosition = fenPosition + pieceMap.get(i).letterToString();
				currentGap = 0;
			} else {
				currentGap++;
			}
			if(i % 8 == 0 && i != 0) {
				if(currentGap != 0) {
					fenPosition += currentGap;
				}
				currentGap = 0;
				
				if(i != 64) {
					fenPosition += "/";
				}
			}
		}
		
		// Who's move
		if(whiteMove) {
			fenWhosMove = "w";
		} else {
			fenWhosMove = "b";
		}
		
		// castle permission
		if(pieceMap.get(61) instanceof King) { // white king check
			King king = (King) pieceMap.get(61); 
			if(king.hasMoved == false) { 
				if(pieceMap.get(64) instanceof Rook) { // KingSide castle check
					Rook rook = (Rook) pieceMap.get(64);
					if(rook.hasMoved == false) {
						fenCastlePermission += "K";
					}
				}
				if(pieceMap.get(57) instanceof Rook) { // queenside castle check
					Rook rook = (Rook) pieceMap.get(57);
					if(rook.hasMoved == false) {
						fenCastlePermission += "Q";
					}
				}
			}
		} 
		if(pieceMap.get(5) instanceof King) { // white king check
			King king = (King) pieceMap.get(5); 
			if(king.hasMoved == false) { 
				if(pieceMap.get(1) instanceof Rook) { // KingSide castle check
					Rook rook = (Rook) pieceMap.get(1);
					if(rook.hasMoved == false) {
						fenCastlePermission += "k";
					}
				}
				if(pieceMap.get(8) instanceof Rook) { // queenside castle check
					Rook rook = (Rook) pieceMap.get(8);
					if(rook.hasMoved == false) {
						fenCastlePermission += "q";
					}
				}
			}
		} 
		if(fenCastlePermission.isEmpty()) {
			fenCastlePermission += "-";
		}
		
		String completeFen = fenPosition + " " + fenWhosMove + " " + 
				fenCastlePermission + " " + enPassantPermission + " " + 
				halfMoveClock + " " +  fullMoveCounter;
		
		
		
		System.out.println(completeFen);
		return completeFen;
	}
	
	
	public boolean fenReader(String fen) {
		
		String[] splitFen = fen.split(" "); // split fen into components
		String position = splitFen[0]; // examine first component, the position
		if(fenPositionReader(position) == false) {
			return false;
		}
		String whosMove = splitFen[1];
		if(fenWhosTurn(whosMove) == false) {
			return false;
		}
		
		reloadPieceMap();
		
		String castleRights = splitFen[2];
		fenCastleRights(castleRights);
		
		String halfMove = splitFen[4];
		String fullMove = splitFen[5];

		int intHalfMove = Integer.parseInt(halfMove);
		int intFullMove = Integer.parseInt(fullMove);

		this.moveNumber = intFullMove * 2; 
		
		return true;
	}
	
	public boolean fenCastleRights(String castleRights) {
		if(castleRights.equals("-")) {
			return true;
		}
		char[] charArray = castleRights.toCharArray();

		for(int i = 0; i < charArray.length; i++) {
			char current = charArray[i];
				if(current == 'K') {
					King king = (King) pieceMap.get(61);
					Rook rook = (Rook) pieceMap.get(64);
					king.hasMoved = false;
					rook.hasMoved = false;
				}
				if(current == 'k') {
					King king = (King) pieceMap.get(5);
					Rook rook = (Rook) pieceMap.get(8);
					king.hasMoved = false;
					rook.hasMoved = false;
				}
				if(current == 'Q') {
					King king = (King) pieceMap.get(61);
					Rook rook = (Rook) pieceMap.get(57);
					king.hasMoved = false;
					rook.hasMoved = false;
				}
				if(current == 'q') {
					King king = (King) pieceMap.get(5);
					Rook rook = (Rook) pieceMap.get(1);
					king.hasMoved = false;
					rook.hasMoved = false;
			}

		}
		return true;
		
	}
	
	public boolean fenWhosTurn (String whosMove) {
		if(whosMove.equals("w")) {
			whiteMove = true;
			return true;
		}
		if(whosMove.equals("b")) {
			whiteMove = false;
			return true;
		}
		return false;
	}
	
	public boolean fenPositionReader(String position) {
		String[] splitPosition = position.split("/"); // split into rows
				
		if(splitPosition.length != 8) {
			return false;
		}
		
		for(int i = 0; i < splitPosition.length; i++) { // split each row into array of characters
			char[] charArray = splitPosition[i].toCharArray();
			int col = 1;
			for(int j = 0; j < charArray.length; j++) {
				char current = charArray[j];
				int currentSquare = (i) * 8 + (col);
				
				
				if(Character.isDigit(current)) { // parse empty squares
					int emptySquares = Character.getNumericValue(current);
					col = col + emptySquares;
				} else {
					col++;
				}
				// parsing position
				if(current == 'r') {
					addRook(currentSquare, false);
				}
				if(current == 'R') {
					addRook(currentSquare, true);
				}
				if(current == 'n') {
					addKnight(currentSquare, false);
				}
				if(current == 'N') {
					addKnight(currentSquare, true);
				}
				if(current == 'b') {
					addBishop(currentSquare, false);
				}
				if(current == 'B') {
					addBishop(currentSquare, true);
				}
				if(current == 'k') {
					addKing(currentSquare, false);
				}
				if(current == 'K') {
					addKing(currentSquare, true);
				}
				if(current == 'q') {
					addQueen(currentSquare, false);
				}
				if(current == 'Q') {
					addQueen(currentSquare, true);
				}
				if(current == 'p') {
					addPawn(currentSquare, false);
				}
				if(current == 'P') {
					addPawn(currentSquare, true);
				}
				
			}

		}
		
		
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
