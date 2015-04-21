package client.model;

import java.util.ArrayList;
import java.util.List;

import client.game.Game;

/**
 * Represents the contex of the client with the current game hall,
 * current game room, current game, and the list of games.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class ClientContext
{
	// list of games
	private List<String> gameList;
	
	// current game hall of the client
	private GameHall gameHall;
	
	// current game room of the client
	private GameRoom gameRoom;
	
	// current game client plays
	private Game game;

	/**
	 * Constructs the client context
	 */
	public ClientContext()
	{
		gameList = new ArrayList<String>();;
	}
	
	/**
	 * Adds a new game to the game list.
	 * 
	 * @param game	name of the game to be added
	 */
	public void addGame(String game)
	{
		gameList.add(game);
	}
	
	
	/*
	 * GETTERS and SETTERS
	 */
	
	public List<String> getGameList() {
		return gameList;
	}
	
	public void setGameList(List<String> gameList) {
		this.gameList = gameList;
	}
	
	public GameHall getGameHall() {
		return gameHall;
	}
	
	public void setGameHall(GameHall gameHall) {
		this.gameHall = gameHall;
	}
	
	public GameRoom getGameRoom() {
		return gameRoom;
	}
	
	public void setGameRoom(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
	}
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
}
