package client.controller;

import client.model.GameHall;
import client.model.GameRoom;
import client.view.ContentPanel;
import client.view.GameRoomPanel;
import client.view.RoomInfoPanel;

/**
 * Aspect that reflects the changes in the client context to the client GUI. 
 * 
 * @author Selcuk Onur Sumer
 *
 */
public aspect RenderingAspect
{
	private ClientController controller = ClientController.getInstance();
	
	// Member introduction to ContentPanel class
	private boolean ContentPanel.changed = false;
	
	/**
	 * Setter for change indicator of the ContentPanel class.
	 */
	public void ContentPanel.setChanged(boolean changed)
	{
		this.changed = changed;
	}
	
	/**
	 * Getter for change indicator of the ContentPanel class.
	 */
	public boolean ContentPanel.isChanged()
	{
		return changed;
	}
	
	/**
	 * Pointcut for the panel switching method call.
	 */
	pointcut switchToPanel (ContentPanel panel) :
		call (* switchTo(ContentPanel)) &&
		args(panel);
	
	/**
	 * Advice for the switchToPanel pointcut.
	 * 
	 * @param panel		panel to switch
	 */
	after (ContentPanel panel) : switchToPanel(panel)
	{	
		if(panel.isChanged())
		{
			panel.refreshPanel(); // refresh panel
			panel.setChanged(false); // reset change indicator
		}
	}
	
	
	// Point cut for the game hall set.
	pointcut resetHallPanel (GameHall hall) :
		call (* setGameHall(GameHall)) &&
		args(hall);
	
	/**
	 * Advises after the game hall of the client context is changed.
	 * 
	 * @param hall	game hall to be set
	 */
	after (GameHall hall) : resetHallPanel(hall)
	{
		if(hall == null)
		{
			controller.getGui().getGameHallPanel().resetPanel();
			controller.getGui().getGameHallPanel().setChanged(true);
		}
		else
		{
			controller.getGui().getGameHallPanel().
					updateList(hall.getUserList());
		}
		
	}
	
	// Pointcut for the game room set.
	pointcut resetRoomPanel (GameRoom room) :
		call (* setGameRoom(GameRoom)) &&
		args(room);
	
	/**
	 * Advises after the game room of the client context is changed.
	 * 
	 * @param room	game room to be set
	 */
	after (GameRoom room) : resetRoomPanel(room)
	{	
		if(room == null) // this client left the room, so reset room panel
		{
			controller.getGui().getGameRoomPanel().resetPanel();
			controller.getGui().getGameRoomPanel().setChanged(true);
		}
		else // this client entered to a new room
		{
			controller.getGui().getGameRoomPanel().
					setTitleText("Room " + room.getRoomNo());
			
			controller.getGui().getGameRoomPanel().update(room);
		}
	}
	
	// Pointcut for the game name set.
	pointcut gameNameChange (String gameName) :
		call (* setGameName(String)) &&
		args(gameName);
	
	/**
	 * Advises after a game name of the game hall is changed.
	 * 
	 * @param gameName	game name to be set
	 */
	after (String gameName) : gameNameChange(gameName)
	{	
		controller.getGui().getGameHallPanel().setTitleText(gameName);
	}
	
	// Pointcut for a addGame method of the client context
	pointcut newGame (String game) : 
		call(* addGame(String)) &&
		args(game);
	
	/**
	 * Advises after a new game added to the game list of the client context.
	 * 
	 * @param game	game to be added
	 */
	after (String game) : newGame (game)
	{
		// add new game button to the interface
		controller.getGui().getGameSelectPanel().addGameButton(game);
		controller.getGui().getGameSelectPanel().setChanged(true);
		
		// refresh panel if it is visible
		if(controller.getGui().getGameSelectPanel().isVisible())
			controller.getGui().getGameSelectPanel().refreshPanel();
	}
	
	// Pointcut for a user addition to or removal from the game hall.
	pointcut newHallUser (String username, GameHall hall) : 
		(call(* addUser(String)) || call(* removeUser(String))) &&
		args(username) &&
		target(hall);
	
	/**
	 * Advises after a new user added to the game hall
	 * 
	 * @param username	name of the user to be added
	 * @param hall		target game hall
	 */
	after (String username, GameHall hall) : 
		newHallUser (username, hall)
	{
		// add new username to the interface
		controller.getGui().getGameHallPanel().updateList(hall.getUserList());
	}

	// Pointcut for a room addition to the game hall.
	pointcut newRoom (GameRoom room, GameHall hall) :
		call(* addRoom(GameRoom)) &&
		args(room) &&
		target(hall);

	/**
	 * Advises after a new room added to the game hall
	 * 
	 * @param username	name of the user to be added
	 * @param hall		target game hall
	 */
	after (GameRoom room, GameHall hall) : 
		newRoom (room, hall)
	{
		// add new game room panel to the interface
		controller.getGui().getGameHallPanel().addRoomPanel(room);
		controller.getGui().getGameHallPanel().setChanged(true);
		
		// refresh game hall panel if it is visible
		if(controller.getGui().getGameHallPanel().isVisible())
			controller.getGui().getGameHallPanel().refreshPanel();
	}
	
	// Pointcut for a room removing from the game hall.
	pointcut closingRoom (Integer roomNo, GameHall hall) :
		call(* removeRoom(Integer)) &&
		args(roomNo) &&
		target(hall);
	
	/**
	 * Advises after a room is removed from the game hall
	 * 
	 * @param roomNo	number of room to be removed
	 * @param hall		target game hall
	 */
	after (Integer roomNo, GameHall hall) : 
		closingRoom (roomNo, hall)
	{
		// add new game room panel to the interface
		controller.getGui().getGameHallPanel().removeRoomPanel(roomNo);
		controller.getGui().getGameHallPanel().setChanged(true);
		
		// refresh game hall panel if it is visible
		if(controller.getGui().getGameHallPanel().isVisible())
			controller.getGui().getGameHallPanel().refreshPanel();
	}
	
	// pointcut for a user addition to or removal from a game room
	pointcut newRoomUser (String username, GameRoom room) : 
		(call(* addPlayer(String)) || call(* removePlayer(String))) &&
		args(username) &&
		target(room);
	
	/**
	 * Advises after a new user added to or removed from the game room
	 * 
	 * @param username	name of the user to be added
	 * @param room		target game room
	 */
	after (String username, GameRoom room) : 
		newRoomUser (username, room)
	{
		//update related room info panel of the game hall panel
		RoomInfoPanel panel = controller.getGui().
				getGameHallPanel().searchInfoPanel(room.getRoomNo());
		
		// update game hall panel
		if(panel != null)
		{
			panel.update(room);
			controller.getGui().getGameHallPanel().setChanged(true);
		}
		
		// refresh game hall panel if it is visible
		if(controller.getGui().getGameHallPanel().isVisible())
			panel.refreshPanel();
			
		// update game room panel if client is in that room
		if((controller.getContext().getGameRoom() != null) &&
				controller.getContext().getGameRoom().equals(room))
		{
			controller.getGui().getGameRoomPanel().update(room);
			controller.getGui().getGameRoomPanel().setChanged(true);
		}
		
		// refresh game room panel if it is visible
		if(controller.getGui().getGameRoomPanel().isVisible())
			controller.getGui().getGameRoomPanel().refreshPanel();
	}
	
	// pointcut for a content addition to the chat pannel
	pointcut newChatContent(GameRoomPanel panel) :
		call (* addChatContent(String, String)) &&
		target(panel);
	
	/**
	 * Advises after a new content added to the chat panel.
	 * 
	 * @param panel		target chat panel 
	 */
	after (GameRoomPanel panel) : newChatContent(panel)
	{
		// refresh chat panel after a new chat content added
		panel.getChatPanel().refreshPanel();
	}
}
