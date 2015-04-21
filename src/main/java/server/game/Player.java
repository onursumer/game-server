package server.game;

public abstract class Player
{
	protected boolean available;
	protected boolean ready;
	
	public Player()
	{
		this.available = true;
		this.ready = false;
	}
	
	public boolean isAvailable()
	{
		return available;
	}
	
	public void setAvailable(boolean available)
	{
		this.available = available;
	}
	
	public boolean isReady()
	{
		return ready;
	}
	
	public void setReady(boolean ready)
	{
		this.ready = ready;
	}
}
