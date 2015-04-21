package server.action;

import java.util.HashMap;

import server.comm.Client;
import server.game.Game;
import server.game.Player;
import action.Action;

public class GameAction extends Action
{
	/**
	 * Applies the game action that is received from the specified client
	 * to the game which is being played by that client.
	 * (action command: GAME_ACTION)
	 * 
	 * @param client	source Client of the action command 
	 * @param param		parameter list of the action
	 * @return			action result as a string
	 */
	public String gameAction(Client client, HashMap<String, String> param)
	{
		String actionResult = null;
		String gameAction = param.get("gameAction");
		
		Game game = client.getUserAccount().getGameRoom().getGame();
		Player player = client.getUserAccount().getPlayer();
		
		if(player == null || !player.isReady()) // user is not in the game
		{
			actionResult = Action.ERROR + "?errorTitle=Game Error" + 
				"&errorContent=You are not in the game!\n" + 
					"Please start the game first";
		}
		else // user is a valid player
		{
			// apply user action to the game
			game.applyAction(player, gameAction);
		}
		
		return actionResult;
	}
	
	/**
	 * Sets the player of the specified client according to the given player
	 * information.
	 * (action command: SELECT_PLAYER)
	 * 
	 * @param client	source Client of the action command 
	 * @param param		parameter list of the action
	 * @return			action result as a string
	 */
	public String selectPlayer(Client client, HashMap<String, String> param)
	{
		String actionResult = null;
		String playerInfo = param.get("player");
		
		Game game = client.getUserAccount().getGameRoom().getGame();
		Player player;
		
		player = client.getUserAccount().getPlayer();
		
		// user already selected a player
		if(player != null)
		{
			// release current player
			player.setAvailable(true);
			player.setReady(false);
		}
		
		player = game.selectPlayer(playerInfo);
		
		if((player == null) || (!player.isAvailable()))
		{
			actionResult = Action.ERROR + "?errorTitle=Player Error" + 
				"&errorContent=Sorry...\n" +
					"Selected player is not available";
		}
		else
		{
			// set the player of the user
			client.getUserAccount().setPlayer(player);
			player.setAvailable(false);
			
			// TODO send the state of the selected player
			actionResult = Action.SET_PLAYER_STATE + "?player=" + 
				client.getUserAccount().getGameRoom().getGame().
					playerInfo(player);
		}
		
		return actionResult;
	}
	
	/**
	 * Sets the player controlled by the specified player as ready to start
	 * the game.
	 * (action command: START_GAME)
	 * 
	 * @param client	source Client of the action command 
	 * @param param		parameter list of the action
	 * @return			action result as a string
	 */
	public String startGame(Client client, HashMap<String, String> param)
	{
		String actionResult = null;
		
		Game game = client.getUserAccount().getGameRoom().getGame();
		Player player = client.getUserAccount().getPlayer();
		
		// try to add a new player to the game
		if(player != null)
		{
			game.addPlayer(player);
			player.setReady(true);
		}
		else
		{
			actionResult = Action.ERROR + "?errorTitle=Player Error" +			
				"&errorContent=Please select a player!";
		}
		
		// TODO check for all players to be ready
		
		return actionResult;
	}
}
