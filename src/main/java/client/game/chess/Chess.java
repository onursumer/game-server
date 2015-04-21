package client.game.chess;

import java.util.HashMap;

import client.game.TurnBasedGame;
import client.game.TurnBasedPlayer;

/**
 * Chess class extends TurnBasedGame.
 * 
 * @author Alper Karacelik
 *
 */
public class Chess extends TurnBasedGame
{
	private String[][] chessBoard = new String[8][8];
	private char turn;
	private String color;
	private HashMap<String, String> encodingMap = new HashMap<String, String>();
	private String winner;
	
	public Chess() {
		super();
		constructEncodingMap();
	}

	private void constructEncodingMap(){
		
		encodingMap.put("00", "WhiteRook");
		encodingMap.put("01", "WhiteKnight");
		encodingMap.put("02", "WhiteBishop");
		encodingMap.put("03", "WhiteQueen");
		encodingMap.put("04", "WhiteKing");
		encodingMap.put("05", "WhitePawn");
		encodingMap.put("06", "");
		encodingMap.put("07", "BlackRook");
		encodingMap.put("08", "BlackKnight");
		encodingMap.put("09", "BlackBishop");
		encodingMap.put("10", "BlackQueen");
		encodingMap.put("11", "BlackKing");
		encodingMap.put("12", "BlackPawn");
		
	}
	
	/**
	 * Applies movement which comes from server model
	 * to client model
	 * 
	 * @param action	movement to be applied
	 * @return		action result as a boolean
	 */
	@Override
	public boolean applyAction(String action)
	{
		if(action.charAt(3) == '#')
		{
			if(action.charAt(4) == 'W'){
				setWinner("White");
			}
			else
			{
				setWinner("Black");
			}
		}
		else
		{
			int fromX = Integer.parseInt(action.substring(0,1));
			int fromY = Integer.parseInt(action.substring(1,2));
			int toX = Integer.parseInt(action.substring(2,3));
			int toY = Integer.parseInt(action.substring(3,4));
			makeMovement(fromX, fromY, toX, toY);	
		}
		
		return true;
	}

	/**
	 * Applies the state of server model (current board formation
	 * and current turn)
	 * 
	 * @param state	state to be applied
	 * @return		action result as a boolean
	 */
	@Override
	public boolean applyGameState(String state) 
	{
		// Parse state
		int index = 0;
		
		String piece = state.substring(index, index+2);
		
		for (int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				chessBoard[i][j] = encodingMap.get(piece);
				index += 2;
				piece = state.substring(index, index+2);
				
			}
		}
		
		if(! piece.startsWith("#"))
		{
			return false;
		}
		else{
			turn = piece.charAt(1);
			return true;
		}
	}

	public String[][] getChessBoard() {
		return chessBoard;
	}

	private void makeMovement(int fromX, int fromY, int toX, int toY)
	{
		chessBoard[toX][toY] = chessBoard[fromX][fromY];
		chessBoard[fromX][fromY] = "";
	}
	
	/**
	 * Sets color of current client
	 * 
	 * @param state	color of current client
	 * @return		action result as a boolean
	 */
	@Override
	public boolean applyPlayerState(String state) {

		setColor(state);
		return true;
	}

	/**
	 * If client not holding a piece to move then it not a valid action
	 * Otherwise, action is send to server for checking validity of movement
	 * 
	 * @param action	movement that client tries to make
	 * @return			action result as a boolean
	 */
	@Override
	public boolean isValidAction(String action) {

		// Parse source and destination squares
		int fromX = Integer.valueOf(action.charAt(0))- 48;
		int fromY = Integer.valueOf(action.charAt(1))- 48;
		
		if((chessBoard[fromX][fromY] == null) || (chessBoard[fromX][fromY].compareTo("") == 0)){
			return false;
		}
		return true;
	}

	@Override
	protected TurnBasedPlayer nextPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public String getWinner() {
		return winner;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}
}
