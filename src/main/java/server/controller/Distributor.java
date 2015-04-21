package server.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import server.comm.Client;
import server.game.Game;
import server.model.GameRoom;

/**
 * Distributor Class.
 * 
 * This class contains methods for distributing changes to the clients.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class Distributor
{
	/**
	 * Sends usernames of all clients in the given client list
	 * to the specified client. 
	 * 
	 * @param client		receiver client
	 * @param clientList	list to be sent
	 */
	public void sendClientList(Client client, List<Client> clientList)
	{
		Iterator<Client> iter = clientList.iterator();
		String command = "ADD_HALL_USER";
		Client current;
		
		while(iter.hasNext())
		{
			current = iter.next();
			
			try {
				client.send(command + "?username=" +
						current.getUserAccount().getUsername());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sends the username of the specified client to the all clients
	 * in the given client list. 
	 * 
	 * @param clientList	receiver clients
	 * @param client		client to be sent
	 */
	public void sendClient(List<Client> clientList, Client client)
	{
		Iterator<Client> iter = clientList.iterator();
		String command = "ADD_HALL_USER";
		Client current;
		
		while(iter.hasNext())
		{
			current = iter.next();
			
			try {
				// send user info
				current.send(command + "?username=" + 
						client.getUserAccount().getUsername());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sends the username of the specified client to the all clients
	 * in the given client list. 
	 * 
	 * @param clientList	receiver clients
	 * @param client		client to be sent
	 */
	public void sendExClient(List<Client> clientList, Client client)
	{
		Iterator<Client> iter = clientList.iterator();
		String command = "REMOVE_HALL_USER";
		Client current;
		
		while(iter.hasNext())
		{
			current = iter.next();
			
			try {
				// send user info
				current.send(command + "?username=" + 
						client.getUserAccount().getUsername());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sends names of all games in the given game list
	 * to the specified client.
	 * 
	 * @param client		receiver client
	 * @param clientList	list to be sent
	 */
	public void sendGameList(Client client, List<String> gameList)
	{
		Iterator<String> iter = gameList.iterator();
		String command = "ADD_GAME";
		
		while(iter.hasNext())
		{
			try {
				client.send(command + "?gameName=" + iter.next());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sends the information of the specified room to the all clients
	 * in the given client list.
	 * 
	 * @param clientList	receiver clients
	 * @param room			room to be sent
	 */
	public void sendRoom(List<Client> clientList, GameRoom room)
	{
		Iterator<Client> iter = clientList.iterator();
		String command = "ADD_ROOM";
		Client current;
		
		while(iter.hasNext())
		{
			current = iter.next();
			
			try {
				// send room info
				current.send(command + "?roomNo=" + room.getRoomNo());
				
				// send user info of the room
				this.sendPlayerList(current,
						room.getRoomNo(),
						room.getClientList());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sends the information of the specified room to the all clients
	 * in the given client list.
	 * 
	 * @param clientList	receiver clients
	 * @param room			room to be sent
	 */
	public void sendExRoom(List<Client> clientList, GameRoom room)
	{
		Iterator<Client> iter = clientList.iterator();
		String command = "REMOVE_ROOM";
		Client current;
		
		while(iter.hasNext())
		{
			current = iter.next();
			
			try {
				// send room info
				current.send(command + "?roomNo=" + room.getRoomNo());
				
				// send user info of the room
				this.sendPlayerList(current,
						room.getRoomNo(),
						room.getClientList());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sends information of all rooms in the given room list
	 * to the specified client.
	 * 
	 * @param client		receiver client
	 * @param roomList		list to be sent
	 */
	public void sendRoomList(Client client, List<GameRoom> roomList)
	{
		Iterator<GameRoom> iter = roomList.iterator();
		String command = "ADD_ROOM";
		GameRoom current;
		
		while(iter.hasNext())
		{
			current = iter.next();
			
			try {
				// send room info
				client.send(command + "?roomNo=" + current.getRoomNo());
				
				// send user info of the room
				this.sendPlayerList(client,
						current.getRoomNo(),
						current.getClientList());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Send username of the specified client to the clients in the given 
	 * client list.
	 * 
	 * @param clientList	receiver clients
	 * @param client		client to be sent
	 * @param roomNo		room that the client is related
	 */
	public void sendPlayer(List<Client> clientList,
			Client client,
			Integer roomNo)
	{
		Iterator<Client> iter = clientList.iterator();
		String command = "ADD_ROOM_USER";
		Client current;
		
		while(iter.hasNext())
		{
			current = iter.next();
			
			try {
				current.send(command + "?roomNo=" + roomNo +
						"&username=" + client.getUserAccount().getUsername());
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Send username of the specified client to the clients in the given 
	 * client list.
	 * 
	 * @param clientList	receiver clients
	 * @param client		client to be sent
	 * @param roomNo		room that the client is related
	 */
	public void sendExPlayer(List<Client> clientList,
			Client client,
			Integer roomNo)
	{
		Iterator<Client> iter = clientList.iterator();
		String command = "REMOVE_ROOM_USER";
		Client current;
		
		while(iter.hasNext())
		{
			current = iter.next();
			
			try {
				current.send(command + "?roomNo=" + roomNo +
						"&username=" + client.getUserAccount().getUsername());
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sends usernames of all clients in the given client list
	 * to the specified client. 
	 * 
	 * @param client		receiver client
	 * @param roomNo		room that the client is related
	 * @param clientList	list to be sent
	 */
	public void sendPlayerList(Client client,
			Integer roomNo,
			List<Client> clientList)
	{
		Iterator<Client> iter = clientList.iterator();
		String command = "ADD_ROOM_USER";
		Client current;
		
		while(iter.hasNext())
		{
			current = iter.next();
			try {
				client.send(command + "?roomNo=" + roomNo +
						"&username=" + current.getUserAccount().getUsername());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sends the contents of a chat massage and the sender of the message
	 * to the clients in the given client list. 
	 * 
	 * @param clientList	receiver clients
	 * @param client		message author client
	 * @param chatContent	content of the chat message
	 */
	public void sendChatMsg(List<Client> clientList,
			Client client,
			String chatContent)
	{
		Iterator<Client> iter = clientList.iterator();
		String command = "CHAT_MSG";
		Client current;
		
		while(iter.hasNext())
		{
			current = iter.next();
			
			try {
				// send user info
				current.send(command + "?username=" + 
						client.getUserAccount().getUsername() + 
						"&chatContent=" + chatContent);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sends an initialization command for the given game to the specified
	 * client.
	 * 
	 * @param client	receiver client
	 * @param gameName	name of the game to be initialized
	 */
	public void sendGameInit(Client client,
			String gameName)
	{
		String command = "INIT_GAME";
		
		try {
			client.send(command + "?gameName=" + gameName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Sends the state of a game to the specified client
	 * 
	 * @param client	receiver client
	 * @param game		source of the game state 
	 */
	public void sendGameState(Client client,
			Game game)
	{
		String command = "SET_GAME_STATE";
		
		try {
			client.send(command + "?state=" + game.stateInfo());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
