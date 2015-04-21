package server.comm;

import controller.Common;
import server.controller.ServerController;
import server.game.Player;
import server.model.GameRoom;
import server.model.GameHall;
import server.model.User;

/**
 * This aspect performs operations when a new connection is established to 
 * a client or the client connection is lost.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public aspect ConnectionAspect
{
	// pointcut for the creation of new client object
	pointcut clientCreation() : (call(public Client.new(..)));
	
	/**
	 * Advises after the creation of the client
	 *  
	 * @param client	created client
	 */
	after () returning (Client client): clientCreation()
	{
		// set connection time of the client
		client.setConnectionTime(Common.getCurrentTime());
	}
	
	// pointcut for the execution of the client thread
	pointcut connection(ClientThread thread) :
		execution(public * ClientThread.run())
		&& this(thread);
	
	/**
	 * Advises after the execution of the client thread
	 * to perform clean up operations.
	 * 
	 * @param thread	client thread
	 */
	after (ClientThread thread) : connection(thread)
	{
		Client client = thread.getClient();
		
		User user = client.getUserAccount();
		
		if(user == null)
			return;
		
		// remove client from the communicator list
		ServerController.getInstance().getCommunicator()
			.removeClient(client);
		
		GameHall hall = user.getGameHall();
		
		if(hall == null)
			return;
		
		// remove client from the hall
		hall.removeClient(client);
		
		GameRoom room = user.getGameRoom();
		
		// remove client from the room
		if(room != null)
			room.removeClient(client);
		
		// release game related resources
		Player player = user.getPlayer();
		
		if(player != null)
		{
			player.setAvailable(true);
			player.setReady(false);
		}
	}
}
