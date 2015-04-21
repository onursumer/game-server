package client.game.chess;

import javax.swing.JPanel;

import client.game.Game;
import client.game.chess.Chess;
import client.game.GameFactory;

/**
 * Abstract Factory class for chess family
 * Family members: Chess: model
 * 				   ChessPanel: view
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
	public JPanel createPanel() {
		return new ChessPanel();
	}
}
