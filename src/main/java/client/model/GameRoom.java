package client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game room with its number, and a list of players.
 *  
 * @author Selcuk Onur Sumer
 *
 */
public class GameRoom
{
	// room number
	private Integer roomNo;
	
	// players in the room
	private List<String> players;
	
	/**
	 * Construct a new GameRoom object
	 */
	public GameRoom()
	{
		players = new ArrayList<String>();
		roomNo = -1;
	}
	
	/**
	 * Construct a new GameRoom object with the given room number.
	 * 
	 * @param roomNo	room no of the room
	 */
	public GameRoom(Integer roomNo)
	{
		this();
		this.roomNo = roomNo;
	}
	
	/**
	 * Adds a new username to the list of players. 
	 * 
	 * @param username	name of the player to be added
	 */
	public void addPlayer(String username)
	{
		this.players.add(username);
	}
	
	/**
	 * Removes the specified user from the player list.
	 * 
	 * @param username	name of the player to be removed.
	 */
	public void removePlayer(String username)
	{
		this.players.remove(username);
	}
	
	/*
	 * GETTERS and SETTERS 
	 */
	
	public Integer getRoomNo()
	{
		return roomNo;
	}
	
	public void setRoomNo(Integer roomNo)
	{
		this.roomNo = roomNo;
	}
	
	public List<String> getPlayers()
	{
		return players;
	}
	
	public void setPlayers(List<String> players)
	{
		this.players = players;
	}
	
}
