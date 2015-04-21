package client.game;

/**
 * Abstract Game Class.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public abstract class Game
{
	public abstract boolean isValidAction(String action);
	public abstract boolean applyAction(String action);
	public abstract boolean applyGameState(String state);
	public abstract boolean applyPlayerState(String state);
}
