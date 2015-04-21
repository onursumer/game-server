package server.game;

import java.util.HashMap;

public abstract class GameFactory
{
	private static HashMap<String, String> factoryMap = initMap();
	
	public static GameFactory getFactory(String gameName)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException
	{
		Class<?> factoryClass = Class.forName(factoryMap.get(gameName));
		
		return (GameFactory)factoryClass.newInstance();
	}
	
	public abstract Game createGame();
	public abstract Player createPlayer();
	
	// TODO xml file class list for game factory
	private static HashMap<String, String> initMap()
	{
		HashMap<String, String> factoryMap = new HashMap<String, String>();
		
		factoryMap.put("CHESS", "server.game.chess.ChessFactory");
		
		return factoryMap;
	}
	
}
