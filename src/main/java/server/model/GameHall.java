package server.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import server.comm.Client;

import annot.Distributed;

/**
 * Represents a game hall of a certain game with a game name, a list of
 * currently connected clients to the hall, and a list of currently
 * available rooms. 
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class GameHall
{
	// name of the game in this hall
	private String gameName;
	
	// list of connected clients in this hall
	private List<Client> clientList;
	
	// list of currently available rooms in this hall
	private List<GameRoom> roomList;

	/**
	 * Constructs a GameHall with given game name.
	 * 
	 * @param gameName	name of the game
	 */
	public GameHall(String gameName)
	{
		this.gameName = gameName;
		this.clientList = new ArrayList<Client>();
		this.roomList = new ArrayList<GameRoom>();
	}
	
	/**
	 * Adds the given client to the client list of the hall.
	 *  
	 * @param client	client to be added
	 */
	@Distributed
	public void addClient(Client client)
	{
		clientList.add(client);
	}
	
	/**
	 * Searches for a game room having the specified room no.
	 * 
	 * @param roomNo	room no to be searched
	 * @return	game room having the given no
	 */
	public GameRoom searchRoom(Integer roomNo)
	{
		Iterator<GameRoom> iter = this.roomList.iterator();
		GameRoom room;
		
		while(iter.hasNext())
		{
			room = iter.next();
			
			if(room.getRoomNo().equals(roomNo))
				return room;
		}
		
		return null;
	}
	
	/**
	 * Removes the specified client from the game hall.
	 * 
	 * @param client	client to be removed
	 */
	@Distributed
	public void removeClient(Client client)
	{
		clientList.remove(client);
	}
	
	/**
	 * Adds the specified game room to the hall.
	 * 
	 * @param room	room to be added
	 */
	@Distributed
	public void addRoom(GameRoom room)
	{
		roomList.add(room);
	}
	
	/**
	 * Removes the specified game room from the hall
	 * 
	 * @param room	room to be removed
	 */
	@Distributed
	public void removeRoom(GameRoom room)
	{
		roomList.remove(room);
	}
	
	/*
	 * GETTERS and SETTERS 
	 */
	
	public void setGameName(String gameName)
	{
		this.gameName = gameName;
	}

	public String getGameName()
	{
		return gameName;
	}

	public List<Client> getClientList()
	{
		return clientList;
	}
	
	public List<GameRoom> getRoomList()
	{
		return roomList;
	}
}
