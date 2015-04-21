package client.action;

import java.util.HashMap;

import client.controller.ClientController;

import action.Action;

public class ClientAction extends Action
{
	/**
	 * Displays the incoming error message in the GUI.
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String error(HashMap<String, String> param)
	{
		String actionResult = null;
		ClientController controller = ClientController.getInstance();
		
		String errorTitle = param.get("errorTitle"); // get error title
		String errorContent = param.get("errorContent"); // get error content
		
		// shows the error message with its title
		controller.getGui().showError(errorTitle, errorContent);
		
		return actionResult;
	}
}
