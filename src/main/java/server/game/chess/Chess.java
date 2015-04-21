package server.game.chess;

import java.util.ArrayList;
import java.util.Iterator;

import server.game.GameResult;
import server.game.Player;
import server.game.TurnBasedGame;
import server.game.TurnBasedPlayer;

/**
 * Chess class extends TurnBasedGame.
 * Class includes chess logic and essentials(chess board, current turn, etc.)
 * 
 * @author Alper Karacelik
 *
 */
public class Chess extends TurnBasedGame
{
	public static enum Color {white, black};

	private ChessPiece[][] chessBoard = new ChessPiece[8][8];
	private Color turn;
	private boolean endOfGame;
	private Color winner;
	
	public Chess() {
		super();
		playerList = new ArrayList<Player>();
		minNumOfPlayers = 2;
		maxNumOfPlayers = 2;
		turn = Color.white;
		initializeBoard();
		endOfGame = false;
		winner = null;
	}

	/**
	 * Re-initializes the board. 
	 * (Players are in the game, but can not make a move until re-starting)
	 * 
	 */
	private void reinitialize()
	{
		//playerList.clear();
		Iterator<Player> iter = playerList.iterator();
		
		while(iter.hasNext())
		{
			iter.next().setReady(false);
		}
		
		turn = Color.white;
		initializeBoard();
		endOfGame = false;
		winner = null;
	}
	
	public boolean isEndOfGame() {
		return endOfGame;
	}

	public void setEndOfGame(boolean endOfGame) {
		this.endOfGame = endOfGame;
	}

	public void setWinner(Color winner) {
		this.winner = winner;
	}

	public Color getWinner() {
		return winner;
	}
	
	public void setChessBoardAt(int i, int j, ChessPiece piece)
	{
		chessBoard[i][j] = piece;
	}
	
	public ChessPiece getChessBoardAt(int i, int j)
	{
		return chessBoard[i][j];
	}
	
	public void initializeBoard()
	{
		chessBoard[7][0] = new ChessPiece("Rook", Color.white);   // A1
		chessBoard[7][1] = new ChessPiece("Knight", Color.white); // B1
		chessBoard[7][2] = new ChessPiece("Bishop", Color.white); // C1
		chessBoard[7][3] = new ChessPiece("Queen", Color.white);  // D1
		chessBoard[7][4] = new ChessPiece("King", Color.white);   // E1
		chessBoard[7][5] = new ChessPiece("Bishop", Color.white); // F1
		chessBoard[7][6] = new ChessPiece("Knight", Color.white); // G1
		chessBoard[7][7] = new ChessPiece("Rook", Color.white);   // H1
		
		for (int j = 0; j <= 7; j++){
			chessBoard[6][j] = new ChessPiece("Pawn", Color.white); // A2..H2
		}
		
		chessBoard[0][0] = new ChessPiece("Rook", Color.black);   // A8
		chessBoard[0][1] = new ChessPiece("Knight", Color.black); // B8
		chessBoard[0][2] = new ChessPiece("Bishop", Color.black); // C8
		chessBoard[0][3] = new ChessPiece("Queen", Color.black);  // D8
		chessBoard[0][4] = new ChessPiece("King", Color.black);   // E8
		chessBoard[0][5] = new ChessPiece("Bishop", Color.black); // F8
		chessBoard[0][6] = new ChessPiece("Knight", Color.black); // G8
		chessBoard[0][7] = new ChessPiece("Rook", Color.black);   // H8
		
		for (int j = 0; j <= 7; j++){
			chessBoard[1][j] = new ChessPiece("Pawn", Color.black); // A7..H7
		}
		
		for (int i = 2; i <= 5; i++){
			for(int j = 0; j <= 7; j++){
				
				chessBoard[i][j] = null;
			}
		}
	}

	/**
	 * Checks if player tries to move other player's piece
	 * 
	 * @param player	player who tries to make a move on board
	 * @param fromX		X coordinate of source board cell
	 * @param fromY		Y coordinate of source board cell
	 * @return			true: player holds his/her own piece
	 * 					false: otherwise
	 */
	private boolean currentPlayersPiece(ChessPlayer player, int fromX, int fromY)
	{
		if(chessBoard[fromX][fromY].getColor() == player.getColor()){
			return true;
		}
		return false;
	}


	/**
	 * Checks if current movement is a valid one
	 * 
	 * @param player	player who tries to make a move on board
	 * @param fromX		X coordinate of source board cell
	 * @param fromY		Y coordinate of source board cell
	 * @param toX		X coordinate of destination board cell
	 * @param toY		Y coordinate of destination board cell 
	 * @return			true: if movement is valid
	 * 					false: otherwise
	 */
	private boolean movementValid(ChessPlayer player, int fromX, int fromY, int toX, int toY)
	{
		if( (chessBoard[fromX][fromY].getName().compareToIgnoreCase("Pawn") == 0) &&
			(chessBoard[fromX][fromY].getColor() == Color.white)){
			return whitePawnMovementValid(fromX, fromY, toX, toY);
		}
		else if( (chessBoard[fromX][fromY].getName().compareToIgnoreCase("Pawn") == 0) &&
				 (chessBoard[fromX][fromY].getColor() == Color.black)){
			return blackPawnMovementValid(fromX, fromY, toX, toY);
		}
		else if(chessBoard[fromX][fromY].getName().compareToIgnoreCase("Rook") == 0){
			return rookMovementValid(player, fromX, fromY, toX, toY);
		}
		else if(chessBoard[fromX][fromY].getName().compareToIgnoreCase("Knight") == 0){
			return knightMovementValid(player, fromX, fromY, toX, toY);
		}
		else if(chessBoard[fromX][fromY].getName().compareToIgnoreCase("Bishop") == 0){
			return bishopMovementValid(player, fromX, fromY, toX, toY);
		}
		else if(chessBoard[fromX][fromY].getName().compareToIgnoreCase("Queen") == 0){
			return queenMovementValid(player, fromX, fromY, toX, toY);
		}
		else if(chessBoard[fromX][fromY].getName().compareToIgnoreCase("King") == 0){
			return kingMovementValid(player, fromX, fromY, toX, toY);
		}
		return false;
	}

	private boolean whitePawnMovementValid(int fromX, int fromY, int toX, int toY){
		
		if(fromX <= toX){
			return false;
		}
		if((fromX-toX) > 2){
			return false;
		}
		// if it is not the first movement of white pawn
		if( (fromX != 6) && ((fromX-toX) == 2)){
			return false;
		}
		if((fromY == toY)){
			for (int i = (fromX-1); i >= toX; i--){
				if((chessBoard[i][toY] != null) && (chessBoard[i][toY].getName().compareTo("") != 0) ){
					return false;
				}
			}
			return true;
		}
		if(Math.abs(fromY-toY) > 1){
			return false;
		}
		else if(Math.abs(fromY-toY) == 1){
			if ( (chessBoard[toX][toY] == null) || (! (chessBoard[toX][toY].getColor() == Color.black))){
				return false;
			}
		}
		return true;
	}
	
	private boolean blackPawnMovementValid(int fromX, int fromY, int toX, int toY){
		
		if(fromX >= toX){
			return false;
		}
		if((toX - fromX) > 2){
			return false;
		}
		// If it is not the first movement of white pawn
		if( (fromX != 1) && ((toX - fromX) == 2)){
			return false;
		}
		if((fromY == toY)){
			for (int i = (fromX+1); i <= toX; i++){
				if((chessBoard[i][toY] != null) && (chessBoard[i][toY].getName().compareTo("") != 0) ){
					return false;
				}
			}
			return true;
		}
		if(Math.abs(fromY-toY) > 1){
			return false;
		}
		else if(Math.abs(fromY-toY) == 1){
			if ( (chessBoard[toX][toY] == null) || (! (chessBoard[toX][toY].getColor() == Color.white))){
				return false;
			}
		}
		return true;
	}
	
	private boolean rookMovementValid(ChessPlayer player, int fromX, int fromY, int toX, int toY){
		
		if( movingToOwnPiece(player, toX, toY) ){
			return false;
		}
		
		// Check movement shape
		if( (fromX != toX) && fromY != toY){
			return false;
		}
		
		// Check the path
		if( fromX > toX ){
			for(int i = (fromX-1); i > toX; i--){
				if((chessBoard[i][toY] != null) && (chessBoard[i][toY].getName().compareTo("") != 0) ){
					return false;
				}
			}
			return true;
		}
		else if( fromX < toX ){
			for(int i = (fromX+1); i < toX; i++){
				if((chessBoard[i][toY] != null) && (chessBoard[i][toY].getName().compareTo("") != 0) ){
					return false;
				}
			}
			return true;
		}
		if( fromY > toY ){
			for(int i = (fromY-1); i > toY; i--){
				if((chessBoard[toX][i] != null) && (chessBoard[toX][i].getName().compareTo("") != 0) ){
					return false;
				}
			}
			return true;
		}
		else if( fromY < toY ){
			for(int i = (fromY+1); i < toY; i++){
				if((chessBoard[toX][i] != null) && (chessBoard[toX][i].getName().compareTo("") != 0) ){
					return false;
				}
			}
			return true;
		}
		
		return true;
	}
	
	private boolean knightMovementValid(ChessPlayer player, int fromX, int fromY, int toX, int toY){
		
		if( movingToOwnPiece(player, toX, toY) ){
			return false;
		}
		if((Math.abs(fromX-toX) == 2 && Math.abs(fromY-toY) == 1) ||
		   (Math.abs(fromX-toX) == 1 && Math.abs(fromY-toY) == 2)){
			return true;
		}
		else{
			return false;
		}
			
	}
	
	private boolean bishopMovementValid(ChessPlayer player, int fromX, int fromY, int toX, int toY){
		
		if( movingToOwnPiece(player, toX, toY) ){
			return false;
		}
		
		// Check movement shape
		if( Math.abs(fromX-toX) != Math.abs(fromY-toY)) {
			return false;
		}
		
		// Check the path
		// SOUTH-EAST
		if( (fromX < toX) && (fromY < toY) ){
			int i = fromX+1;
			int j = fromY+1;
			while((i < toX) && (j < toY)){
				if((chessBoard[i][j] != null) && (chessBoard[i][j].getName().compareTo("") != 0) ){
					return false;
				}
				i++;
				j++;
			}
			return true;
		}
		// SOUTH-WEST
		else if( (fromX < toX) && (fromY > toY) ){
			int i = fromX+1;
			int j = fromY-1;
			while((i < toX) && (j > toY)){
				if((chessBoard[i][j] != null) && (chessBoard[i][j].getName().compareTo("") != 0) ){
					return false;
				}
				i++;
				j--;
			}
			return true;
		}
		// NORTH-EAST
		else if( (fromX > toX) && (fromY < toY) ){
			int i = fromX-1;
			int j = fromY+1;
			while((i > toX) && (j < toY)){
				if((chessBoard[i][j] != null) && (chessBoard[i][j].getName().compareTo("") != 0) ){
					return false;
				}
				i--;
				j++;
			}
			return true;
		}
		// NORTH-WEST
		else if( (fromX > toX) && (fromY > toY) ){
			int i = fromX-1;
			int j = fromY-1;
			while((i > toX) && (j > toY)){
				if((chessBoard[i][j] != null) && (chessBoard[i][j].getName().compareTo("") != 0) ){
					return false;
				}
				i--;
				j--;
			}
			return true;
		}
		return true;
	}
	
	private boolean queenMovementValid(ChessPlayer player, int fromX, int fromY, int toX, int toY){
		
		return (rookMovementValid(player, fromX, fromY, toX, toY) || bishopMovementValid(player, fromX, fromY, toX, toY));
	}
	
	private boolean kingMovementValid(ChessPlayer player, int fromX, int fromY, int toX, int toY){
		
		if( movingToOwnPiece(player, toX, toY) ){
			return false;
		}
		
		if( (Math.abs(fromX-toX) > 1) || (Math.abs(fromY-toY) > 1) ){
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if player tries to move his/her piece to a cell which contains his/her own piece
	 * 
	 * @param player	player who tries to make a move on board
	 * @param fromX		X coordinate of source board cell
	 * @param fromY		Y coordinate of source board cell
	 * @return			true: player tries to move his/her piece to a cell which contains his/her own piece
	 * 					false: otherwise
	 */
	private boolean movingToOwnPiece(ChessPlayer player, int toX, int toY){
		
		if( chessBoard[toX][toY] != null ){
			if((player.getColor() == Color.white) && (chessBoard[toX][toY].getColor() == Color.white) ||
			   (player.getColor() == Color.black) && (chessBoard[toX][toY].getColor() == Color.black)){
				return true;
			}
		}
		return false;
	}
	
	private void makeMovement(int fromX, int fromY, int toX, int toY)
	{
		chessBoard[toX][toY] = chessBoard[fromX][fromY];
		chessBoard[fromX][fromY] = null;
	}

	/**
	 * Checks end of game
	 * 
	 * @param fromX		X coordinate of source board cell
	 * @param fromY		Y coordinate of source board cell
	 * @return			true: current movement causes end of game
	 * 					false: otherwise
	 */
	public void endOfGame(int toX, int toY)
	{
		String destRock = null;
		if(chessBoard[toX][toY] != null)
		{
			destRock = chessBoard[toX][toY].getName();
		}
		if ((destRock != null) && (destRock.compareTo("") != 0)){
			if(destRock.compareToIgnoreCase("King") == 0){
				if( chessBoard[toX][toY].getColor() == Color.white){
					setWinner(Color.black);
				}
				else{
					setWinner(Color.white);
				}
				setEndOfGame(true);
			}
		}
	}
	
	@Override
	protected boolean isValidAction(String action) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected TurnBasedPlayer nextPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addPlayer(Player player)
	{
		Iterator<Player> iter = this.playerList.iterator();
		
		if(playerList.size() == (maxNumOfPlayers))
		{
			return false;
		}
		else
		{
			while(iter.hasNext())
			{
				if(iter.next() == player) // player is already in list
				{
					return false;
				}
			}
			
			playerList.add(player);
		}
		
		return true;
	}

	/**
	 * Tries to apply movement(action) comes from a player 
	 * 
	 * @param player	player who tries to make a move on board
	 * @param action	movement (like 0011->A8B7)
	 * @return			true: if current action is valid
	 * 					false: otherwise
	 */
	@Override
	public boolean applyAction(Player player, String action) {
		
		ChessPlayer chessPlayer = (ChessPlayer) player;
		
		if(chessPlayer.getColor() != turn){
			return false;
		}
		
		// Parse source and destination squares
		int fromX = Integer.valueOf(action.charAt(0)) -48;
		int fromY = Integer.valueOf(action.charAt(1)) -48;
		int toX = Integer.valueOf(action.charAt(2)) -48;
		int toY = Integer.valueOf(action.charAt(3)) -48;
		
		if( ! currentPlayersPiece(chessPlayer, fromX, fromY)){
			return false;
		}
		if( ! movementValid(chessPlayer, fromX, fromY, toX, toY)){
			return false;
		}
		
		endOfGame(toX, toY);
		if(! endOfGame)
		{
			makeMovement(fromX, fromY, toX, toY);
			changeTurn();
		}
		else{
			reinitialize();
		}
		return true;
	}

	private void changeTurn()
	{
		if(turn == Color.white)
		{
			turn = Color.black;
		}
		else{
			turn = Color.white;
		}
	}
	
	@Override
	public GameResult generateResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removePlayer(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Return current state(chess board and current turn) of model in server side 
	 * 
	 * @return	current state of  model in server side
	 */
	@Override
	public String stateInfo() {
		
		String state = "";
		
		for (int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				if(chessBoard[i][j] == null)
				{
					state = state.concat("06");
				}
				else if(chessBoard[i][j].getColor() == Color.white && (chessBoard[i][j].getName().compareToIgnoreCase("Rook") == 0))
				{
					state = state.concat("00");
				}
				else if(chessBoard[i][j].getColor() == Color.white && (chessBoard[i][j].getName().compareToIgnoreCase("Knight") == 0))
				{
					state = state.concat("01");
				}
				else if(chessBoard[i][j].getColor() == Color.white && (chessBoard[i][j].getName().compareToIgnoreCase("Bishop") == 0))
				{
					state = state.concat("02");
				}
				else if(chessBoard[i][j].getColor() == Color.white && (chessBoard[i][j].getName().compareToIgnoreCase("Queen") == 0))
				{
					state = state.concat("03");
				}
				else if(chessBoard[i][j].getColor() == Color.white && (chessBoard[i][j].getName().compareToIgnoreCase("King") == 0))
				{
					state = state.concat("04");
				}
				else if(chessBoard[i][j].getColor() == Color.white && (chessBoard[i][j].getName().compareToIgnoreCase("Pawn") == 0))
				{
					state = state.concat("05");
				}
				else if(chessBoard[i][j].getColor() == Color.black && (chessBoard[i][j].getName().compareToIgnoreCase("Rook") == 0))
				{
					state = state.concat("07");
				}
				else if(chessBoard[i][j].getColor() == Color.black && (chessBoard[i][j].getName().compareToIgnoreCase("Knight") == 0))
				{
					state = state.concat("08");
				}
				else if(chessBoard[i][j].getColor() == Color.black && (chessBoard[i][j].getName().compareToIgnoreCase("Bishop") == 0))
				{
					state = state.concat("09");
				}
				else if(chessBoard[i][j].getColor() == Color.black && (chessBoard[i][j].getName().compareToIgnoreCase("Queen") == 0))
				{
					state = state.concat("10");
				}
				else if(chessBoard[i][j].getColor() == Color.black && (chessBoard[i][j].getName().compareToIgnoreCase("King") == 0))
				{
					state = state.concat("11");
				}
				else if(chessBoard[i][j].getColor() == Color.black && (chessBoard[i][j].getName().compareToIgnoreCase("Pawn") == 0))
				{
					state = state.concat("12");
				}
				
			}
		}
		if(turn == Color.white)
		{
			state = state.concat("#W");
		}
		else
		{
			state = state.concat("#B");
		}
		return state;
	}
	
	@Override
	public Player selectPlayer(String playerInfo)
	{
		Iterator<Player> iter = this.playerList.iterator();
		ChessPlayer player;
		
		if(playerInfo.equals("white"))
		{
			while(iter.hasNext())
			{
				player = (ChessPlayer)iter.next();
				
				if(player.getColor() == Color.white)
					return player;
			}
			
			player = new ChessPlayer();
			player.setColor(Color.white);
			return player;
		}
		else if(playerInfo.equals("black"))
		{
			while(iter.hasNext())
			{
				player = (ChessPlayer)iter.next();
				
				if(player.getColor() == Color.black)
					return player;
			}
			
			player = new ChessPlayer();
			player.setColor(Color.black);
			return player;
		}
		else // invalid player
		{
			return null;
		}
	}

	@Override
	public String playerInfo(Player player) 
	{
		ChessPlayer chessPlayer = (ChessPlayer) player;
		if(chessPlayer.getColor() == Color.white)
		{
			return "White";
		}
		else
		{
			return "Black";
		}
		

	}

}
