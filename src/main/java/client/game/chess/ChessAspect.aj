package client.game.chess;

import annot.Producer;
import client.comm.Command;
import client.controller.ClientController;
import java.awt.event.MouseEvent;

/**
 * ChessAspect aspect encapsulates both request synchronizing and rendering aspects.
 * it reflects the changes in server model to client view, 
 * and forwards action performed by client
 * 
 * @author Alper Karaçelik
 *
 */
public aspect ChessAspect {

	/**
	 * cellClicked cuts when client try to make a movement on chess board.
	 * 
	 * @param chessPanel	chess panel
	 * @param toX			X coordinate of destination board cell
	 * @param toY			Y coordinate of destination board cell 
	 */
	pointcut cellClicked(ChessPanel chessPanel, int toX, int toY): call (private boolean ChessPanel.clickCell(MouseEvent, int, int, int)) 
																   && target(chessPanel)
																   && args(*, toX, toY, *);
	
	/**
	 * Advises after client try to drop a chess piece to destination cell
	 */
	after(ChessPanel chessPanel, int toX, int toY) returning(boolean pieceDropped) : cellClicked(chessPanel, toX, toY)
	{
		if(pieceDropped)
		{
			String command = "GAME_ACTION?gameAction=";
			int fromX = chessPanel.getHeldX();
			int fromY = chessPanel.getHeldY();
			chessPanel.setHeldX(-1);
			chessPanel.setHeldY(-1);
			command = command + fromX + fromY + toX + toY;
			produceCommand(command);
		}
		
	}
	
	/**
	 * movementMade cuts when server passes last movement to current client
	 * 
	 * @param chess			model of chess in the client side
	 * @param fromX			X coordinate of source board cell
	 * @param fromY			Y coordinate of source board cell 
	 * @param toX			X coordinate of destination board cell
	 * @param toY			Y coordinate of destination board cell 
	 */
	pointcut movementMade(Chess chess, int fromX, int fromY, int toX, int toY): call(private void Chess.makeMovement(int, int, int, int))
																				&& target(chess)
																				&& args(fromX, fromY, toX, toY);
	
	/**
	 * Advises after server passes last movement to current client
	 * Chess panel is rendered
	 */
	after(Chess chess, int fromX, int fromY, int toX, int toY) returning(): movementMade(chess, fromX, fromY, toX, toY)
	{
		ChessPanel panel = (ChessPanel)ClientController.getInstance().getGui().getGameRoomPanel().getGamePanel();
		panel.makeMovement(fromX, fromY, toX, toY);
		
	}
	
	/**
	 * gameStateChanged cuts when the sate of game in client model is changed
	 * (cut when chess board initialized)
	 * 
	 * @param chess			model of chess in the client side
	 */
	pointcut gameStateChanged(Chess chess): execution(public boolean Chess.applyGameState(String))
											&& this(chess);
	
	/**
	 * Advises after the sate of game in client model is changed
	 * Chess panel is rendered
	 */
	after(Chess chess) returning(): gameStateChanged(chess)
	{
		ChessPanel panel = (ChessPanel)ClientController.getInstance().getGui().getGameRoomPanel().getGamePanel();
		panel.setChessBoard(chess.getChessBoard());
	}
	
	@Producer
	public Command produceCommand(String input)
	{
		return new Command(input);
	}
	
	/**
	 * gameOver cuts when game over message comes from server
	 * 
	 * @param chess			model of chess in the client side
	 */
	pointcut gameOver(Chess chess): call(public void Chess.setWinner(String)) 
									&& target(chess);
	
	/**
	 * Advises after game is over
	 * Chess panel is rendered
	 */
	after(Chess chess) returning(): gameOver(chess)
	{
		ChessPanel panel = (ChessPanel)ClientController.getInstance().getGui().getGameRoomPanel().getGamePanel();
		panel.endOfGame(chess.getWinner());
	}
	
	/**
	 * playerSet cuts when color of client is set
	 * 
	 * @param chess			model of chess in the client side
	 */
	pointcut playerSet(Chess chess): call(public void Chess.setColor(String)) 
									 && target(chess);
	
	/**
	 * Advises after color of client is set
	 * Chess panel is rendered
	 */
	after(Chess chess) returning(): playerSet(chess)
	{
		ChessPanel panel = (ChessPanel)ClientController.getInstance().getGui().getGameRoomPanel().getGamePanel();
		panel.getLblPlayerColor().setText("You Are " + chess.getColor());
	}
}
