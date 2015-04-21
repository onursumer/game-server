package action;

/**
 * Abstract Action Class, that represents the client and server actions. 
 * 
 * @author Selcuk Onur Sumer
 *
 */
public abstract class Action
{
	public static final String LOGIN = "LOGIN";
	
	public static final String EXIT = "EXIT";
	public static final String HELP = "HELP";
	public static final String ABOUT = "ABOUT";
	public static final String SAVE_SETTINGS = "SAVE_SETTINGS";
	public static final String SELECT_GAME = "SELECT_GAME";
	public static final String EXIT_HALL = "EXIT_HALL";
	public static final String CREATE_ROOM = "CREATE_ROOM";
	public static final String JOIN_ROOM = "JOIN_ROOM";
	public static final String EXIT_ROOM = "EXIT_ROOM";
	public static final String START_GAME = "START_GAME";
	public static final String SELECT_PLAYER = "SELECT_PLAYER";
	
	public static final String ADD_GAME = "ADD_GAME"; 
	public static final String ADD_ROOM = "ADD_ROOM";
	public static final String REMOVE_ROOM = "REMOVE_ROOM";
	public static final String ADD_HALL_USER = "ADD_HALL_USER";
	public static final String REMOVE_HALL_USER = "REMOVE_HALL_USER";
	public static final String ADD_ROOM_USER = "ADD_ROOM_USER";
	public static final String REMOVE_ROOM_USER = "REMOVE_ROOM_USER";
	public static final String MOVE_TO_GAME_SELECT = "MOVE_TO_GAME_SELECT";
	public static final String MOVE_TO_GAME_HALL = "MOVE_TO_GAME_HALL";
	public static final String MOVE_TO_GAME_ROOM = "MOVE_TO_GAME_ROOM";
	
	public static final String CHAT = "CHAT";
	public static final String CHAT_MSG = "CHAT_MSG";
	
	public static final String INIT_GAME = "INIT_GAME";
	public static final String SET_GAME_STATE = "SET_GAME_STATE";
	public static final String SET_PLAYER_STATE = "SET_PLAYER_STATE";
	public static final String GAME_ACTION = "GAME_ACTION";
	public static final String GAME_ACTION_RESULT = "GAME_ACTION_RESULT";
	
	public static final String ERROR = "ERROR";
}
