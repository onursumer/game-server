package client.action;

import java.util.HashMap;

import action.Action;

import client.controller.ClientController;

/**
 * Class that handles menu and button actions.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class MenuAction extends Action
{
	/**
	 * Terminates the program.
	 * (action command: EXIT)
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String exit(HashMap<String, String> param)
	{
		System.exit(0);
		return null;
	}
	
	/**
	 * Constructs a request to be sent to the server with the selected
	 * game information.
	 * (action command: SELECT_GAME)
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String selectGame(HashMap<String, String> param)
	{
		String actionResult = null;
		
		String gameName = param.get("gameName"); // get game name
		
		// construct action result
		actionResult = Action.SELECT_GAME + "?gameName=" + gameName;
		
		return actionResult;
	}
	
	/**
	 * Constructs a request to be sent to the server.
	 * (action command: CREATE_ROOM)
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String createRoom(HashMap<String, String> param)
	{
		String actionResult = null;
		
		// construct action result
		actionResult = Action.CREATE_ROOM + "?";
		
		return actionResult;
	}
	
	/**
	 * Constructs a request to be sent to the server with the room number.
	 * (action command: JOIN_ROOM)
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String joinRoom(HashMap<String, String> param)
	{
		String actionResult = null;
		String roomNo = param.get("roomNo"); // get room no
		
		// construct action result
		actionResult = Action.JOIN_ROOM + "?roomNo=" + roomNo;
		
		return actionResult;
	}
	
	/**
	 * Constructs a request to be sent to the server.
	 * (action command: START_GAME)
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String startGame(HashMap<String, String> param)
	{
		String actionResult = null;
		
		// construct action result
		actionResult = Action.START_GAME + "?";
		
		return actionResult;
	}
	
	/**
	 * Constructs a request to be sent to the server with the player info.
	 * (action command: SELECT_PLAYER)
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String selectPlayer(HashMap<String, String> param)
	{
		String actionResult = null;
		
		String player = param.get("player"); // get player
		
		// construct action result
		actionResult = Action.SELECT_PLAYER + "?player=" + player;
		
		return actionResult;
	}
	
	/**
	 * Constructs a request to be sent to the server and updates the client
	 * context.
	 * (action command: EXIT_ROOM)
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String exitRoom(HashMap<String, String> param)
	{
		String actionResult = null;
		ClientController controller = ClientController.getInstance();
		
		// construct action result
		actionResult = Action.EXIT_ROOM + "?";
		
		// update context
		controller.getContext().setGameRoom(null);
		
		return actionResult;
	}
	
	/**
	 * Constructs a request to be sent to the server.
	 * (action command: EXIT_HALL)
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String exitHall(HashMap<String, String> param)
	{
		String actionResult = null;
		ClientController controller = ClientController.getInstance();
		
		// construct action result
		actionResult = Action.EXIT_HALL + "?";
		
		// update client context
		controller.getContext().setGameRoom(null);
		controller.getContext().setGameHall(null);
		
		return actionResult;
	}
	
	/**
	 * Saves the current settings to the settings file
	 * (action command: SAVE_SETTINGS)
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String saveSettings(HashMap<String, String> param)
	{
		String actionResult = null;
		ClientController controller = ClientController.getInstance();
		
		// get host name
		String hostName = controller.getGui().getLoginPanel().
				getSettingsPanel().getHostName();
				 
		// get port no
		String portNo = controller.getGui().getLoginPanel().
				getSettingsPanel().getPortNo();
		
		// configure and save settings
		controller.getSettings().configHostIP(hostName);
		controller.getSettings().configPort(portNo);
		controller.getSettings().saveSettings();
		
		// update communicator data
		controller.getCommunicator().
				setHost(controller.getSettings().getHost());
		controller.getCommunicator().
				setPortNo(controller.getSettings().getPortNo());
		
		return actionResult;
	}
}
