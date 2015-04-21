package server.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents the server realm with a list of games, and a list of game halls.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class ServerRealm
{
	// list of games
	private List<String> gameList;
	
	// list of game halls
	private List<GameHall> gameHallList;
	
	/**
	 * Constructs a new ServerRealm object.
	 */
	public ServerRealm()
	{
		gameList = new ArrayList<String>();
		gameHallList = new ArrayList<GameHall>();
	}
	
	/**
	 * Searches for a game hall having the specified game name.
	 * 
	 * @param gameName	name of the game
	 * @return	game hall having the given game name.
	 */
	public GameHall searchHall(String gameName)
	{
		Iterator<GameHall> iter = this.gameHallList.iterator();
		GameHall hall;
		
		while(iter.hasNext())
		{
			hall = iter.next();
			
			if(hall.getGameName().equals(gameName))
				return hall;
		}
		
		return null;
	}
	
	
	/**
	 * Constructs the list of available games.
	 */
	public void constructGameList()
	{
		// TODO database game list
		gameList.add("CHESS");
		gameList.add("KING");
	}
	
	/**
	 * Creates a game hall for each available game.
	 */
	public void constructHallList()
	{
		Iterator<String> iter = this.gameList.iterator();
		
		while(iter.hasNext())
		{
			gameHallList.add(new GameHall(iter.next()));
		}
		
	}
	
	/*
	 * GETTERS and SETTERS
	 */
	
	public List<String> getGameList()
	{
		return gameList;
	}

	public void setGameList(List<String> gameList)
	{
		this.gameList = gameList;
	}

	public List<GameHall> getGameHallList()
	{
		return gameHallList;
	}

	public void setGameHallList(List<GameHall> gameHallList)
	{
		this.gameHallList = gameHallList;
	}
}
