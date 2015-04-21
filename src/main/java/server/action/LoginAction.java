package server.action;

import java.util.HashMap;

import action.Action;

import server.comm.Client;
import server.controller.ServerController;
import server.model.NormalUser;

public class LoginAction extends Action
{
	/**
	 * Logs the client in to the realm if the login information is correct.
	 * 
	 * (action command: LOGIN)
	 * 
	 * @param client	source Client of the action command 
	 * @param param		parameter list of the action
	 * @return			action result as a string
	 */
	public String login(Client client, HashMap<String, String> param)
	{
		String actionResult = null;
		String username = param.get("username");
		String password = param.get("password");
		ServerController controller = ServerController.getInstance();
		
		if(this.checkPass(username, password))
		{
			//add client to the list
			controller.getCommunicator().addClient(client);
			
			// associate client with account
			client.setUserAccount(new NormalUser(username));
			
			//send game list to the client
			controller.getDistributor().sendGameList(client, 
					controller.getRealm().getGameList());
			
			//set response for the client
			actionResult = Action.MOVE_TO_GAME_SELECT + "?";
		}
		else
		{
			client.setUserAccount(null);
			
			actionResult = Action.ERROR + "?" + "errorTitle=Login Failed&" + 
					"errorContent=Invalid username or password";
		}
		
		return actionResult;
	}
	
	/**
	 * Authenticates the client by checking the username and password.
	 * 
	 * @param username	username of the client
	 * @param password	password of the client	
	 * @return	authentication result as a boolean
	 */
	private boolean checkPass(String username, String password)
	{
		
		if(password.equals(ServerController.getInstance().hashMap.get(username)))
			return true;
		else		
			return false;
	}
}
