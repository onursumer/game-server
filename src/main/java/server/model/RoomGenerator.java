package server.model;

/**
 * RoomGenerator Class.
 * This class contains static methods for automated room generation. 
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class RoomGenerator
{
	// current room no
	private static Integer roomNo = 0;
	
	/**
	 * Generates next room number.
	 * 
	 * @return	generated room number
	 */
	public static GameRoom generate()
	{
		roomNo++;
		GameRoom room = new GameRoom();
		room.setRoomNo(roomNo);
		
		return room;
	}
}
