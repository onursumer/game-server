package client.action;

import java.util.HashMap;

import javax.swing.JPanel;

import action.Action;

import client.controller.ClientController;
import client.game.Game;
import client.game.GameFactory;

public class GameAction extends Action
{
	/**
	 * Constructs a request to be sent to the server with the performed
	 * game action.
	 * (action command: GAME_ACTION) 
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String sendGameAction(HashMap<String, String> param)
	{
		String actionResult = null;
		ClientController controller = ClientController.getInstance();
		
		String gameAction = param.get("gameAction");
		
		// before sending action, validate action on client side
		if(controller.getContext().getGame().isValidAction(gameAction))
		{
			// send game action to the server
			actionResult = Action.GAME_ACTION + "?gameAction=" + gameAction;
		}
		else // invalid move
		{
			// show error message on the screen
			controller.getGui().showError("Error", "Invalid Move");
		}
		
		return actionResult;
	}
	
	/**
	 * Applies the game action that is received from the server to the
	 * current game.
	 * (action: GAME_ACTION_RESULT)
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String receiveGameAction(HashMap<String, String> param)
	{
		String actionResult = null;
		ClientController controller = ClientController.getInstance();
		
		String gameAction = param.get("gameAction");
		
		controller.getContext().getGame().applyAction(gameAction);

		return actionResult;
	}
	
	/**
	 * Creates a game object for the given game name.
	 * (action command: INIT_GAME)
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String initGame(HashMap<String, String> param)
	{
		String actionResult = null;
		ClientController controller = ClientController.getInstance();
		
		String gameName = param.get("gameName");
		
		try {
			GameFactory factory = GameFactory.getFactory(gameName);
			Game game = factory.createGame();
			JPanel gamePanel = factory.createPanel();
			
			controller.getContext().setGame(game);
			controller.getGui().getGameRoomPanel().updateGamePanel(gamePanel);
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
		
		return actionResult;
	}
	
	/**
	 * Sets the state of the game that is received from the server.
	 * (action command: SET_STATE)
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String setState(HashMap<String, String> param)
	{
		String actionResult = null;
		ClientController controller = ClientController.getInstance();
		
		String state = param.get("state");
		controller.getContext().getGame().applyGameState(state);
		
		return actionResult;
	}
	
	/**
	 * Sets the state of the player which is controlled by this client.
	 * (action command: SET_PLAYER_STATE)
	 * 
	 * @param param		parameter list of the action
	 * @return		action result as a string
	 */
	public String setPlayer(HashMap<String, String> param)
	{
		String actionResult = null;
		ClientController controller = ClientController.getInstance();
		
		String state = param.get("player");
		controller.getContext().getGame().applyPlayerState(state);
		
		return actionResult;
	}
}
