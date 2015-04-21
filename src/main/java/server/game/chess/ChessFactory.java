package server.game.chess;

import server.game.Game;
import server.game.GameFactory;
import server.game.Player;

/**
 * Abstract Factory class for chess family
 * Family members: Chess: model
 * 				   ChessPlayer: chess player
 * 
 * @author Alper Karacelik
 *
 */
public class ChessFactory extends GameFactory
{
	@Override
	public Game createGame()
	{
		return new Chess();
	}

	@Override
	public Player createPlayer()
	{
		return new ChessPlayer();
	}
}
