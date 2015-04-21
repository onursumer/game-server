package server.action;

import java.util.HashMap;
import java.util.List;

import server.comm.Client;
import server.controller.ServerController;
import action.Action;

/**
 * 
 * Class that handles chat related actions.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class ChatAction extends Action
{
	/**
	 * Distributes incoming chat message to the clients in the related
	 * game room.
	 * (action command: CHAT)
	 * 
	 * @param client
	 * @param param
	 * @return
	 */
	public String chat(Client client, HashMap<String, String> param)
	{
		String actionResult = null;
		String chatContent = param.get("chatContent");
		ServerController controller = ServerController.getInstance();
		
		List<Client> clientList = client.getUserAccount().
				getGameRoom().getClientList(); 
		
		controller.getDistributor().
				sendChatMsg(clientList, client, chatContent);
		
		return actionResult;
	}
}
