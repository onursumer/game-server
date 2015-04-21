package client.action;

import java.util.HashMap;

import action.Action;

import client.controller.ClientController;
import client.model.GameHall;
import client.model.GameRoom;

public class RealmAction extends Action
{
	/**
	 * Adds a new game to the server context
	 * (action command: ADD_GAME)
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String addGame(HashMap<String, String> param)
	{
		String actionResult = null;
		ClientController controller = ClientController.getInstance();
		String gameName = param.get("gameName");
		
		controller.getContext().addGame(gameName);
		
		return actionResult;
	}
	
	/**
	 * Adds a new room to the current game hall.
	 * (action command: ADD_ROOM)
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String addRoom(HashMap<String, String> param)
	{
		String actionResult = null;
		ClientController controller = ClientController.getInstance();
		String roomNo = param.get("roomNo");
		
		controller.getContext().getGameHall().
				addRoom(new GameRoom(Integer.parseInt(roomNo)));
		
		return actionResult;
	}
	
	/**
	 * Removes the room having the specified room no from the current
	 * game hall.
	 * (action command: REMOVE_ROOM)
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String removeRoom(HashMap<String, String> param)
	{
		String actionResult = null;
		ClientController controller = ClientController.getInstance();
		String roomNo = param.get("roomNo");
		
		controller.getContext().getGameHall().
				removeRoom(Integer.parseInt(roomNo));
		
		return actionResult;
	}
	
	/**
	 * Adds a new user to the current game hall.
	 * (action command: ADD_HALL_USER)
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String addHallUser(HashMap<String, String> param)
	{
		String actionResult = null;
		ClientController controller = ClientController.getInstance();
		
		String username = param.get("username"); // get username
		controller.getContext().getGameHall().addUser(username);
		
		return actionResult;
	}
	
	/**
	 * Removes the specified user form the current game hall.
	 * (action command: REMOVE_HALL_USER)
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String removeHallUser(HashMap<String, String> param)
	{
		String actionResult = null;
		ClientController controller = ClientController.getInstance();
		
		String username = param.get("username"); // get username
		controller.getContext().getGameHall().removeUser(username);
		
		return actionResult;
	}
	
	/**
	 * Adds a user to the specified game room.
	 * (action command: ADD_USER)
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String addRoomUser(HashMap<String, String> param)
	{
		String actionResult = null;
		ClientController controller = ClientController.getInstance();
		GameRoom currentRoom = controller.getContext().getGameRoom();
		
		String roomNo = param.get("roomNo"); // get room no
		String username = param.get("username"); // get username
		
		// check if player is in that room
		if((currentRoom != null) &&
				currentRoom.getRoomNo().equals(Integer.parseInt(roomNo)))
		{
			currentRoom.addPlayer(username);
		}
		else
		{
			GameRoom room = controller.getContext().getGameHall()
					.searchRoom(Integer.parseInt(roomNo));
			room.addPlayer(username);
		}
		
		return actionResult;
	}
	
	/**
	 * Removes the specified user from the specified room. 
	 * (action command: REMOVE_ROOM_USER)
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String removeRoomUser(HashMap<String, String> param)
	{
		String actionResult = null;
		ClientController controller = ClientController.getInstance();
		GameRoom currentRoom = controller.getContext().getGameRoom();
		
		String roomNo = param.get("roomNo"); // get room no
		String username = param.get("username"); // get username
		
		// check if player is in that room
		if((currentRoom != null) &&
				currentRoom.getRoomNo().equals(Integer.parseInt(roomNo)))
		{
			currentRoom.removePlayer(username);
		}
		else
		{
			GameRoom room = controller.getContext().getGameHall()
					.searchRoom(Integer.parseInt(roomNo));
			room.removePlayer(username);
		}
		
		return actionResult;
	}
	
	/**
	 * Switches the GUI to the game selection panel.
	 * (action command: MOVE_TO_GAME_SELECT)
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String moveToSelect(HashMap<String, String> param)
	{
		String actionResult = null;
		ClientController controller = ClientController.getInstance();
		
		controller.getContext().setGameHall(new GameHall()); // reset game hall
		controller.getGui().switchTo(controller.getGui().
				getGameSelectPanel()); // switch view
		
		return actionResult;
	}
	
	/**
	 * Switches the GUI to the game hall panel.
	 * (action command: MOVE_TO_GAME_HALL)
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String moveToHall(HashMap<String, String> param)
	{
		String actionResult = null;
		ClientController controller = ClientController.getInstance();
		
		String gameName = param.get("gameName");
		controller.getContext().getGameHall().setGameName(gameName);
		controller.getContext().setGameRoom(null); // reset current room
		controller.getGui().switchTo(controller.getGui().
				getGameHallPanel()); // switch view
		
		return actionResult;
	}
	
	/**
	 * Switches the GUI to the game room panel
	 * (action command: MOVE_TO_GAME_ROOM)
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String moveToRoom(HashMap<String, String> param)
	{
		String actionResult = null;
		ClientController controller = ClientController.getInstance();
		
		String roomNo = param.get("roomNo"); // get room no
		GameRoom room = controller.getContext().getGameHall().
				searchRoom(Integer.parseInt(roomNo));
		controller.getContext().setGameRoom(room);
		controller.getGui().switchTo(controller.getGui().
				getGameRoomPanel()); // switch view
		
		return actionResult;
	}
}
