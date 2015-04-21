package client.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.NoSuchElementException;

import controller.Controller;
import controller.Settings;

import action.Action;
import action.ActionHandler;
import action.ActionInfo;

import client.comm.Command;
import client.model.ClientContext;
import client.view.ClientGUI;


/**
 * Controller Class of the client application.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class ClientController extends Controller
{
	// ClientController as a singleton object
	private static ClientController instance = new ClientController();
	
	// Client GUI
	private ClientGUI gui = ClientGUI.getInstance();
	
	private Communicator communicator;
	private ClientContext context;
	private Settings settings;
	
	/**
	 * Constructs a ClientController object by initializing the client context,
	 * the communicator, and the action map. Also loads initial settings. 
	 */
	private ClientController()
	{
		super();
		context = new ClientContext();
		settings = new Settings();
		settings.loadSettings();
		communicator = new Communicator(settings.getHost(),
				settings.getPortNo());
		
		initActionMap();
		
	}

	/**
	 * Returns the singleton instance.
	 * @return	singleton instance
	 */
	public static ClientController getInstance()
	{
		return instance;
	}	
	
	/**
	 * Processes the given command and finds the corresponding action class 
	 * and action method for that command. Corresponding action method of
	 * the corresponding action class is invoked and if any result is
	 * produced by the action method, it is sent to the server.
	 * 
	 * @param command		command with its command string.
	 * @throws InstantiationException		if a reflection error occurs
	 * @throws IllegalAccessException		if a reflection error occurs
	 * @throws IllegalArgumentException		if a reflection error occurs
	 * @throws InvocationTargetException	if a reflection error occurs
	 * @throws SecurityException			if a reflection error occurs
	 * @throws ClassNotFoundException		if a reflection error occurs
	 * @throws NoSuchMethodException		if a reflection error occurs
	 */
	public void processCommand(Command command) throws InstantiationException,
		IllegalAccessException,	IllegalArgumentException,
		InvocationTargetException, SecurityException,
		ClassNotFoundException, NoSuchMethodException,
		NoSuchElementException
	{
		ActionHandler handler = this.parse(command.getInput());
		String actionResult = null;
		Action action;
		
		if(handler != null)
		{
			action = (Action)(handler.getActionClass().newInstance());
			actionResult = (String)(handler.getActionMethod().
					invoke(action, handler.getActionParam()));
		}
		
		try {
			if(actionResult != null)
			{
				this.communicator.request(actionResult);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Initializes the action map.
	 */
	private void initActionMap()
	{
		Class<?> paramTypes[] = new Class<?>[1];
		paramTypes[0] = HashMap.class;
		
		// TODO Construct action map by parsing an XML file 
		
		// login actions
		this.actionMap = new HashMap<String, ActionInfo>();
		this.actionMap.put("LOGIN", new ActionInfo("client.action.LoginAction", "login", paramTypes));
		
		// menu actions
		this.actionMap.put("EXIT", new ActionInfo("client.action.MenuAction", "exit", paramTypes));
		this.actionMap.put("SAVE_SETTINGS", new ActionInfo("client.action.MenuAction", "saveSettings", paramTypes));
		this.actionMap.put("SELECT_GAME", new ActionInfo("client.action.MenuAction", "selectGame", paramTypes));
		this.actionMap.put("EXIT_HALL", new ActionInfo("client.action.MenuAction", "exitHall", paramTypes));
		this.actionMap.put("CREATE_ROOM", new ActionInfo("client.action.MenuAction", "createRoom", paramTypes));
		this.actionMap.put("JOIN_ROOM", new ActionInfo("client.action.MenuAction", "joinRoom", paramTypes));
		this.actionMap.put("EXIT_ROOM", new ActionInfo("client.action.MenuAction", "exitRoom", paramTypes));
		this.actionMap.put("START_GAME", new ActionInfo("client.action.MenuAction", "startGame", paramTypes));
		this.actionMap.put("SELECT_PLAYER", new ActionInfo("client.action.MenuAction", "selectPlayer", paramTypes));
		
		// realm actions
		this.actionMap.put("ADD_GAME", new ActionInfo("client.action.RealmAction", "addGame", paramTypes));
		this.actionMap.put("ADD_ROOM", new ActionInfo("client.action.RealmAction", "addRoom", paramTypes));
		this.actionMap.put("REMOVE_ROOM", new ActionInfo("client.action.RealmAction", "removeRoom", paramTypes));
		this.actionMap.put("ADD_HALL_USER", new ActionInfo("client.action.RealmAction", "addHallUser", paramTypes));
		this.actionMap.put("REMOVE_HALL_USER", new ActionInfo("client.action.RealmAction", "removeHallUser", paramTypes));
		this.actionMap.put("ADD_ROOM_USER", new ActionInfo("client.action.RealmAction", "addRoomUser", paramTypes));
		this.actionMap.put("REMOVE_ROOM_USER", new ActionInfo("client.action.RealmAction", "removeRoomUser", paramTypes));
		this.actionMap.put("MOVE_TO_GAME_SELECT", new ActionInfo("client.action.RealmAction", "moveToSelect", paramTypes));
		this.actionMap.put("MOVE_TO_GAME_HALL", new ActionInfo("client.action.RealmAction", "moveToHall", paramTypes));
		this.actionMap.put("MOVE_TO_GAME_ROOM", new ActionInfo("client.action.RealmAction", "moveToRoom", paramTypes));
		
		// chat actions
		this.actionMap.put("CHAT", new ActionInfo("client.action.ChatAction", "sendChatMsg", paramTypes));
		this.actionMap.put("CHAT_MSG", new ActionInfo("client.action.ChatAction", "receiveChatMsg", paramTypes));
		
		// game action
		this.actionMap.put("INIT_GAME", new ActionInfo("client.action.GameAction", "initGame", paramTypes));
		this.actionMap.put("SET_GAME_STATE", new ActionInfo("client.action.GameAction", "setState", paramTypes));
		this.actionMap.put("SET_PLAYER_STATE", new ActionInfo("client.action.GameAction", "setPlayer", paramTypes));
		this.actionMap.put("GAME_ACTION", new ActionInfo("client.action.GameAction", "sendGameAction", paramTypes));
		this.actionMap.put("GAME_ACTION_RESULT", new ActionInfo("client.action.GameAction", "receiveGameAction", paramTypes));
		
		// error action
		this.actionMap.put("ERROR", new ActionInfo("client.action.ClientAction", "error", paramTypes));
	}
	
	/*
	 * GETTERS and SETTERS
	 */
	
	public ClientGUI getGui()
	{
		return gui;
	}

	public Communicator getCommunicator()
	{
		return communicator;
	}
	
	public ClientContext getContext()
	{
		return context;
	}
	
	public Settings getSettings()
	{
		return settings;
	}
}
