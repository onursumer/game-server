package server.action;

import java.util.HashMap;

import action.Action;

import server.comm.Client;
import server.controller.ServerController;
import server.game.Game;
import server.game.GameFactory;
import server.model.GameHall;
import server.model.GameRoom;
import server.model.RoomGenerator;

public class RealmAction extends Action
{
	/**
	 * Sets the game of the client as the selected game.
	 * (action command: SELECT_GAME)
	 * 
	 * @param client	source Client of the action command 
	 * @param param		parameter list of the action
	 * @return			action result as a string
	 */
	public String selectGame(Client client, HashMap<String, String> param)
	{
		String actionResult = null;
		String gameName = param.get("gameName");
		ServerController controller = ServerController.getInstance();
		
		// search the hall of the game
		GameHall hall = controller.getRealm().searchHall(gameName);
		
		if(hall == null)
			actionResult = Action.ERROR + "?" + "errorTitle=Game Error" +
					"&errorContent=Game not found";
		
		// add the client to the game hall
		client.getUserAccount().setGameHall(hall);
		hall.addClient(client);
		
		
		//set response for the client
		actionResult = Action.MOVE_TO_GAME_HALL + "?" + "gameName=" + gameName;
		
		return actionResult;
	}
	
	/**
	 * Removes the client from the specified game hall.
	 * (action command: EXIT_HALL)
	 * 
	 * @param client	source Client of the action command 
	 * @param param		parameter list of the action
	 * @return			action result as a string
	 */
	public String exitHall(Client client, HashMap<String, String> param)
	{
		String actionResult = null;
		
		// remove client from the game hall
		client.getUserAccount().getGameHall().removeClient(client);
		client.getUserAccount().setGameHall(null);
		
		//set response for the client
		actionResult = Action.MOVE_TO_GAME_SELECT + "?";
		
		return actionResult;
	}
	
	/**
	 * Creates a new room in the game hall in which the specified client is.
	 * (action command: CREATE_ROOM)
	 * 
	 * @param client	source Client of the action command 
	 * @param param		parameter list of the action
	 * @return			action result as a string
	 */
	public String createRoom(Client client, HashMap<String, String> param)
	{
		String actionResult = null;
		GameHall hall = client.getUserAccount().getGameHall();
		
		if(hall == null) // invalid game
			actionResult = Action.ERROR + "?" +
				"errorTitle=Room Creation Error" +
					"&errorContent=Game not found";
		
		// add the room to the game hall			
		GameRoom room = RoomGenerator.generate();
		hall.addRoom(room);
		
		// create a new game for the room
		try {
			GameFactory factory = GameFactory.getFactory(hall.getGameName());
			Game game = factory.createGame();
			room.setGame(game);
			game.setGameRoom(room);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// add client to the room
		client.getUserAccount().setGameRoom(room);
		room.addClient(client);
		
		//set response for the client
		actionResult = Action.MOVE_TO_GAME_ROOM + "?" + 
			"roomNo=" + room.getRoomNo();
		
		return actionResult;
	}
	
	/**
	 * Adds the client to the specified game room.
	 * (action command: JOIN_ROOM)
	 * 
	 * @param client	source Client of the action command 
	 * @param param		parameter list of the action
	 * @return			action result as a string
	 */
	public String joinRoom(Client client, HashMap<String, String> param)
	{
		String actionResult = null;
		String roomNo = param.get("roomNo"); // get room no
		GameHall hall = client.getUserAccount().getGameHall();
		
		if(hall == null) // invalid game
			actionResult = Action.ERROR + "?" +
				"errorTitle=Room Joining Error" +
					"&errorContent=Game not found";
		
		GameRoom room = hall.searchRoom(Integer.parseInt(roomNo));
		
		if(room == null) // invalid game room
			actionResult = Action.ERROR + "?" +
				"errorTitle=Room Joining Error" +
					"&errorContent=Game room not found";
		
		// add client to the room
		client.getUserAccount().setGameRoom(room);
		room.addClient(client);
		
		//set request for the client
		actionResult = Action.MOVE_TO_GAME_ROOM + "?" +
			"roomNo=" + room.getRoomNo();

		return actionResult;
	}
	
	/**
	 * Removes the client from its current game room.
	 * (action command: EXIT_ROOM)
	 * 
	 * @param client	source Client of the action command 
	 * @param param		parameter list of the action
	 * @return			action result as a string
	 */
	public String exitRoom(Client client, HashMap<String, String> param)
	{
		String actionResult = null;
		
		// remove client from the game room
		client.getUserAccount().getGameRoom().removeClient(client);
		client.getUserAccount().setGameRoom(null);
		
		// reset user's player info
		if(client.getUserAccount().getPlayer() != null)
		{
			client.getUserAccount().getPlayer().setAvailable(true);
			client.getUserAccount().getPlayer().setReady(false);
			client.getUserAccount().setPlayer(null);
		}
		
		//set request for the client
		actionResult = Action.MOVE_TO_GAME_HALL + "?" + "gameName=" +  
				client.getUserAccount().getGameHall().getGameName();
		
		return actionResult;
	}
}
