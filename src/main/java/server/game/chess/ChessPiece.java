package server.game.chess;

import server.game.chess.Chess.Color;

/**
 * ChessPiece class for Chess model
 * Chess piece has a color (black or white) and a name (Rook, Bishop, etc.)
 * 
 * @author Alper Karacelik
 *
 */
public class ChessPiece {

	private String name;
	private Color color;
	
	public ChessPiece(String name, Color color) {
		super();
		this.name = name;
		this.color = color;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public Color getColor() {
		return color;
	}
}
