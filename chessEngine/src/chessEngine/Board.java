package chessEngine;

public class Board {
	public Board() {
		int[] board = new int[64];
		
        for (int i = 0; i < 64; i++) {
        	board[i] = i + 1;
        }
	}
}
