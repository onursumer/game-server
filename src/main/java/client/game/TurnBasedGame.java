package client.game;


public abstract class TurnBasedGame extends Game
{
	protected boolean endOfGame; // end of game indicator
	protected TurnBasedPlayer currentPlayer; // turn indicator
	
	protected abstract TurnBasedPlayer nextPlayer();
	
	public void setEndOfGame(boolean endOfGame)
	{
		this.endOfGame = endOfGame;
	}
	
	public boolean isEndOfGame()
	{
		return endOfGame;
	}
}
