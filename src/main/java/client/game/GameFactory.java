package client.game;

import java.util.HashMap;

import javax.swing.JPanel;

/**
 * Abstract Factory for the Game family.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public abstract class GameFactory
{
	// map for the relation between the game name and the game class name
	private static HashMap<String, String> factoryMap = initMap();
	
	/**
	 * Returns the proper GameFactory object for the given game name.
	 *  
	 * @param gameName	name of the game
	 * @return			GameFactory object for the given game name
	 * @throws ClassNotFoundException	if a reflection error occurs
	 * @throws InstantiationException	if a reflection error occurs
	 * @throws IllegalAccessException	if a reflection error occurs
	 */
	public static GameFactory getFactory(String gameName)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException
	{
		Class<?> factoryClass = Class.forName(factoryMap.get(gameName));
		
		return (GameFactory)factoryClass.newInstance();
	}
	
	/**
	 * Abstract method for the Game creation.
	 * 
	 * @return	the Game object for the Game family
	 */
	public abstract Game createGame();
	
	/**
	 * Abstract method for the game panel creation.
	 * 
	 * @return	the JPanel object for the Game family
	 */
	public abstract JPanel createPanel();
	
	// TODO xml file class list for game factory
	private static HashMap<String, String> initMap()
	{
		HashMap<String, String> factoryMap = new HashMap<String, String>();
		
		factoryMap.put("CHESS", "client.game.chess.ChessFactory");
		
		return factoryMap;
	}
	
}
