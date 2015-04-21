package server.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.NoSuchElementException;

import controller.Controller;
import controller.Settings;

import action.Action;
import action.ActionHandler;
import action.ActionInfo;

import server.comm.Client;
import server.comm.Command;
import server.model.ServerRealm;
import server.view.ServerConsoleUI;

/**
 * Controller Class of the server application.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class ServerController extends Controller
{
	// ServerController as a singleton instance
	private static ServerController instance = new ServerController();
	
	// Server Console
	private ServerConsoleUI console = ServerConsoleUI.getInstance();
	
	// username-password map
	public HashMap<String,String> hashMap;
	
	private Communicator communicator;
	private Distributor distributor;
	private ServerRealm realm;
	private Settings settings;
	
	/**
	 * Constructs a ServerController object by initializing the communicator,
	 * the distributor, the realm, the action map, and the password map.
	 */
	private ServerController()
	{
		super();
		this.distributor = new Distributor();
		this.realm = new ServerRealm();
		this.settings = new Settings();
		this.settings.loadSettings();
		this.communicator = new Communicator(settings.getPortNo());
		
		init();
	}
	
	/**
	 * Returns the singleton instance.
	 * 
	 * @return	singleton instance
	 */
	public static ServerController getInstance()
	{	
		return instance;
	}
	
	// TODO init
	public boolean init()
	{
		this.insertTempPass();
		this.initActionMap();
		realm.constructGameList();
		realm.constructHallList();
		
		return true;
	}

	
	/**
	 * Processes the given command and finds the corresponding action class 
	 * and action method for that command. Corresponding action method of
	 * the corresponding action class is invoked and if any result is
	 * produced by the action method, it is sent to the client of the command.
	 * 
	 * @param command		command with its command string and client
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
		Client client = command.getClient();
		Action action;
		
		if(handler != null)
		{
			action = (Action)(handler.getActionClass().newInstance());
			actionResult = (String)(handler.getActionMethod().
					invoke(action, client, handler.getActionParam()));
		}
		
		try {
			if(actionResult != null)
			{
				if(client != null)
				{
					client.send(actionResult);
					
					// connection will be closed due to login error
					if(client.getUserAccount() == null)
						client.getSocket().close();	
				}	
				else
					; // TODO console: show info
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// TODO database passwords
	private void insertTempPass()
	{
		hashMap = new HashMap<String, String>();
		hashMap.put("admin", "admin");
		hashMap.put("kingdomdark", "dark");
		hashMap.put("alper", "alper");
		hashMap.put("onur", "onur");
		hashMap.put("cumo", "cumo");
	}

	private void initActionMap()
	{
		Class<?>[] paramTypes = new Class<?>[2];
		
		paramTypes[0] = Client.class;
		paramTypes[1] = HashMap.class;
		
		// TODO xml file action map construction
		this.actionMap = new HashMap<String, ActionInfo>();
		this.actionMap.put("LOGIN", new ActionInfo("server.action.LoginAction", "login", paramTypes));
		
		this.actionMap.put("SELECT_GAME", new ActionInfo("server.action.RealmAction", "selectGame", paramTypes));
		this.actionMap.put("EXIT_HALL", new ActionInfo("server.action.RealmAction", "exitHall", paramTypes));
		this.actionMap.put("CREATE_ROOM", new ActionInfo("server.action.RealmAction", "createRoom", paramTypes));
		this.actionMap.put("JOIN_ROOM", new ActionInfo("server.action.RealmAction", "joinRoom", paramTypes));
		this.actionMap.put("EXIT_ROOM", new ActionInfo("server.action.RealmAction", "exitRoom", paramTypes));
		
		this.actionMap.put("GAME_ACTION", new ActionInfo("server.action.GameAction", "gameAction", paramTypes));
		this.actionMap.put("START_GAME", new ActionInfo("server.action.GameAction", "startGame", paramTypes));
		this.actionMap.put("SELECT_PLAYER", new ActionInfo("server.action.GameAction", "selectPlayer", paramTypes));
		
		this.actionMap.put("CHAT", new ActionInfo("server.action.ChatAction", "chat", paramTypes));
		
		this.actionMap.put("START_SERVER", new ActionInfo("server.action.ServerAction", "startServer", paramTypes));
		this.actionMap.put("STOP_SERVER", new ActionInfo("server.action.ServerAction", "stopServer", paramTypes));
		this.actionMap.put("EXIT", new ActionInfo("server.action.ServerAction", "exit", paramTypes));
	}
	
	/*
	 * GETTERS and SETTERS
	 */
	
	public Communicator getCommunicator()
	{
		return communicator;
	}

	public Distributor getDistributor()
	{
		return distributor;
	}

	public ServerRealm getRealm()
	{
		return realm;
	}
	
	public ServerConsoleUI getConsole()
	{
		return console;
	}

	public Settings getSettings()
	{
		return settings;
	}
}
