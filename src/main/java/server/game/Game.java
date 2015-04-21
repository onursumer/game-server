package server.game;

import java.util.List;

public abstract class Game
{
	protected List<Player> playerList;
	protected int minNumOfPlayers;
	protected int maxNumOfPlayers;
	
	public abstract boolean addPlayer(Player player);
	public abstract boolean removePlayer(Player player);
	public abstract boolean applyAction(Player player, String action);
	public abstract GameResult generateResult();
	public abstract String stateInfo();
	public abstract String playerInfo(Player player);
	public abstract Player selectPlayer(String playerInfo);
}
