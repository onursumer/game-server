package client.action;

import java.util.HashMap;

import action.Action;

import client.controller.ClientController;

import controller.Constants;

public class LoginAction extends Action
{
	/**
	 * Gets the login information from the GUI and constructs request
	 * to be sent to the server with username and password.
	 * (action command: LOGIN)
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String login(HashMap<String, String> param)
	{
		String actionResult = null;
		ClientController controller = ClientController.getInstance();
		String username = controller.getGui().
				getLoginPanel().getUsername(); // get username
		String password = controller.getGui().
				getLoginPanel().getPassword(); // get password
		
		// invalid login
		if(!checkPass(username, password))
		{
			controller.getGui().showError("Login Failed", "Invalid Format");
		}
		// no connection available
		else if(!controller.getCommunicator().initConnection())
		{
			controller.getGui().showError("Connection Error",
					"Cannot connect to server");
		}
		else // action will be forwarded to server
			actionResult = Action.LOGIN + "?username=" + username +
					"&password=" + password;
		
		return actionResult;
	}
	
	/**
	 * Checks the format of username and password for validity.
	 * 
	 * @param username	username of the client
	 * @param password	password of the client
	 * @return	TRUE if format is valid, FALSE otherwise
	 */
	private boolean checkPass(String username, String password)
	{		
		if((username == null) || (password == null))
			return false;
		else if(password.length() < 1)
			return false;
		else if(!username.matches(Constants.USERNAME_FORMAT))
			return false;
		else
			return true;
	}
	
	private String encrypt(char[] password)
	{
		// TODO encryption method..
		return new String(password);
	}
}
