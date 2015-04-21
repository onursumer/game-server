package server.model;

/**
 * Abstract User Class.
 * This class contains attributes and methods common for all user types.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public abstract class User
{
	// username of the user
	protected String username;
	
	// game room of the user
	protected GameRoom gameRoom;
	
	// game hall of the user
	protected GameHall gameHall;
	
	
	/*
	 * GETTERS and SETTERS 
	 */
	
	public String getUsername()
	{
		return username;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public GameRoom getGameRoom()
	{
		return gameRoom;
	}
	
	public void setGameRoom(GameRoom gameRoom)
	{
		this.gameRoom = gameRoom;
	}
	
	public GameHall getGameHall()
	{
		return gameHall;
	}
	
	public void setGameHall(GameHall gameHall)
	{
		this.gameHall = gameHall;
	}
}
