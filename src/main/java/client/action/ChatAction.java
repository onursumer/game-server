package client.action;

import java.util.HashMap;

import controller.Converter;


import action.Action;

import client.controller.ClientController;

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
	 * Gets the content of the chat message from the GUI and returns
	 * the action result as a string.
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String sendChatMsg(HashMap<String, String> param)
	{
		String actionResult = null;
		ClientController controller = ClientController.getInstance();
		
		// get chat content from GUI
		String chatContent = controller.getGui().getGameRoomPanel().
				getChatPanel().getChatField().getText();
		
		// clear chat field
		controller.getGui().getGameRoomPanel().getChatPanel().
				getChatField().setText(null);
		
		// convert content to a safe string 
		chatContent = Converter.convert(chatContent);
		
		// set action result
		actionResult = Action.CHAT + "?chatContent=" + chatContent;
		
		return actionResult;
	}
	
	/**
	 * Extracts the chat content and the sender from an incoming chat string,
	 * and updates the chat content of the related game room.
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String receiveChatMsg(HashMap<String, String> param)
	{
		String actionResult = null;
		ClientController controller = ClientController.getInstance();
		
		// get action params
		String chatContent = param.get("chatContent");
		String username = param.get("username");
		
		// revert chat content to its original form
		chatContent = Converter.revert(chatContent);
		
		// add chat content to the game room panel
		controller.getGui().getGameRoomPanel().
				addChatContent(username, chatContent);

		return actionResult;
	}
}
