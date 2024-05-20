package chessEngine;
import java.util.ArrayList;
import java.util.HashMap;


public abstract class Piece {
	int position;
	Game game;
	boolean isWhite;
	public HashMap<Integer, Boolean> possibleMoves;
	public HashMap<Integer, Boolean> possibleAttacks;
	public HashMap<Integer, Boolean> protectingSquares;

	public int attackOrder;
	public boolean seesOppositeKing;

	public Piece(int position, boolean isWhite, Game game) {
		this.game = game;
		this.position = position;
		this.isWhite = isWhite;
		protectingSquares = new HashMap<>();
		possibleMoves = new HashMap<>();
		possibleAttacks = new HashMap<>();
		seesOppositeKing = false;
	}
	

	
	public void changePosition(int newPosition) {
		int oldPosition = position;
		this.position = newPosition;
		game.pieceMap.remove(oldPosition);
		game.pieceMap.put(newPosition, this);
		
		resetPossibleMoves();
	}
	
	public HashMap<Integer, Boolean> getPossibleMoves() {
		return possibleMoves;
	}
	
	public void seesKing() {
		possibleAttacks.forEach((key, value) -> {
			if(game.pieceMap.get(key) instanceof King) {
				this.seesOppositeKing = true;
		    } else {
		    	this.seesOppositeKing = false;
		    }
		});
	}
	
	
	public abstract void resetPossibleMoves();
	
	public abstract String toString();
	
	public abstract String letterToString();
	
	public abstract int getValue();
}
