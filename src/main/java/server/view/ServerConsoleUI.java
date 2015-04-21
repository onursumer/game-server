package server.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import server.comm.Command;
import view.UserInterface;
import annot.Producer;

/**
 * Console User Interface for the server.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class ServerConsoleUI extends Thread implements UserInterface
{
	// ServerConsoleUI as a singleton instance
	private static ServerConsoleUI instance = null;
	
	// input reader
	private BufferedReader console;
	
	/**
	 * Returns the singleton instance.
	 * 
	 * @return	singleton instance
	 */
	public static ServerConsoleUI getInstance()
	{
		if(instance == null)
		{
			instance = new ServerConsoleUI();
		}
		
		return instance;
	}
	
	/**
	 * Initializes the console by starting the console thread.
	 */
	public void init()
	{
		this.console = new BufferedReader(new InputStreamReader(System.in));
		this.start();
	}
	
	/**
	 * Thread method.
	 */
	public void run()
	{	
		String buffer;
		
		while(true)
		{
			System.out.print("> ");
			
			try {
				// try to read input line
				buffer = console.readLine();
				produceCommand(buffer);
			} catch (IOException e) {
				//TODO showError("Console I/O error: ", "Cannot read input!\n");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Shows the specified info message with its title on the console output.
	 */
	@Override
	public void showInfo(String infoTitle, String infoMsg)
	{
	    System.out.println(infoTitle + ": " + infoMsg);
	}
	
	/**
	 * Shows the specified error message with its title on the console output.
	 */
	@Override
	public void showError(String errorTitle, String errorMsg)
	{
		System.out.println(errorTitle + ": " + errorMsg);
	}
	
	/**
	 * Produces command from the input string.
	 *  
	 * @param input		request string
	 * @return	Command object containing the input command.
	 */
	@Producer
	public Command produceCommand(String input)
	{
		return new Command(null, input);
	}
}
