package server.game.chess;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import server.comm.Client;

/**
 * ChessAspect aspect encapsulates both request synchronizing and distribution aspects.
 * it forwards the changes in server model to client view, 
 * and forwards state changes in model to relevant clients
 * 
 * @author Alper Karaçelik
 *
 */
public aspect ChessAspect {

	/**
	 * endOfGame cuts when game is over
	 * 
	 * @param chess			model of chess in the client side
	 */
	pointcut endOfGame(Chess chess): call(public void Chess.setEndOfGame(boolean))
	  								 && target(chess);
	
	/**
	 * Advises after game is over. (game over state is send to relevant clients)
	 */
	after(Chess chess) returning() : endOfGame(chess)
	{
		String movement = "END#";
		if(chess.getWinner() == Chess.Color.white)
		{
			movement += "W";
		}
		else
		{
			movement += "B";
		}
		sendMovement(chess.getGameRoom().getClientList(), movement);
	}
	
	/**
	 * reinitialize cuts when game is reinitialized
	 * 
	 * @param chess			model of chess in the client side
	 */
	pointcut reinitialize(Chess chess): call(private void Chess.reinitialize())
	  									&& target(chess);
	
	/**
	 * Advises after game is reinitialized. 
	 * (state of the model (chess board and current turn) is send to relevant clients)
	 */
	after(Chess chess) returning() : reinitialize(chess)
	{
		sendState(chess.getGameRoom().getClientList(), chess.stateInfo());
	}
	
	/**
	 * movement cuts when a movement is made on chess board
	 * 
	 * @param chess			model of chess in the client side
	 * @param fromX			X coordinate of source board cell
	 * @param fromY			Y coordinate of source board cell 
	 * @param toX			X coordinate of destination board cell
	 * @param toY			Y coordinate of destination board cell 
	 */
	pointcut movement(Chess chess, int fromX, int fromY, int toX, int toY): call(private void Chess.makeMovement(int, int, int, int))
																			&& target(chess)
																			&& args(fromX, fromY, toX, toY);
	
	/**
	 * Advises after a movement is made on board. 
	 * (movement is send to relevant clients)
	 */
	after(Chess chess, int fromX, int fromY, int toX, int toY) returning(): movement(chess, fromX, fromY, toX, toY)
	{
		String movement = "" + fromX + fromY + toX + toY;
		sendMovement(chess.getGameRoom().getClientList(), movement);
	}
	
	/**
	 * Sends the last valid movement 
	 * to the all clients in current game room
	 * 
	 * @param clientList	receiver clients
	 * @param movement	    movement to be sent
	 */
	private void sendMovement(List<Client> clientList, String movement)
	{
		Iterator<Client> iter = clientList.iterator();
		String command = "GAME_ACTION_RESULT?gameAction=";
		Client current;
		
		while(iter.hasNext())
		{
			current = iter.next();
			
			try {
				// send user info
				current.send(command + movement);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sends the reinitialized chess state 
	 * to the all clients in current game room
	 * 
	 * @param clientList	receiver clients
	 * @param state	        state to be sent
	 */
	private void sendState(List<Client> clientList, String state)
	{
		Iterator<Client> iter = clientList.iterator();
		String command = "SET_GAME_STATE?state=";
		Client current;
		
		while(iter.hasNext())
		{
			current = iter.next();
			
			try {
				// send user info
				current.send(command + state);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

