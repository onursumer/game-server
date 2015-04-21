package server.controller;

import server.comm.Client;
import server.game.Player;
import server.game.Game;
import server.model.User;
import server.model.GameRoom;

/**
 * This aspect introduces members for predefined classes.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public aspect AssociationAspect
{
	// data member introduction to Client class
	private String Client.connectionTime;
	private User Client.userAccount;
	
	public void Client.setUserAccount(User userAccount)
	{
		this.userAccount = userAccount;
	}

	public User Client.getUserAccount()
	{
		return userAccount;
	}
	
	public String Client.getConnectionTime()
	{
		return connectionTime;
	}

	public void Client.setConnectionTime(String connectionTime)
	{
		this.connectionTime = connectionTime;
	}
	
	// data member introduction to User class
	private Player User.player;
	
	public Player User.getPlayer()
	{
		return this.player;
	}
	
	public void User.setPlayer(Player player)
	{
		this.player = player;
	}
	
	// data member introduction to Game class
	private GameRoom Game.gameRoom;
	
	public GameRoom Game.getGameRoom()
	{
		return this.gameRoom;
	}
	 
	public void Game.setGameRoom(GameRoom gameRoom)
	{
		this.gameRoom = gameRoom;
	}
}
