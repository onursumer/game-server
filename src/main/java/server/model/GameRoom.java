package server.model;

import java.util.ArrayList;
import java.util.List;

import server.comm.Client;
import server.game.Game;

import annot.Distributed;

/**
 * Represent a game room with its room number, the game being played in
 * the room, and the list of the clients in the room.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class GameRoom
{
	// game of the room
	private Game game;
	
	// room number
	private Integer roomNo;
	
	// list of clients in the room
	private List<Client> clientList;
	
	/**
	 * Constructs a new GameRoom object.
	 */
	public GameRoom()
	{
		super();
		clientList = new ArrayList<Client>();
	}
	
	/**
	 * Adds the given client to the game room.
	 * 
	 * @param client	client to be added
	 */
	@Distributed
	public void addClient(Client client)
	{
		clientList.add(client);
	}
	
	/**
	 * Removes the specified client from the game room.
	 * 
	 * @param client
	 */
	@Distributed
	public void removeClient(Client client)
	{
		clientList.remove(client);
	}

	/*
	 * GETTERS and SETTERS 
	 */
	
	public List<Client> getClientList()
	{
		return clientList;
	}

	public void setRoomNo(Integer roomNo)
	{
		this.roomNo = roomNo;
	}

	public Integer getRoomNo()
	{
		return roomNo;
	}
	
	public Game getGame()
	{
		return game;
	}

	public void setGame(Game game)
	{
		this.game = game;
	}
}
