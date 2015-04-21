package client.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents the game hall with a game name, a list of rooms,
 * and a list of users.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class GameHall
{
	// name of the game for the current hall
	private String gameName;
	
	// list of available rooms in the hall
	private List<GameRoom> roomList;
	
	// list of connected users to the hall
	private List<String> userList;
	
	/**
	 * Constructs a new Game Hall
	 */
	public GameHall()
	{
		roomList = new ArrayList<GameRoom>();
		userList = new ArrayList<String>();
	}
	
	/**
	 * Adds a new game room to the hall.
	 * 
	 * @param room		room to be added
	 */
	public void addRoom(GameRoom room)
	{
		roomList.add(room);
	}
	
	/**
	 * Removes the room having the specified room no from the hall.
	 * 
	 * @param roomNo	room number of the room to be removed
	 */
	public void removeRoom(Integer roomNo)
	{
		GameRoom room = searchRoom(roomNo);
		
		if(room != null)
			roomList.remove(room);
	}

	/**
	 * Adds the given user to the hall.
	 * 
	 * @param username	name of the user to be added
	 */
	public void addUser(String username)
	{
		userList.add(username);
	}
	
	/**
	 * Removes the user from the hall.
	 * 
	 * @param username	name of the user to be removed 
	 */
	public void removeUser(String username)
	{
		userList.remove(username);
	}
	
	/**
	 * Search for the room having the specified room no.
	 * 
	 * @param roomNo	number of room to be searched
	 * @return		game room having the specified no
	 */
	public GameRoom searchRoom(Integer roomNo)
	{
		Iterator<GameRoom> iter = this.roomList.iterator();
		GameRoom room = null;
		
		while(iter.hasNext())
		{
			room = iter.next();
			
			if(room.getRoomNo().equals(roomNo))
				return room;
		}
		
		return room;
	}

	/*
	 * GETTERS and SETTERS 
	 */
	
	public String getGameName()
	{
		return gameName;
	}

	public void setGameName(String gameName)
	{
		this.gameName = gameName;
	}

	public List<GameRoom> getRoomList()
	{
		return roomList;
	}

	public void setRoomList(List<GameRoom> roomList)
	{
		this.roomList = roomList;
	}

	public List<String> getUserList()
	{
		return userList;
	}

	public void setUserList(List<String> userList)
	{
		this.userList = userList;
	}
}
