package server.game.chess;

import server.game.TurnBasedPlayer;
import server.game.chess.Chess.Color;

/**
 * Player class for chess model. Each chess player only has color
 * 
 * @author Alper Karacelik
 *
 */
public class ChessPlayer extends TurnBasedPlayer
{
	
	private Color color;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
}
