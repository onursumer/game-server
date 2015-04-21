package server.controller;

import server.comm.Client;
import server.model.GameRoom;
import server.model.GameHall;
import annot.Distributed;

/**
 * This aspects catches the changes in the server model, and notifies the
 * affected clients about these changes.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public aspect DistributionAspect
{
	private ServerController controller;
	
	public DistributionAspect()
	{
		controller = ServerController.getInstance();
	}
	
	// pointcut for entering of a client to the game hall
	pointcut enteringHall(GameHall hall, Client client) :
		call(@Distributed * add*(..)) &&
		target(hall) &&
		args(client);
	
	/**
	 * Advises before the client enters the game hall.
	 * Sends the client list of the game hall to the entering client before
	 * adding the client to the game hall to prevent duplicate sending of
	 * its username.
	 * 
	 * @param hall		the hall that the client is entering
	 * @param client	client that enters the hall
	 */
	before (GameHall hall, Client client) :
		enteringHall(hall, client)
	{
		// send list of all clients in the hall to the entering client
		controller.getDistributor().sendClientList(client, hall.getClientList());
	}
	
	/**
	 * Advises after the client enters the game hall.
	 * Send list of all rooms in the hall to the entered client, and 
	 * username of the client to the all clients in the hall.
	 * 
	 * @param hall		the hall that the client is entering
	 * @param client	client that enters the hall
	 */
	after (GameHall hall, Client client) :
		enteringHall(hall, client)
	{
		// send list of all rooms in the hall to the entered client
		controller.getDistributor().sendRoomList(client, hall.getRoomList());
		
		// for all clients in the game hall send new entered user
		controller.getDistributor().sendClient(hall.getClientList(), client);
	}
	
	// pointcut for the exiting of a client from the game hall
	pointcut exitingHall(GameHall hall,	Client client) :
		call(@Distributed * remove*(..)) &&
		target(hall) &&
		args(client);
	
	/**
	 * Advises after the client left the game hall. Informs all remaining
	 * clients in the hall about the exiting of that client.
	 * 
	 * @param hall		hall the client exits
	 * @param client	client that is exiting the hall
	 */
	after (GameHall hall, Client client) :
		exitingHall(hall, client)
	{
		// for all clients in the game hall send exiting user
		controller.getDistributor().sendExClient(hall.getClientList(), client);
	}
	
	// pointcut for the entering of a client to a game room
	pointcut enteringRoom(GameRoom room, Client client) :
		call(@Distributed * add*(..)) &&
		target(room) &&
		args(client);
	
	/**
	 * Advises after a client enters a game room. Informs all users in the
	 * game hall about the change of the room that the client entered. Send
	 * game info and the game state to the client that enters the room.
	 * 
	 * @param room		room that the client entered
	 * @param client	client that enters the room
	 */
	after (GameRoom room, Client client) :
		enteringRoom(room, client)
	{
		// for all clients in the game hall send new entered user
		controller.getDistributor().sendPlayer(client.getUserAccount().
				getGameHall().getClientList(), client, room.getRoomNo());
		
		// for the new client send game info
		controller.getDistributor().sendGameInit(client,
				client.getUserAccount().getGameHall().getGameName());
		
		controller.getDistributor().sendGameState(client,
				client.getUserAccount().getGameRoom().getGame());
	}
	
	// pointcut for the exiting of a client from the game room
	pointcut exitingRoom(GameRoom room,	Client client) :
		call(@Distributed * remove*(..)) &&
		target(room) &&
		args(client);
	
	/**
	 * Advises after the client left the game room. Informs all clients
	 * in the hall about the exiting of that client from the room.
	 * 
	 * @param room		room the client exits
	 * @param client	client that is exiting the room
	 */
	after (GameRoom room, Client client) :
		exitingRoom(room, client)
	{
		// for all clients in the game hall send exiting user
		controller.getDistributor().sendExPlayer(client.getUserAccount().
				getGameHall().getClientList(), client, room.getRoomNo());
	}
	
	// pointcut for a new room creation in a game hall
	pointcut newRoom(GameHall hall,	GameRoom room) :
		call(@Distributed * add*(..)) &&
		target(hall) &&
		args(room);
	
	/**
	 * Advises after a new game room is created in the a game hall. Informs
	 * all clients in the game hall about the creation of the new room.
	 * 
	 * @param hall	the hall in which a new room is created
	 * @param room	the newly created room 
	 */
	after (GameHall hall, GameRoom room) :
		newRoom(hall, room)
	{	
		// for all clients in the game hall send new created room
		controller.getDistributor().sendRoom(hall.getClientList(), room);
	}
	
	// pointcut for a closing of an existing room in a game hall
	pointcut closingRoom(GameHall hall, GameRoom room) :
		call(@Distributed * remove*(..)) &&
		target(hall) &&
		args(room);
	
	/**
	 * Advises after an existing game room is closed in a game hall. Informs
	 * all clients in the game hall about the closure of the game room.
	 * 
	 * @param hall	the hall from which the room is removed
	 * @param room	the room that is removed from the hall
	 */
	after (GameHall hall, GameRoom room) :
		closingRoom(hall, room)
	{
		// for all clients in the game hall send removed room
		controller.getDistributor().sendExRoom(hall.getClientList(), room);
	}
}
