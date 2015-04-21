package server.action;

import java.util.HashMap;

import action.Action;

import server.comm.Client;
import server.controller.ServerController;

public class ServerAction extends Action
{
	/**
	 * Starts the server.
	 * (action command: START_SERVER)
	 * 
	 * @param client	source Client of the action command 
	 * @param param		parameter list of the action
	 * @return			action result as a string
	 */
	public String startServer(Client client, HashMap<String, String> param)
	{
		String actionResult = null;
		ServerController controller = ServerController.getInstance();
		
		if(controller.getCommunicator().startServer())
		{
			controller.getConsole().showInfo("Success", "START_SERVER");
		}
		else
		{
			controller.getConsole().showError("Error", "START_SERVER");
		}
		
		return actionResult;
	}
	
	/**
	 * Stops the server.
	 * (action command: STOP_SERVER)
	 * 
	 * @param client	source Client of the action command 
	 * @param param		parameter list of the action
	 * @return			action result as a string
	 */
	public String stopServer(Client client, HashMap<String, String> param)
	{
		String actionResult = null;
		ServerController controller = ServerController.getInstance();
		
		controller.getCommunicator().stopServer();
		
		return actionResult;
	}
	
	/**
	 * Terminates the program.
	 * (action command: EXIT)
	 * 
	 * @param client	source Client of the action command 
	 * @param param		parameter list of the action
	 * @return			action result as a string
	 */
	public String exit(Client client, HashMap<String, String> param)
	{
		String actionResult = null;
		ServerController controller = ServerController.getInstance();
		
		controller.getCommunicator().stopServer();
		System.exit(0);
		
		return actionResult;
	}
}
